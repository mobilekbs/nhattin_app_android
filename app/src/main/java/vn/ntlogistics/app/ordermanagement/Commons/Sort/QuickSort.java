package vn.ntlogistics.app.ordermanagement.Commons.Sort;

/**
 * Created by Zanty on 27/07/2016.
 */
public class QuickSort {
    // Tìm chốt
    public int findPivot(int i, int j, int[] array) {
        if (array.length == 1) {
            return -1;
        }
        int k = i + 1;
        int pivot = array[i];

        while ((k <= j) && (array[k] == pivot)) {
            k++;
        }
        if (k > j) {
            return -1;
        }
        if (array[k] > pivot) {
            return k;
        } else {
            return i;
        }
    }

    // Tìm partition
    public int pointPartition(int i, int j, int pivotKey, int[] array) {
        int partition = -1;

        int L = i;
        int R = j;
        while (L <= R) {
            while (array[L] < pivotKey)
                L++;
            while (array[R] >= pivotKey)
                R--;
            if (L < R) {
                int temp = array[L];
                array[L] = array[R];
                array[R] = temp;
            }
        }
        partition = L;
        return partition;

    }

    // Sắp xếp
    public void quickSort(int i, int j, int[] array) {
        int pivot = findPivot(i, j, array);
        if (pivot == -1)
            return;
        int partition = pointPartition(i, j, array[pivot], array);
        quickSort(i, partition - 1, array);
        quickSort(partition, j, array);
    }
}
