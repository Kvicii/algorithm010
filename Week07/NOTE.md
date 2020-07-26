学习笔记

## Trie树的基本实现和特性

1. 字典树的数据结构
      - 字典树，即Trie树，又称单词查找树或键树，是一种树形结构。典型应用是用于统计和排序大量的字符串（但不仅限于字符串），所以经常被**搜索引擎系统用于文本词频统计**。
      - 它的优点：最大限度地减少无谓的字符串比较，查询效率比哈希表高。 
2. 字典树的核心思想
      - Trie树的核心思想是空间换时间。
      - 利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。 
3. 字典树的基本性质
      - 节点本身不存完整单词；
      - 从根节点到某一个节点，路径上经过的字符连接起来，为该节点对应的字符串；
      - 每个节点的所有子结点路径代表的字符都不相同。 

Trie树代码模板

* Python

``` 
class Trie(object):
  
    def __init__(self): 
        self.root = {} 
        self.end_of_word = "#" 
 
    def insert(self, word): 
        node = self.root 
        for char in word: 
            node = node.setdefault(char, {}) 
        node[self.end_of_word] = self.end_of_word 
 
    def search(self, word): 
        node = self.root 
        for char in word: 
            if char not in node: 
                return False 
            node = node[char] 
        return self.end_of_word in node 
 
    def startsWith(self, prefix): 
        node = self.root 
        for char in prefix: 
            if char not in node: 
                return False 
            node = node[char] 
        return True
```

* Java

``` 
class Trie {
    private boolean isEnd;
    private Trie[] next;
    /** Initialize your data structure here. */
    public Trie() {
        isEnd = false;
        next = new Trie[26];
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) return;
        Trie curr = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            int n = words[i] - 'a';
            if (curr.next[n] == null) curr.next[n] = new Trie();
            curr = curr.next[n];
        }
        curr.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Trie node = searchPrefix(prefix);
        return node != null;
    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            node = node.next[words[i] - 'a'];
            if (node == null) return null;
        }
        return node;
    }
}
```

## 并查集

* Disjoint Set 并查集
* 适用场景
    - 组团、配对问题
    - Group or not ?
* 基本操作
    - makeSet(s)：建立一个新的并查集，其中包含s个单元素集合。
    - unionSet(x, y)：把元素x和元素y所在的集合合并，要求x和y所在的集合不相交，如果相交则不合并。
    - find(x)：找到元素x所在的集合的代表，该操作也可以用于判断两个元素是否位于同一个集合，只要将它们各自的代表比较一下就可以了。

并查集代码模板：

* Java

``` 
class UnionFind { 
    private int count = 0; 
    private int[] parent; 
    public UnionFind(int n) { 
        count = n; 
        parent = new int[n]; 
        for (int i = 0; i < n; i++) { 
            parent[i] = i;
        }
    } 
    public int find(int p) { 
        while (p != parent[p]) { 
            parent[p] = parent[parent[p]]; 
            p = parent[p]; 
        }
        return p; 
    }
    public void union(int p, int q) { 
        int rootP = find(p); 
        int rootQ = find(q); 
        if (rootP == rootQ) return; 
        parent[rootP] = rootQ; 
        count--;
    }
}
```

* Python

``` 
def init(p): 
    # for i = 0 .. n: p[i] = i; 
    p = [i for i in range(n)] 
 
def union(self, p, i, j): 
    p1 = self.parent(p, i) 
    p2 = self.parent(p, j) 
    p[p1] = p2 
 
def parent(self, p, i): 
    root = i 
    while p[root] != root: 
        root = p[root] 
    while p[i] != i: # 路径压缩 ?
        x = i; i = p[i]; p[x] = root 
    return root
```

## 高级搜索

### 1. 剪枝的实现和特性

