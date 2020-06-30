链表、子串、数组  -------   双指针

快慢指针：

部分链表问题

归并排序找中点  用双指针

链表连成环  用双指针

左右指针：

反转数组 用双指针

滑动窗口：

子串问题 通常用滑动窗口

### 滑动窗口

主要解决**寻找子串**的问题

滑动窗口算法框架

```java
void slidingWindow(String s, String t){
    Map<Character, Integer> need, window;
    for(char c : t){
        need.push
    }
    //valid是验证 窗口内是否已经全部包含需要元素了
    int left = 0, right = 0, valid = 0;
    while(right < s.length()){
        //向右扩大窗口
        //c是将移入窗口的字符
        char c = s.charAt(right);
        //右移窗口
        right ++;
        //进行窗口内的数据的一系列更新
        ...;
        //判断左侧窗口是否要收缩
        while(window needs shrink){
            //d是将移出窗口的字符
            char d = s.charAt(left);
            //左移窗口
            left ++;
            //进行窗口内一系列更新
            ...;
        }
    }
}
```

左移右移窗口时 进行的窗口内的一系列更新 实际是处于对称的。

右侧窗口扩是一直移动的，左侧窗口缩是需要判断的。

右侧更新操作和左侧更新操作是刚好对称相反的！

右侧

使用该模板需要注意的四个点：（1）右侧更新操作、（2）何时暂停扩大而是缩小窗口、（3）左侧更新操作、（4）最终需要的结果是在扩大窗口时得到还是缩小窗口时得到。

力扣题 76、567、438、3。

### 快慢指针

主要解决**链表**问题

初始化时都指向链表的头节点，前进时快指针fast在前，慢指针slow在后。

快指针一次走两步，慢指针一次走一步。

解决环：如果快慢指针会相遇，说明有环。 力扣141、142

### 左右指针

左右指针在数组中实际是指两个索引值。

一般初始化为 left = 0, right = nums.length - 1。

力扣

### TwoSum

结合哈希集合。难点是**给定数组无序**。

```java
int[] twoSum(int[] nums, int target) {
    int n = nums.length;
    index<Integer, Integer> index = new HashMap<>();
    // 构造一个哈希表：元素映射到相应的索引
    for (int i = 0; i < n; i++)
        index.put(nums[i], i);

    for (int i = 0; i < n; i++) {
        int other = target - nums[i];
        // 如果 other 存在且不是 nums[i] 本身
        if (index.containsKey(other) && index.get(other) != i)
            return new int[] {i, index.get(other)};
    }

    return new int[] {-1, -1};
}
```

add频繁的TwoSum数据结构 模板

```java
class TwoSum {
    Map<Integer, Integer> freq = new HashMap<>();

    public void add(int number) {
        // 记录 number 出现的次数
        freq.put(number, freq.getOrDefault(number, 0) + 1);
    }

    public boolean find(int value) {
        for (Integer key : freq.keySet()) {
            int other = value - key;
            // 情况一
            if (other == key && freq.get(key) > 1)
                return true;
            // 情况二
            if (other != key && freq.containsKey(other))
                return true;
        }
        return false;
    }
}
```

find频繁的TwoSum数据结构 模板

```java
class TwoSum {
    Set<Integer> sum = new HashSet<>();
    List<Integer> nums = new ArrayList<>();

    public void add(int number) {
        // 记录所有可能组成的和
        for (int n : nums)
            sum.add(n + number);
        nums.add(number);
    }

    public boolean find(int value) {
        return sum.contains(value);
    }
}
```

力扣1、170

位运算 n&(n-1)的应用        注意（n&(n-1)）要括起来

力扣191、231

