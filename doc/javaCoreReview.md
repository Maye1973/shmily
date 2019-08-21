# java基础复习
######

# 目录索引
  * <a href="#A">环境变量设置</a>
     * <a href="#a1">系统背景</a>
     * <a href="#a2">相关术语</a>
     * <a href="#a3">参考资料</a>
  * <a href="#B">基础知识</a>
     * <a href="#b1">8种基本数据类型</a>
     * <a href="#b2">数值类型转换规则</a>
     * <a href="#b3">Arrays类</a>
     * <a href="#b4">对象与类</a>
        * <a href="#b4.1">对象对象之间的关系与类</a>
        * <a href="#b4.2">类设计技巧</a>
     * <a href="#b5">继承</a>
        * <a href="#b5.1">this和supper</a>
        * <a href="#b5.2">默认构造器的重要性</a>
        * <a href="#b5.3">多态和动态绑定</a>
        * <a href="#b5.4">equals 方法和 hashCode 方法</a>
    * <a href="#b6">异常</a>
        * <a href="#b6.1">处理异常</a>
        * <a href="#b6.2">使用异常机制的建议</a>
        

## <a name="A">环境变量设置</a>
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

## <a name="B">基础知识</a>
### <a name="b1">8种基本数据类型</a>

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

### <a name="b2">数值类型转换规则</a>

- 如果两个操作数中有一个是double类型，另一个操作数就会转换为double类型。
- 否则，如果其中一个操作数是float类型，另一个操作数就会转换为float类型。
- 否则，如果其中一个操作数是long类型，另一个操作数就会转换为long类型。
- 否则，两个操作数都转换为int类型。

