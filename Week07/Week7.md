## 实现Trie模板

***https://leetcode-cn.com/problems/implement-trie-prefix-tree/***

* Go

``` 
type Trie struct {
	isEnd bool
	next  [26]*Trie
}

/** Initialize your data structure here. */
func Constructor() Trie {
	return Trie{}
}

/** Inserts a word into the trie. */
func (this *Trie) Insert(word string) {
	for _, v := range word {
		if this.next[v-'a'] == nil {
			this.next[v-'a'] = new(Trie)
		}
		this = this.next[v-'a']
	}
	this.isEnd = true
}

/** Returns if the word is in the trie. */
func (this *Trie) Search(word string) bool {
	for _, v := range word {
		if this.next[v-'a'] == nil {
			return false
		}
		this = this.next[v-'a']
	}
	return this.isEnd
}

/** Returns if there is any word in the trie that starts with the given prefix. */
func (this *Trie) StartsWith(prefix string) bool {
	for _, v := range prefix {
		if this.next[v-'a'] == nil {
			return false
		}
		this = this.next[v-'a']
	}
	return true
}
```

## 单词搜索 II

***https://leetcode-cn.com/problems/word-search-ii/***

* Go

``` 
func findWords(board [][]byte, words []string) []string {

	res := make([]string, 0)
	if len(words) == 0 {
		return res
	}
	trie := buildTrie(words)
	for i := 0; i < len(board); i++ {
		for j := 0; j < len(board[0]); j++ {
			dfs(board, i, j, &res, trie)
		}
	}
	return res
}

func buildTrie(words []string) *Trie {
	trie := &Trie{}
	for _, word := range words {
		p := trie
		for _, ch := range word {
			if p.next[ch-'a'] == nil {
				p.next[ch-'a'] = &Trie{}
			}
			p = p.next[ch-'a']
		}
		p.word = word
	}
	return trie
}

func dfs(board [][]byte, row int, col int, res *[]string, trie *Trie) {
	ch := board[row][col]
	if ch == '-' || trie.next[ch-'a'] == nil {
		return
	}
	trie = trie.next[ch-'a']
	if len(trie.word) != 0 {
		*res = append(*res, trie.word)
		trie.word = ""
	}
	board[row][col] = '-'
	if row > 0 {
		dfs(board, row-1, col, res, trie)
	}
	if row < len(board)-1 {
		dfs(board, row+1, col, res, trie)
	}
	if col > 0 {
		dfs(board, row, col-1, res, trie)
	}
	if col < len(board[0])-1 {
		dfs(board, row, col+1, res, trie)
	}
	board[row][col] = ch
}

type Trie struct {
	next [26]*Trie
	word string
}
```

## 朋友圈

***https://leetcode-cn.com/problems/friend-circles/***

* Java

``` 
class Solution {
    
    class UnionFind {
        
        private int count;
        
        private int[] data;
        
        public UnionFind(int n) {
            this.count = n;
            data = new int[n];
            for (int i = 0; i < n; i++) data[i] = i;
        }
        
        public int find(int p) {
            while (p != data[p]) {
                data[p] = data[data[p]];
                p = data[p];
            }
            return p;
        }
        
        public void union(int p, int q) {
            int pFind = find(p);
            int qFind = find(q);
            if (pFind == qFind) return;
            data[pFind] = qFind;
            count--;
        }
    }
    
    public int findCircleNum(int[][] M) {
        // dfs
        if (M == null || M.length == 0) return 0;
        // int res = 0;
        // int[] visited = new int[M.length];
        // for (int i = 0; i < M.length; i++) {
        //     if (visited[i] == 0) {
        //         dfs(M, visited, i);
        //         res++;
        //     }
        // }
        // return res;
        // 并查集
        UnionFind unionFind = new UnionFind(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.count;
    }
    
    private void dfs(int[][] M, int[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(M, visited, j);
            }
        }
    }
}
```

## 岛屿数量

***https://leetcode-cn.com/problems/number-of-islands/***

* Java

