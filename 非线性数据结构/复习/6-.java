public class Node{
	int e;
	Node left;
	Node right;
	Node(int e, Node left, Node right){
		this.e = e;
		this.left = left;
		this.right = right;
	}
}
//添加新元素  
public void add(int e){
	root = add(root, e);
}
//添加的递归实现
private Node add(Node root, int e){
	if(root == null){ //没有可比较的元素了 就是到头了
		size ++;
		return new Node(e, null, null);
	}
	if(e.compareTo(root.e) < 0){
		root.left = add(root.left, e);
	}else if(e.compareTo(root.e) > 0){
		root.right = add(root.right, e);
	}
	return root;
}
//添加的非递归实现
public void add2(int e){
	if(root == null){
		root = new Node(e, null, null);
		size ++;
		return;
	}
	Node cur = root;
	while(cur!=null){
		if(e.compareTo(cur.e) < 0){
			if(cur.left == null){
				cur.left = new Node(e, null, null);
				size ++;
				return;
			}
			cur = cur.left;
		}else if(e.compareTo(cur.e) > 0){
			if(cur.right == null){
				cur.right = new Node(e, null, null);
				size ++;
				return;
			}
			cur = cur.right;
		}else{
			return;
		}
	}	
}

//搜索元素 递归实现
public boolean contain(int e){
	return contain(root, e);
}
private boolean contain(Node root, int e){
	if(root == null){
		return false;
	}
	if(e.compareTo(root.e) == 0){
		return true;
	}else if(e.compareTo(root.e) < 0){
		return contain(root.left, e);
	}else{
		return contain(root.right, e);
	}
}
//搜索元素 非递归实现
public boolean contain(int e){
	Node cur = root;
	while(cur != null){
		if(e.compareTo(cur.e) == 0){
			return true;
		}else if(e.compareTo(cur.e) < 0){
			cur = cur.left;
		}else{
			cur = cur.right;
		}
	}
	return false;
}

//前序遍历 
public void preOrder(){
	preOrder(root);
} 
//前序遍历的递归实现
private void preOrder(Node root){
	if(root == null){
		return;
	}
	System.out.print(root.e);
	preOrder(root.left);
	preOrder(root.right);
}
//非递归实现  利用栈的原理
public void preOrder2(){
	if(root == null){
		return;
	}
	//利用系统栈的原理
	Deque<Node> stack = new ArrayDeque<>();
	stack.push(root); //先把根节点放进去
	while(!stack.isEmpty()){
		//取出根节点
		Node node = stack.pop();
		System.out.println(node.e);
		if(node.right != null){ //右节点存在
			stack.push(node.right);
		}
		if(node.left != null){ //左节点存在
			stack.push(node.left);
		}	
	}
}

//中序遍历
public void inOrder(){
	inOrder(root);
}
//中序遍历 递归实现
private void inOrder(Node root){
	if(root == null){
		return;
	}
	inOrder(root.left);
	System.out.println(root.e);
	inOrder(root.right);
}
//中序遍历 非递归实现
//也是根据系统栈的原理
public void inOrder(){
	if(root == null){
		return;
	}
	Deque<Node> stack = new ArrayDeque<>();
	Node head = root;
	while(!stack.isEmpty() || head != null){
		if(head != null){
			stack.push(head.e);
			head = head.left;
			continue;
		}
		head = stack.pop();
		System.out.println(head.e);
		head = head.right;
	}
}

//后序遍历
public void postOrder(){
}
//后序遍历 递归实现
private void postOrder(Node root){
	if(root == null){
		return;
	}
	postOrder(root.left);
	postOrder(root.right);
	System.out.print(root.e);
}
//后序遍历 非递归实现
public void postOrder2(){
	if(root == null){
		return;
	}
	Deque<Node> stack = new ArrayDeque<>();
	Node head = root;
	Node preStack = null;
	while(!stack.isEmpty() && head != null){
		//先找到尽头的左
		if(head != null){
			stack.push(head);
			head = head.left;
			continue;
		}
		//然后判断当前父节点的右孩子 
		//1 有右孩子
		//      （1）当前节点的右孩子还没被遍历过，将这个右孩子赋值为头节点，继续向左找
		//      （2）当前节点的右孩子刚被遍历过，输出这个节点值
		//2 没有右孩子
		//      输出这个节点值
		//只查值，不取出来
		Node nodeRoot = stack.peek();
		if(nodeRoot.right == null || (nodeRoot.right.e).equals(preStack.e)){
			preStack = stack.pop();
			System.out.println(preStack.e);		
		}else{
			head = nodeRoot.right;
		}	
	}
}

//广度优先遍历  用队列思想实现，先进先出
public void levelOrder(){
	if(root == null){
		return;
	}
	Deque<Node> queue = new ArrayDeque<>();
	queue.add(root);
	while(!queue.isEmpty()){
		Node node = queue.poll();
		if(node.left != null){
			queue.add(node.left);
		}
		if(node.right != null){
			queue.add(node.right);
		}
		System.out.println(node.e);
	}
}

//删除最小节点
//根据中序遍历的结果是从小到大，可以得知 最左端的那个元素就是最小元素
public void removeMin(){
	removeMin(root);
}
//利用递归找到最左端元素
private Node removeMin(Node root){
	if(root.left == null){ //说明此时的root是最左端的
		//如果这个root有右孩子，将这个右孩子返回回去接替自己原有位置，然后将右孩子null
		Node rightC = root.right;
		root.right = null;
		size --;
		return rightC;
	}
	root.left = removeMin(root.left);
	return root;
}

//删除最大节点
//根据中序遍历的结果是从小到大，可以得知 最右端的那个元素就是最大元素
public void removeMax(){
	removeMax(root);
}
//递归找到最右端
//最右端可能有左孩子
private Node removeMax(Node root){
	if(root.right == null){
		Node leftC = root.left;
		root.left = null;
		size --;
		return leftC;
	}
	root.right = removeMax(root);
	return root;
}

//删除任意元素
//被删除的元素：
//       只有左子树（无右子树）左子树代替原位置
//       只有右子树（无左子树）右子树代替原位置
//       左右子树都有（各自可能还有左或右或都有的子树）	找左子树的最大或右子树的最小代替原位置
public void remove(int e){
}																																																																																																																																																																																																																																																																																																																																																																																																																																											
//递归实现
private Node remove(Node root, int e){
	if(root == null){
		return null;
	}
	if(e.compareTo(root.e) < 0){
		root.left = remove(root.left, e);
		return root; //这里是因为最后会递归回来，然后要拼接好，返回回去
	}else if(e.compareTo(root.e) > 0){
		root.right = remove(root.right, e);
		return root;
	}else{ //找到了
		if(root.right == null){	//只有左子树
			Node leftC = root.left;
			root.left = null;
			size --;
			return leftC;
		}else if(root.left == null){ //只有右子树
			Node rightC = root.right;
			root.right = null;
			size --;
			return rightC;	
		}else{
			//左右子树都有
			//找左子树的最大
			Node res = getMax(root.left); //先找到左子树最大那个节点，拿到一边
			//size ++;
			res.left = removeMax(root.left); // 删除左子树原有的最大的那个节点，然后拼在这个最大的节点的左端
			res.right = root.right;
			
			root.left = null;
			root.right = null;
			//size --;
			return res;
		}
	}
}