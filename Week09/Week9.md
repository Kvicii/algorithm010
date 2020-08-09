## 爬楼梯

**https://leetcode-cn.com/problems/climbing-stairs/**

* Go暴力递归

``` 
func climbStairs(n int) int {
    return recursive(n)
}

func recursive(n int) int {
    if n == 1 || n == 2 {
        return n
    }
    return recursive(n - 1) + recursive(n - 2)
}
```

* Go动态规划 - 使用额外空间存储

``` 
func climbStairs(n int) int {
    return dp(n)
}

func dp(n int) int {
    dp := make([]int, n + 1)
    dp[0] = 1
    dp[1] = 1
    for i := 2; i <= n; i++ {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n]
}
```

* Go动态规划二

``` 
func climbStairs(n int) int {
    return dp(n)
    
}

func dp(n int) int {
    p1 := 1
    p2 := 1
    for i := 2; i <= n; i++ {
        tmp := p1 + p2
        p1 = p2
        p2 = tmp
    }
    return p2
}
```

## 不同路径

**https://leetcode-cn.com/problems/unique-paths/**

* Go 动态规划二维数组存储中间结果

``` 
func uniquePaths(m int, n int) int {
    return dp(m, n)
}

func dp (m int, n int) int {
    // dp方程 dp[i][j] = dp[i + 1][j] + dp[i][j + 1]
    var dp [][]int
    for x := 0; x < m + 1; x++ {
        inner := make([]int, 0, n + 1)
        for y := 0; y < n + 1; y++ {
            inner = append(inner, 1)
        }
        dp = append(dp, inner)
    }
    for i := 1; i <= m; i++ {
        for j := 1; j <= n; j++ {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }
    return dp[m - 1][n - 1]
}
```

* Go 动态规划一维数组存储中间结果

``` 
func uniquePaths(m int, n int) int {
    return dp(m, n)
}

func dp(m int, n int) int {
    dp := make([]int, n + 1)
    dp[0] = 1
    for i := 1; i <= m; i++ {
        for j := 1; j <= n; j++ {
            dp[j] += dp[j - 1]
        }
    }
    return dp[n - 1]
}
```

## 打家劫舍

**https://leetcode-cn.com/problems/house-robber/**

* Java一维动态规划

``` 
class Solution {
    public int rob(int[] nums) {
        return dp(nums);
    }
    
    private int dp(int[] nums) {
        // dp方程 dp[i] = max(dp[i - 1], dp[i - 1] + nums[i])
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] += Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[nums.length - 1];
    }
}
```

* Java二维动态规划

``` 
class Solution {
    public int rob(int[] nums) {
        return dp(nums);
    }
    
    private int dp(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int len = nums.length;
        int[][] dp = new int[len][len];
        // dp方程 
        // dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1])
        // dp[i][1] = Math.max(dp[i - 1][0], dp[i - 2][1]) + nums[i]
        dp[0][0] = 0;
        dp[0][1] = nums[0];
        dp[1][0] = Math.max(dp[0][1], dp[0][0]);
        dp[1][1] = Math.max(nums[1], dp[1][0]);
        for (int i = 2; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = Math.max(dp[i - 1][0], dp[i - 2][1]) + nums[i];
        }
        return Math.max(dp[len - 1][0], dp[len - 1][1]);
    }
}
```

## 最小路径和

**https://leetcode-cn.com/problems/minimum-path-sum/**

* Java

``` 
class Solution {
    public int minPathSum(int[][] grid) {
        return dp(grid);
    }
    
    private int dp(int[][] grid) {
        // dp方程 dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
        int m = grid.length, n = grid[0].length;
        for (int i = 1; i < m; i++) grid[i][0] += grid[i - 1][0];
        for (int i = 1; i < n; i++) grid[0][i] += grid[0][i - 1];
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
        }
        return grid[m - 1][n - 1];
    }
}
```

## 买卖股票的最佳时机

