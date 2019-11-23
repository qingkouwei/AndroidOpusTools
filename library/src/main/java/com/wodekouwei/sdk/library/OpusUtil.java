package com.wodekouwei.sdk.library;

public class OpusUtil {
  static {
    System.loadLibrary("opusutil-lib");
  }
  public static native long _createOpusEncoder(int sampleRateInHz, int channel, int bitrate,
      int complexity);

  public static native int _encodeOpus(long enc, short[] buffer, int offset, byte[] encoded);

  public static native void _destroyOpusEncoder(long enc);
}
