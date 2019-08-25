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
    * <a href="#b7">泛型</a>
        * <a href="#b7.1">泛型类</a>
        * <a href="#b7.2">泛型方法</a>
        * <a href="#b7.3">类型变量的限定</a>
        * <a href="#b7.4">类型类型和虚拟机</a>
        * <a href="#b7.5">通配符类型</a>
    * <a href="#b8">集合</a>
        * <a href="#b8.1">泛型类</a>
        * <a href="#b8.2">泛型方法</a>
        * <a href="#b8.3">类型变量的限定</a>
        * <a href="#b8.4">类型类型和虚拟机</a>
        * <a href="#b8.5">通配符类型</a>
    * <a href="#b9">多线程</a>
        * <a href="#b9.1">中断线程</a>
        * <a href="#b9.2">线程6状态</a>
        * <a href="#b9.3">线程属性</a>
        * <a href="#b9.4">线程同步</a>
        * <a href="#b9.5">阻塞队列</a>
        

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
    1. 执行 try 块里的代码。
    2. 如果没有异常，则暂存 return 语句的值，然后跳转到 finally 块执行里面的代码，如果finally 语句块有 return 语句，则会覆盖 try 块的返回值。
    3. 如果出现异常，则跳转到 catch 块，执行里面的代码，有 return 语句，则暂存 return 语句的值，然后跳转到 finally 块执行里面的代码，如果finally 语句块有 return 语句，则会覆盖 catch 块的返回值。
    
    > finally 块中的 return 值 会覆盖  catch 块中的 return 值；catch 块中的 return 值 会覆盖  try 块中的 return 值； 
      
    > 也就是 finally 块的return值优先级 > catch 块的return值优先级 > try 块的return值优先级
 
#### <a name="b6.2">使用异常机制的建议</a>
* 异常处理不能代替简单的测试。
    > 异常处理开销大，仅在异常情况下才应该使用异常机制。
* 不要过分的细化异常。
* 利用异常层次结构。
    > 不要只抛出RuntimeException，应该寻找更适合的子类或者自定义异常类。  
    > 不要只捕获 Throwable 异常，否则代码更加难阅读、难维护。
* 不要压制异常。
* 在检查错误时，"苛刻比放任更好。
* 不要羞于传递异常。

> 断言：允许在测试期间在代码中插入一些检查语句，当代码发布时这些检测语句会被自动移走。  
> -ea(-enableassertions) 参数代表启用断言 -da(-disableassertions) 参数表示禁用断言，默认是禁用。  
> 断言可以根据某个类或某个包单独设置 -ea:MyClass -da:com.your 类MyClass启用断言 包 com.your 禁用断言。

### <a name="b7">泛型</a>
#### <a name="b7.1">泛型类</a>

* 泛型类的定义：一个泛型类就是有一个或多个类型变量的类。  
```java
public class Pair<T> {

    private T first;
    private T second;

    public T getFirst(){
        return this.first;
    }

    public T getSecond(){
        return this.second;
    }
}
```  
> 类型变量使用大写形式且比较短。java库中使用变量 E 表示集合的元素类型，K 和 V 表示表的关键字和值的类型，T（U/S）表示”任意类型“。  

#### <a name="b7.2">泛型方法</a>

* 泛型方法可以定义在普通类中。  
```java
public class ArrayAlg {

    public static <T> T getMiddle(T[] arr){
        return arr[arr.length/2];
    }
}
```  

#### <a name="b7.3">类型变量的限定</a>

* 类型变量限定  
```java
public static <T extends Comparable> T min(T[] arr) {
    // 业务逻辑
}
```  
> 该方法只能被实现了 Comparable 接口的类的数组调用。  

* 一个类型变量或通配符可以有多个限定  
> T extends Comparable & Serializable ；限定类型用 & 分隔，而逗号(,)用来分隔类型变量。  

#### <a name="b7.4">类型类型和虚拟机</a>

