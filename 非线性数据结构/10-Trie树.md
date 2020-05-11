## 10.1 Trie树

​		Trie树是个**多叉树**，和之前学习的树的结构是不一样的。（也叫**前缀树**）

​		有点地方把映射这种数据结构也叫做字典，字典表示的就是一个词条和一个释意相对应。通常**Trie被用作处理字符串**。查询每个条目的时间复杂度和字典中有多少条目无关。而和查询的单词长度有关，时间复杂度为O（w）,w是查询单词的长度。

**什么是Trie**

​		每个节点有**若干个**指向下个节点的指针，因此封装Trie数据结构时，**用Map < char, Node > 表示当前节点的孩子结点们**。多叉树最头上只有一节点，没有值存在里面，只存着指向的孩子节点们。因此char存的是某个孩子节点的值，对应的Node是这个孩子节点的孩子节点们。

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
        return cur.isWord; //注意这里得返回cur.isWord 因为可能找到了那个单词的内容，但他没被存为单词
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

另外:因为题目说定了只存26个字母，所以可以直接用数组表示，而且不需要真正存数据，26个字母数组的索引就可以对应这26个字母了。

```java
class Trie {
    private class Node{
        private boolean isWorld;
        private Node[] next;

        Node(){
            isWorld = false;
            next = new Node[26];
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
            if(cur.next[c-'a'] == null){
                cur.next[c-'a'] = new Node();
            }
            cur = cur.next[c-'a'];
        }
        if(!cur.isWorld){
            cur.isWorld = true;
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next[c-'a'] == null){
                return false;
            }
            cur = cur.next[c-'a'];
        }
        return cur.isWorld;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i ++){
            char c = prefix.charAt(i);
            if(cur.next[c-'a'] == null){
                return false;
            }
            cur = cur.next[c-'a'];
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

> 执行用时 :41 ms, 在所有 Java 提交中击败了94.51%的用户
>
> 内存消耗 :49 MB, 在所有 Java 提交中击败了100.00%的用户

#### 10.2.2 添加与搜索单词

> 设计一个支持以下两种操作的数据结构：
>
> void addWord(word)
> bool search(word)
> search(word) 可以搜索文字或正则表达式字符串，字符串只包含字母 . 或 a-z 。 . 可以表示任何一个字母。
>
> 示例:
>
> addWord("bad")
> addWord("dad")
> addWord("mad")
> search("pad") -> false
> search("bad") -> true
> search(".ad") -> true
> search("b..") -> true
> 说明:
>
> 你可以假设所有单词都是由小写字母 a-z 组成的。
>

```java
import java.util.TreeMap;
class WordDictionary {
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
    public WordDictionary() {
        root = new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                cur.next.put(c, new Node());
            }
            cur.next.put('.', new Node());
            cur = cur.next.get(c);
        }
        if(!cur.isWord){
            cur.isWord = true;
        }
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return match(root, word, 0);
    }
    private boolean match(Node root, String word, int index){
        //递归结束条件
        if(index == word.length()){
            return root.isWord;
        }
        //递归主体内容
        char c = word.charAt(index);
        // 当前判断的字符不是‘ . ’
        if(c != '.'){
            if(root.next.get(c) == null){
                return false;
            }
            return match(root.next.get(c), word, index+1);
        }else{ //当前判断的字符为‘ . ’，就要遍历所有的孩子节点的孩子节点们
            for(char nextC : root.next.keySet()){
                if( match(root.next.get(nextC), word, index+1) ){
                    return true;
                }
            }
            return false;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

> 执行用时 :94 ms, 在所有 Java 提交中击败了17.48%的用户
>
> 内存消耗 :59 MB, 在所有 Java 提交中击败了50.00%的用户

另外: 跟第一道题一样的思路，用26长度的字符数组

```java
class WordDictionary {
    private class Node{
        private boolean isWorld;
        private Node[] next;

        Node(){
            isWorld = false;
            next = new Node[26];
        }
    }
    private Node root;

    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Node();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node cur = root;
        for(int i = 0; i < word.length(); i ++){
            char c = word.charAt(i);
            if(cur.next[c-'a'] == null){
                cur.next[c-'a'] = new Node();
            }
            cur = cur.next[c-'a'];
        }
        if(!cur.isWorld){
            cur.isWorld = true;
        }
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(root, word, 0);
    }
    //递归查询
    private boolean search(Node node, String word, int index){
        if(index == word.length()){
            return node.isWorld;
        }
        char c = word.charAt(index);
        if(c == '.'){
            for(int i = 0; i < 26; i ++){
                if(node.next[i] != null && search(node.next[i], word, index+1)){
                    return true;
                }
            }
            return false;
        }else{
            if(node.next[c-'a'] == null){
                return false;
            }
            return search(node.next[c-'a'], word, index+1);
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
```

> 执行用时 :50 ms, 在所有 Java 提交中击败了91.86%的用户
>
> 内存消耗 :50.9 MB, 在所有 Java 提交中击败了100.00%的用户

#### 10.2.3 键值映射

> 实现一个 MapSum 类里的两个方法，insert 和 sum。
>
> 对于方法 insert，你将得到一对（字符串，整数）的键值对。字符串表示键，整数表示值。如果键已经存在，那么原来的键值对将被替代成新的键值对。
>
> 对于方法 sum，你将得到一个表示前缀的字符串，你需要返回所有以该前缀开头的键的值的总和。
>
> 示例 1:
>
> 输入: insert("apple", 3), 输出: Null
> 输入: sum("ap"), 输出: 3
> 输入: insert("app", 2), 输出: Null
> 输入: sum("ap"), 输出: 5

```java
import java.util.TreeMap;

class MapSum {
    public class Node{
        private int val;
        private TreeMap<Character, Node> next;
        public Node(){
            val = 0;
            next = new TreeMap<>();
        }
    }
    private Node root;
    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }
    
    public void insert(String key, int val) {
        Node cur = root;
        for(int i = 0; i < key.length(); i ++){
            char c = key.charAt(i);
            if(cur.next.get(c) == null){
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        cur.val = val; //不管等不等于0都重新赋值
    }
    
    public int sum(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return 0;
            }
            cur = cur.next.get(c);
        }
        return(sum(cur));
    }

    private int sum(Node root){
        //递归终止条件
        if(root.next.size() == 0){
            return root.val;
        }
        //递归主体内容
        int res = root.val; //把只有一个的情况先列出来
        //然后可能有孩子
        for(char nextC : root.next.keySet()){
            // 对孩子再求val就是sum(cur.next.get(nextC))
            res += sum(root.next.get(nextC));
        }
        return res;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
```

> 执行用时 :19 ms, 在所有 Java 提交中击败了41.70%的用户
>
> 内存消耗 :39.7 MB, 在所有 Java 提交中击败了33.33%的用户

另外：加了一个sum记录

```java
class MapSum {
    private class Node{
        private int num; //记录自己的值
        private int sum; //记录作为前缀，其余的总值
        private TreeMap<Character, Node> next; //孩子节点们

        private Node(){
            num = 0;
            sum = 0;
            next = new TreeMap<>();
        }
    }
    private Node root;
    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }
    
    public void insert(String key, int val) {
        insert(root, key, val, 0);
    }
    private int insert(Node root, String key, int val, int index){
        if(index == key.length()){
            int res = val - root.num; //返回的要添加的值
            root.num = val;  
            root.sum += res;
            return res;
        }
        char c = key.charAt(index);
        if(root.next.get(c) == null){
            root.next.put(c, new Node());
        }
        int add = insert(root.next.get(c), key, val, index+1);
        root.sum += add;
        return add;
    }
    
    public int sum(String prefix) {
        Node cur = root;
        for(int i = 0; i < prefix.length(); i ++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return 0;
            }
            cur = cur.next.get(c);
        }
        return cur.sum;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */
```

> 执行用时 :16 ms, 在所有 Java 提交中击败了74.45%的用户
>
> 内存消耗 :39.7 MB, 在所有 Java 提交中击败了33.33%的用户

## 10.3 扩展

**最大问题就是空间问题**。

为了解决空间问题，设置压缩字典树，即把单列字符合并在一起，但是维护起来就更难了。有得就有失。

还有一种三叉字典树，（比如以d为顶上节点， 分小于d，大于d，等于d），这样使用三分搜索树，搜索的时间复杂度增加了，但是节约了空间，而且时间复杂度依旧和单词长度成正比。

#### 10.3.1 更多字符串问题

​	子串查询

​	文件压缩

​	模式匹配