package com.skunkworks.rpc.akka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * stole on 08.04.17.
 */
public class Order {

    final List<Item> items;

    @JsonCreator
    public Order(@JsonProperty("items") List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}
