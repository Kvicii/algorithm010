# Week4作业

## 柠檬水找零
```
func lemonadeChange(bills []int) bool {

    five := 0
    ten := 0
    for _, bill := range(bills) {
        if bill == 5 {
            five++
        } else if bill == 10 {
            five--
            ten++
        } else {
            if ten > 0 {
                ten--
                five--
            } else {
                five -= 3
            }
        }
        if five < 0 {
            return false
        }
    }
    return true
}
```

## 买卖股票的最佳时机 II
```
class Solution {
    public int maxProfit(int[] prices) {
        // 低价买入 高价卖出
        if (prices == null || prices.length < 1) return 0;
        int total = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i + 1]) total += prices[i + 1] - prices[i];
        }
        return total;
    }
}
```

## 分发饼干
```
class Solution {
    public int findContentChildren(int[] g, int[] s) {

        if (g == null || g.length < 1 || s == null || s.length < 1) return 0;
        Arrays.sort(g);
        Arrays.sort(s);
        int sLen = 0, gLen = 0;
        while (sLen < s.length && gLen < g.length) {
            if (s[sLen] >= g[gLen]) gLen++;
            sLen++;
        }
        return gLen;
    }
}
```

## 模拟行走机器人
```
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {

        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int x = 0, y = 0, res = 0, direction = 0;
        Set<String> set = new HashSet();
        for (int[] obstacle : obstacles) {
            set.add(obstacle[0] + "-" + obstacle[1]);
        }
        for (int command : commands) {
            if (command == -1) direction = (direction + 1) % 4;
            else if (command == -2) direction = (direction + 3) % 4;
            else {
                while (command-- > 0) {
                    int nextX = x + directions[direction][0];
                    int nextY = y + directions[direction][1];
                    if (set.contains(nextX + "-" + nextY)) {
                        break;
                    }
                    x = nextX;
                    y = nextY;
                    res = Math.max(res, x * x + y * y);
                }
            }
        }
        return res;
    }
}
```

## 使用二分查找，寻找一个半有序数组 [4, 5, 6, 7, 0, 1, 2] 中间无序的地方
```
左边界索引为0，右边界索引为nums.length - 1，套用二分模板，计算中间位置索引mid,如果nums[mid] > nums[mid + 1]说明该位置是要找的点，返回mid + 1，否则继续执行循环：

class Solution {
    public int search(int[] nums) {
        int left = 0, right = nums.length - 1, mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] > nums[mid + 1]) return mid + 1;
            if (nums[mid] < nums[right]) right = mid;
            else left = mid;
        }
        return -1;
    }
}
```

## 单词接龙
```
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
        if (!wordList.contains(endWord)) return 0;
        return dfs(1, new HashSet(Collections.singleton(beginWord)), new HashSet(Collections.singleton(endWord)), new HashSet(wordList));
    }

    private int dfs (int level, Set<String> beginSet, Set<String> endSet, Set<String> wordDict){

        if (beginSet.isEmpty() || endSet.isEmpty()) return 0;
        wordDict.removeAll(beginSet);
        level++;
        Set<String> nextSet = new HashSet();
        
        for (String begin : beginSet) {
            char[] beginArr = begin.toCharArray();
            for (int i = 0; i < beginArr.length; i++) {
                char sourceChar = beginArr[i];
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    beginArr[i] = ch;
                    String target = new String(beginArr);
                    if (!wordDict.contains(target)) continue;
                    if (endSet.contains(target)) return level;
                    nextSet.add(target);
                }
                beginArr[i] = sourceChar;
            }
        }
        if (nextSet.size() <= endSet.size()) beginSet = nextSet;
        else {
            beginSet = endSet;
            endSet = nextSet;
        }
        return dfs(level, beginSet, endSet, wordDict);
    }
}
```

## 岛屿数量
```
class Solution {
    public int numIslands(char[][] grid) {
        
        int res = 0;
        if (grid == null || grid.length == 0 || grid[0].length == 0) return res;
        int row = grid.length, col = grid[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    bfs(grid, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int row, int col) {
        
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] != '1') return;

        grid[row][col] = '2';
        dfs(grid, row - 1, col);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
    }
    
    private void bfs(char[][] grid, int row, int col) {

        LinkedList<int[]> queue = new LinkedList();
        queue.add(new int[]{row, col});
        while (!queue.isEmpty()) {
            int[] index = queue.poll();
            row = index[0];
            col = index[1];
            if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == '1') {
                grid[row][col] = '2';
                queue.add(new int[]{row - 1, col});
                queue.add(new int[]{row + 1, col});
                queue.add(new int[]{row, col - 1});
                queue.add(new int[]{row, col + 1});
            }
        }
    }
}
```

