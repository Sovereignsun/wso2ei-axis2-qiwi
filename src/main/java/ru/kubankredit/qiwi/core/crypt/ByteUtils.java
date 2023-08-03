package ru.kubankredit.qiwi.core.crypt;

import java.nio.ByteBuffer;

public class ByteUtils {

    public ByteUtils() {
    }

    public byte[] concat(byte[] array1, byte[] array2) {
        return ByteBuffer.allocate(array1.length + array2.length)
                .put(array1)
                .put(array2)
                .array();
    }

    public byte[] subArray(byte[] byteArray, int beginIndex, int length) {
        byte[] subArray = new byte[length];
        System.arraycopy(byteArray, beginIndex, subArray, 0, subArray.length);
        return subArray;
    }

    public int byteLength(int bitLength) {
        return bitLength / 8;
    }
}
