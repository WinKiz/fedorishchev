import java.util.HashSet;
import java.util.Set;

public class SetExample
{
    public static void main(String[] args)
    {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        set1.add(5);

        if (set1.contains(1))
        {
            System.out.println("1 находится в множестве");
        }
        else
        {
            System.out.println("1 отсутствует в множестве");
        }

        set1.add(6);
        set1.remove(3);

        int length = set1.size();
        System.out.println("Размер множества: " + length);
        for (int element : set1)
        {
            System.out.println(element);
        }
    }
}