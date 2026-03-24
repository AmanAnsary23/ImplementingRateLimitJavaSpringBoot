package rateLimit.limit;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LimiterService {
    private final Map<String , long[]> requestCount = new ConcurrentHashMap<>();

    private static final int MAX_REQUEST = 5;
    private static final long WINDOW_MS = 60_000;

    public boolean isAllowed(String clientIp) {
        long currentTime = System.currentTimeMillis();

        requestCount.putIfAbsent(clientIp, new long[]{0, currentTime});

        long[] date = requestCount.get(clientIp);
        long count = date[0];
        long windowStart = date[1];

        //Window expired? Reset the counter
        if (currentTime - windowStart > WINDOW_MS) {
            date[0] = 1;
            date[1] = currentTime;
            return true;
        }

        if (count < MAX_REQUEST) {
            date[0]++;
            return true;
        }

        return false; // limit exceeded
    }
}
