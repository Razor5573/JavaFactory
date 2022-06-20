package factory;

import java.util.Observable;

import java.util.ArrayList;

public class Storage<Type> extends Observable {
    private Integer storageSize;
    private ArrayList<Type> storage;
    private Controller controller;
    private Integer shipped;
    private Class<Type> className;

    Storage(Integer size, Class<Type> className) {
        this.className = className;
        storage = new ArrayList<>();
        storageSize = size;
        shipped = 0;
    }

    void setController(Controller controller) {
        this.controller = controller;
    }

    void setObserver(Observer observer) {
        this.addObserver(observer);
    }

    public void addToStorage(Type detail) throws InterruptedException {
        synchronized (this) {
            while (isFull()) {
                System.out.println("Storage is full for " + Thread.currentThread().getName());
                wait();
            }
            System.out.println(Thread.currentThread().getName()
                    + " added " + detail.getClass().getSimpleName() + " to storage");
            storage.add(detail);
            setChanged();
            notifyObservers();
            if (storage.size() == 1) {
                notify();
            }
        }

    }

    public Type getFromStorage() throws InterruptedException {
        synchronized (this) {
            Type detail = null;
            while (storage.isEmpty()) {
                System.out.println("Storage is empty for " + Thread.currentThread().getName());
                if (controller != null) {
                    System.out.println(Thread.currentThread().getName() + " is calling controller");
                    synchronized (controller) {
                        controller.notify();
                        System.out.println("Storage of cars is empty, controller notified by " + Thread.currentThread().getName());
                    }
                }
                wait();
            }
            detail = storage.remove(storage.size() - 1);
            shipped++;
            setChanged();
            notifyObservers();
            System.out.println(Thread.currentThread().getName()
                    + " got " + detail.getClass().getSimpleName() + " from storage");
            if (storage.size() == storageSize - 1)
                this.notify();
            if (controller != null) {
                System.out.println("Car was sold, " + Thread.currentThread().getName() + " calls controller");
                if (!isFull()) {
                    synchronized (controller) {
                        controller.notify();
                        System.out.println("Controller notified by " + Thread.currentThread().getName());
                    }
                }
            }
            return detail;
        }
    }

    public boolean isFull() {
        return storage.size() == storageSize;
    }

    public Integer getShipped() {
        return shipped;
    }

    public Class<Type> getClassName() {
        return className;
    }

    public Integer getCurrentStorageSize() {
        return storage.size();
    }

    public Integer getMaxStorageSize() {
        return storageSize;
    }

}
