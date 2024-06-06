package org.example.javaasg;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class OrderIdGenerator {
    private static int orderCount = 0;

    public OrderIdGenerator() {
        initializeOrderCount();
    }

    private void initializeOrderCount() {
        Path path = Paths.get(Directories.getOrderDB());
        try (Stream<String> lines = Files.lines(path)) {
            orderCount = lines
                .map(line -> line.split(";")[0])
                .map(orderId -> Integer.parseInt(orderId.substring(3)))
                .max(Integer::compare)
                .orElse(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNextOrderId() {
        return String.format("Ord%03d", ++orderCount);
    }
}