package com.skunkworks.rpc;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Generates the client side used to invoke the service.
 *
 * stole on 02.04.17.
 */
class RpcClientGenerator {
    private final ProcessingEnvironment processingEnv;

    RpcClientGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    void generate(Element annotatedElement) {
    }
}
