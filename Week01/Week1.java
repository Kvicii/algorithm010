package org.apache.jute;

import java.util.Deque;
import java.util.LinkedList;

public class Week1 {

    // 1.用 add first 或 add last 这套新的 API 改写 Deque 的代码

    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<>();

        deque.addFirst("a");
        deque.addFirst("b");
        deque.addFirst("c");

        System.out.println(deque);

        String str = deque.peekLast();
        System.out.println(str);
        System.out.println(deque);

        while (deque.size() > 0) {
            System.out.println(deque.pollFirst());
        }
        System.out.println(deque);
    }
    // 2.分析 Queue 和 Priority Queue 的源码
    /**
     * Queue：定义了一个队列的基本方法
     * 添加的方法有 add(队列满时抛出异常)   offer(队列满时不抛异常)
     * 获取并移除的方法有 poll(队列为空时返回努力了)  remove(队列为空时抛出异常)
     * 获取元素但不删除的方法有 element(队列为空抛出异常)   peek(队列为空返回null)
     *
     * PriorityQueue：优先队列
     * 优先队列的作用是能保证每次取出的元素都是队列中权值最小的 这里牵涉到了大小关系 元素大小的评判可以通过元素本身的自然顺序 也可以通过构造时传入的比较器
     * Java中PriorityQueue实现了Queue接口 不允许放入null元素
     * 其通过堆实现，具体说是通过完全二叉树实现的小顶堆 就意味着可以通过数组来作为PriorityQueue的底层实现
     *
     * add(E e)和offer(E e)的语义相同 都是向优先队列中插入元素 前者在插入失败时抛出异常后者则会返回false
     * element()和peek()的语义完全相同 都是获取但不删除队首元素 二者唯一的区别是当方法失败时前者抛出异常后者返回null
     *
     * remove()和poll()方法的语义也完全相同 都是获取并删除队首元素 区别是当方法失败时前者抛出异常，后者返回null
     */

    // 3. 删除排序数组中的重复项
    // func removeDuplicates(nums []int) int {
    //     // 1.遍历
    //     index := 1
    //     for i := 1; i < len(nums); i++ {
    //         if nums[i] != nums[i - 1] {
    //             nums[index] = nums[i]
    //             index++
    //         }
    //     }
    //     return index
    // }

    // func removeDuplicates(nums []int) int {
    //     2.双指针
    //     p := 0
    //     q := 1
    //     for q < len(nums) {
    //     if nums[p] != nums[q] {
    //         if q - p > 1 {
    //             nums[p + 1] = nums[q]
    //         }
    //         p++
    //     }
    //     q++
    // }
    //     return p + 1
    // }
    // 4.旋转数组
    // func rotate(nums []int, k int)  {
//
    //     // 1.暴力
    //     // for i := 0; i < k; i++ {
    //     //     first := nums[len(nums) - 1]
    //     //     for j := 0; j < len(nums); j++ {
    //     //         tmp := nums[j]
    //     //         nums[j] = first
    //     //         first = tmp
    //     //     }
    //     // }
    //     // 2. 三次翻转
    //     k %= len(nums)
    //     reverse(nums, 0, len(nums) - 1)
    //     reverse(nums, 0, k - 1)
    //     reverse(nums, k, len(nums) - 1)
    // }
//
    // func reverse(nums []int, start int, end int) {
//
    //     for start < end {
    //         tmp := nums[start]
    //         nums[start] = nums[end]
    //         nums[end] = tmp
//
    //         start++
    //         end--
    //     }
    // }
    // 5.合并两个有序链表
    /**
     * Definition for singly-linked list.
     * type ListNode struct {
     *     Val int
     *     Next *ListNode
     * }
     */
    // func mergeTwoLists(l1 *ListNode, l2 *ListNode) *ListNode {
//
    //     // 1.迭代
    //     // if l1 == nil {
    //     //     return l2
    //     // }
    //     // if l2 == nil {
    //     //     return l1
    //     // }
    //     // node := &ListNode{Val:0}
    //     // curr := node
