学习笔记

## DFS模板
```
#递归前处理
visited = set()  # 节点是否被访问

def dfs(node,visited):
    # 递归终止条件
    if node in visited: # 是否被访问
        return
    
    # 递归到下一层前处理（当前层处理）
    visited.add(node) 
    # 其它处理

    # 递归到下一层

    for next_node in node.children(): 
        if not next_node in visited: 
			dfs(next_node, visited)

    # 递归下层次返回后处理
```

## BFS模板
```
def bfs(graph, start, end):
    # 迭代前处理
    queue = [] # 辅助队列
    queue.append([start])   # 入队列
    visited.add(node)   # 标记访问
    # 迭代终止条件
    while queue:
        # 迭代  
        node = queue.pop(0)  # 出队列
        visited.add(node)   # 标记访问

        rocess (node)   # 当前节点处理
        nodes = generate_related_nodes(node)  # 生成相关节点
        queue.push(nodes) # 入队列

    # 迭代后处理     
```

## 二分查找模板
```

left, right = 0, len(array) - 1 
while left <= right: 
	  # mid = (left + right) / 2
      mid = low + (high-low)/2
	  if array[mid] == target: 
		    # 找到目标
		    break or return result 
	  elif array[mid] < target: 
		    left = mid + 1 
	  else: 
		    right = mid - 1
```

## 贪心、分治、 回溯、 动态规划
* 贪心、回溯、动态规划可以归为一类：三个算法解决问题的模型，都可以抽象成多阶段决策最优解模型；
* 分治单独可以作为一类：分治算法解决的问题尽管一部分也是最优解问题，但是，大部分都不能抽象成多阶段决策模型；
* 回溯：“万金油”算法。基本上能用动态规划、贪心解决的问题，都可以用回溯算法解决。回溯算法相当于穷举搜索。穷举所有的情况，然后对比得到最优解；
* 动态规划解决的问题，需要满足三个特征，最优子结构、无后效性、重复子问题；
* 动态规划和分治的区别：在重复子问题这一点上，分治算法要求分割成的子问题，不能有重复子问题，而动态规划正好相反，动态规划之所以高效，就是因为回溯算法实现中存在大量的重复子问题；
* 贪心法实际上是动态规划算法的一种特殊情况。通过局部最优的选择，能产生全局的最优选择；