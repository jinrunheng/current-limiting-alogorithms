package sliding_window_algorithm;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Dooby Kim
 * @Date 2023/6/25 4:07 下午
 * @Version 1.0
 */
public class SlidingWindowRateLimiter {
    // 时间窗口总大小，单位毫秒
    private long window = 1000;
    // QPS 阈值，即在时间窗口内允许的最大请求数
    private int limit = 100;
    // 一个时间窗口包含多少个子窗口
    private int windowSize = 10;
    // 计数器
    private AtomicInteger counter = new AtomicInteger(0);
    //
    private Vector<Integer> subWindows = new Vector<>();

    public SlidingWindowRateLimiter() {
        subWindows.add(0);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(window / windowSize);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (SlidingWindowRateLimiter.class) {
                    subWindows.add(0);
                    if (subWindows.size() > windowSize) {
                        // 滑动一个格子
                        counter.set(counter.get() - subWindows.get(0));
                    }
                }
            }
        }).start();
    }

    public boolean tryAcquire() {
        synchronized (SlidingWindowRateLimiter.class) {
            if (counter.get() == limit) {
                return false;
            }
            int lastElement = subWindows.get(subWindows.size() - 1);
            subWindows.set(subWindows.size() - 1, lastElement + 1);
            counter.incrementAndGet();
            return true;
        }
    }
}
