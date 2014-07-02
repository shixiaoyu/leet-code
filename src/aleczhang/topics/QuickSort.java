package aleczhang.topics;

public class QuickSort {
    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int newRight = partition(nums, left, right);
            quickSort(nums, left, newRight);
            quickSort(nums, newRight + 1, right);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[left];
        int l = left - 1;
        int r = right + 1;
        while (true) {
            l++;
            while (l < right && nums[l] < pivot) {
                l++;
            }
            r--;
            while (r > left && nums[r] > pivot) {
                r--;
            }
            if (l < r) {
                int temp = nums[l];
                nums[l] = nums[r];
                nums[r] = temp;
            }
            else {
                return r;
            }
        }
    }

}
