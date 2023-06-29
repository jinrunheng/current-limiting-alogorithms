package token_bucket_algorithm;

/**
 * @Author Dooby Kim
 * @Date 2023/6/29 5:54 下午
 * @Version 1.0
 * @Desc 令牌桶算法
 */
public class TokenBucketRateLimiter {
    // 用于保存上次令牌生成的时间
    private long timeStamp;
    // 桶中可以存放令牌的容量
    private long capacity;
    // 令牌生成的速度，单位为 per/ms
    private long rate;
    // 桶中令牌的数量
    private long tokens;

    public synchronized boolean tryAcquire() {
        // 生成令牌
        long currentTime = System.currentTimeMillis();
        tokens = Math.min(tokens + (currentTime - timeStamp) * rate, capacity);
        timeStamp = currentTime;
        if (tokens > 0) {
            tokens--;
            return true;
        } else {
            return false;
        }
    }
}
