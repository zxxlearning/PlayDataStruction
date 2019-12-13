---
typora-copy-images-to: pics\12-平衡树和AVL(二分搜索树的扩展)
---

## 12.1 AVL树

​	典型的平衡二叉树结构。

#### 12.1.1 平衡二叉树

​	一棵满二叉树就是平衡二叉树。完全二叉树空的部分一定是右边，也是平衡二叉树。线段树也是平衡二叉树。

​	在AVL中，对平衡二叉树的定义是：对于任意一个节点，左子树和右子树的高度差不能超过1。平衡二叉树的高度和节点数之间的关系是O（logn）的。需要标注每个节点的高度和平衡因子。

##### . 获取高度和平衡因子的代码

```java
    //获取节点高度
    private int getHeight(Node node){
        if(node == null){
            return 0;
        }
        return node.height;
    }
    //获取节点平衡因子
    private int getBanlanceFactor(Node node){
        if(node == null){
            return 0;
        }
        return Math.abs(getHeight(node.left) - getHeight(node.right));
    }
    public void add(E e){
        root = add(root, e);
    }
    private Node add(Node root, E e){
        if(root == null){
            size ++;
            return new Node(e, null, null);
        }
        if(e.compareTo(root.e) < 0){
            root.left = add(root.left, e);
        }else if(e.compareTo(root.e) > 0){
            root.right = add(root.right, e);
        }
        // 高就是自身的高度1+两个孩子中较高的孩子的高度。
        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
        return root;
    }
```

#### 12.1.2 AVL满足的特点

​	AVL树是对二分搜索树的进一步改进，是为了防止二分搜索树退化成链表所出现的一种平衡二叉树。因此AVL树也必须满足**左孩子的值小于根节点值、右孩子的值大于根节点值**的特点，同时还要满足平衡二叉树的特点（**平衡因子小于等于1**）。

```java
    //判断是否符合二分搜索树的特点
    //就中序遍历一下这个树，然后如果得到的值是从小到大排列就说明是二分搜索树
    public boolean isBST(){
        ArrayList<E> arr = new ArrayList<>();
        inOrder(root, arr);
        for(int i = 1; i < arr.size(); i ++){
            if(arr.get(i).compareTo(arr.get(i-1)) < 0){
                return false;
            }
        }
        return true;
    }
    //中序遍历
    private void inOrder(Node root, ArrayList<E> arr){
        //遍历的节点为空，本次遍历结束
        if(root == null){
            return;
        }
        inOrder(root.left, arr);
        arr.add(root.e);
        inOrder(root.right, arr);
    }
    //判断是否符合平衡二叉树的特点
    public boolean isBanlance(){
        return isBanlance(root);
    }
```

#### 12.1.3 AVL出现不平衡的情况

​	1. LL：某结点的左端的左端加了元素后，左子树高于右子树，另其变成不平衡节点，此时要进行右旋转调整。

​	2. RR：某结点的右端的右端加了元素后，右子树高于左子树，另其变成不平衡节点，此时要进行左旋转调整。

​	3. LR：某结点的左端的右端加了元素后，左子树高于右子树，左端节点平衡因子小于0，先对不平衡节点的左结点做左旋转转化成LL情况，再对不平衡节点做右旋转调整。

​	4. RL：某结点的右端的左端加了元素后，右子树高于左子树，右端节点平衡因子大于0，先对不平衡节点的右结点做右旋转转化为RR情况，再对不平衡节点做左旋转调整。

## 12.2 AVL树的左旋转和右旋转

​	**AVL树在什么时候维护平衡**？加入新的节点后，平衡因子和高度受到影响的是新节点的父节点和祖先节点们。因此为了维护平衡，在加入新节点后，向上回溯进行维护，就是对父节点和祖先节点们大于1的平衡因子的节点都维护一下平衡特性。

​	**如何维护平衡性呢**？右旋转或者左旋转

​	添加元素后，从下往上追溯，直到某个节点的平衡节点大于1（**每次添加新元素前都是平衡的**（因为每次添加新元素完都要进行平衡调整），所以大于1的情况应该也就是2了，我觉得。），说明此节点不平衡了。如果是左子树比右子树高，说明新加的节点是加到了左边；反之是加到了右边。

#### 12.2.1 LL 右旋转

​	当新添加的**节点是加到了左边**时，需要执行右旋转，从而调整为平衡二叉树。插入节点在不平衡的节点的左侧的左侧。

![img](H:\Learning\JAVA\Github_JavaLearning_Notes\PlayDataStruction\PlayDataStruction\非线性数据结构\pics\12-平衡树和AVL(二分搜索树的扩展)\右旋转.jpg)

```java
    //右旋转
    private Node rightXuanZhuan(Node y){
        Node x = y.left;
        Node tempXRight = x.right;
        x.right = y;
        y.left = tempXRight;
        //更新高
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }
```

#### 12.2.2 RR 左旋转

