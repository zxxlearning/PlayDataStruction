---
typora-copy-images-to: pics\12-平衡树和AVL(二分搜索树的扩展)
---

## 12.1 AVL树

​	典型的平衡二叉树结构。

#### 12.1.1 平衡二叉树

​	一棵满二叉树就是平衡二叉树。完全二叉树空的部分一定是右边，也是平衡二叉树。线段树也是平衡二叉树。

​	在AVL中，对平衡二叉树的定义是：对于任意一个节点，左子树和右子树的高度差不能超过1。平衡二叉树的高度和节点数之间的关系是O（logn）的。需要标注每个节点的高度和平衡因子。

​	平衡因子：左子树和右子树的高度差。

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
        return getHeight(node.left) - getHeight(node.right); //这里不取绝对值，根据正负判断哪个子树高
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

​	AVL树是对二分搜索树的进一步改进，是为了防止二分搜索树退化成链表所出现的一种平衡二叉树。因此AVL树也必须满足**左子树的值都小于根节点值、右子树的值都大于根节点值**的特点，同时还要满足平衡二叉树的特点（**平衡因子小于等于1**）。

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

​	1. LL：某结点的**左端**的左端**加了元素后，左子树高于右子树**，另其变成不平衡节点，此时要进行右旋转调整。

​	2. RR：某结点的**右端**的右端**加了元素后，右子树高于左子树**，另其变成不平衡节点，此时要进行左旋转调整。

​	3. LR：某结点的**左端**的右端**加了元素后，左子树高于右子树**，左端节点平衡因子小于0，先对不平衡节点的左结点做左旋转转化成LL情况，再对不平衡节点做右旋转调整。

​	4. RL：某结点的**右端**的左端**加了元素后，右子树高于左子树**，右端节点平衡因子大于0，先对不平衡节点的右结点做右旋转转化为RR情况，再对不平衡节点做左旋转调整。

## 12.2 AVL树的左旋转和右旋转

​	**AVL树在什么时候维护平衡**？加入新的节点后，新节点的父节点和祖先节点们的平衡因子和高度都受到影响。因此为了维护平衡，在加入新节点后，向上回溯进行维护，就是对父节点和祖先节点们大于1的平衡因子的节点都维护一下平衡特性。

​	**如何维护平衡性呢**？右旋转或者左旋转

​	添加元素后，从下往上追溯，直到某个节点的平衡节点大于1（**每次添加新元素前都是平衡的**（因为每次添加新元素完都要进行平衡调整），所以大于1的情况应该也就是2了，我觉得。），说明此节点不平衡了。**如果是左子树比右子树高，说明新加的节点是加到了左边；反之是加到了右边**。

#### 12.2.1 LL 右旋转

​	当新添加的**节点是加到了左边**时，需要执行右旋转，从而调整为平衡二叉树。插入节点在不平衡的节点的左侧的左侧。

<img src="D:\DataFiles\Learn\Github\PlayDataStruction\非线性数据结构\\pics\12-平衡树和AVL(二分搜索树的扩展)\右旋转.jpg" alt="img" style="zoom:30%;" />

<img src="D:\DataFiles\Learn\Github\PlayDataStruction\非线性数据结构\pics\12-平衡树和AVL(二分搜索树的扩展)\右旋转+左旋转图示.jpg" alt="右旋转+左旋转图示" style="zoom:33%;" />

```java
//右旋转（左高）
private Node rightXuanZhuan(Node y){ //传进来的y就是不平衡节点
	Node x = y.left;
	Node tempXRight = x.right;
	x.right = y;
	y.left = tempXRight;
	//调整高度 动的是x  y
	x.height = Math.max( getHeight(x.left) + getHeight(x.right) ) + 1;
	y.height = Math.max( getHeight(y.left) + getHeight(y.right) ) + 1;
	return x; //现在x已经替代y的位置了，所以要返回x
}
```

#### 12.2.2 RR 左旋转

​	当新添加的**节点是加到了右边**时，需要执行左旋转，从而调整为平衡二叉树。插入节点在不平衡的节点的右侧的右侧。

```java
//左旋转（右高）
private Node leftXuanZhuan(Node y){
	Node x = y.right;
	Node tempXLeft = x.left;
	x.left = y;
	y.right = tempXLeft;
	//调整高度 动的是x  y
	x.height = Math.max( getHeight(x.left) + getHeight(x.right) ) + 1;
	y.height = Math.max( getHeight(y.left) + getHeight(y.right) ) + 1;
	return x; //现在x已经替代y的位置了，所以要返回x
} 
```

#### 12.2.3 LR 先左旋再右旋

对不平衡节点的左节点先做左旋转，转成LL样子，再对不平衡节点做右旋转。

