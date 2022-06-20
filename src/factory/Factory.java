package factory;

import threadpool.ThreadPool;

import java.util.LinkedList;

public class Factory {
    private ThreadPool<Worker, CarTask> workshop;
    private Storage<Car> carStorage;
    private Storage<Accessory> accessoryStorage;
    private Storage<Engine> engineStorage;
    private Storage<Body> bodyStorage;
    private LinkedList<CarTask> tasks;


    Factory(Storage<Car> carStorage, Storage<Accessory> accessoryStorage,
            Storage<Engine> engineStorage, Storage<Body> bodyStorage,
            int workersAmount, int workingSpeed) {
        tasks = new LinkedList<>();
        this.carStorage = carStorage;
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        workshop = new ThreadPool<>(workersAmount, tasks, Worker.class, workingSpeed);
    }

    public void createTask() {
        synchronized (tasks) {
            tasks.add(new CarTask(accessoryStorage, bodyStorage, engineStorage, carStorage));
            tasks.notify();
            System.out.println("Task added");
        }
    }

    public void setObserver(Observer observer) {
        workshop.setObserver(observer);
    }

    public void runFactory() {
        workshop.poolStart();
    }

    public ThreadPool<Worker, CarTask> getWorkshop() {
        return workshop;
    }
}
