package problem1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        UsernameAvailabilityChecker checker =
                new UsernameAvailabilityChecker();

        checker.addUser("john_doe", 1);
        checker.addUser("admin", 2);

        int totalRequests = 1000;

        ExecutorService executor =
                Executors.newFixedThreadPool(20);

        long startTime = System.nanoTime();

        for (int i = 0; i < totalRequests; i++) {

            int finalI = i;

            executor.submit(() -> {
                if (finalI % 2 == 0) {
                    checker.checkAvailability("john_doe");
                } else {
                    checker.checkAvailability("user" + finalI);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        long endTime = System.nanoTime();

        System.out.println("Total requests: " + totalRequests);
        System.out.println("Time taken (ms): "
                + (endTime - startTime) / 1_000_000);

        System.out.println("Most attempted username: "
                + checker.getMostAttempted());
    }
}