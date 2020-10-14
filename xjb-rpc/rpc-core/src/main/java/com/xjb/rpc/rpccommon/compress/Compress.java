package com.xjb.rpc.rpccommon.compress;

import com.xjb.rpc.rpccommon.extension.SPI;

@SPI
public interface Compress {

    /**
     * 压缩
     * @param bytes
     * @return
     */
    byte[] compress(byte[] bytes);

    /**
     * 解压缩
     * @param bytes
     * @return
     */
    byte[] decompress(byte[] bytes);
}
