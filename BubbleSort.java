import java.util.Arrays;
public class BubbleSort<T extends Comparable<T>>
{
    public void sort(T[] items)
    {
        int n = items.length;
        boolean swapped;
        
        for (int i = 0; i<n - 1; i++)
        {
            swapped = false;
            
            for (int j = 0; j < n-1 -i;j++)
            {
                if (items[j].compareTo(items[j + 1]) > 0)
                {
                    swap(items,j,j+1);
                    swapped = true;
                }
            }
            
            if (!swapped)
            {
                break;
            }
        }
            
    }
    
    private void swap(T[] items,int left,int right)
    {
        if (left != right)
        {
            T temp = items[left];
            items[left] = items[right];
            items[right] = temp;
        }
    }
    
    public static void main(String[] args)
    {
        Integer[] array = {64, 34, 25, 12, 11};
        BubbleSort<Integer> sorter = new BubbleSort<>();
        sorter.sort(array);
        System.out.println("Отсортированный массив");
        System.out.println(Arrays.toString(array));
    }
}
