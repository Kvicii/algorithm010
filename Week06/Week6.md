## 最小路径和
```
public int minPathSum(int[][] grid) {
    /*
     * 重复问题
     *      当前位置到右下角的最小路径 = min(下一格路径, 右一格路径) + grid[i][j]
     */
    int row = grid.length;
    if (row == 0) return 0;
    int col = grid[0].length;
    // 状态数组
    int[][] dp = new int[row + 1][col + 1];
    for (int i = 0; i < row; i++) {
        dp[i][col] = Integer.MAX_VALUE;
    }
    Arrays.fill(dp[row], Integer.MAX_VALUE);
    dp[row - 1][col] = 0;
    dp[row][col - 1] = 0;
    // time: O(M * N)   space: O(M * N)
    for (int i = row - 1; i >= 0; i--) {
        for (int j = col - 1; j >= 0; j--) {
            // dp方程: f(i, j) = min(f(i, j + 1), f(i + 1, j)) + grid[i][j]
            dp[i][j] = Math.min(dp[i][j + 1], dp[i + 1][j]) + grid[i][j];
        }
    }
    return dp[0][0];
}
```

## 解码方法
```
public int numDecodings(String s) {
    /*
     * 重复问题:
     *      当前位置解码种数 = 前一位之前的解码种数 + 前两位之前的解码种数
     */
    int len = s.length();
    if (len == 0) return 0;
    // 状态数组
    int[] dp = new int[len];
    char[] charArr = s.toCharArray();
    if (charArr[0] == '0') return 0;
    dp[0] = 1;
    // time: O(n)   space: O(n)
    for (int i = 1; i < len; i++) {
        /*
         * dp方程
         *      f(i) = 0                        s(i) == 0
         *      f(i) = f(i - 1) + f(i - 2)      10 < s(i - 1, i) < 27 && s(i) != '0'
         *      f(i) = f(i - 1)                 !(10 < s(i - 1, i) < 27) && s(i) != '0'
         */
        if (charArr[i] != '0') dp[i] = dp[i - 1];
        int num = 10 * (charArr[i - 1] - '0') + charArr[i] - '0';
        if (num > 9 && num < 27) {
            if (i == 1) dp[i]++;
            else dp[i] += dp[i - 2];
        }
    }
    return dp[len - 1];
}
```

## 最大正方形
```
public int maximalSquare(char[][] matrix) {
    int row = matrix.length;
    if (row == 0) return 0;
    int col = matrix[0].length, maxSide = 0;
    // 状态数组
    int[][] dp = new int[row + 1][col + 1];
    // time: o(M * N)   space: O(M * N)
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++) {
            if (matrix[i][j] == '1') {
                /*
                 * dp方程: 
                 *  f(i, j) = min(min(f(i - 1, j), f(i, j - 1)), f(i - 1, j - 1)) + 1    matrix[i][j] == '1'
                 *  f(i, j) = 0                                                          others
                 */
                dp[i + 1][j + 1] = Math.min(Math.min(dp[i + 1][j], dp[i][j + 1]), dp[i][j]) + 1;
                maxSide = Math.max(maxSide, dp[i + 1][j + 1]);
            }
        }
    }
    return maxSide * maxSide;
}
```

## 任务调度器
```
public int leastInterval(char[] tasks, int n) {
    int[] nums = new int[26];
    for (int i = 0; i < tasks.length; i++) {
        nums[tasks[i] - 'A']++;
    }
    Arrays.sort(nums);
    int maxVal = nums[25] - 1, idelSlot = maxVal * n;
    for (int i = 24; i >= 0 && nums[i] > 0; i--) {
        idelSlot -= Math.min(nums[i], maxVal);
    }
    return idelSlot > 0 ? idelSlot + tasks.length : tasks.length;
}
```

## 回文子串
```
public int countSubstrings(String s) {
    if (s == null || s.length() == 0) return 0;
    int len = s.length();
    char[] charArr = s.toCharArray();
    // 状态数组
    boolean[][] dp = new boolean[len][len];
    int result = len;
    for (int i = 0; i < len; i++) {
        dp[i][i] = true;
    }
    // time: O(n ^ 2)       space: O(n ^ 2)
    for (int i = len - 1; i >= 0; i--) {
        for (int j = i + 1; j < len; j++) {
            // dp方程: f(i, j) = (j - i == 1) ? true : f(i + 1, j - 1)
            if (charArr[i] == charArr[j]) {
                dp[i][j] = j - i == 1 ? true : dp[i + 1][j - 1];
                if (dp[i][j]) result++;
            }
        }
    }
    return result;
}
```
