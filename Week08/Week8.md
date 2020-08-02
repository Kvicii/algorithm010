## 位1的个数
**https://leetcode-cn.com/problems/number-of-1-bits/**
* Go
```
func hammingWeight(num uint32) int {
    // & 运算
    // res := 0
    // for num != 0 {
    //     if (num & 1) == 1 {
    //         res++
    //     }
    //     num = num >> 1
    // }
    // return res
    // 最后一位置0
    res := 0
    for num != 0 {
        res++
        num = num & (num - 1)
    }
    return res
}
```

## 2的幂
**https://leetcode-cn.com/problems/power-of-two/**
* Go
```
func isPowerOfTwo(n int) bool {
    
    // if n <= 0 {
    //     return false
    // }
    // for (n > 1) {
    //     if n & 1 == 1 {
    //         return false
    //     }
    //     n = n >> 1
    // }
    // return true
    return n > 0 && n & (n - 1) == 0
}
```

## 颠倒二进制位
 **https://leetcode-cn.com/problems/reverse-bits/**
 * Java
```
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res = (res << 1) + (n & 1);
            n >>= 1;
        }
        return res;
    }
}
```

## N皇后
**https://leetcode-cn.com/problems/n-queens/**
* Java DFS
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

## N皇后 II
**https://leetcode-cn.com/problems/n-queens-ii/description/**
* Java 位运算
```
class Solution {
    
    private int count;
    
    private int size;
    
    public int totalNQueens(int n) {
        count = 0;
        // 00000000...11111111
        size = (1 << n) - 1;
        solve(0, 0, 0);
        return count;
    }
    
    private void solve(int row, int lowerLeft, int upperRight) {
        if (row == size) {  // 初始没有皇后
            count++;
            return;
        }
        int pos = (~(row | lowerLeft | upperRight)) & size; // 当前还可以放的皇后数
        while (pos != 0) {
            int last = pos & (-pos);    // 获取最后一个可放皇后的位置
            pos -= last;    // 更新剩余的可放皇后位置
            solve(row | last, (lowerLeft | last) << 1, (upperRight | last) >> 1);   // 下一层
        }
    }
}
```

## 比特位计数
**https://leetcode-cn.com/problems/counting-bits/**
* Java
```
class Solution {
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            res[i] = res[i & (i - 1)] + 1;
        }
        return res;
    }
}
```

##  LRU缓存机制
**https://leetcode-cn.com/problems/lru-cache/**
* Java
```
class LRUCache {
    
    private int capacity;
    
    private LinkedHashMap<Integer, Integer> map;

    public LRUCache(int capacity) {
        map = new LinkedHashMap(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return map.size() > capacity;
            }
        };
        this.capacity = capacity;
    }
    
    public int get(int key) {
        return map.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        map.put(key, value);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
```

* Go 构造双向链表
```
type Node struct {
	Key  int
	Val  int
	Prev *Node
	Next *Node
}

type LRUCache struct {
	Map      map[int]*Node
	capacity int
	First    *Node
	Last     *Node
}

func Constructor(capacity int) LRUCache {
	cache := LRUCache{
		Map:      make(map[int]*Node, capacity),
		capacity: capacity,
		First:    &Node{},
		Last:     &Node{},
	}
	cache.First.Next = cache.Last
	cache.Last.Prev = cache.First
	return cache
}

func (this *LRUCache) Get(key int) int {
	node, ok := this.Map[key]
	if !ok {
		return -1
	}
	remove(node)
	setHeader(this, node)
	return node.Val
}

func (this *LRUCache) Put(key int, value int) {
	node, ok := this.Map[key]
	if ok {
		remove(node)
	} else {
		if len(this.Map) == this.capacity {
			delete(this.Map, this.Last.Prev.Key)
			remove(this.Last.Prev)
		}
		node = &Node{Key: key, Val: value}
		this.Map[key] = node
	}
	node.Val = value
	setHeader(this, node)
}

func setHeader(this *LRUCache, node *Node) {
	this.First.Next.Prev = node
	node.Next = this.First.Next
	this.First.Next = node
	node.Prev = this.First
}

func remove(node *Node) {
	node.Prev.Next = node.Next
	node.Next.Prev = node.Prev
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * obj := Constructor(capacity);
 * param_1 := obj.Get(key);
 * obj.Put(key,value);
 */
```

