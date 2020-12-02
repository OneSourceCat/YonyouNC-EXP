package com.example;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.apache.commons.lang.ArrayUtils;
import org.asynchttpclient.*;

public class HTTPTest {
    public static void main(String[] args) throws Exception {
        String url = "https://www.baidu.com/";

       SslContext sslContext = SslContextBuilder
                .forClient()
                .sslProvider(SslProvider.JDK)
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
        builder.setSslContext(sslContext);

        AsyncHttpClientConfig asyncHttpClientConfig = builder.build();

        AsyncHttpClient c = new DefaultAsyncHttpClient(asyncHttpClientConfig);
        c.prepareGet(url)
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        System.out.println("Server Return Data: ");
                        System.out.println(response.getResponseBody());
                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        System.out.println("HTTP出现异常");
                        t.printStackTrace();
                        super.onThrowable(t);
                    }
                }).toCompletableFuture().join();
        c.close();
    }
}
