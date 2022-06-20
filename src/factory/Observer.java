package factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Observer extends Observable implements java.util.Observer {
    private int engineStorageCurrent;
    private int engineStorageMax;
    private int accessoryStorageCurrent;
    private int accessoryStorageMax;
    private int bodyStorageCurrent;
    private int bodyStorageMax;

    private HashMap<String, Integer> workersState;

    private int carStorageCurrent;
    private int carStorageMax;

    private int carsSold;

    Observer(int engineStorageMax, int accessoryStorageMax, int bodyStorageMax,
             int carStorageMax, HashMap<String, Integer> workShift) {
        this.engineStorageMax = engineStorageMax;
        this.accessoryStorageMax = accessoryStorageMax;
        this.bodyStorageMax = bodyStorageMax;
        this.carStorageMax = carStorageMax;
        engineStorageCurrent = 0;
        accessoryStorageCurrent = 0;
        bodyStorageCurrent = 0;
        carStorageCurrent = 0;
        carsSold = 0;
        workersState = workShift;
    }

    @Override
    public void update(Observable obj, Object arg) {
        synchronized (this) {
            if (arg != null) {
                workersState.put((String) arg, ((WorkerObserver) obj).getWorkState());
            } else {
                int newCurrentStorage = ((Storage) obj).getCurrentStorageSize();
                if (((Storage) obj).getClassName() == Car.class) {
                    carStorageCurrent = newCurrentStorage;
                    carsSold = ((Storage) obj).getShipped();
                } else if (((Storage) obj).getClassName() == Body.class) {
                    bodyStorageCurrent = newCurrentStorage;
                } else if (((Storage) obj).getClassName() == Engine.class) {
                    engineStorageCurrent = newCurrentStorage;
                } else {
                    accessoryStorageCurrent = newCurrentStorage;
                }
            }
            setChanged();
            notifyObservers();
        }
    }

    public HashMap<String, Integer> getMap() {
        return workersState;
    }

    public int getEngineStorageCurrent() {
        return engineStorageCurrent;
    }

    public int getEngineStorageMax() {
        return engineStorageMax;
    }

    public int getAccessoryStorageCurrent() {
        return accessoryStorageCurrent;
    }

    public int getAccessoryStorageMax() {
        return accessoryStorageMax;
    }

    public int getBodyStorageCurrent() {
        return bodyStorageCurrent;
    }

    public int getBodyStorageMax() {
        return bodyStorageMax;
    }

    public int getCarStorageCurrent() {
        return carStorageCurrent;
    }

    public int getCarStorageMax() {
        return carStorageMax;
    }

    public int getCarsSold() {
        return carsSold;
    }
}
