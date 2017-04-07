package com.skunkworks.rpc.model;

import lombok.Data;

/**
 * stole on 06.04.17.
 */
@Data
public class EchoResponse {

    private String echoString;

    private int echoInt;

    private long echoLong;
}
