import java.util.Arrays;
public class SelectionSort <T extends Comparable<T>>
{
    public void sort(T[] items)
    {
        int sortedRangeEnd = 0;
        while (sortedRangeEnd < items.length)
        {
            int nextIndex = findIndexOfSmallestFromIndex(items, sortedRangeEnd);
            swap(items, sortedRangeEnd, nextIndex);
            sortedRangeEnd++;
        }
    }
    private int
    findIndexOfSmallestFromIndex(T[] items, int sortedRangeEnd)
    {
        T currentSmallest = items[sortedRangeEnd];
        int currentSmallestIndex = sortedRangeEnd;
        
        for (int i = sortedRangeEnd + 1; i< items.length;i++)
        {
            if (currentSmallest.compareTo(items[i]) > 0)
            {
                currentSmallest = items[i];
                currentSmallestIndex = i;
            }
        }
        
        return currentSmallestIndex;
    }
    
    private void swap(T[] items, int left, int right)
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
        Integer[] array = {64, 25, 12, 22,11};
        SelectionSort<Integer> sorter = new SelectionSort<>();
        sorter.sort(array);
        
        System.out.println("Отсортированный массив: ");
        System.out.println(Arrays.toString(array));
    }
    
}
