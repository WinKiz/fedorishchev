package zoo.animals.mammals;

import zoo.animals.abstract.Animal;
import zoo.animals.interfaces.Runnable;

// Абстрактный класс для млекопитающих
public abstract class Mammal extends Animal implements Runnable {
    protected int furLength; // длина меха в мм
    protected boolean hasTail;
    
    public Mammal(String name, int age, double weight, String habitat, 
                  int furLength, boolean hasTail) {
        super(name, age, weight, habitat);
        this.furLength = furLength;
        this.hasTail = hasTail;
    }
    
    @Override
    public String getDietType() {
        return "Varies by species";
    }
    
    // Реализация Runnable
    @Override
    public void run() {
        System.out.println(name + " is running");
    }
    
    @Override
    public double getSpeed() {
        return 10.0; // средняя скорость по умолчанию
    }
    
    // Специфичные для млекопитающих методы
    public void groom() {
        System.out.println(name + " is being groomed");
    }
    
    public int getFurLength() { return furLength; }
    public boolean hasTail() { return hasTail; }
}

// Конкретный класс: Лев
public class Lion extends Mammal {
    private double maneLength;
    private boolean isAlpha;
    
    public Lion(String name, int age, double weight, double maneLength, boolean isAlpha) {
        super(name, age, weight, "Savannah", 50, true);
        this.maneLength = maneLength;
        this.isAlpha = isAlpha;
    }
    
    @Override
    public String getSpecies() {
        return "Panthera leo";
    }
    
    @Override
    public String getDietType() {
        return "Carnivore";
    }
    
    @Override
    public String makeSound() {
        return "ROAR!";
    }
    
    @Override
    public double getSpeed() {
        return 80.0; // львы быстрые
    }
    
    @Override
    public String getCareInstructions() {
        return "Lion " + name + ": Feed meat daily, keep habitat secure";
    }
    
    public void hunt() {
        System.out.println(name + " is hunting");
    }
    
    public double getManeLength() { return maneLength; }
    public boolean isAlpha() { return isAlpha; }
}

// Конкретный класс: Обезьяна
public class Monkey extends Mammal {
    private int tailLength;
    private boolean canUseTools;
    
    public Monkey(String name, int age, double weight, int tailLength, boolean canUseTools) {
        super(name, age, weight, "Jungle", 20, true);
        this.tailLength = tailLength;
        this.canUseTools = canUseTools;
    }
    
    @Override
    public String getSpecies() {
        return "Primate";
    }
    
    @Override
    public String getDietType() {
        return "Omnivore";
    }
    
    @Override
    public String makeSound() {
        return "Ooh-ooh-ah-ah!";
    }
    
    @Override
    public String getCareInstructions() {
        return "Monkey " + name + ": Provide fruits, nuts, and climbing structures";
    }
    
    public void climbTree() {
        System.out.println(name + " climbs a tree");
    }
    
    public void useTool() {
        if (canUseTools) {
            System.out.println(name + " uses a tool");
        }
    }
}