## 数组的相对排序
**https://leetcode-cn.com/problems/relative-sort-array/**
* Java
```
class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        // 桶排序
        int[] bucket = new int[1001];
        for (int num : arr1) {
            bucket[num]++;
        }
        int i = 0;
        for (int num : arr2) {
            while (bucket[num]-- > 0) arr1[i++] = num;
        }
        for (int j = 0; j < 1001; j++) {
            while (bucket[j]-- > 0) arr1[i++] = j;
        }
        return arr1;
    }
}
```

## 有效的字母异位词
**https://leetcode-cn.com/problems/valid-anagram/**
* Java
```
class Solution {
    public boolean isAnagram(String s, String t) {
        // 1.HashMap
        // return resolveByHashMap(s, t);
        // sort
        // return resolveBySortAPI(s, t);
        // array bucket sort
        return resolveByArray(s, t);
    }
    
    private boolean resolveByArray(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] bucket = new int[26];
        for (int i = 0; i < s.length(); i++) {
            bucket[s.charAt(i) - 'a']++;
            bucket[t.charAt(i) - 'a']--;
        }
        for (int num : bucket) {
            if (num != 0) return false;
        }
        return true;
    }
    
    private boolean resolveBySortAPI(String s, String t) {
        char[] sArr = s.toCharArray();
        Arrays.sort(sArr);
        char[] tArr = t.toCharArray();
        Arrays.sort(tArr);
        return Arrays.equals(sArr, tArr);
    }
    
    private boolean resolveByHashMap(String s, String t) {
        Map<Character, Integer> map = new HashMap();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        for (char ch : t.toCharArray()) {
            int count = map.getOrDefault(ch, 0);
            if (count == 0) return false;
            map.put(ch, --count);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 0) return false;
        }
        return true;
    }
}
```

## 力扣排行榜
**https://leetcode-cn.com/problems/design-a-leaderboard/**
* Java
```
class Leaderboard {
    
    private Map<Integer, Integer> rank;

    public Leaderboard() {
        rank = new TreeMap();
    }
    
    public void addScore(int playerId, int score) {
        rank.put(playerId, rank.getOrDefault(playerId, 0) + score);
    }
    
    public int top(int K) {
        List<Integer> rankScore = new ArrayList(rank.values());
        Collections.sort(rankScore, (o1, o2) -> o2 - o1 > 0 ? 1 : o1 == o2 ? 0 : -1);
        int ans = 0;
        for (int i = 0; i < K; i++) ans+= rankScore.get(i);
        return ans;
    }
    
    public void reset(int playerId) {
        rank.remove(playerId);
    }
}

/**
 * Your Leaderboard object will be instantiated and called as such:
 * Leaderboard obj = new Leaderboard();
 * obj.addScore(playerId,score);
 * int param_2 = obj.top(K);
 * obj.reset(playerId);
 */
```

## 合并区间
**https://leetcode-cn.com/problems/merge-intervals/**
* Java
```
class Solution {
    public int[][] merge(int[][] intervals) {
        
        if (intervals == null || intervals.length == 0) return new int[][]{};
        Arrays.sort(intervals, Comparator.comparingInt(r -> r[0]));
        
        List<int[]> ans = new ArrayList();
        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i][1] >= intervals[i + 1][0]) {
                intervals[i + 1][0] = intervals[i][0];
                intervals[i + 1][1] = Math.max(intervals[i + 1][1], intervals[i][1]);
            } else ans.add(intervals[i]);
        }
        ans.add(intervals[intervals.length - 1]);
        return ans.toArray(new int[ans.size()][2]);
    }
}
```

## 翻转对
**https://leetcode-cn.com/problems/reverse-pairs/**
* Java
```
class Solution {
    
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int left, int right) {
        if (left >= right) return 0;
        int mid = left + ((right - left) >> 1);
        int count = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        
        int m = left, n = left, k = 0;
        int[] temp = new int[right - left + 1];
        for (int j = mid + 1; j <= right; j++, k++) {
            while (m <= mid && nums[m] /2.0 <= nums[j]) m++;
            while (n <= mid && nums[n] < nums[j]) temp[k++] = nums[n++];
            temp[k] = nums[j];
            count += mid - m + 1;
        }
        while (n <= mid) temp[k++] = nums[n++];
        System.arraycopy(temp, 0, nums, left, right - left + 1);
        return count;
    }
}
```