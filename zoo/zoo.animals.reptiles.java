package zoo.animals.reptiles;

import zoo.animals.abstract.Animal;
import zoo.animals.interfaces.Swimmable;

// Абстрактный класс для рептилий
public abstract class Reptile extends Animal implements Swimmable {
    protected double bodyTemperature;
    protected boolean isVenomous;
    protected String scaleType;
    
    public Reptile(String name, int age, double weight, String habitat, 
                   boolean isVenomous, String scaleType) {
        super(name, age, weight, habitat);
        this.bodyTemperature = 25.0; // по умолчанию
        this.isVenomous = isVenomous;
        this.scaleType = scaleType;
    }
    
    @Override
    public String getDietType() {
        return "Varies by species";
    }
    
    // Реализация Swimmable
    @Override
    public void swim() {
        System.out.println(name + " is swimming");
    }
    
    @Override
    public int getDiveDepth() {
        return 5; // глубина по умолчанию в метрах
    }
    
    // Специфичные для рептилий методы
    public void baskInSun() {
        System.out.println(name + " basks in the sun");
        bodyTemperature += 5.0;
    }
    
    public void shedSkin() {
        System.out.println(name + " sheds its skin");
    }
}

// Конкретный класс: Крокодил
public class Crocodile extends Reptile {
    private double biteForce; // сила укуса в кг/см²
    
    public Crocodile(String name, int age, double weight, double biteForce) {
        super(name, age, weight, "Swamp", false, "Osteoderms");
        this.biteForce = biteForce;
    }
    
    @Override
    public String getSpecies() {
        return "Crocodylidae";
    }
    
    @Override
    public String getDietType() {
        return "Carnivore";
    }
    
    @Override
    public String makeSound() {
        return "Hiss!";
    }
    
    @Override
    public int getDiveDepth() {
        return 20; // крокодилы могут глубоко нырять
    }
    
    @Override
    public String getCareInstructions() {
        return "Crocodile " + name + ": Extreme caution required! Secure enclosure needed";
    }
    
    public void deathRoll() {
        System.out.println(name + " performs death roll");
    }
}

// Конкретный класс: Черепаха
public class Turtle extends Reptile {
    private int shellDiameter; // диаметр панциря в см
    
    public Turtle(String name, int age, double weight, int shellDiameter) {
        super(name, age, weight, "Freshwater", false, "Scutes");
        this.shellDiameter = shellDiameter;
    }
    
    @Override
    public String getSpecies() {
        return "Testudines";
    }
    
    @Override
    public String getDietType() {
        return "Herbivore";
    }
    
    @Override
    public String makeSound() {
        return "..."; // черепахи тихие
    }
    
    @Override
    public String getCareInstructions() {
        return "Turtle " + name + ": Provide clean water and vegetables";
    }
    
    public void hideInShell() {
        System.out.println(name + " hides in its shell");
    }
}