​	当新添加的**节点是加到了右边**时，需要执行左旋转，从而调整为平衡二叉树。插入节点在不平衡的节点的右侧的右侧。

```java
    //左旋转
    private Node leftXuanZhuan(Node y){
        Node x = y.right;
        Node tempXLeft = x.left;
        x.left = y;
        y.right = tempXLeft;
        //更新高
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }
```

#### 12.2.3 LR 先左旋再右旋

#### 12.2.4 RL 先右旋再左旋

#### 12.2.5 添加时的旋转操作

```java
    private Node add(Node root, E e){
        if(root == null){
            size ++;
            return new Node(e, null, null);
        }
        if(e.compareTo(root.e) < 0){
            root.left = add(root.left, e);
        }else if(e.compareTo(root.e) > 0){
            root.right = add(root.right, e);
        }
        // 高就是自身的高度1+两个孩子中较高的孩子的高度。
        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
        int banlanceF = getBanlanceFactor(root);
        //大于1说明当前节点非平衡节点, 且左端高
        // 必须是在左端的左端加元素才能进行右旋转
        //LL
        if(banlanceF > 1 && getBanlanceFactor(root.left) >= 0){
            return rightXuanZhuan(root);
        }
        //小于-1说明当前节点非平衡节点，且右端高
        //必须在右端的右端加元素才能进行左旋转
        //RR
        if(banlanceF < -1 && getBanlanceFactor(root.right) <= 0){
            return leftXuanZhuan(root);
        }
        //LR
        if(banlanceF > 1 && getBanlanceFactor(root.left) < 0){
            root.left = leftXuanZhuan(root.left);
        }
        if(banlanceF < -1 && getBanlanceFactor(root.right) > 0){
            root.right = rightXuanZhuan(root.right);
        }
        //RL
        return root;
    }
```

## 12.3 AVL树的删除

​	AVL树的删除操作和二分搜索树的删除操作一样。但是删除操作后，被删除的节点的父节点至其祖先节点们可能会出现非平衡的情况，因此要从被删除节点向上回溯维护平衡，维护操作和添加时的维护操作一样。

```java
    //删除节点
    //要删除的节点可能是叶子节点
    //                 只有左子树的节点
    //                 只有右子树的节点
    //                 左右子树都有的节点
    public void remove(E e){
        root = remove(root, e);
    }
    private Node remove(Node root, E e){
        //找到最后也没找到要删除的节点
        if(root == null){
            return null;
        }
        //找要删除的节点
        if(e.compareTo(root.e) < 0){
            root.left = remove(root.left, e);
            return root;
        }else if(e.compareTo(root.e) > 0){
            root.right = remove(root.right, e);
            return root;
        }else{ //找到要删除的节点  就是root.e == e的情况 要记得把删除的节点那个连线撤掉，也就是置null
            //该结点有右孩子
            if(root.left == null){
                Node res = root.right;
                root.right = null;
                size --;
                return res;
            }
            //该结点有左孩子
            if(root.right == null){
                Node res = root.left;
                root.left = null;
                size --;
                return res;
            }
            //该结点左右孩子都有
            //可以选左边的最小做继承者，也可以选右边的最小做继承者
            //此处选择右边的最小
            Node res = getMin(root.right);
            res.right = removeMin(root.right);
            res.left = root.left;
            root.left = null;
            root.right = null;
            return res;
        }
    }

    //从某个节点开始
    public Node getMin(Node node){
        if(node == null){
            throw new IllegalArgumentException("node is null");
        }
        if(node.left == null){
            return node;
        }
        return getMin(node.left);
    }
    //删除当前节点下的最小节点
    private Node removeMin(Node node){
        //可能有右结点也可能没有
        if(node.left == null){
            Node res = node.right;
            node.right = null;
            size --;
            return res;
        }
        node.left = removeMin(node.left);
        //最终返回的是传进来的节点
        return node;
    }
```

## 12.4 基于AVL树的集合和映射

​	之前介绍了基于链表和二分搜索树做底层实现的集合（Set）和映射（Map）。

​	以AVL树做底层实现映射，效率比二分搜索树要高。

​	以AVL底层实现的映射再做集合的底层实现，直接把value值置空即可，置为null。因为AVL树不存重复节点值，因此也适合做Set的底层实现。效率也是更高的。

​	可以看出，AVL这种自平衡树是有优势的。增删改查的操作都是O（logn）的时间复杂度。

##### . AVL优化

​	如果某个节点的高更新后和之前的高的值一样时，其父节点及其祖先节点们的高其实是不用再继续更新了的。

```java
      //前面定义 updateHFlag 默认值为 true
        
        
        if(updateHFlag){
            // 高就是自身的高度1+两个孩子中较高的孩子的高度。
            int tempH = 1 + Math.max(getHeight(root.left), getHeight(root.right));
            if(tempH != root.height)
                root.height = tempH;
            else
                updateHFlag = false;
        }
```









Java自带的TreeMap，底层实现是红黑树