package com.skunkworks.rpc.tool;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * stole on 06.04.17.
 */
public enum Tools {
    ;

    public static void warn(Messager messager, String message) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, message);
    }

    public static void error(Messager messager, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }
}
