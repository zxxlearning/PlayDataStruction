//获取节点的高度
public int getHeight(Node node){
	if(node == null){
		return 0;
	}
	return node.height;
}
//获取平衡因子
public int getBanlanceFactor(Node node){
	if(node == null){
		return 0;
	}
	return getHeight(node.left) - getHeight(node.right);
}
//普通二叉树添加元素
public void add(int e){
	add(root, e);
}
private Node add(Node node, int e){
	if(node == null){
		size ++;
		return new Node(e, null, null);
	}
	if(node.e > e){
		node.left = add(node.left, e);
	}else if(node.e < e){
		node.right = add(node.right, e);
	}
	node.height = Math.max( getHeight(node.left), getHeight(node.right) ) + 1; //维护高度的值
	return node;
}

//判断是否符合二叉树
public boolean isBST(Node node){
	if(node == null){
		return false;
	}
	ArrayList<Integer> arr = new ArrayList<>();
	inOrder(node, arr);
	for(int i = 1; i < arr.size(); i ++){
		if(arr.get(i) - arr.get(i-1) < 0){
			return false;
		}
	}
	return true;
}
//中序遍历
private boolean inOrder(Node node, ArrayList<Integer> arr){
	if(node == null){
		return false;
	}
	inOrder(node.left, arr);
	arr.add(node.e);
	inOrder(node.right, arr);
}

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

//增加旋转控制的添加操作
private Node add(Node node, int e){
	if(node == null){
		size ++;
		return new Node(e, null, null);
	}
	if(node.e > e){
		node.left = add(node.left, e);
	}else if(node.e < e){
		node.right = add(node.right, e);
	}
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
	if(banlanceFactor < -1 && getBanlanceFactor(node.rigsht) < 0){
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


