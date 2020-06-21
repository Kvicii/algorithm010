# 1.写一个关于 HashMap 的总结
## 1. hash函数
```
    static final int hash(Object key) {
        int h;
        // 从整个计算过程上来说: ^ (h >>> 16)只有这一块的逻辑 两个位操作性能肯定是有保障的 那么如果想要保证哈希函数的高效性 只需要传入的key自身的Object#hashCode()方法的高效即可
        // 增加^ (h >>> 16)扰动函数的目的在于直接计算出的hashcode在32位有符号数的范围中(-2147483648到2147483648)大约占用40亿的映射空间 该空间内存是放不下的
        // 高16位与低16位做异或就是为了混合高位和低位 以此增大低位的随机性 混合后的低位也混有高位的部分特征 高位的信息也变相的被保留下来
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);   // 先计算hashcode 再与计算出来的hashcode的高16位进行异或 保证计算出来的hash值更加离散
    }
```
## 2. tableSizeFor函数
```
    /**
     * Returns a power of two size for the given target capacity.
     * 返回 >= cap 的最小的2的幂次方
     * 为什么一定要是2的幂次方:
     * 1.计算table数组对应的位置时 使用了(n - 1) & hash 该方式 (n - 1) % hash的效果是一致的 出于性能考虑最终选择了& 这样就要求数组容量n一定要做到2的幂次方
     * 2.resize扩容时 HashMap的容量一直是2的幂次方
     */
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);   // 将 cap 从最高位(最左边)第一个为1开始的位开始全部设置为 1
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;    // 由于第一行处理的结果都如 0000 00 ... 1111 这种形式 + 1之后返回2的幂次方(例如 cap = 10 经计算后是 -1 >>> 28 + 1 = 16)
    }
```
## 3. put函数
```
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K, V>[] tab;   // tables 数组
        Node<K, V> p;   // 对应位置的 Node 节点
        int n, i;   // 数组大小, 对应的 table 的位置
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;    // 延迟初始化思想 如果当前的桶是空的就需要进行初始化 resize方法会判断是否进行初始化
        if ((p = tab[i = (n - 1) & hash]) == null)  // 根据key的hashcode定位到具体的桶并判断是否为空 为空表明没有发生hash冲突 直接构造node放入 (n - 1) & hash定位到桶
            tab[i] = newNode(hash, key, value, null);
        else {  // 以下是存在hash冲突的情况
            Node<K, V> e;   // key 在 HashMap 对应的老节点
            K k;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) // 比较桶中首个元素的与写入的值的hashcode和key 相等进行赋值
                e = p;
            else if (p instanceof TreeNode) // 按照红黑树的方式进行赋值
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            else {  // 说明是链表 思想是 没有到达链表尾部就进行遍历寻找相同key值的node 如果到了链表尾部还没有找到则在链表尾部追加新的node
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) { // 从p.next的位置开始判断 因为之前已经判断过p的位置 位于链表尾部 构造节点
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st. 达到了红黑树的扩容阈值 转换为红黑树
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) // 说明链表的非首位元素存在相同key值的node 进行记录
                        break;
                    p = e;  // 令p指向下一个node节点
                }
            }
            if (e != null) { // existing mapping for key. 说明hash冲突的元素中存在相同key值的node 针对上述情况统一进行值的覆盖
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)  // 在value允许修改的情况下 对节点的value进行修改
                    e.value = value;
                afterNodeAccess(e); // 节点被访问的回调 用于HashMap的子类LinkedHashMap需要做的拓展逻辑
                return oldValue;
            }
        }
        ++modCount; // 增加修改次数
        if (++size > threshold) // 达到扩容阈值进行扩容
            resize();
        afterNodeInsertion(evict);  // 添加节点后的回调
        return null;
    }
```
## 4. resize函数
```
/**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     * <p>
     * 用于扩容或进行初始化
     * <p>
     * 并发操作容易在一个桶上形成环形链表
     * 这样当获取一个不存在的key时 计算出的index正好是环形链表的下标就会出现死循环
     * <p>
     * 扩容逻辑: 计算新的扩容阈值和容量 创建新的 table 数组 --> 将老的 table 复制到新的 table 数组中
     * <p>
     * 有 hash(key1) = 1111 1111 1111 1111 0000 1111 0000 0101
     * 和 hash(key2) = 1111 1111 1111 1111 0000 1111 0001 0101
     * 原容量为16 则 n - 1 = 0000 0000 0000 0000 0000 0000 0000 1111
     * 所以
     * key1 的位置 = 0000 0000 0000 0000 0000 0000 0000 0101
     * key2 的位置 = 0000 0000 0000 0000 0000 0000 0000 0101
     * <p>
     * resize 扩容 n变为原来的2倍 n - 1 = 0000 0000 0000 0000 0000 0000 0001 1111
     * 此时
     * key1 的位置 = 0000 0000 0000 0000 0000 0000 0000 0101(即还在原位置)
     * key2 的位置 = 0000 0000 0000 0000 0000 0000 0001 0101(原位置 + 16)
     * <p>
     * 因此在扩容时不再需要 1.7 重新计算 hash 只需要看原来hash值新增的那个bit位是0(位置没变)还是1(位置变为 oldCap + 原位置)
     * 所以这个设计可以认为 新增的bit位是0还是1是随机的 重新的将之前冲突的节点分散到了新的桶
     * 所以在链表部分的判断 e.hash & oldCap 实际上就是判断的最高位是否为1
     * <p>
     * 1.7 旧链表在迁移时如果在新的 table 数组索引位置相同 链表会倒置 1.8 不会倒置
     *
     * @return the table
     */
    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {   // oldCap > 0 说明table非空
            if (oldCap >= MAXIMUM_CAPACITY) {   // 超过最大容量 直接设置扩容阈值为 Integer.MAX_VALUE 不再允许扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) // * 目的是2倍扩容 如果 oldCap >= DEFAULT_INITIAL_CAPACITY(16) 扩容阈值也变为原来的2倍 newCap = oldCap << 1 实际的速度要比 newCap * loadFactor逻辑的运算速度要快
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0) // initial capacity was placed in threshold. 不满足上述if条件的说明 oldCap = 0 该判断和下面的判断是为了进行初始化. [非默认构造方法] oldThr > 0 直接使用oldThr作为新的容量
            newCap = oldThr;    // 由于 tableSizeFor 方法使用计算阈值 所以可以保证 HashMap 初始容量为2的幂次方
        else {  // zero initial threshold signifies using defaults. [默认的构造方法] 设置新的容量为默认的 DEFAULT_INITIAL_CAPACITY(16) 扩容阈值为 DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY(12)
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {  // 上述逻辑均未触发 使用 newCap * loadFactor 作为新的阈值. oldCap > 0 并且不满足其中的第二个条件时 或者 oldThr > 0 这两个条件下会进入该逻辑设置阈值
            float ft = newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        // 开始扩容操作
        threshold = newThr; // 将新阈值赋值给 threshold
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];  // 创建新的Node数组 赋值给 table
        table = newTab;
        if (oldTab != null) {   // 老数组非空 需要搬运数据
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;
                if ((e = oldTab[j]) != null) {  // 获得老数组的第j个桶
                    oldTab[j] = null;
                    if (e.next == null) // 该 node 没有下一个元素(说明该位置上只有一个元素) 直接重新计算位置 赋值 无论是红黑树还是链表
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode) // 该位置是红黑树节点 按照红黑树分裂方式处理
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    else { // preserve order.   该位置是链表
                        // HashMap是2倍扩容 原来位置的链表节点会被分散到新的 table 的两个位置上
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            next = e.next;  // next指向下一个节点
                            // 通过 e.hash & oldCap 计算 根据结果分到高位和低位的位置中
                            if ((e.hash & oldCap) == 0) {   // 计算结果为0 放置到低位
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {    // 计算结果非0 放置到高位
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);   // do...while的原因是 e已经非空 减少一次判断
                        if (loTail != null) {   // 设置低位到新的 table 上
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {   // 设置高位到新的 table 的 j + oldCap 位置上
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```
## 5. remove函数
```
final Node<K, V> removeNode(int hash, Object key, Object value,
                                boolean matchValue, boolean movable) {
        Node<K, V>[] tab;   // table 数组
        Node<K, V> p;   // hash 对应 table 位置的节点
        int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (p = tab[index = (n - 1) & hash]) != null) {    // 查找 hash 对应 table 位置的节点p
            Node<K, V> node = null, e;  // 如果找到 key 对应的节点 赋值给 node
            K k;
            V v;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) // p节点就是要删除的节点 直接赋值给 node
                node = p;
            else if ((e = p.next) != null) {
                if (p instanceof TreeNode)  // 节点p是红黑树节点 在红黑树中继续查找
                    node = ((TreeNode<K, V>) p).getTreeNode(hash, key);
                else {  // 节点p是链表节点 遍历查找
                    do {
                        if (e.hash == hash &&
                                ((k = e.key) == key ||
                                        (key != null && key.equals(k)))) {  // 遍历节点 如果找到了直接break
                            node = e;
                            break;
                        }
                        p = e;  // 保存节点e的前一个节点
                    } while ((e = e.next) != null);
                }
            }
            if (node != null && (!matchValue || (v = node.value) == value ||
                    (value != null && value.equals(v)))) {  // 找到了匹配的节点进行删除 如果有要求匹配 value 的条件 也一并进行判断
                if (node instanceof TreeNode)   // 如果找到的 node 是红黑树节点 直接在红黑树中删除
                    ((TreeNode<K, V>) node).removeTreeNode(this, tab, movable);
                else if (node == p) // 要删除的节点是链表首个位置的节点 直接将 table 在该位置对应的元素位置指向链表的下一个节点
                    tab[index] = node.next;
                else    // 要删除的节点是链表的中间节点 将p的下一个位置指向要删除元素的下一个位置
                    p.next = node.next;
                ++modCount; // 增加修改次数
                --size; // 减少 HashMap 的长度
                afterNodeRemoval(node); // 移除 node 之后的回调
                return node;
            }
        }
        return null;    // 没有找到直接返回 null
    }
```

