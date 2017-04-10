package com.skunkworks.serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * stole on 08.04.17.
 */
public class ManualMarshaller {

    public void serialize(final OutputStream bos, final BagOfPrimitives item) throws Exception {
        final DataOutputStream os = new DataOutputStream(bos);
        os.writeLong(item.getLongValue());
        os.writeInt(item.getIntValue());
        os.writeBoolean(item.isBooleanValue());
        //os.writeUTF(item.getNullValue());
        os.writeUTF(item.getStringValue());
    }

    public BagOfPrimitives deserialize(final InputStream bis) throws Exception {
        DataInputStream is = new DataInputStream(bis);
        BagOfPrimitives item = new BagOfPrimitives();
        item.setLongValue(is.readLong());
        item.setIntValue(is.readInt());
        item.setBooleanValue(is.readBoolean());
        //item.setNullValue(is.readUTF());
        item.setStringValue(is.readUTF());
        return item;
    }

    /////RADI
//    private final ByteArrayOutputStream bos = new ByteArrayOutputStream(10_000);
//    private final DataOutputStream os = new DataOutputStream(bos);

//    public byte[] serialize(final BagOfPrimitives item) throws Exception {
//        bos.reset();
//        os.writeLong(item.getLongValue());
//        os.writeInt(item.getIntValue());
//        os.writeBoolean(item.isBooleanValue());
//        //os.writeUTF(item.getNullValue());
//        os.writeUTF(item.getStringValue());
//        //os.flush();
//
//        return bos.toByteArray();
//    }
//
//    public BagOfPrimitives deserialize(final byte[] bytes) throws Exception {
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        DataInputStream is = new DataInputStream(bis);
//        BagOfPrimitives item = new BagOfPrimitives();
//        item.setLongValue(is.readLong());
//        item.setIntValue(is.readInt());
//        item.setBooleanValue(is.readBoolean());
//        //item.setNullValue(is.readUTF());
//        item.setStringValue(is.readUTF());
//        return item;
//    }

//    private final static byte TRUE = 1;
//    private final static byte FALSE = 0;
//    private final ByteBuffer serializeBuffer = ByteBuffer.allocate(10_000);
//    public byte[] serialize(final BagOfPrimitives item) throws Exception {
//        String s = "sdfsf";
//        s.substring(32);
//        serializeBuffer.rewind();
//        serializeBuffer.putLong(item.getLongValue());
//        serializeBuffer.putInt(item.getIntValue());
//        serializeBuffer.put(item.isBooleanValue() ? TRUE : FALSE);
//        serializeBuffer.put(item.getStringValue().getBytes());
//        return serializeBuffer.array();
//
//    }
//
//    public BagOfPrimitives deserialize(final byte[] bytes) throws Exception {
//        return null;
//    }
}
