package com.skunkworks.rpc.template;

/**
 * stole on 06.04.17.
 */
public class FieldData {
    private final String name;
    private final String dataType;
    private final String getter;
    private final String setter;
    private final boolean nullable;
    private final String deserializer;
    private final String hash;

    public FieldData(
            String name,
            String dataType, String getter,
            String setter,
            boolean nullable,
            String deserializer,
            String hashCode
    ) {
        this.name = name;
        this.dataType = dataType;
        this.getter = getter;
        this.setter = setter;
        this.nullable = nullable;
        this.deserializer = deserializer;
        this.hash = hashCode;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public String getGetter() {
        return getter;
    }

    public String getSetter() {
        return setter;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getDeserializer() {
        return deserializer;
    }

    public String getHash() {
        return hash;
    }
}