# 2.有效的字母异位词
```
class Solution {
    public boolean isAnagram(String s, String t) {
        // 1.哈希
        // if (s.length() != t.length()) {
        //     return false;
        // }
        // Map<Character, Integer> map = new HashMap();
        // for (int i = 0; i < s.length(); i++) {
        //     map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        // }
        // for (int i = 0; i < t.length(); i++) {
        //     Integer value = map.get(t.charAt(i));
        //     if (value == null) {
        //         return false;
        //     }
        //     map.put(t.charAt(i), value - 1);
        // }
        // for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        //     if (entry.getValue() != 0) {
        //         return false;
        //     }
        // }
        // return true;
        // 2.直接sort
        // if (s.length() != t.length()) {
        //     return false;
        // }
        // char[] sarr = s.toCharArray();
        // char[] tarr = t.toCharArray();
        // Arrays.sort(sarr);
        // Arrays.sort(tarr);
        // return Arrays.equals(sarr, tarr);
        // 3.数组存储
        if (s.length() != t.length()) return false;
        int[] res = new int[26];
        for (int i = 0; i < s.length(); i++) {
            res[s.charAt(i) - 'a']++;
            res[t.charAt(i) - 'a']--;
        }
        for (int num : res) {
            if (num != 0) return false;
        }
        return true;
    }
}
```

