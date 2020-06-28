学习笔记

# 递归
递归本质就是通过函数体来循环。

## 代码模版
```
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
```
## 核心
* 终止条件
* 当前层逻辑
* 下探下一层
* (可选)清理当前环境

# 分治、回溯
两者的本质就是递归。

## 分治
```
def divide_conquer(problem, param1, param2, ...):
    # recursion terminator
    if problem in None:
        print_result
        return
    # prepare data
    data = prepare_data(problem)
    subproblems = split_problem(problem, data)
    # conquer subproblems
    subresult1 = self.divide_conquer(subproblems[0], p1, ...)
    subresult2 = self.divide_conquer(subproblems[0], p1, ...)
    subresult3 = self.divide_conquer(subproblems[0], p1, ...)
    ...
    # process and generate the final result
    result = process_result(subresult1, subresult2, subresult3)

```

### 代码和泛形递归的差异区别 多了一步合并子结果

* 问题解决标志: 不存在子问题
* 处理当前逻辑: 将大问题分成子问题
* 下探一层: 处理子问题
* 结果组装

## 回溯
不断得在每一层就去试，如果错了就返回，如果没有出错就继续下探。