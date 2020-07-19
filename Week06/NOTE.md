学习笔记

## 递归、分治、回溯、动态规划

**本质**：将问题分解为子问题，同时寻找重复性。

注意：

* 避免人肉递归（人脑记忆有限）
* 找到最近、最简方法，将其分解为可重复解决的问题 
* 数学归纳法思维

### 递归
```
// Java
public void recur(int level, int param) { 
	// terminator 
	if (level > MAX_LEVEL) { 
	      // process result 
	      return; 
	}
	// process current logic 
	process(level, param); 
	// drill down 
	recur( level: level + 1, newParam); 
	// restore current status 	 
}
```
	
### 分治

大的问题具有重复性（自相似性），将大的问题分解为相同的小的子问题，分别运算，聚合结果

```
# Python
def divide_conquer(problem, param1, param2, ...): 
  # recursion terminator 
  if problem is None: 
	print_result 
	return 
  # prepare data 拆分子问题
  data = prepare_data(problem) 
  subproblems = split_problem(problem, data) 
  # conquer subproblems 调递归函数
  subresult1 = self.divide_conquer(subproblems[0], p1, ...) 
  subresult2 = self.divide_conquer(subproblems[1], p1, ...) 
  subresult3 = self.divide_conquer(subproblems[2], p1, ...) 
  …
  # process and generate the final result 合并
  result = process_result(subresult1, subresult2, subresult3, …)
	
  # revert the current level states 恢复当前层状态
```

### 动态规划

动态递推（Dynamic programming）

simplifying a complicated problem by breaking it down to simpler subproblems(in a recursive manner).

devide & conquer + optimal substructure 分治 + 最优子结构

求最优解、最大值，只存最优状态

用数组缓存

**关键点**：

动态规划和递归、分治无本质区别，关键看有无最优子结构

**共性：找到重复子问题**

差异性：最优子结构，中途可以淘汰次优解

复杂度：指数级->多项式级

**解题流程：**

* subproblems
* DP 数组,状态数组
* DP 方程