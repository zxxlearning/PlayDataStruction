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

​	1.某结点的左端的左端加了元素后，左子树高于右子树，另其变成不平衡节点，此时要进行右旋转调整。

​	2。某结点的右端的右端加了元素后，右子树高于左子树，另其变成不平衡节点，此时要进行左旋转调整。

## 12.2 AVL树的左旋转和右旋转

​	**AVL树在什么时候维护平衡**？加入新的节点后，平衡因子和高度受到影响的是新节点的父节点和祖先节点们。因此为了维护平衡，在加入新节点后，向上回溯进行维护，就是对父节点和祖先节点们大于1的平衡因子的节点都维护一下平衡特性。

​	**如何维护平衡性呢**？右旋转或者左旋转

​	添加元素后，从下往上追溯，直到某个节点的平衡节点大于1（**每次添加新元素前都是平衡的**（因为每次添加新元素完都要进行平衡调整），所以大于1的情况应该也就是2了，我觉得。），说明此节点不平衡了。如果是左子树比右子树高，说明新加的节点是加到了左边；反之是加到了右边。

#### 12.2.1 右旋转

​	当新添加的**节点是加到了左边**时，需要执行右旋转，从而调整为平衡二叉树。插入节点在不平衡的节点的左侧的左侧。

![img](H:\Learning\JAVA\Github_JavaLearning_Notes\PlayDataStruction\PlayDataStruction\非线性数据结构\pics\12-平衡树和AVL(二分搜索树的扩展)\A4B7DA5F66FE753154E6270CB3ED09AD.jpg)



#### 12.2.2 左旋转













Java自带的TreeMap，底层实现是红黑树