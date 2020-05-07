public int parent(int index){
	if(index == 0){
		throw new IllegalArgumentException("index error");	
	}
	return (index-1)/2;
}
public int leftChild(int index){
	return (index*2)+1;
}
public int  rightChild(int index){
	return (index*2)+2;
}

//添加元素
public void addLast(int e){
	arr[size] = e;
	size ++;
}
public void add(int e){
	//先扔到数组后面
	addLast(e);
	//再调整元素位置
	int curP = data.getSize() - 1;
	shiftUp(curP);
}
private void shiftUp(int curP){
	while(curP > 0 && data[curP].compareTo( data[parent(curP)] ) > 0){ //大于父节点
		swap(curP, parent(curP));
		curP = parent(curP);
	}
}
public vois swap(int i, int j){
	if(i < 0 || j < 0 || i >= data.getSize() || j >= data.getSize()){
		throw new IllegalArgumentException("index error");
	}
	int temp = data[i];
	data[i] = data[j];
	data[j] = temp;
}
//删除最大元素
public void deleteMax(){
	swap(0, data.getSize()-1); //交换第一个元素和最后一个元素
	size --; //这样就删除了最后一个元素了
	//然后调整
	shiftDown(0);
}
public void shiftDown(int curP){
	//因为是完全二叉树，所以可能有左子树但是没有右子树。
	int lP = leftChild(curP);
	int rP = rightChild(curP);
	while(lP < data.getSize()){
		//把这个父节点和更大的一个孩子节点做交换
		int k = lP;
		//先找到大的孩子节点
		if(rP < data.getSize()){ //注意先保证这个
			k = data[lP] > data[rP] ? lP : rP;
		}
		//然后再和父节点比较
		if(data[curP].compareTo(data[k]) >= 0){
			break;
		}
		swap(curP, k);
		curP = k;
	}
}
// 数组转二叉堆
public void maxHeap(int[] arr){
	data = arr;
	int k = parent(arr.length-1); //找到最后一个非叶子节点
	while(k >= 0){
		shiftDown(k);
		k --;
	}
}
