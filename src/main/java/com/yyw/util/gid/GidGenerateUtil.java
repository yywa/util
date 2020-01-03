package com.yyw.util.gid;

/**
 * @author laiweigeng
 * @date 2018/12/11 10:48
 */
public class GidGenerateUtil {

    /**
     * 生成一个全局统一的GID
     *
     * @return 生成的GID
     */
    public static Long gid() {
        return GidGenerator.get().next();
    }

    /**
     * 生成一个全局统一的流水号
     *
     * @return 生成的GSN
     */
    public static String gsn() {
        return String.valueOf(GsnGenerator.get().next());
    }
}
