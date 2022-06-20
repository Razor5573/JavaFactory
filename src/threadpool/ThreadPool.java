package threadpool;

import java.util.ArrayList;
import java.util.LinkedList;

import factory.Observer;

public class ThreadPool<Worker extends PoolWorker, Task extends PoolTask> {
    private Integer threadsAmount;
    private LinkedList<Task> tasks;
    private ArrayList<Worker> threads;
    private Class<Worker> workerType;

    public ArrayList<Worker> getWorkers() {
        return threads;
    }

    public ThreadPool(int threadsAmount, LinkedList<Task> tasks, Class<Worker> workerType, int workingSpeed) {
        this.threadsAmount = threadsAmount;
        this.tasks = tasks;
        this.workerType = workerType;
        threads = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            try {
                threads.add(i, workerType.getDeclaredConstructor(tasks.getClass(), Integer.TYPE).newInstance(tasks, workingSpeed));
                threads.get(i).setName("Worker " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setObserver(Observer observer) {
        for (Worker worker : threads) {
            ((factory.Worker) worker).getWorkerObserver().setObserver(observer);
        }
    }

    public void poolStart() {
        for (Worker worker : threads) {
            worker.start();
        }
    }

}

