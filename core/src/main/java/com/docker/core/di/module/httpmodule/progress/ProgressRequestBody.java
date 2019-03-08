package com.docker.core.di.module.httpmodule.progress;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {

    private final RequestBody requestBody;
    private  BufferedSink bufferedSink;
    private final ProgressListener listener;

    public ProgressRequestBody(RequestBody requestBody,ProgressListener listener){
       this.requestBody = requestBody;
       this.listener = listener;
    }

    public ProgressRequestBody(File file,ProgressListener listener){
        this.requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        this.listener = listener;
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    private Sink sink(Sink sink){
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                listener.onProgress(bytesWritten,contentLength,contentLength==bytesWritten);
            }
        };
    }
}