**https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/**

* Java

``` 
class Solution {
    public int maxProfit(int[] prices) {
        return dp(prices);
    }
    
    private int dp(int[] prices) {
        // dp方程
        // dp[i][0] = max(dp[i - 1][0], dp[i - 1][1] + prices[i])
        // dp[i][1] = max(dp[i - 1][1], - prices[i])
        int len = prices.length;
        if (len < 2) return 0;
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[len - 1][0];
    }
}
```

## 使用最小花费爬楼梯

**https://leetcode-cn.com/problems/min-cost-climbing-stairs/**

* Java

``` 
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        // 1.dp with array
        // return dpWithArray(cost);
        // 2. dp 
        return dp(cost);
    }
    
    private int dp(int[] cost) {
        int p1 = 0, p2 = 0;
        for (int i = 0; i < cost.length; i++) {
            int p0 = Math.min(p1, p2) + cost[i];
            p1 = p2;
            p2 = p0;
        }
        return Math.min(p1, p2);
    }
    
    private int dpWithArray(int[] cost) {
        int l = cost.length;
        int[] n_cost = new int[l + 1];
        System.arraycopy(cost, 0, n_cost, 0, l);
        int[] dp = new int[l + 1];
        dp[0] = cost[0]; dp[1] = cost[1];
        for (int i = 2; i <= l; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + n_cost[i];
        }
        return dp[l];
    }
}
```

## 正则表达式匹配

**https://leetcode-cn.com/problems/regular-expression-matching/**

* Java

``` 
class Solution {
    public boolean isMatch(String s, String p) {
        // dp
        return dp(s, p);
    }
    
    private boolean dp(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            if (p.charAt(i - 1) == '*') dp[0][i] = dp[0][i - 2];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') dp[i][j] = dp[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*') {
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') dp[i][j] = dp[i][j - 2] || dp[i - 1][j] || dp[i][j - 1];
                    else dp[i][j] = dp[i][j - 2];
                }
            }
        }
        return dp[m][n];
    }
}
```

## 编辑距离

**https://leetcode-cn.com/problems/edit-distance/**

* Java

``` 
class Solution {
    public int minDistance(String word1, String word2) {
        // dp方程
        // 1.该位置字符相同 -> dp[i][j] = dp[i - 1][j - 1]
        // 2.该位置字符不同 -> min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) + 1
        return dp(word1, word2);
    }
    
    private int dp(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= n; i++) dp[0][i] = i;
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
            }
        }
        return dp[m][n];
    }
}
```

## 最长上升子序列

**https://leetcode-cn.com/problems/longest-increasing-subsequence/**
*Java

``` 
class Solution {
    public int lengthOfLIS(int[] nums) {
        // dp
        return dp(nums);
    }
    
    private int dp(int[] nums) {
        int m = nums.length, ans = 0;
        int[] dp = new int[m];
        if (m < 2) return m;
        Arrays.fill(dp, 1);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        for (int i = 0; i < dp.length; i++) ans = Math.max(dp[i], ans);
        return ans;
    }
}
```

## 解码方法

**https://leetcode-cn.com/problems/decode-ways/**

* Java

``` 
class Solution {
    public int numDecodings(String s) {
        // dp
        return dp(s);
    }
    
    private int dp(String s) {
        int l = s.length();
        int[] dp = new int[l + 1];
        dp[0] = 1;
        for (int i = 0; i < l; i++) {
            dp[i + 1] = s.charAt(i) == '0' ? 0 : dp[i];
            if (i > 0 && (s.charAt(i - 1) == '1' || (s.charAt(i - 1) == '2' && s.charAt(i) <= '6'))) dp[i + 1] += dp[i - 1];
        }
        return dp[l];
    }
}
```

## 不同的子序列

**https://leetcode-cn.com/problems/distinct-subsequences/**

* Java

``` 
class Solution {
    public int numDistinct(String s, String t) {
        // dp方程
        // s[i] == p[j] -> dp[i][j] = dp[i][j - 1]
        // s[i] != p[j] -> dp[i][j] = dp[i][j - 1] + dp[i - 1][j - 1]
        return dp(s, t);
    }
    
    private int dp(String s, String t) {
        int sLen = s.length(), tLen = t.length();
        int[][] dp = new int[tLen + 1][sLen + 1];
        for (int i = 0; i <= sLen; i++) dp[0][i] = 1;
        for (int i = 1; i <= tLen; i++) {
            for (int j = 1; j <= sLen; j++) {
                if (t.charAt(i - 1) == s.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1];
                else dp[i][j] = dp[i][j - 1];
            }
        }
        return dp[tLen][sLen];
    }
}
```

## 赛车

**https://leetcode-cn.com/problems/race-car/**

* Java

``` 
class Solution {
    public int racecar(int target) {
        int[] dp = new int[target + 3];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; dp[1] = 1; dp[2] = 4;

        for (int t = 3; t <= target; ++t) {
            int k = 32 - Integer.numberOfLeadingZeros(t);
            if (t == (1<<k) - 1) {
                dp[t] = k;
                continue;
            }
            for (int j = 0; j < k-1; ++j) dp[t] = Math.min(dp[t], dp[t - (1<<(k-1)) + (1<<j)] + k-1 + j + 2);
            if ((1<<k) - 1 - t < t) dp[t] = Math.min(dp[t], dp[(1<<k) - 1 - t] + k + 1);
        }
        return dp[target];  
    }
}
```

## 最大矩形

**https://leetcode-cn.com/problems/maximal-rectangle/**

* Java

``` 
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int rowLen = matrix.length, colLen = matrix[0].length;
        int[][] dp = new int[rowLen][colLen];
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (matrix[i][j] == '1') dp[i][j] = 1;
            }
        }
        for (int i = 1; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (dp[i - 1][j] >= 1) {
                    if (dp[i][j] == 1) dp[i][j] = dp[i - 1][j] + 1;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (dp[i][j] == 0) continue;
                int heigth = dp[i][j];
                for (int k = j; k >= 0 && dp[i][k] > 0; k--) {
                    int width = j - k + 1;
                    heigth = Math.min(dp[i][k], heigth);
                    ans = Math.max(ans, Math.max(dp[i][k], heigth * width));
                }
            }
        }
        return ans;
    }
}
```

## 最长有效括号

**https://leetcode-cn.com/problems/longest-valid-parentheses/**

* Java

``` 

class Solution {

    public int longestValidParentheses(String s) {
        // dp
        // return dp(s);
        // stack
        // return forceStack(s);
        // 双向遍历
        return forceTwoWayTraversal(s);
    }
    
    private int dp(String s) {
        int sLen = s.length(), ans = 0;
        int[] dp = new int[sLen];
        for (int i = 1; i < sLen; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2; 
                else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') dp[i] = dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] -2] : 0) + 2;
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
    
    private int forceStack(String s) {
        int sLen = s.length(), ans = 0;
        Stack<Integer> stack = new Stack();
        stack.push(-1);
        for (int i = 0; i < sLen; i++) {
            if (s.charAt(i) == '(') stack.push(i);
            else {
                stack.pop();
                if (stack.isEmpty()) stack.push(i);
                ans = Math.max(ans, i - stack.peek());
            }
        }
        return ans;
    }
    
    private int forceTwoWayTraversal(String s) {
        int left = 0, right = 0, ans = 0, sLen = s.length();
        for (int i = 0; i < sLen; i++) {
            if (s.charAt(i) == '(') left++;
            else right++;
            if (left == right) ans = Math.max(ans, 2 * right);
            else if (right > left) left = right = 0;
        }
        left = right = 0;
        for (int i = sLen - 1; i >= 0; i--) {
            if (s.charAt(i) == ')') right++;
            else left++;
            if (left == right) ans = Math.max(ans, 2 * left);
            else if (left > right) left = right = 0;
        }
        return ans;
    }

}

```

## 转换成小写字母

**https://leetcode-cn.com/problems/to-lower-case/**

* Java

``` 

class Solution {

    public String toLowerCase(String str) {
        // 1.ascii比较
        char[] ans = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 65 && str.charAt(i) <= 90) ans[i] = (char) (str.charAt(i) + 32);
            else ans[i] = str.charAt(i);
        }
        return new String(ans);
        // 2.直接调用api
        // return str.toLowerCase();
    }

}

```

## 最后一个单词的长度

**https://leetcode-cn.com/problems/length-of-last-word/**

* Java

``` 

class Solution {

    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) return 0;
        int ans = 0;
        s = s.trim();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') ans++;
            else break;
        }
        return ans;
    }

}

```

## 宝石与石头

**https://leetcode-cn.com/problems/jewels-and-stones/**

* Java

``` 

class Solution {

    public int numJewelsInStones(String J, String S) {
        if (J == null || J.length() == 0) return 0;
        if (S == null || S.length() == 0) return 0;
        
        byte[] ans = new byte[58];  // 122 - 65
        int res = 0;
        for (char ch : J.toCharArray()) ans[ch - 'A'] = 1;
        for (char ch : S.toCharArray()) {
            if (ans[ch - 'A'] == 1) res++;
        }
        return res;
    }

}

```

## 字符串中的第一个唯一字符

**https://leetcode-cn.com/problems/first-unique-character-in-a-string/**

* Java

``` 

class Solution {

    public int firstUniqChar(String s) {
        // 1.map
        // return dealWithHash(s);
        // 2.array
        return dealWithArray(s);
    }
    
    private int dealWithArray(String s) {
        int[] ans = new int[26];
        for (int i = 0; i < s.length(); i++) ans[s.charAt(i) - 'a'] += 1;
        for (int i = 0; i < s.length(); i++) {
            if (ans[s.charAt(i) - 'a'] == 1) return i;
        }
        return -1;
    }
    
    private int dealWithHash(String s) {
        Map<Character, Integer> ans = new HashMap();
        for (int i = 0; i < s.length(); i++) ans.put(s.charAt(i), ans.getOrDefault(s.charAt(i), 0) + 1);
        for(int i = 0; i < s.length(); i++) {
            if (ans.get(s.charAt(i)) == 1) return i;
        }
        return -1; 
    }

}

```

## 字符串转换整数 (atoi)

**https://leetcode-cn.com/problems/string-to-integer-atoi/**

* Java

``` 

class Solution {

    public int myAtoi(String str) {
        if (str == null || str.isEmpty()) return 0;
        int len = str.length(), index = 0, total = 0, sign = 0;
        while (index < len && str.charAt(index) == ' ') index++;
        if (index >= len) return 0;
        if (str.charAt(index) == '-' || str.charAt(index) == '+') {
            sign = str.charAt(index) == '-' ? -1 : 1;
            index++;
        } else if (str.charAt(index) - '0' > 9 || str.charAt(index) - '0' < 0) return 0;
        else sign = 1;
        while (index < len) {
            int number = str.charAt(index++) - '0';
            if (number < 0 || number > 9) break;
            if (total > Integer.MAX_VALUE / 10 || total == Integer.MAX_VALUE / 10 && number > Integer.MAX_VALUE % 10) return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            total = total * 10 + number;
        }
        return sign * total;
    }

}

```

## 最长公共前缀

**https://leetcode-cn.com/problems/longest-common-prefix/**

* Java

``` 

class Solution {

    
    private static class Trie {
        private Trie[] next;
        private boolean isEnd;
        
        public Trie() {
            this.next = new Trie[26];
            this.isEnd = false;
        }
        
        public void insert(String word) {
            if (word == null || word.isEmpty()) return;
            Trie curr = this;
            for (char ch : word.toCharArray()) {
                if (curr.next[ch - 'a'] == null) curr.next[ch - 'a'] = new Trie();
                curr = curr.next[ch - 'a'];
            }
            curr.isEnd = true;
        }
    }
    
    public String longestCommonPrefix(String[] strs) {
        // 1. 
        // return commonPrefix(strs);
        // 2.Trie
        return searchByTrie(strs);
    }
    
    private String searchByTrie(String[] strs) {
        Trie curr = new Trie();
        for (String word : strs) {
            if (word.isEmpty()) return "";
            curr.insert(word);
        }
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            int count = 0;
            char ch = '0';
            for (int i = 0; i < 26; i++) {
                if (curr.next[i] != null) {
                    count++;
                    ch = (char) (i + 'a');
                }
            }
            if (count == 1) {
                sb.append(ch);
                curr = curr.next[ch - 'a'];
                if (curr.isEnd) break;
            } else break;
        }
        return sb.toString();
    }
    
    private String commonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char ch = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != ch) return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }

}

```

## 反转字符串

**https://leetcode-cn.com/problems/reverse-string/**

* Java

``` 

class Solution {

    public void reverseString(char[] s) {
        if (s == null || s.isEmpty()) return;
        for (int i = 0, j = s.length - 1; i < j; i++, j--) {
            char tmp = s[i]; s[i] = s[j]; s[j] = tmp;
        }
    }

}

```

## 反转字符串 II

**https://leetcode-cn.com/problems/reverse-string-ii/**

* Java

``` 

class Solution {

    public String reverseStr(String s, int k) {
        
        if (s == null || s.isEmpty()) return s;
        char[] chArr = s.toCharArray();
        for (int i = 0; i < chArr.length; i += 2 * k) {
            int left = i, right = Math.min(chArr.length - 1, left + k - 1);
            while (left < right) {
                char tmp = chArr[left]; chArr[left] = chArr[right]; chArr[right] = tmp;
                left++; right--;
            }
        }
        return String.valueOf(chArr);
    }

}

```

## 翻转字符串里的单词

**https://leetcode-cn.com/problems/reverse-words-in-a-string/**

* Java

``` 

class Solution {

    public String reverseWords(String s) {
        String[] words = s.trim().split(" +");
        Collections.reverse(Arrays.asList(words));
        return String.join(" ", words);
    }

}

```

## 仅仅反转字母

**https://leetcode-cn.com/problems/reverse-only-letters/**

* Java

``` 

class Solution {

    public String reverseOnlyLetters(String S) {
        if (S == null || S.isEmpty()) return S;
        char[] chArr = S.toCharArray();
        for (int i = 0, j = chArr.length - 1; i < j; ) {
            if (!Character.isLetter(chArr[i])) i++;
            if (!Character.isLetter(chArr[j])) j--;
            if (Character.isLetter(chArr[i]) && Character.isLetter(chArr[j])) {
                char tmp = chArr[i]; chArr[i] = chArr[j]; chArr[j] = tmp;
                i++; j--;
            }
        }
        return new String(chArr);
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

## 字母异位词分组

**https://leetcode-cn.com/problems/group-anagrams/**

* Java

``` 

class Solution {

    public List<List<String>> groupAnagrams(String[] strs) {

        return resolveByHashMap(strs);
    }
    
    private List<List<String>> resolveByHashMap(String[] strs) {
        
        Map<String, List<String>> ans = new HashMap();
        for (String str : strs) {
            char[] chArr = str.toCharArray();
            Arrays.sort(chArr);
            List<String> tmp = ans.getOrDefault(new String(chArr), new ArrayList());
            tmp.add(str);
            ans.put(new String(chArr), tmp);
        }
        return new ArrayList(ans.values());
    }

}

```

## 找到字符串中所有字母异位词

**https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/**

* Java

``` 

class Solution {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList();
        int[] target = new int[26], window = new int[26];
        int left = 0, right = 0, total = p.length();
        for (char ch : p.toCharArray()) target[ch - 'a']++;
        while (right < s.length()) {
            int index_r = s.charAt(right) - 'a';
            if (target[index_r] > 0) {
                window[index_r]++;
                if (window[index_r] <= target[index_r]) total--;
            }
            while (total == 0) {
                if (right - left + 1 == p.length()) ans.add(left);
                int index_l = s.charAt(left) - 'a';
                if (target[index_l] > 0) {
                    window[index_l]--;
                    if (window[index_l] < target[index_l]) total++;
                }
                left++;
            }
            right++;
        }
        return ans;
    }

}

```

* Go

``` 

func findAnagrams(s string, p string) []int {

	// 滑动窗口
	ans := []int{}
	target := make([]int, 26)
	window := make([]int, 26)
	left := 0
	right := 0
	total := len(p)
	for _, ch := range p {
		target[ch-'a']++
	}
	for right < len(s) {
		indexR := s[right] - 'a'
		if target[indexR] > 0 {
			window[indexR]++
			if window[indexR] <= target[indexR] {
				total--
			}
		}
		for total == 0 {
			if right-left+1 == len(p) {
				ans = append(ans, left)
			}
			indexL := s[left] - 'a'
			if target[indexL] > 0 {
				window[indexL]--
				if window[indexL] < target[indexL] {
					total++
				}
			}
			left++
		}
		right++
	}
	return ans

}

```

## 验证回文串

**https://leetcode-cn.com/problems/valid-palindrome/**

* Java

``` 

class Solution {

    public boolean isPalindrome(String s) {
        
        if (s == null || s.isEmpty()) return true;
        s = s.replaceAll("[^a-z0-9A-Z]+", "").toLowerCase();
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
            left++; right--;
        }
        return true;
    }

}

```

## 
**https://leetcode-cn.com/problems/valid-palindrome-ii/**

* Java

``` 

class Solution {

    public boolean validPalindrome(String s) {
        for (int left = 0, right = s.length() - 1; left < right; left++, right--) {
            if (s.charAt(left) != s.charAt(right)) return isValid(s, left + 1, right) || isValid(s, left, right - 1);
        }
        return true;
    }
    
    private boolean isValid(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }

}

```

## 最长公共子序列

**https://leetcode-cn.com/problems/longest-common-subsequence/**

* Java

``` 

class Solution {

    public int longestCommonSubsequence(String text1, String text2) {
        // dp方程
        // 1.当前位置字符相同 说明找到了相同子序列 dp[i][j] = dp[i - 1][j - 1] + 1
        // 2.当前位置字符不同 dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
        return dp(text1, text2);
    }
    
    private int dp(String text1, String text2) {
        int l1 = text1.length(), l2 = text2.length();
        int[][] dp = new int[l1 + 1][l2 + 1];
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[l1][l2];
    }

}

```

## 最长回文子串

**https://leetcode-cn.com/problems/longest-palindromic-substring/**

* Java

``` 

class Solution {

    private int left, right;
    public String longestPalindrome(String s) {
        // 1. optimized force
        // return optimizedForceMain(s);
        // 2. dp方程
        // 如果当前位置相同 dp[i][j] = s[i] == s[j] && (j - i < 2 || dp[i + 1][j - 1])
        return dp(s);
    }
    
    private String dp(String s) {
        int l = s.length();
        boolean[][] dp = new boolean[l][l];
        String ans = "";
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i; j < l; j++) {
                dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1]);
                if (dp[i][j] && j - i + 1 > ans.length()) ans = s.substring(i, j + 1);
            }
        }
        return ans;
    }
    
    private String optimizedForceMain(String s) {
        if (s == null || s.length() < 2) return s;
        int l = s.length();
        for (int i = 0; i < l - 1; i++) {
            optimizedForce(s, i, i);
            optimizedForce(s, i, i + 1);
        }
        return s.substring(left, left + right);
    }
    
    private void optimizedForce(String s, int i, int j) {
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--; j++;
        }
        if (right < j - i - 1) {
            left = i + 1;
            right = j - i - 1;
        }
    }

}

```

## 通配符匹配

**https://leetcode-cn.com/problems/wildcard-matching/**

* Java

``` 

class Solution {

    public boolean isMatch(String s, String p) {
        // 1. optimized force
        // return optimizedForce(s, p);
        // 2. dp方程
        // s[i] == p[j] || p[j] == '?' -> dp[i][j] = dp[i - 1][j - 1]
        // p[j] == '*' -> dp[i][j] = dp[i - 1][j] || dp[i][j - 1]
        // dp[0][0] == true, dp[0][j] -> s为空, 只有p == '*' 才为true
        // dp[i][0] == false, s非空, p为空自然全为false
        return dp(s, p);
    }
    
    private boolean dp(String s, String p) {
        int l1 = s.length(), l2 = p.length();
        boolean[][] dp = new boolean[l1 + 1][l2 + 1]; dp[0][0] = true;
        for (int i = 1; i <= l2; i++) dp[0][i] = dp[0][i - 1] && p.charAt(i - 1) == '*';
        for (int i = 1; i <= l1; i++) {
            for (int j = 1; j <= l2; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') dp[i][j] = dp[i - 1][j - 1];
                else if (p.charAt(j - 1) == '*') dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
            }
        }
        return dp[l1][l2];
    }
    
    private boolean optimizedForce(String s, String p) {
        int sIndex = 0, pIndex = 0, sFlagIndex = -1, pFlagIndex = -1, l1 = s.length(), l2 = p.length();
        while (sIndex < l1) {
            if (pIndex < l2 && (s.charAt(sIndex) == p.charAt(pIndex) || p.charAt(pIndex) == '?')) {
                sIndex++; pIndex++;
            } else if (pIndex < l2 && p.charAt(pIndex) == '*') {
                sFlagIndex = sIndex; pFlagIndex = pIndex++;
            } else if (sFlagIndex >= 0) {
                sIndex = ++sFlagIndex; pIndex = pFlagIndex + 1;
            } else return false;
        }
        while (pIndex < l2 && p.charAt(pIndex) == '*') pIndex++;
        return pIndex == l2;
    }

}
```