``` 
class Solution {
    public int numIslands(char[][] grid) {
        
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length, res = 0;
        UnionFind data = new UnionFind(grid, m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
        //             dfs(grid, i, j, m, n);
        //             bfs(grid, i, j, m, n);
        //             res++;
                    grid[i][j] = '0';
                    if (i + 1 < m && grid[i + 1][j] == '1') data.union(i * n + j, (i + 1) * n + j);
                    if (j + 1 < n && grid[i][j + 1] == '1') data.union(i * n + j, i * n + j + 1);
                }
            }
        }
        return data.count;
        // return res;
    }
    
    // 并查集
    class UnionFind {
        
        private int count;
        
        private int[] data;
        
        public UnionFind(char[][] grid, int m, int n) {
            data = new int[m * n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        data[i * n + j] = i * n + j;
                        count++;
                    }
                }
            }
        }
        
        public int find(int p) {
            while (p != data[p]) {
                data[p] = data[data[p]];
                p = data[p];
            }
            return p;
        }
        
        public void union(int p, int q) {
            int pFind = find(p);
            int qFind = find(q);
            if (pFind == qFind) return;
            data[pFind] = qFind;
            count--;
        }
    }
    
    // dfs
    private void dfs(char[][] grid, int row, int col, int m, int n) {
        if (row < 0 || row >= m || col < 0 || col >= n || grid[row][col] != '1') return;
        
        grid[row][col] = '2';
        dfs(grid, row + 1, col, m, n);
        dfs(grid, row - 1, col, m, n);
        dfs(grid, row, col + 1, m, n);
        dfs(grid, row, col - 1, m, n);
    }
    
    // bfs
    private void bfs(char[][] grid, int row, int col, int m, int n) {
        
        LinkedList<int[]> queue = new LinkedList();
        queue.addLast(new int[]{row, col});
        while (!queue.isEmpty()) {
            int[] index = queue.pollFirst();
            row = index[0];
            col = index[1];
            if (row >= 0 && row < m && col >= 0 && col < n && grid[row][col] == '1') {
                grid[row][col] = '2';
                queue.addLast(new int[]{row + 1, col});
                queue.addLast(new int[]{row - 1, col});
                queue.addLast(new int[]{row, col + 1});
                queue.addLast(new int[]{row, col - 1});
            }
        }
    }
}
```

## 被围绕的区域

***https://leetcode-cn.com/problems/surrounded-regions/***

* Java

``` 
class Solution {
    public void solve(char[][] board) {
        
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;
        UnionFind data = new UnionFind(board, m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 'O') continue;
                    if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                        data.union(i * n + j, m * n);
                        continue;
                    }
                    if (i - 1 >= 0 && board[i - 1][j] == 'O') data.union(i * n + j, (i - 1) * n + j);
                    if (i + 1 < m && board[i + 1][j] == 'O') data.union(i * n + j, (i + 1) * n + j);
                    if (j - 1 >= 0 && board[i][j - 1] == 'O') data.union(i * n + j, i * n + j - 1);
                    if (j + 1 < n && board[i][j + 1] == 'O') data.union(i * n + j, i * n + j + 1);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!data.connect(i * n + j, m * n)) board[i][j] = 'X';
                else board[i][j] = 'O';
            }
        }
    }
    
    class UnionFind {
        
        private int count;
        
        private int[] data;
        
        public UnionFind(char[][] board, int m, int n) {
            this.count = m * n;
            data = new int[count + 1];
            for (int i = 0; i < count + 1; i++) data[i] = i;
        }
        
        public int find(int p) {
            while (p != data[p]) {
                data[p] = data[data[p]];
                p = data[p];
            }
            return p;
        }
            
        public void union(int p, int q) {
            int pFind = find(p);
            int qFind = find(q);
            if (pFind == qFind) return;
            data[pFind] = qFind;
            count--;
        }
        
        public boolean connect(int p, int q) {
            return find(p) == find(q);
        }
    }
}
```

## 爬楼梯

***https://leetcode-cn.com/problems/climbing-stairs/***

* Java

``` 
class Solution {
    public int climbStairs(int n) {
        // recursive
        // if (n == 1 || n == 2) return n;
        // return climbStairs(n - 1) + climbStairs(n - 2);
        // 数组剪枝
        // if (n == 1) return n;
        // int[] res = new int[n + 1];
        // res[0] = 1;
        // res[1] = 1;
        // res[2] = 2;
        // for (int i = 2; i <= n; i++) {
        //     res[i] = res[i - 1] + res[i - 2];
        // }
        // return res[n];
        // 只保留需要移动的值
        int first = 1;
        int second = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = first + second;
            first = second;
            second = tmp;
        }
        return second;
    }
}
```

## 括号生成

***https://leetcode-cn.com/problems/generate-parentheses/***

* Go DFS

``` 
func generateParenthesis(n int) []string {
	// dfs
	res := make([]string, 0)
	if n == 0 {
		return res
	}
	s := ""
	dfs(&s, &res, n, 0, 0)
	return res
}

func dfs(str *string, res *[]string, n int, left int, right int) {

	if n == left && n == right {
		*res = append(*res, *str)
		return
	}
	if left < n {
		leftStr := *str + "("
		dfs(&leftStr, res, n, left+1, right)
	}
	if right < left {
		rightStr := *str + ")"
		dfs(&rightStr, res, n, left, right+1)
	}
}
```

* Java DFS BFS DP

