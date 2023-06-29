package leaky_bucket_algorithm;

/**
 * @Author Dooby Kim
 * @Date 2023/6/28 3:11 下午
 * @Version 1.0
 * @Desc 漏桶算法
 */
public class LeakyBucketRateLimiter {
    private long capacity; // 漏桶容量
    private long rate; // 漏桶出水速度，单位为 per/ms
    private long left; // 当前桶中剩余的水量
    private long leakTime; // 用于保存上次漏水的时间

    public LeakyBucketRateLimiter(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.left = 0;
        this.leakTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire(long water) {
        long currentTime = System.currentTimeMillis();
        // 需要漏掉的水量
        long leak = (currentTime - leakTime) * rate;
        left = Math.max(0, left - leak);
        leakTime = currentTime;

        if (water + left <= capacity) {
            left += water;
            return true;
        }

        return false;
    }

}
