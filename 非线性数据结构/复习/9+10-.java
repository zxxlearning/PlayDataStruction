public class SegmentTree<E>{
	E[] tree; //线段树
	E[] data; //数组存放单个元素
	
	//构造函数
	public SegmentTree(E[] arr){ //传进来一个数组  改为线段树
		data = (E[])new Object[arr.length];
		for(int i = 0; i < arr.length; i ++){
			data[i] = arr[i];
		}
		tree = (E[])new Object[4 * arr.length];
	}
	//
	public int getSize(){
		return data.length;
	}
	//得到指定元素
	public E get(int index){
		if(index < 0 || index >= data.length){
			throw new IllegalArgumentException("Illegal");
		}
		return data[index];
	}
	//左孩子
	private int leftChild(int index){
		return 2*index+1;
	}
	//右孩子
	private int rightChild(int index){
		return 2*index+2;
	}
}

//递归的方式创建线段树
private void buildST(int treeIndex, int l, int r){
	if(l == r){
		tree[treeIndex] = data[l];
	}
	int leftIndex = leftChild(treeIndex);
	int rightIndex = rightChild(treeIndex);
	int mid = (r-l)/2+l;
	buildST(leftIndex, l, mid); //左半个区间
	buildST(rightIndex, mid+1, r); //右半个区间
	//当前树节点赋值
	tree[treeIndex] = merge(tree[leftIndex], tree[rightIndex]);
}

//调用
query(0, 0, data,length-1, queryL, queryR);

//返回查找的区间的值
private E query(int treeIndex, int l, int r, int queryL, int queryR){
	//直接找到了指定区间
	if(l == queryL && r == queryR){
		return tree[treeIndex];
	}
	//需要分开找
	int leftIndex = leftChild(treeIndex);
	int rightIndex = rightChild(treeIndex);
	int mid = (r-l)/2 + l;
	//左孩子内找[l,mid]
	if(queryR <= mid){
		return query(leftIndex, l, mid, queryL, queryR);
	}
	//右孩子内找[mid+1,r]
	if(queryL >= mid+1){
		return query(rightIndex, mid+1, r, queryL, queryR);
	}
	//左右孩子都包括
	E leftRes = query(leftIndex, l, mid, queryL, mid);
	E rightRes = query(rightIndex, mid+1, r, queryL, queryR);
	return merge(leftRes, rightRes);
}

//更新(确认有)
private void set(int treeIndex, int l, int r, int index, int e){
	if(l == r){
		tree[treeIndex] = e;
		return;
	}
	//需要分开找
	int leftIndex = leftChild(treeIndex);
	int rightIndex = rightChild(treeIndex);
	int mid = (r-l)/2 + l;
	if(index <= mid){
		set(leftIndex, l, mid, index, e);
	}else{
		set(rightIndex, mid+1, r, index, e);
	}
	tree[treeIndex] = merge(tree[leftIndex], tree[rightIndex]);
}


//trie树的添加 
class Node{
	boolean isWorld;
	TreeMap<Character, Node> next;
}
//非递归
public void add(String world){
	Node cur = root;
	for(int i = 0; i < cur.length(); i ++){
		char c = world.charAt(i);
		if(cur.next.get(c) == null){
			cur.next.put(c, new Node());
		}
		cur = cur.next.get(c);
	}
	if(!cur.isWorld){
		cur.isWorld = true;
		size ++;
	}	
}
//递归添加
private void add(Node root, String world, int index){
	if(index == world.length() && !root.isWorld){ //遍历到最后了
		root.isWorld = true;
		size ++;
		return;
	}
	char cur = world.charAt(index);
	if(root.next.get(cur) == null){
		root.next.put(cur, new Node());
	}
	add( root.next.get(cur), world, index+1 );
}

//查询的非递归
public boolean search(String world){
	Node cur = root;
	int i = 0;
	while(i < world.length()){
		char c = world.charAt(i);
		if(cur.next.get(c) == null){
			return false;
		}
		cur = cur.next.get(c);
		i ++;
	}
	return cur.isWorld;
}
//递归
private boolean search(Node root, String world, int index){
	if(index == world.length()){
		return root.isWord;
	}
	char c = world.charAt(index);
	if(root.next.get(c) == null){
		return false;
	}
	return search(cur.next.get(c), world, index+1);
}
//前缀查询
public boolean isPrefix(String prefix){
	Node cur = root;
	for(int i = 0; i < prefix.length(); i++){
		char c = prefix.charAt(i);
		if(cur.next.get(c) == null){
			return false;
		}
		cur = cur.next.get(c);
	}
	return true;
}






