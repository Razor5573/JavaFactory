package factory;


public class Controller implements Runnable {
    private Factory factory;
    private Storage<Car> storage;

    Controller(Factory factory, Storage<Car> storage) {
        this.factory = factory;
        this.storage = storage;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is online");
        for (int i = 0; i < storage.getMaxStorageSize(); i++) {
            factory.createTask();
        }
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waits for notify");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Controller is notified, asking factory to create a car");
                factory.createTask();
            }
        }
    }
}