![image](https://raw.githubusercontent.com/Maye1973/shmily/develop/doc/img/8%E7%A7%8D%E5%9F%BA%E6%9C%AC%E7%B1%BB%E5%9E%8B%E8%BD%AC%E6%8D%A2.png)

### <a name="b3">Arrays类</a>

* 打印数组 Arrays.toString(T[] array)
* 打印二维数组 Arrays.deepToString(T[][] array)
* 数组拷贝 Arrays.copyOf(T[] array, int start, int end)
* 数组排序 Arrays.sort(T[] array) 优化的快排算法
* 数组二分查找 Arrays.binarySearch(T[] array, int start, int end, T targe)

### <a name="b4">对象与类</a>

#### <a name="b4.1">对象对象之间的关系与类</a>
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

#### <a name="b4.2">类设计技巧</a>
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

### <a name="b5">继承</a>

#### <a name="b5.1">this和supper</a>

* 关键字 this 有两个作用  
    1. 引用隐式参数。
    2. 调用该类其他的构造器。
* 关键字 supper 有两个作用  
    1. 调用超类的方法。
    2. 调用超类的构造器。

> 调用构造器的语句只能作为另一个构造器的第一条语句出现。  

#### <a name="b5.2">默认构造器的重要性</a>

* 如果子类的构造器没有显示调用超类的构造器，则将自动调用超类默认构造器(无参数构造器)。如果超类没有不带参数的构造器，且子类的构造器又没有显示调用超类其他构造器，编译将失败。
* 反射 newInstance()调用默认构造器。

#### <a name="b5.3">多态和动态绑定</a>

* 方法签名：方法的名称和参数列表。
* 多态：一个对象变量可以引用多种实际类型的现象。
* 动态绑定：在运行时能够自动选择调用哪个方法的现象。

* 子类数组的引用可以转换成超类数组的引用，而不需要强制类型转换。  

```java
// Manager 继承自 Employee
Manager[] managers = new Manager[10];
Employee[] staff = managers; // ok
staff[0] = new Employee(); // 编译器既然通过，我们把一个普通员工放到了经理行列。运行时将会报错 ArrayStoreException
```

* 动态绑定-方法调用过程  
    1. 编译器查看对象的声明类型和方法名。假设声明类型为C类，方法名为f(params)，则获取C类中名为f的方法、父类中访问属性为protected和public名为f的方法。
    2. 编译器查看调用方法是的参数类型，选择一个匹配性最高的方法。这个过程称为重载解析。
    3. 如果是private、static、final方法或者构造器，编译器明确知道应该调用哪个方法。这种调用方式称为静态绑定。
    4. 每次调用都要搜索，开销大。虚拟机为每一个类创建一个方法表：保存所有方法的签名和调用方法的映射关系。

* 内联：一个方法没被覆盖并且很短，编译器就对个方法进行优化。如内联调用 e.getName() 则被优化为 e.name 。
* 慎用 protected 属性：破话 OOP 数据封装原则。

#### <a name="b5.4">equals 方法和 hashCode 方法</a>

* java语言规范要求 equals 方法具备以下特性：
    1. 自反性：对于任意非空 x，x.equals(x)应该返回true。
    2. 对称性：对于任何引用 x 和 y，当且仅当 x.equals(y) 返回true时，y.equals(x) 返回true时。
    3. 传递性：对于任何引用 x 和 y、z，如果 x.equals(y) 返回true，y.equals(z) 返回true，则 x.equals(z)应该返回true。
    4. 一致性：如果 x 和 y 没有发生变化，反复调用  x.equals(y) 都应该返回相同的结果。
    5. 对于任意非空 x，x.equals(null) 应该返回false。

* 编写 equals 方法的建议：
    1. 显示参数命名为 otherObject ，稍后转换为另一个叫 other 的变量。
    2. 检测 this 和 otherObject 是否引用同一个对象： 
    ```java
    if (this == otherObject) { 
        return true;
    }
    ``` 
    3. 检测 otherObject 是否为 null ，为 null 返回 false ：
    ```java
    if (null == otherObject) { 
        return false;
    }
    ``` 
    4. 检测 this 和 otherObject 是否为同一个类，不是返回 false ：
    ```java
    if (getClass() != otherObject.getClass()) { 
        return false;
    }
    ```   
    如果所有的子类都拥有统一的语言，则使用 instanceof 检测：  
    ```java
    if (!(otherObject instanceof ClassName)) { 
        return false;
    }
    ```  
    5. 将 otherObject 转换为对应类的类型变量 ：
    ```java
    ClassName other = (ClassName)otherObject;
    ```  
    6. 对所有需要比较的域进行比较，使用 == 比较基本类型；使用 equals 比较引用类型 ：
    ```java
    return supper.equals(other) 
            && field1 == other.field1 
            && field2.equals(other.field2);
    ```  
    > 对应数组类型的域可以使用 Arrays.equals() 进行检测。

### <a name="b6">异常</a>
#### <a name="b6.1">处理异常</a>

* 异常分为 Error 和 Exception 两大类。
    1. Error 描述java运行时系统内部错误和资源耗尽错误，对应这种错误，只能尽力使程序安全终止。
    2. Exception 分为运行时异常 RuntimeException 和其他异常。  
    > RuntimeException 由程序错误导致的异常。 “如果出现 RuntimeException 异常，那么一定是你的问题”  
    > 项目中自定义异常都是继承 RuntimeException ，包括参数校验失败时抛出的异常。个人感觉这里属于已检异常，不应该继承于 RuntimeException；只有类型DB错误类我们无法预先判断的才应该继承于 RuntimeException 。

* 派生于 Error 和 RuntimeException 的异常，统称为未检异常，其他统称为已检异常。
> 编译器将核查是否为所有已检异常提供了异常处理器。

* 方法声明抛出异常的建议：  
    1. 调用一个抛出已检查异常的方法。  
    2. 程序运行过程中发现错误，并用 throw 语句抛出一个已检异常。  
    3. 程序出现错误（未检异常）。
    4. JVM 和运行时库出现内部异常。
> 总之一个方法必须声明所有可能抛出的已检异常，而未检异常要么不可控制(Error)，要么就应该避免发生在(RuntimeException)。

* 应该捕获那些知道如何处理的异常，而将那些不知道怎么处理的异常传递出去。

* try-catch-finally 执行顺序  
    1. 
 
