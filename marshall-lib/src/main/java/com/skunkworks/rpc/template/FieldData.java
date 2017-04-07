package com.skunkworks.rpc.template;

/**
 * stole on 06.04.17.
 */
public class FieldData {
    private final String name;
    private final String getter;
    private final String setter;

    public FieldData(String name, String getter, String setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    public String getName() {
        return name;
    }

    public String getGetter() {
        return getter;
    }

    public String getSetter() {
        return setter;
    }
}