//
    //     // for l1 != nil && l2 != nil {
    //     //     if l1.Val > l2.Val {
    //     //         node.Next = l2
    //     //         l2 = l2.Next
    //     //     } else {
    //     //         node.Next = l1
    //     //         l1 = l1.Next
    //     //     }
    //     //     node = node.Next
    //     //     if l1 != nil {
    //     //         node.Next = l1
    //     //     } else {
    //     //         node.Next = l2
    //     //     }
    //     // }
    //     // return curr.Next
    //     // 2.递归
    //     return recursive(l1, l2)
    // }
//
    // func recursive(l1 *ListNode, l2 *ListNode) *ListNode {
//
    //     if l1 == nil {
    //         return l2
    //     }
    //     if l2 == nil {
    //         return l1
    //     }
//
    //     if l1.Val > l2.Val {
    //         l2.Next = recursive(l1, l2.Next)
    //         return l2
    //     } else {
    //         l1.Next = recursive(l1.Next, l2)
    //         return l1
    //     }
    // }

    // 6.合并两个有序数组
    // class Solution {
    //     public void merge(int[] nums1, int m, int[] nums2, int n) {
//
    //         // 头部遍历
    //         // int p = 0, p1 = 0, p2 = 0;
    //         // int[] nums1Copy = new int[m + n];
    //         // System.arraycopy(nums1, 0, nums1Copy, 0, m);
    //         // while (p1 < m && p2 < n) {
    //         //     nums1[p++] = nums1Copy[p1] < nums2[p2] ? nums1Copy[p1++] : nums2[p2++];
    //         // }
//
    //         // if (p1 < m) {
    //         //     System.arraycopy(nums1Copy, p1, nums1, p1 + p2, m + n - p1 - p2);
    //         // }
    //         // if (p2 < n) {
    //         //     System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
    //         // }
    //         // 尾部遍历
    //         int p = m + n - 1, p1 = m - 1, p2 = n - 1;
    //         while (p1 >= 0 && p2 >= 0) {
    //             nums1[p--] = nums1[p1] < nums2[p2] ? nums2[p2--] : nums1[p1--];
    //         }
    //         System.arraycopy(nums2, 0, nums1, 0 , p2 + 1);
    //     }
    // }

    // 7. 两数之和
    // func twoSum(nums []int, target int) []int {
        // 1.暴力
        // res := []int{}
        // for i := 0; i < len(nums) - 1; i++ {
        //     for j := i + 1; j < len(nums); j++ {
        //         if target - nums[i] == nums[j] {
        //             res = append(res, i)
        //             res = append(res, j)
        //         }
        //     }
        // }
        // return res
        // 2.hash表两遍循环
        // res := []int{}
        // hash_map := map[int]int{}
        // for i := 0; i < len(nums); i++ {
        //     hash_map[nums[i]] = i
        // }
        // for i := 0; i < len(nums); i++ {
        //     key := target - nums[i]
        //     if val, ok := hash_map[key]; ok {
        //         if val == i {
        //             continue
        //         }
        //         res = append(res, i)
        //         res = append(res, hash_map[key])
        //     }
        // }
        // return res
        // 3.hash表一遍循环
        // res := []int{}
        // hash_map := map[int]int{}

        // for i := 0; i < len(nums); i++ {
        //     if index, ok := hash_map[target - nums[i]]; ok {
        //         if index == i {
        //             continue
        //         }
        //         res = append(res, i)
        //         res = append(res, index)
        //     } else {
        //         hash_map[nums[i]] = i
        //     }
        // }
        // return res;
    // 8.移动零
    // func moveZeroes(nums []int)  {
    //     // 1.暴力破解
    //     // index := 0
    //     // for i := 0; i < len(nums); i++ {
    //     //     if nums[i] != 0 {
    //     //         tmp := nums[i]
    //     //         nums[i] = nums[index]
    //     //         nums[index] = tmp
    //     //         index++
    //     //     }
    //     // }
    //     // 2.双指针1
    //     // index := 0
    //     // for i := 0; i < len(nums); i++ {
    //     //     if nums[i] != 0 {
    //     //         nums[index] = nums[i]
    //     //         if i != index {
    //     //             nums[i] = 0
    //     //         }
    //     //         index++
    //     //     }
    //     // }
    //     // 3.双指针2
    //     index := 0
    //     for i := 0; i < len(nums); i++ {
    //         if nums[i] == 0 {
    //             index++
    //         } else if (index > 0) {
    //             nums[i - index] = nums[i]
    //             nums[i] = 0
    //         }
    //     }
    // }

    //  9.加一
