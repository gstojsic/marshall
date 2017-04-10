package com.skunkworks.rpc.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * stole on 08.04.17.
 */
public class Item {

    final String name;
    final long id;

    @JsonCreator
    public Item(@JsonProperty("name") String name,
         @JsonProperty("id") long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
