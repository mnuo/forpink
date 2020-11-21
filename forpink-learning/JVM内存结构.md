
#### 1 内存空间
    - 方法区: 属于内存共享区域, 主要用于存储已经被虚拟机加载的类信息, 常量, 静态变量, 即时编译器编译后的代码等, 
                在该区域中, 存放编译器生成的各种字面量和符号的引用, 叫做运行时常量池
    - 堆: 属于线程共享内存, 主要存放对象的实例
    -  程序计数器: 属于线程私有的数据空间, 是一块内存空间, 主要代表当前线程所执行的字节码行数指示器.
    - 虚拟机栈: 属于线程私有数据空间, 与线程同时创建,总数与线程关联, 代表Java方法执行的内存模型
    - 本地方法栈: 主要与虚拟机用到的Native方法相关,一般情况下,我们无须关心此区域


#### 2 例子1 

以上都是纯理论，我们举个例子来说明 JVM 的运行原理，我们来写一个简单的类，代码如下：
public class JVMShowcase {
//静态类常量,
public final static String ClASS_CONST = "I'm a Const";
//私有实例变量
private int instanceVar=15;
public static void main(String[] args) {
//调用静态方法
runStaticMethod();
//调用非静态方法
JVMShowcase showcase=new JVMShowcase();
showcase.runNonStaticMethod(100);
}
//常规静态方法
public static String runStaticMethod(){
return ClASS_CONST;
}
//非静态方法
public int runNonStaticMethod(int parameter){
int methodVar=this.instanceVar * parameter;
return methodVar;
}
}


这个类没有任何意义，不用猜测这个类是做什么用，只是写一个比较典型的类，然后我们来看
看 JVM 是如何运行的，也就是输入 java JVMShow 后，我们来看 JVM 是如何处理的：

        向操作系统申请空闲内存。JVM 对操作系统说“给我 64M 空闲内存”，于是第 1 步，JVM 向操作系统申请空闲内存
作系统就查找自己的内存分配表，找了段 64M 的内存写上“Java 占用”标签，然后把内存段的起 始地址和终止地址给 JVM，JVM 准备加载类文件。

分配内存内存。 第 2 步，JVM 分配内存。JVM 获得到 64M 内存，就开始得瑟了，首先给 heap 分个内存，并
且是按照 heap 的三种不同类型分好的，然后给栈内存也分配好。

        文件。第 3 步，检查和分析 class 文件。若发现有错误即返回错误。

      加载类。第 4 步，加载类。由于没有指定加载器，JVM 默认使用 bootstrap 加载器，就把 rt.jar 下的所有
类都加载到了堆类存的永久存储区，JVMShow 也被加载到内存中。我们来看看栈内存，如下图：

Heap 是空，Stack 是空，因为还没有线程被执行。Class Loader 通知 Execution Enginer 已经加
载完毕。

      执行引擎执行方法。第 5 步，执行引擎执行 main 方法。执行引擎启动一个线程，开始执行 main 方法，在 main 执
行完毕前，方法区如下图所示：


在 Method Area 加入了 CLASS_CONST 常量，它是在第一次被访问时产生的。堆内存中有两 个对象 object 和 showcase 对象，如下图所示：

为什么会有 Object 对象呢？是因为它是 JVMShowcase 的父类，JVM 是先初始化父类，然后再
初始化子类，甭管有多少个父类都初始化。在栈内存中有三个栈帧，如下图所示：

于此同时，还创建了一个程序计数器指向下一条要执行的语句。
     
释放内存。运第 6 步，释放内存。运行结束，JVM 向操作系统发送消息，说“内存用完了，我还给你”
行结束。

#### 3 例子2
静态变量：位于方法区。

实例变量：作为对象的一部分，保存在堆中。

临时变量：保存于栈中，栈随线程的创建而被分配。

栈内存中存放局部变量（基本数据类型和对象引用)，而堆内存用于存放对象（实体）

对于字符串而言，如果是编译期已经创建好(直接用双引号定义的)的就存储在常量池中；如果是运行期（new出来的）才能确定的就存储在堆中。

