public static void quickSort(int[] array, int left, int right) {
    if (left >= right) {
        return;
    }

    int pivot = array[(left + right) / 2];

    int i = left;
    int j = right;

    while (i <= j) {
        while (array[i] < pivot) {
            i++;
        }

        while (array[j] > pivot) {
            j--;
        }

        if (i <= j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;

            i++;
            j--;
        }
    }

    quickSort(array, left, j);
    quickSort(array, i, right);
}
