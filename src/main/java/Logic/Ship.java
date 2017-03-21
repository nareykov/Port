package Logic;

/**
 * Created by narey on 18.03.2017.
 */
public class Ship {
    private static int ID = 0;

    private int id = ID;
    private long duration = 0;
    private int loading = 0;
    private int unloading = 0;
    private int priority = 0;

    public Ship(long duration, int loading, int unloading, int priority) {
        this.duration = duration;
        this.loading = loading;
        this.unloading = unloading;
        this.priority = priority;
        ID++;
    }

    public Ship(int id, int duration, int loading, int unloading, int priority) {
        this.id = id;
        this.duration = duration;
        this.loading = loading;
        this.unloading = unloading;
        this.priority = priority;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getLoading() {
        return loading;
    }

    public void setLoading(int loading) {
        this.loading = loading;
    }

    public int getUnloading() {
        return unloading;
    }

    public void setUnloading(int unloading) {
        this.unloading = unloading;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
