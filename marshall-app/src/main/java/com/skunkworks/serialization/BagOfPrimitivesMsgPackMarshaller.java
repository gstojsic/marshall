package com.skunkworks.serialization;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.ArrayBufferInput;
import org.msgpack.core.buffer.MessageBuffer;
import org.msgpack.core.buffer.MessageBufferInput;

import java.nio.ByteBuffer;

/**
 * stole on 08.04.17.
 */
public class BagOfPrimitivesMsgPackMarshaller {
//    private final MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
//    private final MessageBuffer buffer = new MessageBuffer();
//    private final MessageUnpacker unpacker = MessagePack.newDefaultUnpacker();

    public static byte[] serialize(final BagOfPrimitives item) throws Exception {
//        packer.packLong(item.getLongValue()).
//                packInt(item.getIntValue()).
//                packBoolean(item.isBooleanValue()).
//                //packNil().
//                packString(item.getStringValue());
//        packer.flush();
//        return packer.toByteArray();

        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packLong(item.getLongValue()).
                    packInt(item.getIntValue()).
                    packBoolean(item.isBooleanValue()).
                    //packNil().
                    packString(item.getStringValue());
            return packer.toByteArray();
        }
    }

    public static BagOfPrimitives deserialize(final byte[] bytes) throws Exception {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes)) {
            final BagOfPrimitives item = new BagOfPrimitives();
            item.setLongValue(unpacker.unpackLong());
            item.setIntValue(unpacker.unpackInt());
            item.setBooleanValue(unpacker.unpackBoolean());
            //item.setNullValue(unpacker.unpackString());
            item.setStringValue(unpacker.unpackString());
            return item;
        }
    }
}
