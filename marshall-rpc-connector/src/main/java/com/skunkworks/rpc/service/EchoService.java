package com.skunkworks.rpc.service;

import com.skunkworks.rpc.annotation.Rpc;
import com.skunkworks.rpc.model.EchoRequest;
import com.skunkworks.rpc.model.EchoResponse;

/**
 * stole on 06.04.17.
 */
@Rpc
public interface EchoService {

    EchoResponse echo(EchoRequest request);

    //String echoString(String request);

    //void echoNothing(int bla, long bla2);
}
