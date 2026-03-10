package problem3;

public class DNSCacheTest {

    public static void main(String[] args) throws InterruptedException {

        DNSCache cache = new DNSCache(5);

        System.out.println(cache.resolve("google.com"));

        System.out.println(cache.resolve("google.com"));

        Thread.sleep(6000);

        System.out.println(cache.resolve("google.com"));

        cache.printStats();
    }
}