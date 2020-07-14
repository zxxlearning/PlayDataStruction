BFS不只是一个寻路算法，而是一种暴力搜索算法。

BFS找到的路径一定是最短的，但代价就是空间复杂度比DFS大很多。

**问题本质**:  在一幅图中找到从起点start到终点target的最近距离。

广义描述的变体比如 走迷宫、字符交换变单词、连连看都是找最短/最少的一种情况。

## 框架模板

```java
//主要是借助队列来实现的
int BFS(Node start, Node target){
    Queue<Node> q;//核心数据结构
    Set<Node> visited; //避免走回头路  像二叉树没有子节点到父节点的结构 不会走回头路就不需要visited
    q.offer(start); //将起点加入队列
    visited.add(start);
    int step = 0; //记录扩散的步数
    
    while(q not empty){
        int sz = q.size();
        /*将当前队列中的所有节点向四周扩散*/
        for(int i = 0; i < sz; i ++){
            Node cur = q.poll();
            /**这里判断是否到达了终点**/
            if(cur is target){
                return step;
            }
            /**将cur的相邻节点加到队列**/
            for(Node x : cur.adj()){
                if(x not in visited){
                    q.offer(x);
                    visited.add(x);
                }
            }
        }
        /**步数更新**/
        step ++;
    }
    
}
```



## 二叉树的最小深度

BFS 不如 递归 实现好

## 打开转盘锁 752

普通BFS效率不高 改成双向BFS效率高一些

双向BFS的一个局限性就是 必须**要提前知道知道终点在哪里**。

【双向BFS没理解 没细看】

## 滑动谜题 773

