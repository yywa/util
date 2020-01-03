package com.yyw.util.gid;

/**
 * 序列号生成器的抽象基类
 *
 * @author yyw
 * @date 2018/12/11 10:48
 */
abstract class AbstractGenerator {

    /***** 数据中心ID和节点ID共占10位，共支持部署1024个节点 *****/
    /**
     * 数据中心ID位数
     */
    static final long dcIdBits = 1L;
    /**
     * 工作节点ID位数
     */
    static final long workerIdBits = 10L - dcIdBits;

}
