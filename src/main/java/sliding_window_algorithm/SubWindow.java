package sliding_window_algorithm;

/**
 * @Author Dooby Kim
 * @Date 2023/6/26 6:38 下午
 * @Version 1.0
 * @Desc 子窗口
 */
public class SubWindow {
    // 子窗口的开始时间，单位毫秒
    private long startTime;
    // 落在该子窗口的请求数量
    private int count;

    public SubWindow(long startTime, int count) {
        this.startTime = startTime;
        this.count = count;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