#### 12.2.4 RL 先右旋再左旋

对不平衡节点的右节点先做右旋转，转成RR样子，在对不平衡节点做左旋转。

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
            return rightXuanZhuan(root);
        }
        //RL
        if(banlanceF < -1 && getBanlanceFactor(root.right) > 0){
            root.right = rightXuanZhuan(root.right);
            return leftXuanZhuan(root);
        }
        return root;
    }
```

## 12.3 AVL树的删除

​	AVL树的删除操作和二分搜索树的删除操作一样。但是删除操作后，**被删除的节点的父节点至其祖先节点们可能会出现非平衡的情况**，因此要从被删除节点向上回溯维护平衡，维护操作和添加时的维护操作一样。

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
        Node resNode;
        if(e.compareTo(root.e) < 0){
            root.left = remove(root.left, e);
            resNode = root;
        }else if(e.compareTo(root.e) > 0){
            root.right = remove(root.right, e);
            resNode = root;
        }else{ //找到要删除的节点  就是root.e == e的情况 要记得把删除的节点那个连线撤掉，也就是置null
            //该结点有右孩子
            if(root.left == null){ //如果删除的是叶子节点，那么root.right其实也是null的最终得到resNode就是null
                Node res = root.right;
                root.right = null;
                size --;
                resNode = res;
            }
            //该结点有左孩子
            else if(root.right == null){  //注意必须用else if 这才能保证 左子树不空 因为前面没有return 会继续向下执行
                Node res = root.left;
                root.left = null;
                size --;
                resNode = res;
            }
            //该结点左右孩子都有
            //可以选左边的最小做继承者，也可以选右边的最小做继承者
            //此处选择右边的最小
            else{
                Node res = getMin(root.right);
                res.right = remove(root.right, res.e); //这里也注意
                res.left = root.left;
                root.left = null;
                root.right = null;
                resNode = res;
            }
        }
        if(resNode){ //还有一个问题  如果删除的是叶子节点，那么返回的resNode就是null null就没有左右了
            return null;
        }
        return xuanZhuan(resNode);
    }
    //LL RR LR RL选择
    private Node xuanZhuan(Node node){
        //修正节点高度
        node.height = Math.max( getHeight(node.left) + getHeight(node.right) ) + 1;
        //判断当前节点的平衡因子
        int banlanceFactor = getBanlanceFactor(node);
        //看新加的节点加到什么位置了
        //LL
        if(banlanceFactor > 1 && getBanlanceFactor(node.left) > 0){
            return rightXuanZhuan(node);
        }
        //RR
        if(banlanceFactor < -1 && getBanlanceFactor(node.right) < 0){
            return leftXuanZhuan(node);
        }
        //LR
        if(banlanceFactor > 1 && getBanlanceFactor(node.left) > 0){
            //先左旋转
            node.left = leftXuanZhuan(node.left);
            return rightXuanZhuan(node);
        }
        //RL
        if(banlanceFactor < -1 && getBanlanceFactor(node.right) > 0){
            //先右旋转
            node.right = rightXuanZhuan(node.right);
            return leftXuanZhuan(node);
        }	
    }
    //找最小
    public Node getMin(Node node){
        if(node == null){
            throw new IllegalArgumentException("node is null");
        }
        if(node.left == null){
            return node;
        }
        return getMin(node.left);
    }
```

## 12.4 基于AVL树的集合和映射

​	之前介绍了基于链表和二分搜索树做底层实现的集合（Set）和映射（Map）。

​	以AVL树做底层实现映射，效率比二分搜索树要高。

​	以AVL底层实现的映射再做集合的底层实现，直接把value值置空即可，置为null。因为**AVL树不存重复节点值，因此也适合做Set的底层实现。效率也是更高的**。

​	可以看出，AVL这种自平衡树是有优势的。**增删改查的操作都是O（logn）的时间复杂度**。



##### . AVL优化

​	如果某个节点的高更新后和之前的高的值一样时，其父节点及其祖先节点们的高其实是不用再继续更新了的。

```java
      //updateHFlag 默认为 true  定义在外面static变量
        
        
        if(updateHFlag){
            // 高就是自身的高度1+两个孩子中较高的孩子的高度。
            int tempH = 1 + Math.max(getHeight(root.left), getHeight(root.right));
            if(tempH != root.height)
                root.height = tempH;
            else
                updateHFlag = false;
        }
```

##### .AVL树局限性

AVL现在效率其实挺高了，但是在统计性能上看，红黑树的平均性能比AVL更优。

由于红黑树的旋转操作更少。

AVL树是第一个自平衡的二叉树数据结构。





Java自带的TreeMap，底层实现是红黑树