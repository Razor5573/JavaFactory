package factory;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.file.StandardOpenOption.*;

public class Dealer implements Runnable {

    private Integer sellingSpeed;
    private final Storage<Car> carStorage;
    private boolean logging;
    private File file;

    Dealer(Storage<Car> carStorage) {
        this.sellingSpeed = 15000;
        this.carStorage = carStorage;
    }

    Dealer(Storage<Car> carStorage, Integer sellingSpeed) {
        this.sellingSpeed = sellingSpeed;
        this.carStorage = carStorage;
    }

    Dealer(Storage<Car> carStorage, Integer sellingSpeed, Boolean logging, File file) {
        this.sellingSpeed = sellingSpeed;
        this.carStorage = carStorage;
        this.logging = logging;
        this.file = file;
    }

    @Override
    public void run() {
        Car sellableCar;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Dealer " + Thread.currentThread().getName() + " needs car from storage");
                sellableCar = carStorage.getFromStorage();
                if (logging) {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = sdfDate.format(new Date());
                    String toWrite = strDate + ": Dealer " + Thread.currentThread().getName()
                            + " Auto: " + sellableCar.getId() + " ( Body: " + sellableCar.getBodyID()
                            + ", Motor: " + sellableCar.getEngineID() + ", Accessory: " + sellableCar.getAccessoryID() + ")\n";
                    synchronized (file) {
                        Files.write(file.toPath(), toWrite.getBytes(), WRITE, CREATE, APPEND);
                    }
                }
            } catch (InterruptedException e) {
                return;
            } catch (IOException e1) {
                System.out.println("File for logging not found!");
                setLogging(false);
            }
            System.out.println("Dealer " + Thread.currentThread().getName() + " sold a car");
            try {
                Thread.sleep(sellingSpeed);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
