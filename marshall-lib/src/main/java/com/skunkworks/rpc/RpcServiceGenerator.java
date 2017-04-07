package com.skunkworks.rpc;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Generates the rpc service stub. Use it to implement the server side of the service.
 * stole on 02.04.17.
 */
class RpcServiceGenerator {
    private final ProcessingEnvironment processingEnv;

    RpcServiceGenerator(ProcessingEnvironment processingEnv) {

        this.processingEnv = processingEnv;
    }

    void generate(Element annotatedElement) {

    }
}
