package com.skunkworks.rpc.client;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.japi.function.Procedure;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;

/**
 * stole on 08.04.17.
 */
@Slf4j
public class App {

    public static void main(String[] args) throws Exception {
        // boot up server using the route as defined below
        final ActorSystem system = ActorSystem.create("routes");
        final Materializer materializer = ActorMaterializer.create(system);

        Http http = Http.get(system);

        Sink<Integer, CompletionStage<Done>> sink = Sink.foreach((Procedure<Integer>) i -> {
            final CompletionStage<HttpResponse> responseFuture =
                    http.singleRequest(HttpRequest.create("http://localhost:8080/item/" + i), materializer);
            responseFuture.handle((httpResponse, throwable) -> {
                log.info("HttpResponse:" + httpResponse.entity().toString());
                return NotUsed.getInstance();
            });
        });

        Source.from(() -> new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < 1000;
            }

            @Override
            public Integer next() {
                return index++;
            }
        }).to(sink).run(materializer);
                //runForeach((i) -> connect(i, http, materializer), materializer).; // to(sink).run(materializer);
//        for (int i = 0; i < 1000; i++) {
//            final CompletionStage<HttpResponse> responseFuture =
//                    http.singleRequest(HttpRequest.create("http://localhost:8080/item/" + i), materializer);
//            responseFuture.handleAsync((httpResponse, throwable) -> {
//                log.info("HttpResponse:" + httpResponse.entity().toString());
//                return null;
//            }).thenAccept(unbound -> {});
//        }

        //System.in.read(); // let it run until user presses return
        //system.awaitTermination();
        //system.terminate();
    }

    private static void connect(Integer i, Http http, Materializer materializer) {
        final CompletionStage<HttpResponse> responseFuture =
                http.singleRequest(HttpRequest.create("http://localhost:8080/item/" + i), materializer);
        responseFuture.handle((httpResponse, throwable) -> {
            log.info("HttpResponse:" + httpResponse.entity().toString());
            return Done.getInstance();
        });
    }
}
