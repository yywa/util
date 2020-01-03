package com.yyw.util.gid;

/**
 * Twitter的SnowFlake算法，用来生成全局唯一的ID（GID）。
 * snowflake ID (64位):
 * 0|00000000 00000000 00000000 00000000 00000000 0|00000000 00|00000000 0000
 * 说明：
 * 最高位保留，不用；
 * 第2部分的41位，表示以毫秒为单位的时间戳，可以使用69年；
 * 第3部分的10位，表示数据中心ID和工作节点ID，可按需调整数据中心ID和工作节点ID的位数；
 * 第4部分的12位，为一个不断增加的序列号。
 *
 * @author yyw
 * @date 2018/12/11 10:48
 */

public class GidGenerator extends AbstractGenerator {
    /**
     * 序列号12位
     */
    private static final long sequenceBits = 12L;

    /**
     * 机器节点左移位数
     */

    private static final long workerIdLeftShift = sequenceBits;
    /**
     * 数据中心节点左移位数
     */

    private static final long dcIdLeftShift = sequenceBits + workerIdBits;
    /**
     * 时间毫秒数左移位数
     */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + dcIdBits;

    /**
     * 最大支持数据中心节点数：2 的 dcIdBits次方
     */
    private static final long maxDcId = ~(-1L << dcIdBits);
    /**
     * 最大支持机器节点数：2 的 workerIdBits次方
     */
    private static final long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 2 的 sequenceBits次方
     */
    private static final long sequenceMask = ~(-1L << sequenceBits);

    /**
     * Thu, 04 Nov 2010 01:42:54 GMT
     */
    private static final long twepoch = 1288834974657L;

    /**
     * 数据中心ID
     */
    private long dcId;
    /**
     * 工作节点ID
     */
    private long workerId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    private static class Singleton {
        private static final GidGenerator GID_INSTANCE = new GidGenerator();
    }


    /**
     * 获取 GidGenerator单例
     *
     * @return
     */
    public static GidGenerator get() {
        return Singleton.GID_INSTANCE;
    }

    public GidGenerator() {
        this.dcId = 0L;
        this.workerId = 0L;
    }

    public synchronized long next() {
        // 获取当前毫秒数
        long curTimestamp = System.currentTimeMillis();

        // 如果服务器时间有问题(时钟后退)，报错
        if (lastTimestamp > curTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - curTimestamp));
        }

        // 如果上次生成时间和当前时间相同，在同一毫秒内
        if (lastTimestamp == curTimestamp) {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask按位与，去掉高位
            sequence = (sequence + 1) & sequenceMask;

            // 判断是否溢出，当为4096时，与sequenceMask按位与后，sequence等于0
            if (sequence == 0) {
                // 自旋等待到下一毫秒
                curTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同，重置sequence
            // 即从下一毫秒开始，sequence计数重新从0开始累加
            sequence = 0L;
        }

        lastTimestamp = curTimestamp;

        // 按规则生成snowflake ID
        return ((curTimestamp - twepoch) << timestampLeftShift) | (dcId << dcIdLeftShift)
                | (workerId << workerIdLeftShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }

        return timestamp;
    }

    public synchronized void setDcId(long dcId) {
        if (dcId > maxDcId || dcId < 0) {
            throw new IllegalArgumentException(String.format("ERROR: Datacenter ID should in [0, %d].", maxDcId));
        }

        this.dcId = dcId;
    }

    public synchronized void setWorkerId(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("ERROR: Worker ID should in [0, %d].", maxWorkerId));
        }

        this.workerId = workerId;
    }

    public static void main(String[] args) {
        System.out.println(GidGenerateUtil.gsn());
    }
}
