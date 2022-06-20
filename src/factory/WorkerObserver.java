package factory;


import java.util.Observable;


/**
 * work states :
 * 0 = sleeping
 * 1 = waiting for details
 * 2 = working
 */
public class WorkerObserver extends Observable {
    private Integer workState;

    WorkerObserver() {
        workState = 0;
    }

    /**
     * work states :
     * 0 = sleeping
     * 1 = waiting for details
     * 2 = working
     */
    public void changeWorkState(Integer newWorkState) {
        workState = newWorkState;
        setChanged();
        notifyObservers(Thread.currentThread().getName());
    }

    public Integer getWorkState() {
        return workState;
    }

    public void setObserver(Observer observer) {
        this.addObserver(observer);
    }
}
