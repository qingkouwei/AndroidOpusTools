package com.wodekouwei.androidopustools;

public class ArrayUtil {
  public static short[] bytes2shorts(byte[] data, int readSize) {
    short[] leftData = new short[readSize / 2];
    for (int i = 0; i < readSize / 2; i = i + 1) {
      int v1 = data[i * 2] & 0xFF;
      int v2 = data[i * 2 + 1] & 0xFF;
      int temp = v1 + (v2 << 8);// 小端
      leftData[i] = (short) temp;
    }
    return leftData;
  }
}
