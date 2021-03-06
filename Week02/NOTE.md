学习笔记

# 哈希表  HashMap
## 1. 定义
散列表（Hash table，也叫哈希表），是根据键（Key）而直接访问在内存存储位置的数据结构。也就是说，它通过计算一个关于键值的函数，将所需查询的数据映射到表中一个位置来访问记录，这加快了查找速度。这个映射函数称做散列函数，存放记录的数组称做散列表。

## 2. 特性
哈希表  插入和查找的平均时间复杂度为 O(1) 
在做leecode题目的时候，经常使用哈希表保存中间结果，使算法的时间复杂度降低一个数量级,或者在递归中使用哈希表做缓存，减少重复计算，降低算法复杂度。还可以使用哈希表做两个元素的匹配。
常见题目：leecode 1 two sum ， leecode 242 valid-anagram 等

# 二叉树  Binary Tree
## 1. 定义
二叉树（英语：Binary tree）是每个节点最多只有两个分支（即不存在分支度大于2的节点）的树结构。通常分支被称作“左子树”或“右子树”。二叉树的分支具有左右次序，不能随意颠倒。

## 2. 特性
二叉树  有三种遍历方式：前序、中序和后序
在做leecode题目的时候，绝大多数二叉树的题目都是用递归来解题的，代码格式十分统一。
同时深度优先遍历DFS和广度优先遍历BFS也常在leecode二叉树题目中使用到。
常见题目：leecode 102 等

# 二叉搜索树   Binary Search Tree
## 1. 定义
二叉查找树（英语：Binary Search Tree），也称为二叉搜索树、有序二叉树（ordered binary tree）或排序二叉树（sorted binary tree），是指一棵空树或者具有下列性质的二叉树：
### 1. 若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
### 2. 若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
### 3. 任意节点的左、右子树也分别为二叉查找树；

# 图 Graph
## 1. 定义
图的数学定义很复杂难懂，看老师讲义的图片更加直观。图的类型分为有向图和无向图，程序实现上图有多重表示法，但是leecode上一般使用邻接表方式比较多。
图的算法一般都和DFS和BFS分不开的。

# 堆
## 1. 定义
堆（英语：Heap）是计算机科学中的一种特别的树状数据结构。若是满足以下特性，即可称为堆：
给定堆中任意节点 P 和 C，若 P 是 C 的母节点，那么 P 的值会小于等于（或大于等于） C 的值

## 2. 作用
能在logN复杂度下获取一组可以动态扩容数据流的最小（最大）值。用于实现优先级队列。

## DFS代码模板
```
visited=set()
def dfs(node,visited):
  visited.add(node)
  #process currect node here
  process(node)
  for next_node in node.children:
    if not next_node in visited:
      dfs(next_node,visited)
```

## BFS代码模板
```
def BFS(graph,start,visited):
    queen=[]
    queen.append(start)
    while queen:
        node=queen.pop()
        visited.add(node)
        process(node)
        for next_node in node.children:
            if next_node not in visited:
                queen.append(next_node)
```

## 堆代码模板
```
import java.util.Arrays;
import java.util.NoSuchElementException;


public class BinaryHeap {


    private static final int d = 2;
    private int[] heap;
    private int heapSize;


    /**
     * This will initialize our heap with default size.
     */
    public BinaryHeap(int capacity) {
        heapSize = 0;
        heap = new int[capacity + 1];
        Arrays.fill(heap, -1);
    }


    public boolean isEmpty() {
        return heapSize == 0;
    }


    public boolean isFull() {
        return heapSize == heap.length;
    }




    private int parent(int i) {
        return (i - 1) / d;
    }


    private int kthChild(int i, int k) {
        return d * i + k;
    }


    /**
     * Inserts new element in to heap
     * Complexity: O(log N)
     * As worst case scenario, we need to traverse till the root
     */
    public void insert(int x) {
        if (isFull()) {
            throw new NoSuchElementException("Heap is full, No space to insert new element");
        }
        heap[heapSize] = x;
        heapSize ++;
        heapifyUp(heapSize - 1);
    }


    /**
     * Deletes element at index x
     * Complexity: O(log N)
     */
    public int delete(int x) {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty, No element to delete");
        }
        int maxElement = heap[x];
        heap[x] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(x);
        return maxElement;
    }


    /**
     * Maintains the heap property while inserting an element.
     */
    private void heapifyUp(int i) {
        int insertValue = heap[i];
        while (i > 0 && insertValue > heap[parent(i)]) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = insertValue;
    }


    /**
     * Maintains the heap property while deleting an element.
     */
    private void heapifyDown(int i) {
        int child;
        int temp = heap[i];
        while (kthChild(i, 1) < heapSize) {
            child = maxChild(i);
            if (temp >= heap[child]) {
                break;
            }
            heap[i] = heap[child];
            i = child;
        }
        heap[i] = temp;
    }


    private int maxChild(int i) {
        int leftChild = kthChild(i, 1);
        int rightChild = kthChild(i, 2);
        return heap[leftChild] > heap[rightChild] ? leftChild : rightChild;
    }


    /**
     * Prints all elements of the heap
     */
    public void printHeap() {
        System.out.print("nHeap = ");
        for (int i = 0; i < heapSize; i++)
            System.out.print(heap[i] + " ");
        System.out.println();
    }


    /**
     * This method returns the max element of the heap.
     * complexity: O(1)
     */
    public int findMax() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        return heap[0];
    }


    public static void main(String[] args) {
        BinaryHeap maxHeap = new BinaryHeap(10);
        maxHeap.insert(10);
        maxHeap.insert(4);
        maxHeap.insert(9);
        maxHeap.insert(1);
        maxHeap.insert(7);
        maxHeap.insert(5);
        maxHeap.insert(3);


        maxHeap.printHeap();
        maxHeap.delete(5);
        maxHeap.printHeap();
        maxHeap.delete(2);
        maxHeap.printHeap();
    }
}
```