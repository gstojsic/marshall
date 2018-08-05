package com.skunkworks.serialization;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiConsumer;

/**
 * stole on 08.04.17.
 */
public class ManualMarshaller {
    private static final int LONG_VALUE = "longValue".hashCode();
    private static final int INT_VALUE = "intValue".hashCode();
    private static final int BOOLEAN_VALUE = "booleanValue".hashCode();
    private static final int NULL_VALUE = "nullValue".hashCode();
    private static final int STRING_VALUE = "stringValue".hashCode();
    private static final int THIS_VALUE = "this".hashCode();

//    private static final Map<Integer, BiConsumer<BagOfPrimitives, DataInputStream>> fieldDeserializersMap = new HashMap<Integer, BiConsumer<BagOfPrimitives, DataInputStream>>() {{
//        put("longValue".hashCode(), ManualMarshaller::readLongValue);
//        put("intValue".hashCode(), ManualMarshaller::readIntValue);
//        put("booleanValue".hashCode(), ManualMarshaller::readBooleanValue);
//        put("nullValue".hashCode(), ManualMarshaller::readNullValue);
//        put("stringValue".hashCode(), ManualMarshaller::readStringValue);
//    }};


    public static void serialize(final OutputStream bos, final BagOfPrimitives item) throws Exception {
        final DataOutputStream os = new DataOutputStream(bos);
        //os.writeUTF("longValue");
        os.writeInt(LONG_VALUE);
        os.writeLong(item.getLongValue());
        os.writeInt(INT_VALUE);
        os.writeInt(item.getIntValue());
        os.writeInt(BOOLEAN_VALUE);
        os.writeBoolean(item.isBooleanValue());
        //os.writeUTF(item.getNullValue());
        if (item.getNullValue() != null) {
            os.writeInt(NULL_VALUE);
            os.writeUTF(item.getNullValue());
        }
        if (item.getStringValue() != null) {
            os.writeInt(STRING_VALUE);
            os.writeUTF(item.getStringValue());
        }
        os.writeInt(THIS_VALUE);
    }

    public static BagOfPrimitives deserialize(final InputStream bis) throws Exception {
        DataInputStream is = new DataInputStream(bis);
        BagOfPrimitives item = new BagOfPrimitives();
        int fieldHash;
        while ((fieldHash = is.readInt()) != THIS_VALUE) {
            getDeserializer(fieldHash).accept(item, is);
            //fieldDeserializersMap.getOrDefault(fieldHash, (e, dataInputStream) -> {}).accept(item, is);


//            BiConsumer<BagOfPrimitives, DataInputStream> reader = fieldDeserializersMap.get(fieldHash);
//            if (reader != null)
//                reader.accept(item, is);
        }
//        item.setLongValue(is.readLong());
//        item.setIntValue(is.readInt());
//        item.setBooleanValue(is.readBoolean());
//        //item.setNullValue(is.readUTF());
//        item.setStringValue(is.readUTF());
        return item;
    }

    private static BiConsumer<BagOfPrimitives, DataInputStream> getDeserializer(int fieldHash) {
        //TODO switch
        if (fieldHash == LONG_VALUE)
            return ManualMarshaller::readLongValue;
        else if (fieldHash == INT_VALUE)
            return ManualMarshaller::readIntValue;
        else if (fieldHash == BOOLEAN_VALUE)
            return ManualMarshaller::readBooleanValue;
        else if (fieldHash == NULL_VALUE)
            return ManualMarshaller::readNullValue;
        else if (fieldHash == STRING_VALUE)
            return ManualMarshaller::readStringValue;
        else
            return ManualMarshaller::readDefault;
    }

    private static void readDefault(BagOfPrimitives item, DataInputStream is) {
    }

    private static void readLongValue(BagOfPrimitives item, DataInputStream is) {
        try {
            item.setLongValue(is.readLong());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readIntValue(BagOfPrimitives item, DataInputStream is) {
        try {
            item.setIntValue(is.readInt());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readBooleanValue(BagOfPrimitives item, DataInputStream is) {
        try {
            item.setBooleanValue(is.readBoolean());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readNullValue(BagOfPrimitives item, DataInputStream is) {
        try {
            item.setNullValue(is.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readStringValue(BagOfPrimitives item, DataInputStream is) {
        try {
            item.setStringValue(is.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /////RADI Minimal
//    public void serialize(final OutputStream bos, final BagOfPrimitives item) throws Exception {
//        final DataOutputStream os = new DataOutputStream(bos);
//        os.writeLong(item.getLongValue());
//        os.writeInt(item.getIntValue());
//        os.writeBoolean(item.isBooleanValue());
//        //os.writeUTF(item.getNullValue());
//        os.writeUTF(item.getStringValue());
//    }
//
//    public BagOfPrimitives deserialize(final InputStream bis) throws Exception {
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
