package com.skunkworks;

import com.google.gson.Gson;
import com.skunkworks.serialization.BagOfPrimitives;
import com.skunkworks.serialization.BagOfPrimitivesJsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * stole on 11.12.16.
 */
@SpringBootApplication
@Slf4j
public class SerializationApplication {
    //private static final int ITERATIONS = 100_000_000;
    private static final int ITERATIONS = 10_000_000;
//    private static final int ITERATIONS = 1_000;

    public static void main(String[] args) {
        SpringApplication.run(SerializationApplication.class, args);
    }

    @Bean
    public CommandLineRunner start() {
        return (args) -> {
            Gson gson = new Gson();
            BagOfPrimitives bag = new BagOfPrimitives(10L, 15, false, "banana");
            String serialized = gson.toJson(bag);
            log.info("serialized:" + serialized);

            final long start = System.nanoTime();
            for (int i = 1; i <= ITERATIONS; i++) {
                gson.toJson(bag);
            }
            final long end = System.nanoTime();
            log.info("Gson serialization delta:" + (end - start) / 1_000_000_000D);

            log.info("Marshall serialized:" + BagOfPrimitivesJsonSerializer.toJson(bag));

            final long startMarshall = System.nanoTime();
            for (int i = 1; i <= ITERATIONS; i++) {
                BagOfPrimitivesJsonSerializer.toJson(bag);
            }
            final long endMarshall = System.nanoTime();
            log.info("Marshall serialization delta:" + (endMarshall - startMarshall) / 1_000_000_000D);
            log.info("equals:" + (gson.toJson(bag).equals(BagOfPrimitivesJsonSerializer.toJson(bag))));

            final long startDeserialize = System.nanoTime();
            for (int i = 1; i <= ITERATIONS; i++) {
                gson.fromJson(serialized, BagOfPrimitives.class);
            }
            final long endDeserialize = System.nanoTime();
            log.info("Gson deserialization delta:" + (endDeserialize - startDeserialize) / 1_000_000_000D);
        };
    }
}