``` 
class Solution {
    class Node {
        public int left;
        public int right;
        public String val;

        public Node(int left, int right, String val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
    }
    public List<String> generateParenthesis(int n) {

        // List<String> res = new ArrayList();
        // dfs(0, 0, res, n, "");
        // return res;
        // return bfs(n);
        
        // dp
        List<List<String>> dp = new ArrayList();
        dp.add(Arrays.asList(""));
        
        for (int i = 1; i <= n; i++) {
            List<String> queue = new LinkedList();
            for (int j = 0; j < i; j++) {
                List<String> head = dp.get(j);
                List<String> tail = dp.get(i - j - 1);
                for (int x = 0; x < head.size(); x++) {
                    for (int y = 0; y < tail.size(); y++) {
                        queue.add("(" + head.get(x) + ")" + tail.get(y));
                    }
                }
            }
            dp.add(queue);
        }
        return dp.get(n);
    }

    private void dfs(int left, int right, List<String> res, int depth, String str) {

        if (depth == left && depth == right) {
            res.add(str);
            return;
        }
        if (left < depth) dfs(left + 1, right, res, depth, str + "(");
        if (right < left) dfs(left, right + 1, res, depth, str + ")");
    }
    
    private List<String> bfs(int n) {
        List<String> res = new ArrayList();
        LinkedList<Node> queue = new LinkedList();
        queue.addLast(new Node(0, 0, ""));
        
        while (!queue.isEmpty()) {
            Node node = queue.pollFirst();
            if (node.left == n && node.right == n) {
                res.add(node.val);
                continue;
            }
            if (node.left < n) queue.addLast(new Node(node.left + 1, node.right, node.val + "("));
            if (node.right < node.left) queue.addLast(new Node(node.left, node.right + 1, node.val + ")"));
        }
        return res;
    }
}
```

## N皇后

***https://leetcode-cn.com/problems/n-queens/***

* Java

``` 
class Solution {

    List<List<String>> res = new ArrayList();

    public List<List<String>> solveNQueens(int n) {
        if (n == 0) return res;
        int[][] board = new int[n][n];
        dfs(board, 0, n);
        return res;
    }
    
    private void dfs(int[][] board, int row, int n) {
        if (row == n) {
            res.add(draw(board, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isUsable(board, row, col)) {
                board[row][col] = 1;
                dfs(board, row + 1, n);
                board[row][col] = 0;
            }
        }
    }
    
    private boolean isUsable(int[][] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }
        for (int i = col - 1; i >= 0; i--) {
            if (row - col + i < 0) break;
            if (board[row - col + i][i] == 1) return false;
        }
        for (int i = col + 1; i < board.length; i++) {
            if (row + col - i < 0) break;
            if (board[row + col - i][i] == 1) return false;
        }
        return true;
    }
    
    private List<String> draw(int[][] board, int n) {
        List<String> drawList = new ArrayList();
        for (int row = 0; row < n; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 0) sb.append(".");
                else sb.append("Q");
            }
            drawList.add(sb.toString());
        }
        return drawList;
    }
}
```

## 有效的数独

***https://leetcode-cn.com/problems/valid-sudoku/description/***

* Java

``` 
class Solution {
    public boolean isValidSudoku(char[][] board) {
        int[][] row = new int[9][10];
        int[][] col = new int[9][10];
        int[][] box = new int[9][10];
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int curr = board[i][j] - '0';
                if (row[i][curr] == 1) return false;
                if (col[j][curr] == 1) return false;
                if (box[(i / 3) * 3 + j / 3][curr] == 1) return false;
                row[i][curr] = 1;
                col[j][curr] = 1;
                box[(i / 3) * 3 + j / 3][curr] = 1;
            }
        }
        return true;
    }
}
```

## 解数独

***https://leetcode-cn.com/problems/sudoku-solver/#/description***

* Java

``` 
class Solution {
    public void solveSudoku(char[][] board) {
        
        if (board == null || board.length == 0) return;
        int m = board.length;
        dfs(board, m);
    }
    
    private boolean dfs(char[][] board, int m) {
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '.') {
                    for (char ch = '1'; ch <= '9'; ch++) {
                        if (valid(board, i, j, ch)) {
                            board[i][j] = ch;
                            if (dfs(board, m)) return true;
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean valid(char[][] board, int row, int col, char c) {
        
        for (int i = 0; i < 9; i++) {
            if (board[row][i] != '.' && board[row][i] == c) return false;
            if (board[i][col] != '.' && board[i][col] == c) return false;
            if (board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] != '.' && board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] == c) return false;
        }
        return true;
    }
}
```

## 单词接龙

***https://leetcode-cn.com/problems/word-ladder/***

* Java

