# MethodHandle 和 Invokedynamic

### MethodHandle 方法句柄
* 方法句柄 是 JSR-292 中引入的一个概念，它是对Java中方法、构造方法、和域的一个强类型的可执行引用。
    > 作用类似反射API中的Method类，但是功能更强大、使用更灵活、性能更好。  
* 方法句柄的类型：完全有参数类型和返回类型确定。(和方法名无关)
* MethodType 类：不可变，任何对 MethodType 对象的修改都会返回一个新的 MethodType 对象。
    1. MethodType 只能通过 MethodType 类的静态工厂方法获取。
        > * MethodType.methodType(Class<?> rtype, Class<?>[] ptypes) 的重载方法。第一个参数是返回值类型，后面是0到多个参数类型。    
        > * MethodType.genericMethodType(int objectArgCount, boolean finalArray) 的重载方法。返回参数和返回值类型都是 Object 类型的 MethodType。objectArgCount 参数个数；finalArray == true 时，在参数列表后面添加一个 Object[] 类型的参数。    
        > * MethodType.fromMethodDescriptorString(String descriptor, ClassLoader loader) 使用方法类型在字节码中的表示形式的字符串作为创建 MethodType 的参数。   
            > "(Ljava/lang/String;)Ljava/lang/String;" 所表示的方法类型是返回值和参数类型都是 java.lang.String，相当于 MethodType.methodType(String.class, String.class)。  

    2. MethodType 的修改都是围绕返回值类型和参类型进行的。   
        ```java
        // (int, int)String
        MethodType methodType = MethodType.methodType(String.class, int.class, int.class);
        ```
    > * 尾部添加参数 methodType.appendParameterTypes(Class<?>... ptypesToInsert) 在原来的参数列表后面新增0到多个参数类型。  
        ```java
        // (int, int, long, double)String
        methodType = methodType.appendParameterTypes(long.class, double.class);
        ```  
    > * 指定位置开始添加N个参数 methodType.insertParameterTypes(int num, Class<?>... ptypesToInsert) 。   
        ```java
        // (int, int, String, int, long, double)String
        methodType = methodType.insertParameterTypes(2, String.class, int.class);   
        ```       
    > * 删除从指定位置开始到结束位置(不包含)的参数 methodType.dropParameterTypes(int start, int end)。 
        ```java
        // (int, String, int, long, double)String
        methodType = methodType.dropParameterTypes(1, 2);   
        ```  
    > * 修改指定位置的参数 methodType.changeParameterType(int num, Class<?> nptype)。
        ```java
        // (String, String, int, long, double)String
        methodType = methodType.changeParameterType(0, String.class);   
        ``` 
    > * 改变返回类型 methodType.changeReturnType(Class<?> nrtype)。
        ```java
        // (String, String, int, long, double)int
        methodType = methodType.changeReturnType(int.class);   
        ```     
    > * methodType.wrap() 把基础类型替换为包装类型。    
    > * methodType.unwrap() 把基础类型的包装类型转换为基本类型。    
    > * methodType.generic() 把返回值和参数类型都替换为 Object类型。    
    > * methodType.erase() 把引用类型替换为 Object，不处理基本类型。