# 3.两数之和
```
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 1.暴力
        // int[] res = new int[2];
        // if (nums == null || nums.length < 2) {
        //     return res;
        // }
        // for (int i = 0; i < nums.length - 1; i++) {
        //     for (int j = i + 1; j < nums.length; j++) {
        //         if (nums[i] + nums[j] == target) {
        //             res[0] = i;
        //             res[1] = j;
        //             break;
        //         }
        //     }
        // }
        // return res;
        // 2.hash table
        // int[] res = new int[2];
        // Map<Integer, Integer> map = new HashMap();
        // if (nums == null || nums.length < 2) {
        //     return res;
        // }
        // for (int i = 0; i < nums.length; i++) {
        //     map.put(nums[i], i);
        // }
        // for (int i = 0; i < nums.length; i++) {
        //     Integer value = map.get(target - nums[i]);
        //     if (value != null && value != i) {
        //         res[0] = value;
        //         res[1] = i;
        //     }
        // }
        // return res;
        // 3.hash table
        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            Integer value = map.get(target - nums[i]);
            if (value == null) {
                map.put(nums[i], i);
            } else {
                res[0] = value;
                res[1] = i;
            }
        }
        return res;
    }
}
```

# 4.N叉树的前序遍历
```
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<Integer> preorder(Node root) {
        // 1.递归
        // List<Integer> res = new ArrayList();
        // if (root == null) {
        //     return res;
        // }
        // res.add(root.val);
        // for (Node node : root.children) {
        //     res.addAll(preorder(node));
        // }
        // return res;
        // 2.迭代
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }

        Stack<Node> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node first = stack.pop();
            res.add(first.val);
            Collections.reverse(first.children);
            for (Node node : first.children) {
                stack.push(node);
            }
        }
        return res;
    }
}
```