``` 
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
        // 双向bfs
        if (!wordList.contains(endWord)) return 0;
        
        Set<String> beginWordSet = new HashSet(Collections.singleton(beginWord));
        Set<String> endWordSet = new HashSet(Collections.singleton(endWord));
        Set<String> wordSet = new HashSet(wordList);
        
        int recusive = 1;
        Set<String> visited = new HashSet();
        
        while (!beginWordSet.isEmpty() && !endWordSet.isEmpty()) {
            if (beginWordSet.size() > endWordSet.size()) {
                Set<String> swapSet = endWordSet;
                endWordSet = beginWordSet;
                beginWordSet = swapSet;
            }
            Set<String> recusiveSet = new HashSet();
            for (String word : beginWordSet) {
                char[] wordArr = word.toCharArray();
                for (int i = 0; i < wordArr.length; i++) {
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        char sourceCh = wordArr[i];
                        wordArr[i] = ch;
                        String targetStr = new String(wordArr);
                        if (endWordSet.contains(targetStr)) return recusive + 1;
                        if (!visited.contains(targetStr) && wordSet.contains(targetStr)) {
                            recusiveSet.add(targetStr);
                            visited.add(targetStr);
                        }
                        wordArr[i] = sourceCh;
                    }
                }
            }
            recusive++;
            beginWordSet = recusiveSet;
        }
        return 0;
    }
}
```

## 最小基因变化

***https://leetcode-cn.com/problems/minimum-genetic-mutation/***

* Java

``` 
class Solution {

    char[] targetArr = new char[]{'A', 'C', 'G', 'T'};
    int level = Integer.MAX_VALUE;

    public int minMutation(String start, String end, String[] bank) {

        // 1.bfs
        // return bfs(start, end, bank);
        // 2.dfs
        Set<String> set = new HashSet(Arrays.asList(bank));
        if (start == null || start == "" || end == null || end == "" || set.isEmpty()) return -1;
        if (!set.contains(end)) return -1;
        if (set.contains(start)) set.remove(start);
        dfs(start, end, set, 0);
        return level == Integer.MAX_VALUE ? -1 : level;
    }

    private int bfs(String start, String end, String[] bank) {

        Set<String> set = new HashSet(Arrays.asList(bank));
        if (start == null || start == "" || end == null || end == "" || bank == null || bank.length < 1) return -1;
        if (!set.contains(end)) return -1;
        if (set.contains(start)) set.remove(start);

        LinkedList<String> queue = new LinkedList();
        queue.addFirst(start);
        int step = 0;

        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                char[] sourceArr = queue.pollFirst().toCharArray();
                for (int j = 0; j < sourceArr.length; j++) {
                    char sourceChar = sourceArr[j];
                    for (int k = 0; k < targetArr.length; k++) {
                        sourceArr[j] = targetArr[k];
                        String targetStr = new String(sourceArr);
                        if (targetStr.equals(end)) return step;
                        else if (set.contains(targetStr)) {
                            queue.addFirst(targetStr);
                            set.remove(targetStr);
                        } else {
                            sourceArr[j] = sourceChar;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void dfs(String start, String end, Set<String> set, int depth) {
        
        if (start.equals(end)) {
            level = Math.min(level, depth);
            return;
        }

        char[] sourceArr = start.toCharArray();
        for (int i = 0; i < sourceArr.length; i++) {
            char sourceChar = sourceArr[i];
            for (int j = 0; j < targetArr.length; j++) {
                sourceArr[i] = targetArr[j];
                String targetStr = new String(sourceArr);
                if (set.contains(targetStr)) {
                    set.remove(targetStr);
                    dfs(targetStr, end, set, depth + 1);
                }
            }
            sourceArr[i] = sourceChar;
        }
    }
}
```

## 双向BFS模板

``` 
void BFS(){
    queue<state> Q[2];
    vis[2];
    Q[0].push;Q[1].push();//起终状态入队
    vis[0]=vis[1]=1;//标志起终状态
    int deep=0;
    while(!Q[0].empty()||!Q[1].empty()){
        int i=0;
        while(i<2){
            state tp=Q[i].front();
            if(tp.step!=deep){i++;continue;}
            Q[i].pop();
            for(.....)
            if(vis[1-i]) {cout<<(deep*2+i+1)<<endl;return;//找到解
            if(vis[i]) continue;//状态已经存在
            Q[i].push();
            vis[i]=1;
            }
        }
    deep++;
    }
}
```

## 单词搜索2的时间复杂度
将k称为一个节点下的最大邻居数，d是从起点到终点的距离。 在传统的BFS中，在每个BFS级别上探索k个节点，每个节点在最坏的情况下都会生成k个邻居，直到找到结尾。 因此，探索直到到达终点的最大节点数是k * k * k ... * k，d次。 所以是O（k ^ d）。
在双端BFS中，当向前和向后搜索发生冲突时，即完成搜索。 他们大约在d / 2距离处相遇，将此点称为中间点。 因此它是O(k ^（d / 2)) + O(k ^（d / 2))，即**开始到中间**的过程和**结束到中间**的过程，它与标准BFS的复杂度相同，但是大量的数据，双端BFS可能会更快地提供正确的结果。