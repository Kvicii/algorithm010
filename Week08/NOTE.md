学习笔记

## 基本排序实现
### 以 LeetCode912 排序数组为例进行编写
**https://leetcode-cn.com/problems/sort-an-array/**
* Java选择排序
```
class Solution {
    public int[] sortArray(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[index]) index = j;
            }
            int temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
        }
        return nums;
    }
}
```
* Java插入排序
```
class Solution {
    public int[] sortArray(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int preIndex = i - 1, curr = nums[i];
            while (preIndex >= 0 && nums[preIndex] > curr) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = curr;
        }
        return nums;
    }
}
```
* Java冒泡排序
```
class Solution {
    public int[] sortArray(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }
}
```
* Java快速排序
```
class Solution {
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void quickSort(int[] nums, int begin, int end) {
        
        if (begin >= end)  return;
        int partition = partition(nums, begin, end);
        quickSort(nums, begin, partition - 1);
        quickSort(nums, partition + 1, end);
    }
    
    private int partition(int[] nums, int begin, int end) {
        int pivot = end, counter = begin;
        for (int i = begin; i < end; i++) {
            if (nums[i] < nums[pivot]) {
                swap(nums, i, counter++);
            }
        }
        swap(nums, counter, pivot);
        return counter;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```
* Java归并排序
```
class Solution {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int mid = ((right - left) >> 1) + left;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }
    
    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) temp[k++] = nums[i] < nums[j] ? nums[i++] : nums[j++];
        while (i <= mid) temp[k++] = nums[i++];
        while (j <= right) temp[k++] = nums[j++];
        System.arraycopy(temp, 0, nums, left, temp.length);
    }
}
```

* Java堆排序系统API
```
class Solution {
    public int[] sortArray(int[] nums) {
        PriorityQueue<Integer> heap = new PriorityQueue();
        for (int i = 0; i < nums.length; i++) {
            heap.offer(nums[i]);
        }
        int index = 0;
        while (!heap.isEmpty()) {
            nums[index] = heap.poll();
            index++;
        }
        return nums;
    }
}
```

* Java堆排序自维护
```
class Solution {
    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }
    
    private void heapSort(int[] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            adjust(nums, nums.length, i);
        }
        for (int i = nums.length - 1; i >= 1; i--) {
            swap(nums, i, 0);
            adjust(nums, i, 0);
        }
    }
    
    private void adjust(int[] nums, int length, int index) {
        int left = 2 * index + 1, right = 2 * index + 2, largest = index;
        while (left < length && nums[left] > nums[largest]) largest = left;
        while (right < length && nums[right] > nums[largest]) largest = right;
        if (largest != index) {
            swap(nums, index, largest);
            adjust(nums, length, largest);
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```


* Go选择排序
```
func sortArray(nums []int) []int {
    for i := 0; i < len(nums) - 1; i++ {
        index := i
        for j := i + 1; j < len(nums); j++ {
            if nums[j] < nums[index] {
                index = j
            }
        }
        temp := nums[i]
        nums[i] = nums[index]
        nums[index] = temp
    }
    return nums
}
```
* Go插入排序
```
func sortArray(nums []int) []int {
    for i := 1; i < len(nums); i++ {
        preIndex := i - 1
        curr := nums[i]
        for preIndex >= 0 && nums[preIndex] > curr {
            nums[preIndex + 1] = nums[preIndex]
            preIndex--
        }
        nums[preIndex + 1] = curr
    }
    return nums
}
```
* Go冒泡排序
```
func sortArray(nums []int) []int {
    for i := 0; i < len(nums) - 1; i++ {
        for j := i + 1; j < len(nums); j++ {
            if nums[i] > nums[j] {
                temp := nums[i]
                nums[i] = nums[j]
                nums[j] = temp
            }
        }
    }
    return nums
}
```
* Go快速排序
```
func sortArray(nums []int) []int {
    quickSort(nums, 0, len(nums) - 1)
    return nums
}

func quickSort(nums []int, begin int, end int) {
    if begin >= end {
        return
    }
    partition := partition(nums, begin, end)
    quickSort(nums, begin, partition - 1)
    quickSort(nums, partition + 1, end)
}

func partition(nums []int, begin int, end int) int {
    pivot := end
    counter := begin
    for i := begin; i < end; i++ {
        if nums[i] < nums[pivot] {
            swap(nums, i, counter)
            counter++
        }
    }
    swap(nums, pivot, counter)
    return counter
}

func swap(nums []int, i int, j int) {
    temp := nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}
```
* 归并排序
```
func sortArray(nums []int) []int {
    mergeSort(nums, 0, len(nums) - 1);
    return nums
}

func mergeSort(nums []int, left int, right int) {
    if left >= right {
         return
    }
    mid := (right + left) >> 1
    mergeSort(nums, left, mid)
    mergeSort(nums, mid + 1, right)
    merge(nums, left, mid, right)
}

func merge(nums []int, left int, mid int, right int) {
    
    temp := make([]int, right - left + 1)
    i := left
    j := mid + 1
    k := 0
    for (i <= mid && j <= right) {
        if nums[i] < nums[j] {
            temp[k] = nums[i]
            i++
        } else {
            temp[k] = nums[j]
            j++
        }
        k++
    }
    for (i <= mid) {
        temp[k] = nums[i]
        i++
        k++
    }
    for (j <= right) {
        temp[k] = nums[j]
        j++
        k++
    }
    for i := 0; i < len(temp); i++ {
        nums[left + i] = temp[i]
    }
}
```