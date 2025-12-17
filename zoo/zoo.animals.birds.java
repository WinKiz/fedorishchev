package zoo.animals.birds;

import zoo.animals.abstract.Animal;
import zoo.animals.interfaces.Flyable;

// Абстрактный класс для птиц
public abstract class Bird extends Animal implements Flyable {
    protected double wingspan;
    protected String beakType;
    protected boolean canMigrate;
    
    public Bird(String name, int age, double weight, String habitat, 
                double wingspan, String beakType, boolean canMigrate) {
        super(name, age, weight, habitat);
        this.wingspan = wingspan;
        this.beakType = beakType;
        this.canMigrate = canMigrate;
    }
    
    @Override
    public String getDietType() {
        return "Varies by species";
    }
    
    // Реализация Flyable
    @Override
    public void fly() {
        System.out.println(name + " is flying");
    }
    
    @Override
    public int getFlightHeight() {
        return 100; // высота по умолчанию в метрах
    }
    
    // Специфичные для птиц методы
    public void buildNest() {
        System.out.println(name + " builds a nest");
    }
    
    public void layEgg() {
        System.out.println(name + " lays an egg");
    }
}

// Конкретный класс: Орел
public class Eagle extends Bird {
    private double visionRange; // в километрах
    
    public Eagle(String name, int age, double weight, double wingspan, double visionRange) {
        super(name, age, weight, "Mountains", wingspan, "Hooked", true);
        this.visionRange = visionRange;
    }
    
    @Override
    public String getSpecies() {
        return "Aquila chrysaetos";
    }
    
    @Override
    public String getDietType() {
        return "Carnivore";
    }
    
    @Override
    public String makeSound() {
        return "Screech!";
    }
    
    @Override
    public int getFlightHeight() {
        return 3000; // орлы летают высоко
    }
    
    @Override
    public String getCareInstructions() {
        return "Eagle " + name + ": Provide high perches and fresh meat";
    }
    
    public void huntFromSky() {
        System.out.println(name + " hunts from the sky");
    }
}

// Конкретный класс: Пингвин
public class Penguin extends Bird {
    private double swimSpeed;
    
    public Penguin(String name, int age, double weight, double swimSpeed) {
        super(name, age, weight, "Antarctic", 0.6, "Pointed", false); // не летает
        this.swimSpeed = swimSpeed;
    }
    
    @Override
    public String getSpecies() {
        return "Spheniscidae";
    }
    
    @Override
    public String getDietType() {
        return "Piscivore";
    }
    
    @Override
    public String makeSound() {
        return "Honk!";
    }
    
    @Override
    public void fly() {
        System.out.println(name + " cannot fly, but is excellent swimmer!");
    }
    
    @Override
    public int getFlightHeight() {
        return 0; // не летает
    }
    
    public void slideOnBelly() {
        System.out.println(name + " slides on its belly");
    }
}
