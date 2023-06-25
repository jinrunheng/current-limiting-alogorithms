package fixed_window_algorithm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Dooby Kim
 * @Date 2023/6/25 11:43 上午
 * @Version 1.0
 * @Desc 固定窗口算法
 */
public class FixedWindowRateLimiter {

    // 时间窗口，单位毫秒
    private static long window = 1000;
    // QPS 阈值，即在时间窗口内允许的最大请求数
    private static int limit = 5;
    // 计数器
    private static AtomicInteger counter = new AtomicInteger();
    private static long startTime = System.currentTimeMillis();

    public synchronized static boolean tryAcquire() {
        // 获取系统当前时间
        long currentTime = System.currentTimeMillis();
        // 检查是否在时间窗口内
        if (currentTime - startTime > window) {
            // 计数器归 0
            counter.set(0);
            // 开启新的时间窗口
            startTime = System.currentTimeMillis();
        }
        // 如果计数器值小于等于阈值，则不对其限流
        return counter.incrementAndGet() <= limit;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            Thread.sleep(100);
            if (tryAcquire()) {
                System.out.println("请求通过");
            }else {
                System.out.println("限流");
            }
        }
    }

}
