package factory;

import threadpool.PoolTask;

public class CarTask extends PoolTask {
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Car> carStorage;
    private Worker assignedWorker;

    CarTask(Storage<Accessory> accessoryStorage, Storage<Body> bodyStorage,
            Storage<Engine> engineStorage, Storage<Car> carStorage) {
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.carStorage = carStorage;
        assignedWorker = null;
    }

    public void assignWorker(Worker worker) {
        assignedWorker = worker;
    }

    @Override
    public void run() {
        Accessory accessory = null;
        try {
            System.out.println("Worker " + Thread.currentThread().getName() + " needs accessory");
            accessory = accessoryStorage.getFromStorage();
        } catch (InterruptedException e) {
            return;
        }
        Body body = null;
        try {
            System.out.println("Worker " + Thread.currentThread().getName() + " needs body");
            body = bodyStorage.getFromStorage();
        } catch (InterruptedException e) {
            return;
        }
        Engine engine = null;
        try {
            System.out.println("Worker " + Thread.currentThread().getName() + " needs engine");
            engine = engineStorage.getFromStorage();
        } catch (InterruptedException e) {
            return;
        }
        Car car = new Car(accessory, body, engine);
        try {
            System.out.println("Worker from thread " + Thread.currentThread().getName() + " makes car");
            assignedWorker.getWorkerObserver().changeWorkState(2);
            Thread.sleep(assignedWorker.getWorkSpeed());
            carStorage.addToStorage(car);
        } catch (InterruptedException e) {
            return;
        }
    }

}
