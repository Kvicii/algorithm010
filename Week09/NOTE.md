学习笔记

## 27、字符串算法（String）

* **I. 字符串基本知识**
    - 可变字符串和不可变字符串
    - 字符串的比较

* **II. 高级字符串算法**
    - 结合动态规划
        * DP[i][j]: 一般i代表前一个字符串的相对变化，j代表后一个字符串的相对变化

* **III. 字符串匹配算法**
    - 暴力法 - O(nm)

        

``` swift
        func forceSearch1(_ text: String, _ pat: String) -> Int {
            let M = text.count, N = pat.count
            let textA = Array(text), patA = Array(pat)
            var i = 0, j = 0, k = i
            while i < M {
                if textA[k] == patA[j] {
                    k += 1; j += 1
                    if j == N { return i }
                }else {
                    i += 1; k = i; j = 0
                }
            }
            return -1
        }
        ```

           
        

``` swift
        func forceSearch(_ text: String, _ pat: String) -> Int {
            let M = text.count, N = pat.count
            let textA = Array(text), patA = Array(pat)
            for i in 0...M - N {
        //        var j = 0
        //        for _ in 0..<N {
        //            if textA[i + j] != patA[j] { break }
        //            j += 1
        //        }
        //        if j == N { return i }
                if Array(textA[i..<N+i]) == patA { return i }
            }
            return -1
        }
        ```

            

    - Rabin-Karp算法 - O(m + n)

        

``` swift
        func rabinKarpSearch(_ text: String, _ pat: String) -> Int {
            let D = 256
            let Q = 9997
            
            let M = text.count, N = pat.count
            let txtA = Array(text), patA = Array(pat)
            var txtHash = 0, patHash = 0
            var highestPow = 1 // pow(256, N-1)
            
            for i in 0..<N {
                patHash = (D * patHash + Int(patA[i].asciiValue!)) % Q
                txtHash = (D * txtHash + Int(txtA[i].asciiValue!)) % Q
            }
            
            for _ in 0..<N - 1 { highestPow = (D * highestPow) % Q }
            
            for i in 0...M - N {
                if patHash == txtHash {
        //            var isMatch = true
        //            for j in 0..<N {
        //                if txtA[i + j] != patA[j] { isMatch = false ; break }
        //            }
        //            if isMatch { return i }
                    if Array(txtA[i..<N+i]) == patA { return i }
                }
                if i < M - N {
                    txtHash = (D * (txtHash - Int(txtA[i].asciiValue!) * highestPow) + Int(txtA[i + N].asciiValue!)) % Q
                    if txtHash < 0 { txtHash += Q }
                }
            }
            return -1
        }
        ```

    - KMP算法

        

``` swift
        func prefixTable(_ pattern: String) -> [Int] {
            guard !pattern.isEmpty else { return [] }
            let pa = Array(pattern)
            var prefix = [Int](repeating: 0, count: pa.count)
            var i = 1, len = 0
            while i < pa.count {
                if pa[i] == pa[len] {
                    len += 1
                    prefix[i] = len
                    i += 1
                }else {
                    if len > 0 { len = prefix[len - 1] }
                    else { prefix[i] = len; i += 1 }
                }
            }
            // 相当于整体向后移动一位，然后在第一位加个-1
            prefix.insert(-1, at: 0)
            prefix.removeLast()
            return prefix
        }
        
        /*
        a                       0
        a b                     0
        a b a                   1
        a b a b                 2
        a b a b c               0
        a b a b c b             0
        a b a b c b c           0
        a b a b c b c a         1
        */
        
        ```

        

``` swift
        func kmpSearch(_ text: String, _ pattern: String) -> [Int] {
            let tA = Array(text), pA = Array(pattern)
            let m = tA.count, n = pA.count
            var i = 0, j = 0, res = [Int]()
            
            let prefix = prefixTable(pattern)
            
            while i < m {
                if j == n - 1, tA[i] == pA[j] {
                    res.append(i - j)
                    j = prefix[j] // 继续找下一个匹配
                }
                if tA[i] == pA[j] {
                    i += 1; j += 1
                } else {
                    j = prefix[j]
                    if j == -1 { i += 1; j += 1 }
                }
            }
            return res
        }
        ```

        

    - Boyer-Moore算法
    - Sunday算法
