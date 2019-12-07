## 10.1 Trie树

​		Trie树是个==多叉树==，和之前学习的树的结构是不一样的。（也叫**前缀树**）

​		有点地方把映射这种数据结构也叫做字典，字典表示的就是一个词条和一个释意相对应。通常Trie被用作处理字符串。查询每个条目的时间复杂度和字典中有多少条目无关。而和查询的单词长度有关，时间复杂度为O（w）,w是查询单词的长度。

**什么是Trie**

​		每个节点有若干个指向下个节点的指针，因此封装Trie数据结构时，用Map < char, Node > 表示当前节点的孩子结点们。多叉树最头上只有一节点，没有值存在里面，只存着指向的孩子节点们。因此char存的是某个孩子节点的值，对应的Node是这个孩子节点的孩子节点们。

#### 10.1.1 添加操作

可以用递归或非递归实现。

**非递归**：

```java
//    非递归的增加写法
    public void add(String word){
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if(!cur.isWord){
            cur.isWord = true;
            size ++;
        }
    }
```

**递归**：

```java
//    递归的增加写法(最头上那个是没有东西的，只有一个，值是空，有多个孩子)
//    递归不一定要有return啊，有时候是不能通过return进行连接的
    public void addDG(String word){
        add(root, word, 0);
    }
    private void add(Node root, String word, int index){
        //递归终止情况
        if(index == word.length() && !root.isWord){
            root.isWord = true;
            size ++;
        }
        if(index < word.length()) {  //这句必须有，不然跳不出递归了
            //递归主体内容
            char cur_c = word.charAt(index);
            if (root.next.get(cur_c) == null) {
                root.next.put(cur_c, new Node());
            }
            add(root.next.get(cur_c), word, index + 1);
        }
    }
```

#### 10.1.2 查询操作

```java
//    非递归实现
    public boolean contains(String word){
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

//    递归实现
    public boolean containDG(String word){
        return contain(root, word, 0);
    }
    private boolean contain(Node root, String word, int index){
        //递归结束条件
        if(index == word.length()){
            return root.isWord;
        }

        char cur_c = word.charAt(index);
        if(root.next.get(cur_c) == null){
            return false;
        }
        return contain(root.next.get(cur_c), word, index+1);

    }
```

#### 10.1.3 前缀搜索

```java
//    前缀查询
    public boolean isPrefix(String prefix){
        Node cur = root;
        for(int i = 0; i < prefix.length(); i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }
```

## 10.2 力扣应用

#### 10.2.1 实现Trie树（前缀树）

> 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。
>
> 示例:
>
> Trie trie = new Trie();
>
> trie.insert("apple");
> trie.search("apple");   // 返回 true
> trie.search("app");     // 返回 false
> trie.startsWith("app"); // 返回 true
> trie.insert("app");   
> trie.search("app");     // 返回 true
> 说明:
>
> 你可以假设所有的输入都是由小写字母 a-z 构成的。
> 保证所有输入均为非空字符串。

```java
import java.util.TreeMap;
class Trie {
    /** 定义节点类 */
    private class Node{
        private boolean isWord;
        private TreeMap<Character, Node> next;
        public Node(){
            isWord = false;
            next = new TreeMap<>();
        }
    }
    private Node root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if(!cur.isWord){
            cur.isWord = true;
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return true; 
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

> 执行用时 :79 ms, 在所有 java 提交中击败了65.85%的用户
>
> 内存消耗 :54.4 MB, 在所有 java 提交中击败了88.39%的用户

#### 10.2.2 添加与搜索单词