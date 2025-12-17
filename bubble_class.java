public class BubbleSortSimple {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2};
        
        // Сортировка пузырьком
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        
        // Вывод результата
        for (int num : arr) System.out.print(num + " ");
    }
}
