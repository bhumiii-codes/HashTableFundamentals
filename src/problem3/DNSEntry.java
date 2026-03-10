package problem3;

public class DNSEntry {

    private String domain;
    private String ipAddress;
    private long expiryTime;

    public DNSEntry(String domain, String ipAddress, long ttlSeconds) {
        this.domain = domain;
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + ttlSeconds * 1000;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}