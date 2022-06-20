package factory;

import java.util.UUID;

public class Car {
    private Accessory accessory;
    private Body body;
    private Engine engine;
    private Integer id;

    Car() {
    }

    Car(Accessory accessory, Body body, Engine engine) {
        this.accessory = accessory;
        this.body = body;
        this.engine = engine;
        this.id = UUID.randomUUID().hashCode();
    }

    public int getAccessoryID() {
        return accessory.getID();
    }

    public int getBodyID() {
        return body.getID();
    }

    public int getEngineID() {
        return engine.getID();
    }

    public Integer getId() {
        return id;
    }
}
