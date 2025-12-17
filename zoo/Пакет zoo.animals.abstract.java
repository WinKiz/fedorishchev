package zoo.animals.abstract;

import zoo.animals.interfaces.Soundable;
import zoo.animals.interfaces.Careable;

// Абстрактный базовый класс для всех животных
public abstract class Animal implements Soundable, Careable {
    protected String name;
    protected int age;
    protected double weight;
    protected String habitat;
    protected boolean isHealthy;
    
    public Animal(String name, int age, double weight, String habitat) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.habitat = habitat;
        this.isHealthy = true;
    }
    
    // Абстрактные методы, которые должны быть реализованы в подклассах
    public abstract String getSpecies();
    public abstract String getDietType();
    
    // Реализация методов интерфейса Careable
    @Override
    public void feed(String food) {
        System.out.println(name + " eats " + food);
    }
    
    @Override
    public void clean() {
        System.out.println(name + "'s habitat is being cleaned");
    }
    
    @Override
    public String getCareInstructions() {
        return "General care instructions for " + name;
    }
    
    // Общие методы для всех животных
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public void wakeUp() {
        System.out.println(name + " wakes up");
    }
    
    public void checkHealth() {
        System.out.println(name + " health check: " + (isHealthy ? "Healthy" : "Needs vet"));
    }
    
    // Геттеры
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public String getHabitat() { return habitat; }
    public boolean isHealthy() { return isHealthy; }
    
    public void setHealthy(boolean healthy) { isHealthy = healthy; }
}
