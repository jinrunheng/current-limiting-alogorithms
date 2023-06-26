package sliding_window_algorithm;

/**
 * @Author Dooby Kim
 * @Date 2023/6/26 6:42 下午
 * @Version 1.0
 */
public class SlidingWindowRateLimiter2 {
    // 时间窗口总大小，单位毫秒
    private long window = 1000;
    // QPS 阈值，即在时间窗口内允许的最大请求数
    private int limit = 100;
    // 一个时间窗口包含多少个子窗口
    private int windowSize = 10;

    private SubWindow[] subWindows;

    public SlidingWindowRateLimiter2() {
        subWindows = new SubWindow[windowSize];
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        int count = getCount(currentTime);

        if (count + 1 > limit) {
            return false;
        }

        int windowIndex = (int) ((int) (currentTime % window) / (window / windowSize));
        if (subWindows[windowIndex] == null) {
            subWindows[windowIndex] = new SubWindow(currentTime, 1);
        } else {
            subWindows[windowIndex].setCount(subWindows[windowIndex].getCount() + 1);
        }
        return true;
    }

    private int getCount(long currentTime) {
        int count = 0;
        // 遍历数组，删除所有过期的窗口
        for (int i = 0; i < subWindows.length; i++) {
            if (subWindows[i] != null) {
                if (currentTime - subWindows[i].getStartTime() <= window) {
                    count += subWindows[i].getCount();
                } else {
                    // 说明窗口过期
                    subWindows[i] = null;
                }
            }
        }
        return count;
    }
}