* MethodHandle 类：类似反射 API 的 Method 类，可以调用底层的方法。
    1. MethodHandle 获取方式
        * MethodHandles.Lookup lookup.findVirtual(Class<?> refc, String name, MethodType type) 获取普通方法的方法句柄。普通方法 refc 可以访问的方法包括继承得到的。
        ```text
            refc：要调用目标方法的类或者接口    
            name：调用目标方法的名字    
            type：目标方法的类型(方法的返回值和参数列表)
        ```
            
        * MethodHandles.Lookup lookup.findSpecial(Class<?> refc, String name, MethodType type, Class<?> specialCaller) 获取某个类特有方法的方法句柄。特有方法 specialCaller 可以访问的方法包括私有方法。可以理解 refc 是父类，specialCaller 是具体的某个子类。
        ```text
            refc：要调用目标方法的类或者接口    
            name：调用目标方法的名字    
            type：目标方法的类型(方法的返回值和参数列表)
            specialCaller：实际调用方法的类
        ```
        * MethodHandles.Lookup lookup.findStatic(Class<?> refc, String name, MethodType type) 获取 refc 类的静态方法。
        ```text
            refc：要调用目标方法的类或者接口    
            name：调用目标方法的名字    
            type：目标方法的类型(方法的返回值和参数列表)   
        ```  
        > MethodHandles.Lookup 还有提供了获取构造方法获取域的方法。   
        > * findConstructor(Class<?> refc, MethodType type) 获取构造方法    
        > * findGetter(Class<?> refc, String name, Class<?> type) 获取setter方法   
        > * findSetter(Class<?> refc, String name, Class<?> type) 获取getter方法 

    2. MethodHandle 使用方式    
        * methodHandle.invokeExact(Object... args)：第一个参数代表方法接收者的对象(实际调用的对象)，后面的参数是实际的参数列表。  
            invokeExact 调用进行严格类型匹配(返回值类型、参数列表类型必须和MethodType的一样)需要进行强制类型转换为第一个参数的类型，否则报错。   
            ```java 
            MethodHandles.Lookup lookup =  MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(String.class, int.class, int.class);
            MethodHandle methodHandle = lookup.findVirtual(String.class, "substring", methodType);
            // 必须强制类型转换为 String 输出 EL
            String str = (String)methodHandle.invokeExact("HELLO WORLD", 1, 3);
            ```  

        * methodHandle.invoke(Object... args)：和 invokeExact(Object... args) 类似，不过调用方式更加松散，它会尝试在调用是进行返回值和参数类型的转换工作。通过 MethodHandle 的 asType 方法来完成(把当前的方法句柄适配到新的 MethodType 上，并产生一个新的返回值，适配失败抛异常)。    
            * 原类型 S 到 目标类型 T 匹配成功的基本原则：    
            > 1. Java类型转换 子类 -> 父类。比如 String —> Object    
            > 2. 基本类型转换 类型范围扩大。比如 int -> long    
            > 3. 基本类型的装箱和拆箱机制。    
            > 4. 如果 S 有返回值类型，而 T 的返回值是 void，则 S 的返回值会被丢弃。   
            > 5. 如果 S 返回值类型是 void，而 T 的返回值类型是引用类型，则 T 的返回值是 null。    
            > 6. 如果 S 有返回值是 void，而 T 的返回值是基本类型，则 T 的返回值是 0。

        * methodHandle.invokeWithArguments(Object... arguments)：底层还是通过 invokeExact(Object... args) 进行调用。    
            * 调用过程：    
            > 1. 通过 MethodType.genericMethodType()得到一个返回值类型和参数类型都是 Object 的 MethodType。
            > 2. 再把原始方法句柄通过 asType 转换后得到一个新的方法句柄。
            > 3. 最后通过新方法句柄的 invokeExact(Object... args) 进行调用。   
            * 好处：可以通过反射 API 被正常获取和调用，作为方法句柄和反射 API 的桥梁。
    3. MethodHandle 参数长度可变的方法句柄
        * methodHandle.asVarargsCollector(Class<?> arrayType)：把原始方法句柄最后一个数组类型的参数转换成对应类型的可变长参数，返回新的方法句柄，在新方法句柄调用的时候就可以使用可变长参数语法格式，而不用使用原始数组形式。在实际调用过程中，会把可变长参数组成数组，传给目标方法。(主要为了开发人员编码方便)    
        ```java
        public class Varargs {
            public void normalMethod(String arg1, int arg2, int[] arg3) {
                System.out.println(String.join("-", arg1, String.valueOf(arg2), Arrays.toString(arg3)));
            }

            public void asVarargsCollector() throws Throwable {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodType mt = MethodType.methodType(void.class, String.class, int.class, int[].class);
                MethodHandle md = lookup.findVirtual(Varargs.class, "normalMethod", mt);
                md = md.asVarargsCollector(int[].class);
                // 实际调用 normalMethod("hello", 2, new int[]{3,4,5})
                // 输出 hello-2-[3, 4, 5]
                // md.invokeExact(this, "hello", 2, 3, 4, 5); 报错 WrongMethodTypeException
                md.invoke(this, "hello", 2, 3, 4, 5);
            }
        }

        ```
        * methodHandle.asCollector(Class<?> arrayType, int arrayLength)：和 asVarargsCollector(Class<?> arrayType) 类似，不同的是只会把指定数量的参数收集到原始方法句柄所对应的底层方法的数组类型参数中，而不像 asVarargsCollector 那样可以收集任意数量的参数。   
        ```java
        public class Varargs {
            public void normalMethod(String arg1, int arg2, int[] arg3) {
                System.out.println(String.join("", arg1, String.valueOf(arg2), Arrays.toString(arg3)));
            }

            public void asCollector() throws Throwable {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodType mt = MethodType.methodType(void.class, String.class, int.class, int[].class);
                MethodHandle md = lookup.findVirtual(Varargs.class, "normalMethod", mt);
                md = md.asCollector(int[].class, 2);
                // 实际调用 normalMethod("hello", 2, new int[]{3,4})
                // 输出 hello-2-[3, 4]
                md.invokeExact(this, "hello", 2, 3, 4);
            }
        }

        ```
        * methodHandle.asCollector(Class<?> arrayType, int arrayLength)：把长度可以变的参数转换成数组参数，新方法句柄使用数组作为参数，数组中的元素会被按照顺序分配给原始方法句柄的各个参数。    
        ```java
        public class Varargs {
            public void toBeSpreader(String arg1, int arg2, int arg3, int arg4) {
                System.out.println(String.join("-", arg1, String.valueOf(arg2),String.valueOf(arg3), String.valueOf(arg4));
            }

            public void asSpreader() throws Throwable {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodType mt = MethodType.methodType(void.class, String.class, int.class, int.class, int.class);
                MethodHandle md = lookup.findVirtual(Varargs.class, "toBeSpreader", mt);
                md = md.asSpreader(int[].class, 3);
                // 实际调用 toBeSpreader("hello", 2, 3, 4)
                // 输出 hello-2-3-4
                md.invokeExact(this, "hello", new int[]{2,3,4});
            }
        }

        ```
    4. MethodHandle 参数参数绑定
        * MethodHandle.bindTo(Object x) 可以预先绑定底层方法的调用接收者，后续实际调用的时候桌子要传入实际参数即可。
            > bindTo 只是绑定方法句柄的第一个参数，并不要求这个参数一定是方法调用的接收者。即可以多次使用 bindTo 对其中的多个参数进行绑定。