* 虚拟机没有泛型类型对象-所有对象都是普通类型。 
> 任何时候定义一个泛型类型，都自动提供一个原始类型。原始类型的名字就是删除类型参数后的泛型类型名。擦除类型变量，并替换为限定类型(无限定类型使用Object)。

* 如果一个泛型类有类型变量有多个限定类型，泛型擦除时就使用第一个限定类型变量来替换原始类型，如果没有给定限定就使用Object替换原始类型。  
* 当调用泛型方法时，如果擦除返回类型，编译器插入强制类型转换。

#### <a name="b7.5">通配符类型</a>

* Pair<? extends Employee> 泛型 Pair 类型，它的类型参数是 Employee 的子类。
* 通配符的超类型限定 Pair<? supper Manager> 泛型 Pair 类型，它的类型参数是 Manager 的超类。
* 笑哭
```java
public static <T extends Comparable<? supper T>> T min(T[] arr) {
    // 业务逻辑
}
``` 

### <a name="b9">多线程</a>
#### <a name="b9.1">中断线程</a>

* 当对一个线程调用 interrupt() 方法时，线程的中断状态将被置位(置为 true)。每个线程都应该不时的检查这个标志位，以判断线程是否被中断。  
```java
while(!Thread.currentThread().isInterrupted() && isMoreWorkTodo){
    // do more work
}
```
* 但是如果线程被阻塞，就无法检查中断状态（这是产生 InterruptedException 的地方）。当在一个被阻塞的线程（调用 sleep()或者 wait()）上调用 interrupt() 方法时，阻塞调用将会被 InterruptedException 异常中断。

* Thread 类的实例方法 interrupt()、isInterrupted()和静态方法 interrupted() 区别  
    * 实例方法 void interrupt()：向线程发送中断请求，线程的中断状态被设置为 true。如果目前该线程被一个sleep()/wait()方法阻塞，则抛出 InterruptedException 异常。
    * 实例方法 boolean isInterrupted()：检查当前线程是否被中断，不会改变中断状态。
    * 静态方法 static boolean interrupted()：检查当前线程是否被中断，会将当前线程的中断状态置为 false。
* 捕获到 InterruptedException 时不要直接丢弃，应该让调用者知道这一状态
    * catch 语句块中 调用 Thread.currentThread().interrupt() 把线程的中断状态置为 true。
    * 方法声明抛出 InterruptedException 异常。

#### <a name="b9.2">线程6状态</a>
* 线程6状态：新生、可运行、被阻塞、等待、计时等待、终止。
    * 新生：new Thread(r) 时线程处于的状态。
    * 可运行：调用 start() 方法时线程处于的状态。（可能正在运行、也可能还没运行，取决于系统cpu调度）
    * 被阻塞：运行中的线程由于一些资源(IOZ阻塞\锁等待)而进入阻塞状态，线程暂时不活动，不运行任何代码消耗极少资源。
    * 等待/计时等待：当线程等待另一个线程通知调度器的一个条件时进入的状态。Object.wait(time) Thread.join()  Lock.tryLock() Condition.await()。
    * 终止：
        * run() 方法正常退出而自然死亡。
        * 因为一个没有捕获的异常终止了 run() 方法而意外死亡。
* Thread 常见方法
    * void join()：等待指定的线程终止。
    * void join(long millis)：等待指定的线程终止或经过指定的毫秒数。
    * void yield()：导致当前执行线程处于让步状态。如果有其他可运行的线程具有至少与该线程具有同级别的优先级，那么这些线程将会被调度。
    * Thread.State getState()：获取线程状态：NEW RUNNABLE BLOCKED WAITING TIMED_WAITIND TERMINATED
    * void stop()：停止该线程，方法已经过时。
        > 因为天生不安全，该方法终止所有为结束的方法，当线程终止时，立即释放所有被它锁住的所有对象锁，会导致对象处于不一致状态。当线程要终止另一个线程时，不知道什么时候调用 stop 方法。

    * void suspend()：暂停该线程的执行，方法已经过时。
        > 因为会经常导致死锁。不会破坏对象状态，但是用 suspend 挂起一个持有一个锁的线程，那么该锁在线程恢复前是不可用的，如果调用 suspend 的线程实体获得同一个锁，程序将会死锁。

    * void resume()：恢复线程，仅在调用 suspend() 后调用，方法已经过时。
        > 因为  


