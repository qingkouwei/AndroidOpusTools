package com.wodekouwei.androidopustools;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import com.wodekouwei.sdk.library.OpusUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpusRecorderTask implements Runnable {
  private final static String TAG = "OpusRecorderTask";
  private final static int BUFFER_LENGTH = 80;
  private AudioRecord audioRecord;
  private boolean isRecorder = false;
  private byte[] audioBuffer;
  private String opusAudioOpusPath;
  private int bufferSize;
  private int channelConfig = AudioFormat.CHANNEL_IN_MONO;

  private int bytesPerTenMS = 0;
  private byte[] mRemainBuf = null;
  private int mRemainSize = 0;

  public OpusRecorderTask(String opusAudioOpusPath) {
    this.opusAudioOpusPath = opusAudioOpusPath;
    bufferSize = AudioRecord.getMinBufferSize(Constants.DEFAULT_AUDIO_SAMPLE_RATE,
        channelConfig, AudioFormat.ENCODING_PCM_16BIT) + 2048;
    audioBuffer = new byte[bufferSize];
    audioRecord =
        new AudioRecord(MediaRecorder.AudioSource.MIC, Constants.DEFAULT_AUDIO_SAMPLE_RATE,
            channelConfig, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    bytesPerTenMS =
        Constants.DEFAULT_AUDIO_SAMPLE_RATE * 2 * Constants.DEFAULT_OPUS_CHANNEL / 100 * 2;//每次处理20ms
    Log.i(TAG, "bytesPerTenMs:" + bytesPerTenMS);
    mRemainBuf = new byte[bytesPerTenMS];
    mRemainSize = 0;
  }

  public void stop() {
    isRecorder = false;
  }

  @Override
  public void run() {
    isRecorder = true;
    audioRecord.startRecording();
    File file = new File(opusAudioOpusPath);
    File fileDir = new File(file.getParent());
    if (!fileDir.exists()) {
      fileDir.mkdirs();
    }
    if (file.exists()) {
      file.delete();
    }
    long createEncoder = 0;
    FileOutputStream fileOutputStream = null;
    BufferedOutputStream fileOpusBufferedOutputStream = null;
    try {
      file.createNewFile();
      fileOutputStream = new FileOutputStream(file, true);

      fileOpusBufferedOutputStream = new BufferedOutputStream(fileOutputStream);

      createEncoder = OpusUtil._createOpusEncoder(Constants.DEFAULT_AUDIO_SAMPLE_RATE,
          Constants.DEFAULT_OPUS_CHANNEL, 16, 3);
      Log.i(TAG, "bufferSize:" + bufferSize);
      while (isRecorder) {
        int curShortSize = audioRecord.read(audioBuffer, 0, bufferSize);
        if (curShortSize > 0 && curShortSize <= bufferSize) {

          encodeData(createEncoder, fileOpusBufferedOutputStream, curShortSize);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      Log.e(TAG, "e = " + e.getMessage());
    } finally {
      OpusUtil._destroyOpusEncoder(createEncoder);
      audioRecord.stop();
      audioRecord.release();
      try {
        if(fileOpusBufferedOutputStream != null) {
          fileOpusBufferedOutputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if(fileOutputStream != null) {
          fileOutputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void encodeData(long createEncoder, BufferedOutputStream fileOpusBufferedOutputStream,
      int readSize) throws IOException {
    byte []data = audioBuffer;
    if (mRemainSize > 0) {
      byte totalBuf[] = new byte[readSize + mRemainSize];
      System.arraycopy(mRemainBuf, 0, totalBuf, 0, mRemainSize);
      System.arraycopy(data, 0, totalBuf, mRemainSize, readSize);
      data = totalBuf;
      readSize += mRemainSize;
      mRemainSize = 0;
    }
    int hasHandleSize = 0;
    while (hasHandleSize < readSize) {
      int readCount = bytesPerTenMS;
      if (bytesPerTenMS > readSize) {
        Log.i(TAG, "bytesPerTenMs > readSize");
        mRemainSize = readSize;
        System.arraycopy(data, 0, mRemainBuf, 0, readSize);
        return;
      }
      if ((readSize - hasHandleSize) < readCount) {
        mRemainSize = readSize - hasHandleSize;
        Log.d(TAG, "remain size :" + mRemainSize);
        System.arraycopy(data, hasHandleSize, mRemainBuf, 0, mRemainSize);
        return;
      }
      byte[] bytes = new byte[readCount];
      System.arraycopy(data, hasHandleSize, bytes, 0, readCount);
      short[] leftData = ArrayUtil.bytes2shorts(bytes, readCount);
      byte[] decodedData = new byte[readCount];
      int encodeSize = OpusUtil._encodeOpus(createEncoder, leftData, 0, decodedData);
      Log.d(TAG, "encodeSize = " + encodeSize);
      if (encodeSize > 0) {
        byte[] decodeArray = new byte[encodeSize];
        System.arraycopy(decodeArray, 0, decodedData, 0, encodeSize);
        fileOpusBufferedOutputStream.write(decodeArray);
      } else {
        return;
      }
      hasHandleSize += readCount;
    }
  }
}