//    func plusOne(digits []int) []int {
//
//        for i := len(digits) - 1; i >= 0; i-- {
//            digits[i] ++
//            digits[i] = digits[i] % 10
//            if digits[i] != 0 {
//                return digits
//            }
//        }
//        digits[0] = 1
//        digits = append(digits, 0)
//        return digits
//    }

    // 10.设计循环双端队列
    // class MyCircularDeque {
//
    //     /** Initialize your data structure here. Set the size of the deque to be k. */
    //     Stack<Integer> lastQueue;
    //     Stack<Integer> frontQueue;
    //     int capacity;
    //     public MyCircularDeque(int k) {
    //         lastQueue = new Stack();
    //         frontQueue = new Stack();
    //         this.capacity = k;
    //     }
//
    //     /** Adds an item at the front of Deque. Return true if the operation is successful. */
    //     public boolean insertFront(int value) {
    //         if (isFull()) {
    //             return false;
    //         }
    //         frontQueue.push(value);
    //         return true;
    //     }
//
    //     /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    //     public boolean insertLast(int value) {
    //         if (isFull()) {
    //             return false;
    //         }
    //         lastQueue.push(value);
    //         return true;
    //     }
//
    //     /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    //     public boolean deleteFront() {
    //         if (frontQueue.isEmpty()) {
    //             if (lastQueue.isEmpty()) {
    //                 return false;
    //             }
    //             while (!lastQueue.isEmpty()) {
    //                 frontQueue.push(lastQueue.pop());
    //             }
    //         }
    //         frontQueue.pop();
    //         return true;
    //     }
//
    //     /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    //     public boolean deleteLast() {
    //         if (lastQueue.isEmpty()) {
    //             if (frontQueue.isEmpty()) {
    //                 return false;
    //             }
    //             while (!frontQueue.isEmpty()) {
    //                 lastQueue.push(frontQueue.pop());
    //             }
    //         }
    //         lastQueue.pop();
    //         return true;
    //     }
//
    //     /** Get the front item from the deque. */
    //     public int getFront() {
    //         if (frontQueue.isEmpty()) {
    //             if (lastQueue.isEmpty()) {
    //                 return -1;
    //             }
    //             while (!lastQueue.isEmpty()) {
    //                 frontQueue.push(lastQueue.pop());
    //             }
    //         }
    //         return frontQueue.peek();
    //     }
//
    //     /** Get the last item from the deque. */
    //     public int getRear() {
    //         if (lastQueue.isEmpty()) {
    //             if (frontQueue.isEmpty()) {
    //                 return -1;
    //             }
    //             while (!frontQueue.isEmpty()) {
    //                 lastQueue.push(frontQueue.pop());
    //             }
    //         }
    //         return lastQueue.peek();
    //     }
//
    //     /** Checks whether the circular deque is empty or not. */
    //     public boolean isEmpty() {
    //         return frontQueue.isEmpty() && lastQueue.isEmpty();
    //     }
//
    //     /** Checks whether the circular deque is full or not. */
    //     public boolean isFull() {
    //         return frontQueue.size() + lastQueue.size() >= capacity;
    //     }
    // }

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */

// 11.接雨水
// class Solution {
//     public int trap(int[] height) {
//
//         int res = 0;
//         int left = 0, left_max = 0, right = height.length - 1, right_max = 0;
//         while (left <= right) {
//             if (left_max < right_max) {
//                 if (left_max < height[left]) {
//                     left_max = height[left];
//                 } else{
//                     res += left_max - height[left];
//                 }
//                 left++;
//             } else {
//                 if (right_max < height[right]) {
//                     right_max = height[right];
//                 } else {
//                     res += right_max - height[right];
//                 }
//                 right--;
//             }
//         }
//
//         return res;
//     }
// }
}