package factory;

import java.util.NoSuchElementException;

public class Supplier<DetailType extends Detail> implements Runnable {

    private Integer producingSpeed;
    private Class<DetailType> detailType;
    private final Storage<DetailType> storage;

    Supplier(Storage<DetailType> storage, Class<DetailType> detailType) {
        this.detailType = detailType;
        this.storage = storage;
        producingSpeed = 1000;
    }

    Supplier(Integer speed, Storage<DetailType> storage, Class<DetailType> detailType) {
        this.detailType = detailType;
        this.storage = storage;
        this.producingSpeed = speed;
    }

    @Override
    public void run() {
        DetailType newDetail = null;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Supplier " + Thread.currentThread().getName()
                    + " made " + detailType.getSimpleName());
            try {
                newDetail = detailType.getDeclaredConstructor().newInstance();
                storage.addToStorage(newDetail);
            } catch (InterruptedException e1) {
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                System.out.println(detailType.getSimpleName() + " " + newDetail.getID() + " from " + Thread.currentThread().getName()
                        + " added successfully");
                Thread.sleep(producingSpeed);
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}