#### <a name="b9.3">线程属性</a>
* 线程优先级、守护线程、线程组、处理未捕获异常的处理器。
    * 线程优先级：一个线程继承父线程的优先级，优先级范围[1,10]，默认为 NORM_PRIORITY=5。
        > 线程的优先级高度依赖于操作系统。当虚拟机依赖宿主机平台的线程实现机制时，java线程优先级映射到宿主机平台的优先级上。Windows有7个优先级。在sun为Linux提供的java虚拟机，线程优先级被忽略-所有线程具有相同的优先级。(不要将程序构建为功能的正确性依赖于优先级)  
    * 守护线程：setDeamon(true) 将线程设置为守护线程-为其他线程服务。当只有守护线程时虚拟机就退出了。
        > 守护线程不要去访问固有资源(文件、数据库)，因为守护线程会随时被中断。  
    
    * 未捕获异常处理器：线程的 run() 不能抛出任何被检测的异常，但是不被检测的异常会导致线程终止。线程在死亡前，异常被传递到一个未捕获异常处理器。
        > 未捕获异常处理器 必须属于 Thread.UncaughtExceptionHandler 接口的子类。  
        ```java
        void uncaughtException(Thread t, Throwable e)
        ```
        jdk5.0 后可以使用 setUncaughtExceptionHandler(UncaughtExceptionHandler handler) 方法为任何线程设置未捕获异常处理器。或者使用 Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler handler) 为所有的线程设置未捕获异常处理器。  
        但是，如果不为线程设置未捕获异常处理器，则默认为该线程的 ThreadGroup 对象。     
        * ThreadGroup 实现了 Thread.UncaughtExceptionHandler 接口，它的 uncaughtException 方法做如下操作：
            1. 如果该线程组有父级线程组，则调用父级线程组的 uncaughtException 方法。
            2. 否则 Thread.getDefaultUncaughtExceptionHandler 返回一个非空的处理器，则调用该处理器的 uncaughtException 方法。
            3. 否则 如果 Throwable 是 ThreadDeath 的一个实例，则什么都不做。
            4. 否则 线程的名字以及 Throwable 的栈踪迹被输出到 System.err 上。
    
#### <a name="b9.4">线程同步</a>
* synchronized：
* volatile：声明为 volatile 的域，编译器和虚拟机就知道该域可能被其他线程并发更新。
    * volatile 变量不能保证原子性。
    * 以下3个条件可用保证域的并发访问是安全的：
        1. 域是 final，并且在构造器调用完成后才被访问。
        2. 对域的访问有公有锁进行同步。
        3. 域是volatile的。
    * volatile保证获取一个volatile变量值是直接从主内存获取，修改一个volatile变量时，立刻写回主内存。
    * volatile 可用防止指令重排序。
* Lock：常用方法
    1. void lock()：获取锁，获取不到则阻塞。
    2. void unLock()：释放锁。
    3. boolean tryLock()：尝试获取锁而不发生阻塞，如果成功返回 true。会抢夺可用锁，及时该锁有公平加锁策略。
    4. boolean tryLock(long time TimeUnit unit)：尝试获取锁，阻塞时间不会超过给定的值，如果成功返回 true。
    5. void lockInterruptibly()：获取锁，不确定地发生阻塞，如果线程被中断，则抛出 InterruptedException 异常。


* ReenTrantLock：
* ReenTrantReadWriteLock：可重入读写锁。
    * Lock readLock()：得到一个可以被多个读操作共享的读锁，但会排斥所有的写操作。
    * Lock writeLock()：得到一个写锁，排斥所有其他的读操作和写操作。