*  剪枝
* 双向BFS
* 启发式搜索（A*）
* 初级搜索
    1. 朴素搜索
    2. 优化方式：不重复（fibonacci）、剪枝（生成括号问题）
    3. 搜索方向：
        - DFS：depth first search 深度优先搜索
        - BFS：breadth first search 广度优先搜索

        双向搜索、启发式搜索

* 回溯法
    - 回溯法采用试错的思想，它尝试分步的去解决一个问题。在分步解决问题的过程中，当它通过尝试发现现有的分步答案不能得到有效的正确的解答的时候，它将取消上一步甚至上几步的计算，再通过其它的可能的分步解答再次尝试学找问题的答案。
    - 回溯法通常用最简单的递归方法来实现，在反复重复上述的步骤后可能出现两种情况：
        - 找到一个可能存在的正确的答案；
        - 在尝试了所有可能的分步方法后宣告该问题没有答案
    - 在最坏的情况下，回溯法会导致一次复杂度为指数时间的计算。    
    - 本质就是递归和分治。

DFS代码模板：

* Python

``` 
visited = set() 

def dfs(node, visited):
    if node in visited: # terminator
        # already visited 
        return 

    visited.add(node) 

    # process current node here. 
    ...
    for next_node in node.children(): 
        if next_node not in visited: 
            dfs(next_node, visited)
```

* Java

``` 
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> allResults = new ArrayList<>();
        if(root==null){
            return allResults;
        }
        travel(root,0,allResults);
        return allResults;
    }

    private void travel(TreeNode root,int level,List<List<Integer>> results){
        if(results.size()==level){
            results.add(new ArrayList<>());
        }
        results.get(level).add(root.val);
        if(root.left!=null){
            travel(root.left,level+1,results);
        }
        if(root.right!=null){
            travel(root.right,level+1,results);
        }
    }
```

BFS代码模板

* Python

``` 
def BFS(graph, start, end):
    visited = set()
    queue = [] 
    queue.append([start]) 
    while queue: 
        node = queue.pop() 
        visited.add(node)
        process(node) 
        nodes = generate_related_nodes(node) 
        queue.push(nodes)
    # other processing work 
    ...
```

* Java

``` 
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> allResults = new ArrayList<>();
    if (root == null) {
        return allResults;
    }
    Queue<TreeNode> nodes = new LinkedList<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
        int size = nodes.size();
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = nodes.poll();
            results.add(node.val);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
        allResults.add(results);
    }
    return allResults;
}
```

#### 2. 双向BFS

A*代码模板

* Python

``` 
def AstarSearch(graph, start, end):
    pq = collections.priority_queue() # 优先级 —> 估价函数
    pq.append([start]) 
    visited.add(start)
    while pq: 
        node = pq.pop() # can we add more intelligence here ?
        visited.add(node)
        process(node) 
        nodes = generate_related_nodes(node) 
   unvisited = [node for node in nodes if node not in visited]
        pq.push(unvisited)
```

## AVL树和红黑树的实现和特性

* 二叉树遍历（pre-order、In-order、Post-order）
* AVL树
    1. 发明者G.M.Adelson-Velsky和Evgenii Landis
    2. Banlance Factor（平衡因子）：
        - 是它的左子树的高度减去它的右子树的高度（有时相反）。
        - balance factor = {-1, 0, 1}
        - 通过旋转操作来进行平衡（四种）  
            1. 左旋
            2. 右旋
            3. 左右旋
            4. 右左旋

* Red-black Tree
    - 红黑树是一种近似平衡的二叉搜索树（Binary Search Tree），它能够确保任何一个结点的左右子树的高度差小于两倍。具体来说，红黑树是满足如下条件的二叉搜索树：
    - 每个节点要么是红色，要么是黑色
    - 根节点是黑色
    - 每个叶节点（NIL结点，空结点）是黑色的
    - 不能有相邻接的两个红色结点
    - 从任一结点到其每一个叶子的所有路径都包含相同数目的黑色结点。  
    - 关键性质：从根到叶子的最长的可能路径不多于最短的可能路径的两倍长。
