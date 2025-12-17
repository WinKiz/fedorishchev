// Композиция
class Car {
    private Engine engine;  
    
    public Car() {
        this.engine = new Engine(); 
    }
    
    public void start() {
        System.out.println("Car starting: " + engine.ignite());
    }
}

class Engine {
    public String ignite() {
        return "Vroom!";
    }
}

// Агрегация
class Taxi {
    private Driver driver;  // Driver exists independently
    
    public Taxi() {
        // No driver initially
    }
    
    public void assignDriver(Driver driver) {
        this.driver = driver;  // Driver passed from outside
    }
    
    public void removeDriver() {
        this.driver = null;  // Driver continues to exist
    }
}

class Driver {
    private String licenseNumber;
    
    public Driver(String license) {
        this.licenseNumber = license;
    }
    
    public String drive() {
        return "Driver " + licenseNumber + " is driving";
    }
}
//Композиция — когда объект-часть не имеет смысла без объекта-целого (как сердце без человека или комната без дома). Часть создается внутри конструктора целого и уничтожается вместе с ним.

//Агрегация — когда объект-часть может существовать самостоятельно и передаваться между разными объектами-целыми (как студент между университетами или водитель между автомобилями). Часть передается как параметр и продолжает существовать после уничтожения целого.