* MethodHandles 类实现方法句柄的变换
    1. MethodHandles.dropArguments(MethodHandle target, int pos, Class<?>... valueTypes)：在方法句柄参数中添加无用参数，添加的参数实际调用时不会被传递给底层方法，但可以使变换后的方法句柄参数类型格式符合某些所需要的特定格式。
    ```java
        public static void dropArguments() throws Throwable {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType methodType = MethodType.methodType(String.class, int.class);
            MethodHandle methodHandle = lookup.findVirtual(String.class, "substring", methodType);
            methodHandle = methodHandle.bindTo("Hello World");
            String subStr = (String) methodHandle.invokeExact(1);
            // 实际调用 "Hello World".substring(1)
            // 输出 subStr-ello World
            System.out.println(String.join("-", "subStr", subStr));

            MethodHandle methodHandleNew = MethodHandles.dropArguments(methodHandle, 0, int.class);
            String subStrNew = (String) methodHandleNew.invokeExact(4, 2);
            // 实际调用 "Hello World".substring(2)
            // 输出 subStrNew-llo World
            System.out.println(String.join("-", "subStrNew", subStrNew));
        }

    ```

    2. MethodHandles.insertArguments(MethodHandle target, int pos, Class<?>... valueTypes)：同时为方法句柄多个参数预先绑定具体的值(和 bindTo 类型)。
    
    3.  MethodHandles.filterArguments(MethodHandle target, int pos, MethodHandle... filters)：可以对方法句柄调用时的参数进行预处理，再把处理的结果作为实际调用时的参数。预处理的过程是通过其他方法句柄来完成的。    
    ```java
        public static void filterArguments() throws Throwable {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                // 调用 Math.max(int, int)
                MethodType methodType = MethodType.methodType(int.class, int.class, int.class);
                MethodHandle methodHandle = lookup.findStatic(Math.class, "max", methodType);

                // 调用 string.length
                MethodHandle methodHandleLength
                        = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
                // filter改变方法句柄
                MethodHandle methodHandleNew
                        = MethodHandles.filterArguments(methodHandle, 0, methodHandleLength, methodHandleLength);
                // 先使用 methodHandleLength 计算字符串长度，然后再调用 methodHandle 对应的 Math.max(int, int)
                int max = (int) methodHandleNew.invokeExact("hello", "hello world");
                // 输出 11
                System.out.println(max);
        }
    ```

    4. MethodHandles.foldArguments(MethodHandle target, MethodHandle combiner)：对参数进行预处理，处理后的结果插入到原始参数列表的前面，作为一个新的参数（预处理返回void则不会添加）。参数预处理由一个方法句柄处理。

    5. MethodHandles.fpermuteArguments(MethodHandle target, MethodType newType, int... reorder)：对调用时的参数顺序进行重排序，再传递给原始的方法句柄来完成调用。

    6. MethodHandles.catchException(MethodHandle target,Class<? extends Throwable> exType,MethodHandle handler)：为原始方法句柄指定处理特定异常的方法句柄（可以实现通用异常处理逻辑）。

    7. MethodHandles.filterReturnValue(MethodHandle target, MethodHandle filter)：对原始方法句柄的返回值进行修改。

* 方法句柄实现接口
    1. MethodHandleProxies 类的静态方法 asInterfaceInstance 来实现。
        * <T> T asInterfaceInstance(final Class<T> intfc, final MethodHandle target)    
        > * intfc 要实现的接口类   
        > * target 处理方法调用逻辑的方法句柄对象。  
    > 限制条件    
    > * 接口必须公开。    
    > * 接口只能包含一个名称唯一的方法，   
    > * 接口类型的返回值和方法句柄的类型必须兼容。

* 访问控制权限：查找已有类中的方法得到方法句柄时进行一次访问控制权限检查，不像反射 API 每次调用 Method 类的 invoke 方法时都进行访问控制权限的检查。

* 交换点 SwitchPoint： 交换点是在多线程环境下控制方法句柄的一个开关，初始时为有效状态，一旦从有效状态变成无效状态，就无法再改变状态；这种状态改变是全局和即时生效的。
    1. 通过 SwitchPoint 的 guardWithTest 方法可以设置在交换点的不同状态执行不同的方法句柄。

### Invokedynamic 指令