* Condition：
    * void await()：将该线程放到条件的等待集中。当前线程被阻塞，并放弃了锁。直到其他线程调用了同一个条件的signal或者signalAll方法时，该线程可能有机会重新激活，激活后将重新试图获取锁，获得锁的后将重被阻塞的地方继续执行代码。  
    > 当一个线程调用 await 方法后，它没办法重新激活自己，如果没有其他线程来激活等待的线程，它将永远不会再运行，产生死锁。  

    ```java
    while(!(isCanProceed)) {
        condition.await();
    }
    ```
    * boolean await(long time, TimeUnit unit)：进入条件的等待集中。当前线程被阻塞，并放弃了锁。直到其他线程调用了同一个条件的signal或者signalAll方法时，该线程可能有机会重新激活，激活后将重新试图获取锁，获得锁的后将重被阻塞的地方继续执行代码。 等待超时返回 false。
    * void awaitUninterruptedException()：进入等待集，直到线程被从等待集移除才能解除阻塞。如果线程被中断，不会抛出 InterruptedException 异常。
    * void signalAll()：解除该等待集中的所有线程的阻塞状态。调用该方法不会立即激活一个线程，仅仅解除等待线程的阻塞状态，以便这些线程可以在当前线程退出同步方法之后，通过竞争实现对同步资源的访问。
    * void signal()：从该条件的等待集中随机选择一个线程解除其阻塞状态。比 signalAll() 更有效，但是也更加危险：当随机选择的线程发现仍不能运行自己时，其再次被阻塞。如果没有其他线程再次调用 signal，那么系统就死锁了。 
    
* 内个对象都有一个内部锁
    * 内部锁和条件的局限性
        1. 不能中断一个正式试图获得锁的线程。
        2. 试图获得锁时不能设定超时。
        3. 每个锁只有一个单一的条件，可能是不够的。
* Lock/Condition synchronized 使用的建议
    1. 最后既不要使用 Lock/Condition 也不要使用 synchronized 关键字。大多数情况可以使用 java.util.concurrent 包中的一种机制来满足需求。
    2. 如果 synchronized 关键字满足需求，优先使用：代码少、减少出错几率。
    3. 如果特别需要 Lock/Condition 结构提供的特性，才使用 Lock/Condition 。
* 监视器具备的特性：
    1. 监视器是只包含私有域的类。
    2. 每个监视器类有一个相关的锁。
    3. 使用该锁对所有方法进行加锁。即客户端调用obj.method() ，那么obj对象的锁在调用方法前自动获得，并且在退出方法后自动释放。
    4. 该锁可以有任意多个相关条件。  
> java以不是很精确的方式才有监视器的概念，java中每个对象都有一个内部锁和一个内部条件，当一个方法使用 synchronized 声明，则其表现就像一个监视器。通过 wait/notifyAll/notify来访问条件变量。
* java对象的3个方面不同于监视器，使得线程安全性下降： 
    1. 域不要求必须是 private。
    2. 方法不要求必须是 synchronized。
    3. 内部锁对客户是可用的。

#### <a name="b9.5">阻塞队列</a>
* ArrayBlockingQueue：有界阻塞队列，循环数组实现。
* LinkedBlockingQueue：无界阻塞队列，链表实现。
* LinkedBlockingDeque：无界阻塞双端队列，链表实现。
* DelayQueue：无界阻塞时间有限的阻塞队列，只有那些延迟超过了指定时间的元素才可以从队列中移除。
* PriorityBlockingDeque：无界阻塞优先队列，堆实现。
* ConcurrentHashMap：
* ConcurrentSkipListMap：
* ConcurrentSkipListSet：
* ConcurrentLinkedQueue：
* CopyOnWriteArrayList：
* CopyOnWriteArraySet：
> 任何集合通过使用同步包装器(synchronization wrapper)变成线程安全的。  
> Collections.synchronizedList() Collections.synchronizedSet() Collections.synchronizedMap() Collections.synchronizedSortedSet() Collections.synchronizedSortedMap() 









