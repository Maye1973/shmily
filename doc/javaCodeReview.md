# java基础复习
######

## 环境变量设置
* 1 编辑 ~/.bash_profile 文件内容如下
```text
JAVA_HOME_8=/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home
JAVA_HOME=$JAVA_HOME_8
PATH=$PATH:$GRADLE_HOME/bin:$PROTOC_HOME/bin
CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt/jar

export PATH
export JAVA_HOME
export CLASSPATH
```

## 基础知识
### 8种基本数据类型

|类型|存储需求|取值范围|
|:---:|:---:|:---:|
| char | 1字节 | ascII码 |
| byte | 1字节 | -128 ~ 127 |
| short | 2字节 | -32 768 ~ 32 767 |
| int | 4字节 | -2 147 483 648 ~ 2 147 483 647 |
| long | 8字节 | -2 147 483 648 ~ 2 147 483 647 |
| float | 4字节 | |
| double | 8字节 | |
| boolean | 1字节 | |

* 代码点-一个编码表中一个字符对应的代码值。Unicode 标准中代码点使用16进制书写，并加上前缀 U+。

* strictfp 标记的方法必须使用严格的浮点计算来产生理想的结果。 
 
> double w = X\*Y/Z 很多Intel处理器计算 X*Y，并将结果存储在80位的寄存器中，再除以Z并将结果截断为64位。这样可以得到一个更加精确的结果，并且可以避免指数溢出。但是，这个结果可能和在64位的机器上计算结果不一致。因此，java虚拟机最初规范规定所有中间计算都必须进行截断。这种行为遭到数值计算团队反对。截断计算不仅可能导致溢出，而且由于截断操作需要消耗时间，所以在计算上要比精确计算慢。为此，java程序设计语言承认最佳性能与理想结果存在冲突，并给予了改进。默认情况下，虚拟机设计者允许将中间计算结果才有扩展的精度。但是对于使用了strictfp 标记的方法必须使用严格的浮点计算来产生理想的结果。

* 位运算 >>> 运算符将用0填充高位，>> 运算符用符号位填充高位。  
> 对移位运算符右侧的参数需要进行模32位的运算（除非右边的操作数是long，这时需要对右侧参数模64）。例如 1<<35 和 1<< 3 ，1<<8是一样的。

### 数值类型转换规则

- 如果两个操作数中有一个是double类型，另一个操作数就会转换为double类型。
- 否则，如果其中一个操作数是float类型，另一个操作数就会转换为float类型。
- 否则，如果其中一个操作数是long类型，另一个操作数就会转换为long类型。
- 否则，两个操作数都转换为int类型。

![image](https://raw.githubusercontent.com/Maye1973/shmily/develop/doc/img/8%E7%A7%8D%E5%9F%BA%E6%9C%AC%E7%B1%BB%E5%9E%8B%E8%BD%AC%E6%8D%A2.png)

### Arrays类

* 打印数组 Arrays.toString(T[] array)
* 打印二维数组 Arrays.deepToString(T[][] array)
* 数组拷贝 Arrays.copyOf(T[] array, int start, int end)
* 数组排序 Arrays.sort(T[] array) 优化的快排算法
* 数组二分查找 Arrays.binarySearch(T[] array, int start, int end, T targe)

### 对象与类

#### 对象之间的关系
* 依赖: uses-a
* 聚合: has-a
* 继承: is-a

* 如果要返回一个可变对象的引用，首先要多它进行克隆。

* 值调用：方法接收的是调用者提供的值。
* 引用调用：方法接收的是调用者提供的变量地址。  
> java 程序设计语言总是采用值调用。

* 调用构造器的具体处理步骤  
    1. 所有数据域被初始化为默认值(0,false,null)
    2. 按照在类中出现的次序，依次执行所有域初始化语句和初始化块。
    3. 如果构造器第一行调用了第二个构造器，则执行第二个构造器的主体。
    4. 执行这个构造器的主体。

* 使用类的具体处理步骤
    1. 所有静态域被初始化为默认值(0,false,null)
    2. 按照在类中出现的次序，依次执行所有静态域初始化语句和静态初始化块。

* 对象解析和finalize方法
    1. finalize 方法将在垃圾收集器回收对象之前被调用。不用依赖 finalize 方法来回收短缺资源，因为很难知道这个方法什么时候才会被调用。
    2. Runtime.addShutdownHook 添加关闭钩子。

#### 类设计技巧
* 一定要将数据设计为私用的。
* 一定要对数据进行初始化。
* 不要在类中使用过多的基本数据类型。
* 不是所有的域都需要域访问器和域更改器。
* 使用标准格式进行类的定义。  
    > 1. 公用访问特性部分  
    > 2. 包作用域访问特性部分  
    > 3. 私有访问特性部分  

    > 实例域  
    > 静态域  
    > 实例方法  
    > 静态方法  

* 将职责过多的类进行分解。

### 继承