对于equals相等的字符串，在常量池中永远只有一份，在堆中可以有多份。

最后，借助网上看到的一个例子帮助对栈内存，堆内存的存储进行理解：

 class BirthDate {  
        private int day;  
        private int month;  
        private int year;      
        public BirthDate(int d, int m, int y) {  
            day = d;   
            month = m;   
            year = y;  
        }  
        省略get,set方法………  
    }  
      
    public class Test{  
        public static void main(String args[]){  
             int date = 9;  
             Test test = new Test();        
             test.change(date);   
             BirthDate d1= new BirthDate(7,7,1970);         
        }    
      
        public void change1(int i){  
            i = 1234;  
        }  
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
在这里插入图片描述
对于以上这段代码，date为局部变量，i,d,m,y都是形参为局部变量，day，month，year为成员变量。下面分析一下代码执行时候的变化：

main方法开始执行：int date = 9;
date局部变量，基础类型，引用和值都存在栈中。
Test test = new Test();
test为对象引用，存在栈中，对象(new Test())存在堆中。
test.change(date);
调用change(int i)方法，i为局部变量，引用和值存在栈中。当方法change执行完成后，i就会从栈中消失。
BirthDate d1= new BirthDate(7,7,1970);
调用BIrthDate类的构造函数生成对象。
d1为对象引用，存在栈中；

对象(new BirthDate())存在堆中；

其中d,m,y为局部变量存储在栈中，且它们的类型为基础类型，因此它们的数据也存储在栈中；

day,month,year为BirthDate对象的的成员变量，它们存储在堆中存储的new BirthDate()对象里面；

当BirthDate构造方法执行完之后，d,m,y将从栈中消失。

main方法执行完之后。
date变量，test，d1引用将从栈中消失；

new Test(),new BirthDate()将等待垃圾回收器进行回收

#### 4 问题
JVM 相关问题

问：堆和栈有什么区别堆和栈有什么区别有什么
答：堆是存放对象的，但是对象内的临时变量是存在栈内存中，如例子中的 methodVar 是在运
行期存放到栈中的。
栈是跟随线程的，有线程就有栈，堆是跟随 JVM 的，有 JVM 就有堆内存。

问：堆内存中到底存在着什么东西？堆内存中到底存在着什么东西？
答：对象，包括对象变量以及对象方法。

问：类变量和实例变量有什么区别？类变量和实例变量有什么区别？有什么区别
答：静态变量是类变量，非静态变量是实例变量，直白的说，有 static 修饰的变量是静态变量，
没有 static 修饰的变量是实例变量。静态变量存在方法区中，实例变量存在堆内存中。
     启动时就初始化好的，和你这说的不同呀！

问：我听说类变量是在 JVM 启动时就初始化好的，和你这说的不同呀！
答：那你是道听途说，信我的，没错。
     的方法（函数）到底是传值还是传址值还是传址？

问：Java 的方法（函数）到底是传值还是传址？
答：都不是，是以传值的方式传递地址，具体的说原生数据类型传递的值，引用类型传递的地
址。对于原始数据类型，JVM 的处理方法是从 Method Area 或 Heap 中拷贝到 Stack，然后运行 frame
中的方法，运行完毕后再把变量指拷贝回去。
             产生？

问：为什么会产生 OutOfMemory 产生？
答：一句话：Heap 内存中没有足够的可用内存了。这句话要好好理解，不是说 Heap 没有内存
了，是说新申请内存的对象大于 Heap 空闲内存，比如现在 Heap 还空闲 1M，但是新申请的内存需
要 1.1M，于是就会报 OutOfMemory 了，可能以后的对象申请的内存都只要 0.9M，于是就只出现
一次 OutOfMemory，GC 也正常了，看起来像偶发事件，就是这么回事。 但如果此时 GC 没有回
收就会产生挂起情况，系统不响应了。

问：我产生的对象不多呀，为什么还会产生 OutOfMemory？我产生的对象不多呀，？
答：你继承层次忒多了，Heap 中 产生的对象是先产生 父类，然后才产生子类，明白不？
            错误分几种？问：OutOfMemory 错误分几种？
答：分两种，分别是“OutOfMemoryError:java heap size”和”OutOfMemoryError: PermGen
space”，两种都是内存溢出，heap size 是说申请不到新的内存了，这个很常见，检查应用或调整
堆内存大小。
“PermGen space”是因为永久存储区满了，这个也很常见，一般在热发布的环境中出现，是
因为每次发布应用系统都不重启，久而久之永久存储区中的死对象太多导致新对象无法申请内存，
一般重新启动一下即可。

问：为什么会产生 StackOverflowError？？
答：因为一个线程把 Stack 内存全部耗尽了，一般是递归函数造成的。
         之间可以互访吗？

问：一个机器上可以看多个 JVM 吗？JVM 之间可以互访吗？
答：可以多个 JVM，只要机器承受得了。JVM 之间是不可以互访，你不能在 A-JVM 中访问
B-JVM 的 Heap 内存，这是不可能的。在以前老版本的 JVM 中，会出现 A-JVM Crack 后影响到
B-JVM，现在版本非常少见。
      要采用垃圾回收机制，的显式

问：为什么 Java 要采用垃圾回收机制，而不采用 C/C++的显式内存管理？的显 内存管理？
答：为了简单，内存管理不是每个程序员都能折腾好的。

问：为什么你没有详细介绍垃圾回收机制？为什么你没有详细介绍垃圾回收机制
答：垃圾回收机制每个 JVM 都不同，JVM Specification 只是定义了要自动释放内存，也就是
说它只定义了垃圾回收的抽象方法，具体怎么实现各个厂商都不同，算法各异，这东西实在没必要
深入。
    中到底哪些区域是共享的？哪些是私有的？

问：JVM 中到底哪些区域是共享的？哪些是私有的？
答：Heap 和 Method Area 是共享的，其他都是私有的，

问：什么是 JIT，你怎么没说？，你怎么没说？
答：JIT 是指 Just In Time，有的文档把 JIT 作为 JVM 的一个部件来介绍，有的是作为执行引
擎的一部分来介绍，这都能理解。Java 刚诞生的时候是一个解释性语言，别嘘，即使编译成了字
节码（byte code）也是针对 JVM 的，它需要再次翻译成原生代码(native code)才能被机器执行，于
是效率的担忧就提出来了。Sun 为了解决该问题提出了一套新的机制，好，你想编译成原生代码，
没问题，我在 JVM 上提供一个工具，把字节码编译成原生码，下次你来访问的时候直接访问原生
码就成了，于是 JIT 就诞生了，就这么回事。
    还有哪些部分是你没有提到的？

问：JVM 还有哪些部分是你没有提到的？
答：JVM 是一个异常复杂的东西，写一本砖头书都不为过，还有几个要说明的：
常量池（constant pool）按照顺序存放程序中的常量，：并且进行索引编号的区域。比如 int i =100，
这个 100 就放在常量池中。
安全管理器（Security Manager）：提供 Java 运行期的安全控制，防止恶意攻击，比如指定读取
文件，写入文件权限，网络访问，创建进程等等，Class Loader 在 Security Manager 认证通过后才
能加载 class 文件的。
方法索引表（Methods table），记录的是每个 method 的地址信息，Stack 和 Heap 中的地址指针
其实是指向 Methods table 地址。

问：为什么不建议在程序中显式的生命 System.gc()？？
答：因为显式声明是做堆内存全扫描，也就是 Full GC，是需要停止所有的活动的（Stop The
World Collection），你的应用能承受这个吗？

问：JVM 有哪些调整参数？
答：非常多，自己去找，堆内存、栈内存的大小都可以定义，甚至是堆内存的三个部分、新生
代的各个比例都能调整。

主要参考: 
https://blog.csdn.net/vernonzheng/article/details/8458483?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522160584061119195264748124%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=160584061119195264748124&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_v1~rank_blog_v1-2-8458483.pc_v1_rank_blog_v1&utm_term=%E6%B7%B1%E5%85%A5JVM&spm=1018.2118.3001.4450
https://blog.csdn.net/QAQ123666/article/details/105015842/



