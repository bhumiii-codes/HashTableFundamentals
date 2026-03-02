package problem2;

import java.util.concurrent.*;

public class FlashSaleTest {

    public static void main(String[] args) throws InterruptedException {

        FlashSaleInventoryManager manager =
                new FlashSaleInventoryManager();

        String product = "IPHONE15_256GB";

        manager.addProduct(product, 100);

        int totalUsers = 50000;

        ExecutorService executor =
                Executors.newFixedThreadPool(50);

        for (int i = 0; i < totalUsers; i++) {
            long userId = i;

            executor.submit(() -> {
                manager.purchaseItem(product, userId);
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final Stock: "
                + manager.checkStock(product));

        System.out.println("Waiting List Size: "
                + manager.getWaitingListSize(product));
    }
}