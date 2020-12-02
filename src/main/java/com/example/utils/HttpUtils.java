package com.example.utils;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.asynchttpclient.*;

public class HttpUtils {
    private AsyncHttpClient getClient() {
        AsyncHttpClient c ;

        try{
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .sslProvider(SslProvider.JDK)
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
            builder.setSslContext(sslContext);

            AsyncHttpClientConfig asyncHttpClientConfig = builder.build();

            c = new DefaultAsyncHttpClient(asyncHttpClientConfig);
            return c;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            c = new DefaultAsyncHttpClient();
        }

        return c;
    }

    public Response get(String url, HttpHeaders headers) {
        Response ret = null;
        AsyncHttpClient c = getClient();
        try {
            ret = c.prepareGet(url)
                    .setHeaders(headers)
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            c.close();
                            return response;
                        }

                        @Override
                        public void onThrowable(Throwable t) {
                            System.out.println("HTTP出现异常");
                            t.printStackTrace();
                            super.onThrowable(t);
                        }
                    }).toCompletableFuture().join();
        } catch (Exception e) {
          e.printStackTrace();
        }

        try {
            if (!c.isClosed()){
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Response post(String url, byte[] body, HttpHeaders headers) {
        Response ret = null;
        AsyncHttpClient c = getClient();
        try {
            ret = c.prepareGet(url)
                    .setBody(body)
                    .setHeaders(headers)
                    .execute(new AsyncCompletionHandler<Response>() {
                        @Override
                        public Response onCompleted(Response response) throws Exception {
                            c.close();
                            return response;
                        }

                        @Override
                        public void onThrowable(Throwable t) {
                            System.out.println("HTTP出现异常");
                            t.printStackTrace();
                            super.onThrowable(t);
                        }
                    }).toCompletableFuture().join();

        } catch (Exception e) {
            System.out.println("POST请求错误，执行失败！");
            e.printStackTrace();
        }

        try {
            if (!c.isClosed()){
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }


}
