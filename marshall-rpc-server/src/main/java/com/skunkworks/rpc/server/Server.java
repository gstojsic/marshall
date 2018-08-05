package com.skunkworks.rpc.server;

import akka.Done;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import com.skunkworks.rpc.akka.Item;
import com.skunkworks.rpc.akka.Order;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.PathMatchers.longSegment;

/**
 * stole on 08.04.17.
 */
class Server extends AllDirectives {

    Route createRoute() {
        return route(
                get(() ->
                        pathPrefix("item", () ->
                                path(longSegment(), (Long id) -> {
                                    //final CompletionStage<Optional<Item>> futureMaybeItem = fetchItem(id);
                                    return onSuccess(() -> fetchItem(id), maybeItem ->
                                            maybeItem.map(item -> completeOK(item, Jackson.marshaller()))
                                                    .orElseGet(() -> complete(StatusCodes.NOT_FOUND, "Not Found"))
                                    );
                                })
                        )
                ),
                get(() -> pathPrefix("events", () ->
                        pathEndOrSingleSlash(() -> onSuccess(this::getEvents, events -> complete(StatusCodes.OK, events))))),

                post(() ->
                        path("create-order", () ->
                                //extractDataBytes()
                                entity(Jackson.unmarshaller(Order.class), order -> {
                                    CompletionStage<Done> futureSaved = saveOrder(order);
                                    return onSuccess(() -> futureSaved, done ->
                                            complete("order created")
                                    );
                                })))
        );
    }


    // (fake) async database query api
    private CompletionStage<Optional<Item>> fetchItem(long itemId) {
        return CompletableFuture.completedFuture(Optional.of(new Item("foo", itemId)));
    }

    // (fake) async database query api
    private CompletionStage<Done> saveOrder(final Order order) {
        return CompletableFuture.completedFuture(Done.getInstance());
    }

    private CompletableFuture<String> getEvents() {
        return CompletableFuture.completedFuture("True blue");
    }
}
