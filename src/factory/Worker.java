package factory;

import threadpool.PoolWorker;

import java.util.LinkedList;
import java.util.Observable;

public class Worker extends PoolWorker {
    private LinkedList<CarTask> tasks;
    private Integer workSpeed;
    private WorkerObserver workerObserver;

    public Worker() {
        this.workSpeed = 15000;
        this.workerObserver = new WorkerObserver();
    }

    public Worker(LinkedList<CarTask> tasks) {
        this.tasks = tasks;
        this.workSpeed = 15000;
        this.workerObserver = new WorkerObserver();
    }

    public Worker(LinkedList<CarTask> tasks, int workSpeed) {
        this.tasks = tasks;
        this.workSpeed = workSpeed;
        this.workerObserver = new WorkerObserver();
    }

    public WorkerObserver getWorkerObserver() {
        return workerObserver;
    }

    public void setWorkerObserver(WorkerObserver workerObserver) {
        this.workerObserver = workerObserver;
    }

    @Override
    public void run() {
        CarTask currentTask = null;
        while (!this.isInterrupted()) {
            synchronized (tasks) {
                System.out.println("Worker from thread " + this.getName() + " looks for tasks");
                while (tasks.isEmpty()) {
                    System.out.println("Worker from thread " + this.getName()
                            + " sees that tasklist is empty and waits");
                    workerObserver.changeWorkState(0);
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println("Worker from thread " + this.getName() + " got a task");
                currentTask = tasks.removeFirst();
            }
            currentTask.assignWorker(this);
            System.out.println("Worker from thread " + this.getName() + " does a task");
            workerObserver.changeWorkState(1);
            currentTask.run();
            workerObserver.changeWorkState(0);
        }
    }

    public Integer getWorkSpeed() {
        return workSpeed;
    }
}
