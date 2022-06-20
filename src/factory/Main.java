package factory;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

public class Main {

    public static void startFactory(ArrayList<Integer> numbers, ArrayList<Boolean> parameters) throws InterruptedException, IOException {
        File f = null;

        if (parameters.get(1)) {
            try (InputStream in = Main.class.getResourceAsStream("config.txt")) {
                Properties properties = new Properties();
                properties.load(in);
                numbers.set(0, Integer.parseInt(properties.getProperty("CarStorageSize")));
                numbers.set(1, Integer.parseInt(properties.getProperty("EngineStorageSize")));
                numbers.set(2, Integer.parseInt(properties.getProperty("AccessoryStorageSize")));
                numbers.set(3, Integer.parseInt(properties.getProperty("BodyStorageSize")));
                numbers.set(4, Integer.parseInt(properties.getProperty("Dealers")));
                numbers.set(5, Integer.parseInt(properties.getProperty("Workers")));
                numbers.set(6, Integer.parseInt(properties.getProperty("SupplierEngineSpeed")));
                numbers.set(7, Integer.parseInt(properties.getProperty("SupplierAccessorySpeed")));
                numbers.set(8, Integer.parseInt(properties.getProperty("SupplierBodySpeed")));
                numbers.set(9, Integer.parseInt(properties.getProperty("DealerSpeed")));
                numbers.set(10, Integer.parseInt(properties.getProperty("WorkerSpeed")));
                parameters.set(0, properties.getProperty("LogSale").equals("true"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (parameters.get(0)) {
            f = new File("logs" + System.currentTimeMillis() + ".txt");
            f.createNewFile();
        }
        Storage<Car> carStorage = new Storage<>(numbers.get(0), Car.class);
        Storage<Engine> engineStorage = new Storage<>(numbers.get(1), Engine.class);
        Storage<Accessory> accessoryStorage = new Storage<>(numbers.get(2), Accessory.class);
        Storage<Body> bodyStorage = new Storage<>(numbers.get(3), Body.class);

        Supplier<Engine> engineSupplier = new Supplier<>(numbers.get(6), engineStorage, Engine.class);
        Supplier<Accessory> accessorySupplier = new Supplier<>(numbers.get(7), accessoryStorage, Accessory.class);
        Supplier<Body> bodySupplier = new Supplier<>(numbers.get(8), bodyStorage, Body.class);

        ArrayList<Dealer> dealers = new ArrayList<>();
        for (int i = 0; i < numbers.get(4); i++) {
            dealers.add(new Dealer(carStorage, numbers.get(9), parameters.get(0), f));
        }

        Factory factory = new Factory(carStorage, accessoryStorage, engineStorage, bodyStorage, numbers.get(5), numbers.get(10));
        Controller controller = new Controller(factory, carStorage);
        carStorage.setController(controller);

//		Dealer firstDealer = new Dealer(carStorage, 6000);
//		Dealer secondDealer = new Dealer(carStorage, 7000);
//		Dealer thirdDealer = new Dealer(carStorage, 8000);
        HashMap<String, Integer> workShift = new HashMap<>();
        for (Worker worker : factory.getWorkshop().getWorkers()) {
            workShift.put(worker.getName(), worker.getWorkerObserver().getWorkState());
        }
        View view = new View();
        Observer observer = new Observer(engineStorage.getMaxStorageSize(), accessoryStorage.getMaxStorageSize(),
                bodyStorage.getMaxStorageSize(), carStorage.getMaxStorageSize(), workShift);
        observer.addObserver(view);
        factory.setObserver(observer);
        bodyStorage.setObserver(observer);
        engineStorage.setObserver(observer);
        carStorage.setObserver(observer);
        accessoryStorage.setObserver(observer);
        view.setObserver(observer);
        view.startSwing();
        factory.runFactory();
        ArrayList<Thread> dealersThreads = new ArrayList<>();
        Thread thread1 = new Thread(controller, "Controller");
        Thread thread2 = new Thread(accessorySupplier, "Accessory Supplier");
        Thread thread3 = new Thread(bodySupplier, "Body Supplier");
        Thread thread4 = new Thread(engineSupplier, "Engine Supplier");
        int i = 0;
        for (Dealer dealer : dealers) {
            dealersThreads.add(new Thread(dealer, "Dealer " + i));
            i++;
        }

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        thread3.start();
        thread4.start();
        for (Thread thread : dealersThreads) {
            thread.start();
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Boolean> parameters = new ArrayList<>();
        StartWindow window = new StartWindow();
        window.makeStartWindow(numbers, parameters);

    }


}
