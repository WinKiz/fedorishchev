package zoo.employees;

import zoo.animals.abstract.Animal;
import zoo.animals.interfaces.Careable;

// Абстрактный класс для сотрудников
public abstract class Employee {
    protected String name;
    protected int id;
    protected double salary;
    protected String department;
    
    public Employee(String name, int id, double salary, String department) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.department = department;
    }
    
    public abstract void work();
    public abstract String getJobDescription();
    
    // Общие методы
    public void takeBreak() {
        System.out.println(name + " takes a break");
    }
    
    public void attendMeeting() {
        System.out.println(name + " attends a meeting");
    }
    
    // Геттеры
    public String getName() { return name; }
    public int getId() { return id; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
}

// Конкретный класс: Смотритель
public class Zookeeper extends Employee {
    private String[] assignedAnimals;
    
    public Zookeeper(String name, int id, double salary, String[] assignedAnimals) {
        super(name, id, salary, "Animal Care");
        this.assignedAnimals = assignedAnimals;
    }
    
    @Override
    public void work() {
        System.out.println(name + " takes care of animals");
    }
    
    @Override
    public String getJobDescription() {
        return "Responsible for feeding and caring for animals";
    }
    
    public void feedAnimal(Animal animal, String food) {
        animal.feed(food);
        System.out.println(name + " fed " + animal.getName());
    }
    
    public void cleanHabitat(Animal animal) {
        animal.clean();
        System.out.println(name + " cleaned habitat for " + animal.getName());
    }
}

// Конкретный класс: Ветеринар
public class Veterinarian extends Employee {
    private String specialization;
    
    public Veterinarian(String name, int id, double salary, String specialization) {
        super(name, id, salary, "Medical");
        this.specialization = specialization;
    }
    
    @Override
    public void work() {
        System.out.println(name + " examines and treats animals");
    }
    
    @Override
    public String getJobDescription() {
        return "Provides medical care for animals, specialization: " + specialization;
    }
    
    public void examineAnimal(Animal animal) {
        animal.checkHealth();
        System.out.println(name + " examined " + animal.getName());
    }
    
    public void treatAnimal(Animal animal) {
        animal.setHealthy(true);
        System.out.println(name + " treated " + animal.getName());
    }
}