## 扫雷游戏
```
class Solution {

    int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {
        
        dfs(board, click[0], click[1]);
        return board;
    }

    private void dfs(char[][] board, int x, int y) {

        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return;

        if (board[x][y] == 'E') {
            board[x][y] = 'B';
            int count = judge(board, x, y);
            if (count == 0) {
                for (int i = 0; i < 8; i++) {
                    dfs(board, dx[i] + x, dy[i] + y);
                }
            } else {
                board[x][y] = (char) (count + '0');
            }
        } else if (board[x][y] == 'M') {
            board[x][y] = 'X';
        }
    }

    private void bfs(char[][] board, int x, int y) {
        
        if (x < 0 || x >= board.length || y < 0 || y >= board.length) return;
        LinkedList<int[]> queue = new LinkedList();
        queue.add(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] indexArr = queue.poll();
            x = indexArr[0];
            y = indexArr[1];
            if (board[x][y] == 'E') {
                board[x][y] = 'B';
                int count = judge(board, x, y);
                if (count == 0) {
                    for (int i = 0; i < 8; i++) {
                        queue.add(new int[]{dx[i] + x, dy[i] + y});
                    }
                } else {
                    board[x][y] = (char) (count + '0');
                }
            } else {
                board[x][y] = 'M';
            }
        }
    }

    private int judge(char[][] board, int x, int y) {

        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nextX = dx[i] + x, nextY = dy[i]  + y;
            if (nextX < 0 || nextX >= board.length || nextY < 0 || nextY >= board[0].length) continue;
            if (board[nextX][nextY] == 'M') count++;
        }
        return count;
    }
}
```

## 跳跃游戏
```
func canJump(nums []int) bool {

    if len(nums) == 0 {
        return false
    }
    target := len(nums) - 1
    for i := len(nums) - 1; i >= 0; i-- {
        if (nums[i] + i >= target) {
            target = i
        }
    }
    return target == 0;
}
```

## 搜索旋转排序数组
```
func search(nums []int, target int) int {

    // 1.暴力 O(n)
    // for i := 0; i < len(nums); i++ {
    //     if nums[i] == target {
    //         return i
    //     }
    // }
    // return -1
    // 二分查找
    left := 0
    right := len(nums) - 1
    mid := 0
    for left <= right {
        mid = (left + right) / 2
        if nums[mid] == target {
            return mid
        }
        if nums[mid] >= nums[left] {
            if target >= nums[left] && target <= nums[mid] {
                right = mid - 1
            } else {
                left = mid + 1
            }
        } else {
            if target >= nums[mid] && target <= nums[right] {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
    }
    return -1;
}
```

## 搜索二维矩阵
```
func searchMatrix(matrix [][]int, target int) bool {

    // 二维数组二分
    if len(matrix) == 0 || len(matrix[0]) == 0 {
        return false
    }
    left := 0
    row := len(matrix)
    col := len(matrix[0])
    right := row * col - 1
    mid := 0
    
    for left <= right {
        mid = (left + right) / 2
        if target == matrix[mid / col][mid % col] {
            return true
        }
        if target < matrix[mid / col][mid % col] {
            right = mid - 1
        } else {
            left = mid + 1
        }
    }
    return false
}
```

## 寻找旋转排序数组中的最小值
```
class Solution {
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        int left = 0, right = nums.length - 1, mid = 0;
        // if (nums.length == 1) return nums[0];
        while (left <= right) {
            mid = (right + left) / 2;
            if (nums[mid] < nums[right]) right = mid;
            else left = mid + 1;
        }
        return nums[mid];
    }
}
```

## 单词接龙 II
```
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        // bfs
        List<List<String>> res = new ArrayList();
        if (!wordList.contains(endWord)) return res;
        
        LinkedList<List<String>> queue = new LinkedList();
        queue.add(Arrays.asList(beginWord));
        boolean flag = false;
        Set<String> visited = new HashSet();
        Set<String> wordDict = new HashSet(wordList);

        while (!queue.isEmpty() && !flag) {

            Set<String> levelVisited = new HashSet();
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                List<String> last = queue.poll();
                String word = last.get(last.size() - 1);
                char[] wordArr = word.toCharArray();
                for (int j = 0; j < wordArr.length; j++) {
                    char sourceChar = wordArr[j];
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        if (ch == sourceChar) continue;
                        wordArr[j] = ch;
                        String target = new String(wordArr);
                        if (wordDict.contains(target) && !visited.contains(target)) {
                            List<String> tmpList = new ArrayList(last);
                            tmpList.add(target);
                            if (target.equals(endWord)) {
                                flag = true;
                                res.add(tmpList);
                            }
                            queue.add(tmpList);
                            levelVisited.add(target);
                        }
                    }
                    wordArr[j] = sourceChar;
                }
            }
            visited.addAll(levelVisited);
        }
        return res;
    }
}
```

## 跳跃游戏 II
```
class Solution {
    public int jump(int[] nums) {

        int res = 0, maxPosition = 0, end = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPosition = Math.max(maxPosition, nums[i] + i);
            if (end == i) {
                end = maxPosition;
                res++;
            }
        }
        return res;
    }
}
```