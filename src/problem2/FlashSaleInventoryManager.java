package problem2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FlashSaleInventoryManager {

    // productId -> stock count
    private final Map<String, AtomicInteger> inventory;

    // productId -> waiting list (FIFO)
    private final Map<String, Queue<Long>> waitingList;

    public FlashSaleInventoryManager() {
        inventory = new ConcurrentHashMap<>();
        waitingList = new ConcurrentHashMap<>();
    }

    // Add product with stock
    public void addProduct(String productId, int stock) {
        inventory.put(productId, new AtomicInteger(stock));
        waitingList.put(productId, new ConcurrentLinkedQueue<>());
    }

    // O(1) stock check
    public int checkStock(String productId) {
        AtomicInteger stock = inventory.get(productId);
        return stock != null ? stock.get() : 0;
    }

    // Purchase request
    public String purchaseItem(String productId, long userId) {

        AtomicInteger stock = inventory.get(productId);

        if (stock == null) {
            return "Product not found";
        }

        while (true) {
            int currentStock = stock.get();

            if (currentStock <= 0) {
                waitingList.get(productId).add(userId);
                int position = waitingList.get(productId).size();
                return "Out of stock. Added to waiting list. Position #" + position;
            }

            // Atomic compare and set
            if (stock.compareAndSet(currentStock, currentStock - 1)) {
                return "Success! Remaining stock: " + (currentStock - 1);
            }
        }
    }

    public int getWaitingListSize(String productId) {
        return waitingList.get(productId).size();
    }
}