# 1. 二叉树的最近公共祖先
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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        // 1.递归
        // if (root == null || root == p || root == q) return root;
        // TreeNode left = lowestCommonAncestor(root.left, p, q);
        // TreeNode right = lowestCommonAncestor(root.right, p, q);
        // if (left == null) return right;
        // if (right == null) return left;
        // return root;
        // 2.迭代
        // Map<TreeNode, TreeNode> map = new HashMap();
        // Set<TreeNode> set = new HashSet();
        // Stack<TreeNode> stack = new Stack();

        // map.put(root, null);
        // stack.push(root);
        // while (!map.containsKey(p) || !map.containsKey(q)) {
        //     TreeNode curr = stack.pop();
        //     if (curr.left != null) {
        //         stack.push(curr.left);
        //         map.put(curr.left, curr);
        //     }
        //     if (curr.right != null) {
        //         stack.push(curr.right);
        //         map.put(curr.right, curr);
        //     }
        // }
        // while (p != null) {
        //     set.add(p);
        //     p = map.get(p);
        // }
        // while (q != null) {
        //     if (set.contains(q)) return q;
        //     q = map.get(q);
        // }
        // return null;
        // 3.中序遍历
        if (root == p || root == q) return root;
        TreeNode node = root.left;
        boolean leftFlag = false, rightFlag = false;
        Stack<TreeNode> stack = new Stack();
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            if (node == p) leftFlag = true;
            if (node == q) rightFlag = true;
            if (leftFlag && rightFlag) break;
            node = node.right;
        }
        if (leftFlag && rightFlag) return lowestCommonAncestor(root.left, p, q);
        else if (!leftFlag && !rightFlag) return lowestCommonAncestor(root.right, p, q);
        return root;
    }
}
```

# 2. 从前序与中序遍历序列构造二叉树
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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // return buildTree(preorder, 0, preorder.length, inorder, 0, inorder.length);
        if (preorder.length == 0) return null;
        int pre = 0, in = 0;
        TreeNode curr = new TreeNode(preorder[pre]), root = curr;
        Stack<TreeNode> stack = new Stack();
        stack.push(curr);
        pre++;
        while (pre < preorder.length) {
            if (inorder[in] == curr.val) {
                while (!stack.isEmpty() && stack.peek().val == inorder[in]) {
                    curr = stack.pop();
                    in++;
                }
                curr.right = new TreeNode(preorder[pre]);
                curr = curr.right;
                stack.push(curr);
                pre++;
            } else {
                curr.left = new TreeNode(preorder[pre]);
                curr = curr.left;
                stack.push(curr);
                pre++;
            }
        }
        return root;
    }

    public TreeNode buildTree(int[] preorder, int preStartIndex, int preEndIndex, int[] inorder, int inStartIndex, int inEndIndex) {

        if (preStartIndex == preEndIndex) return null;

        int root = preorder[preStartIndex];
        TreeNode rootNode = new TreeNode(root);
        int rootIndex = 0;
        for (int i = inStartIndex; i < inEndIndex; i++) {
            if (inorder[i] == root) {
                rootIndex = i;
                break;
            }
        }
        int leftIndex = rootIndex - inStartIndex;
        rootNode.left = buildTree(preorder, preStartIndex + 1, preStartIndex + leftIndex + 1, inorder, inStartIndex, rootIndex);
        rootNode.right = buildTree(preorder, preStartIndex + leftIndex + 1, preEndIndex, inorder, rootIndex + 1, inEndIndex);
        return rootNode;
    }
}
```

# 3. 组合
```
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        // 1.回溯
        // List<List<Integer>> res = new ArrayList();
        // dfs(n, k, 1, new ArrayList(), res);
        // return res;
        // 2.C(n, k) = C(n - 1, k - 1) + C(n - 1, k)
        if (n == k || k == 0) {
            List<Integer> res = new ArrayList();
            for (int i = 1; i <= k; i++) {
                res.add(i);
            }
            return new ArrayList(Arrays.asList(res));
        }
        List<List<Integer>> res = combine(n - 1, k - 1);
        res.forEach(list -> list.add(n));
        res.addAll(combine(n - 1, k));
        return res;
    }

    private void dfs(int n, int k, int start, List<Integer> list, List<List<Integer>> res) {
        if (list.size() == k) {
            res.add(new ArrayList(list));
            return;
        }

        for (int i = start; i <= n; i++) {
            list.add(i);
            dfs(n, k, i + 1, list, res);
            list.remove(list.size() - 1);
        }
    }
}
```

# 4. 全排列

```
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList();
        int[] visited = new int[nums.length];
        permute(nums, new ArrayList(), res, visited);
        return res;
    }

    private void permute(int[] nums, List<Integer> list, List<List<Integer>> res, int[] visited) {
        if (list.size() == nums.length) {
            res.add(new ArrayList(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            list.add(nums[i]);
            permute(nums, list, res, visited);
            visited[i] = 0;
            list.remove(list.size() - 1);
        }
    }
}
```

# 5. 全排列 II 

```
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet();
        int[] visited = new int[nums.length];
        permuteUnique(nums, new ArrayList(), set, visited);
        return new ArrayList(set);
    }

    private void permuteUnique(int[] nums, List<Integer> list, Set<List<Integer>> set, int[] visited) {
        if (list.size() == nums.length) {
            set.add(new ArrayList(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            list.add(nums[i]);
            permuteUnique(nums, list, set, visited);
            visited[i] = 0;
            list.remove(list.size() - 1);
        }
    }
}
```