# 5.字母异位词分组
```
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

        List<List<String>> res = new ArrayList();
        Map<String, List<String>> map = new HashMap();

        for (int i = 0; i < strs.length; i++) {
            char[] carr = strs[i].toCharArray();
            Arrays.sort(carr);
            String key = new String(carr);
            if (map.get(key) == null) {
                List<String> tmpList = new ArrayList();
                tmpList.add(strs[i]);
                map.put(key, tmpList);
            } else {
                List<String> tmpList = map.get(key);
                tmpList.add(strs[i]);
                map.put(key, tmpList);
            }
        }
        return new ArrayList(map.values());
    }
}
```

# 6.二叉树的中序遍历
```
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {

        // 1.递归
        // List<Integer> res = new ArrayList();
        // if (root == null) {
        //     return res;
        // }
        // res.addAll(inorderTraversal(root.left));
        // res.add(root.val);
        // res.addAll(inorderTraversal(root.right));
        // return res;
        // 2.迭代
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
```

# 7.二叉树的前序遍历
```
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {

        // 1.递归
        // List<Integer> res = new ArrayList();
        // if (root == null) {
        //     return res;
        // }
        // res.add(root.val);
        // res.addAll(preorderTraversal(root.left));
        // res.addAll(preorderTraversal(root.right));
        // return res;
        // 2.迭代
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        Stack<TreeNode> stack = new Stack();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                res.add(root.val);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }
        return res;
    }

}
```

# 8.N叉树的层序遍历
```
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        
        List<List<Integer>> res = new ArrayList();
        LinkedList<Node> queue = new LinkedList();
        if (root == null) return res;
        queue.addLast(root);

        while (!queue.isEmpty()) {

            List<Integer> tmpList = new ArrayList();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.pollFirst();
                tmpList.add(node.val);
                queue.addAll(node.children);
            }
            res.add(tmpList);
        }
        return res;
    }
}

```
# 9.丑数
```
class Solution {
    public int nthUglyNumber(int n) {
        
        PriorityQueue<Long> queue = new PriorityQueue();
        Set<Long> set = new HashSet();
        queue.add(1L);
        set.add(1L);

        Long num = 1L;
        for (int i = 0; i < n; i++) {
            num = queue.poll();
            long p2 = num * 2;
            long p3 = num * 3;
            long p5 = num * 5;
            if (!set.contains(p2)) {
                queue.add(p2);
                set.add(p2);
            }
            if (!set.contains(p3)) {
                queue.add(p3);
                set.add(p3);
            }
            if (!set.contains(p5)) {
                queue.add(p5);
                set.add(p5);
            }
        }
        return num.intValue();
    }
}
```

# 10.前 K 个高频元素
```
class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((Map.Entry<Integer, Integer> entry1, Map.Entry<Integer, Integer> entry2) -> (entry2.getValue() - entry1.getValue()));
        Map<Integer, Integer> map = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            heap.add(entry);
        }

        int[] res = new int[k];
        int index = 0;
        while (index < k) {
            res[index] = heap.poll().getKey();
            index++;
        }
        return res;
    }
}
```