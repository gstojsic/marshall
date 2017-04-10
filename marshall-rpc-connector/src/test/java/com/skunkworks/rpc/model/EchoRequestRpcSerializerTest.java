package com.skunkworks.rpc.model;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

/**
 * stole on 07.04.17.
 */
public class EchoRequestRpcSerializerTest {

    public static byte[] serialize(final EchoRequest item) throws Exception {
        try (MessageBufferPacker packer = MessagePack.newDefaultBufferPacker()) {
            packer.packString(item.getEchoString()).
                    packInt(item.getEchoInt()).
                    packLong(item.getEchoLong());
            return packer.toByteArray();
        }
    }

    public static EchoRequest deserialize(final byte[] bytes) throws Exception {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes)) {
            final EchoRequest item = new EchoRequest();
            item.setEchoString(unpacker.unpackString());
            item.setEchoInt(unpacker.unpackInt());
            item.setEchoLong(unpacker.unpackLong());
            return item;
        }
    }
}
