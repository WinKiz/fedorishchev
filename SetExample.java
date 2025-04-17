import java.util.HashSet;
import java.util.Set;

public class SetExample {
    public static void main(String[] args) {
        // Создание множества
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        set1.add(5);

        // Проверка наличия элемента в множестве
        if (set1.contains(1)) {
            System.out.println("1 находится в множестве");
        } else {
            System.out.println("1 отсутствует в множестве");
        }

        // Добавление элемента в множество
        set1.add(6);

        // Удаление элемента из множества
        set1.remove(3);

        // Получение размера множества
        int length = set1.size();
        System.out.println("Размер множества: " + length);

        // Итерация по множеству
        for (int element : set1) {
            System.out.println(element);
        }
    }
}