package ${packageName};

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class ${className}RpcSerializer {

    private static final Map<Integer, BiConsumer<${className}, DataInputStream>> fieldDeserializersMap = new HashMap<Integer, BiConsumer<${className}, DataInputStream>>() {{
        <#list fields as field>
        put(${field.hash}, ${className}RpcSerializer::${field.deserializer});
        </#list>
    }};

    public static void serialize(final OutputStream bos, final ${className} item) throws Exception {
        final DataOutputStream os = new DataOutputStream(bos);
    <#list fields as field>
        <#if field.nullable>
        if (item.${field.getter}() != null) {
            os.writeInt(${field.hashCode});
            os.write${field.dataType}(item.${field.getter}());
        }
        <#else>
        os.writeInt(${field.hash});
        os.write${field.dataType}(item.${field.getter}());
        </#if>
    </#list>
        os.writeInt(${endObjectHashCode});
    }

    public static ${className} deserialize(final InputStream bis) throws Exception {
        DataInputStream is = new DataInputStream(bis);
        ${className} item = new ${className}();
        int fieldHash;
        while ((fieldHash = is.readInt()) != ${endObjectHashCode}) {
            fieldDeserializersMap.getOrDefault(fieldHash, (e, dataInputStream) -> {}).accept(item, is);
        }
        return item;
    }
    <#list fields as field>

    private static void ${field.deserializer}(${className} item, DataInputStream is) {
        try {
            item.${field.setter}(is.read${field.dataType}());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    </#list>
}