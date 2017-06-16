package cn.com.fangself.test;


//关于子类和父类的静态代码块，非静态代码块、静态变量的执行顺序问题


/**
 * result：
 * Base static block
child class static block 
Base constructor method execute
Class A constructor executed
Base constructor method execute
Class A constructor executed

*结果是这样的，并没有像想象中的一样，单jvm加载了static 代码块之后，先后初始化父类和子类的静态代码块和静态变量，之后分别在实例化父类和子类的对象，
*但是main方法里再次执行    new As() 之后，只是再次实例化了两个类，并没有再次初始化静态代码块和静态变量，这说明静态变量和静态代码块在jvm中只初始化一次
*
*还有：关于静态代码块和静态变量哪一个先执行的问题，
*书中存在纰漏：
*通过运行结果分析可得到正确的结果，哪一个先声明就先执行哪一个
 * */
class Base{
    static int a=100;
	static {
    	System.out.println("Base static block");
    }
	public Base(){
		System.out.println("Base constructor method execute");
	}
	static int b = 1000;
	
}
class As extends Base{
	static int c = 100;
	static {
		System.out.println("child class static block ");
	}
	static int d = 1000;
	public As(){
		System.out.println("Class A constructor executed");
	}
	static int e = 100;
}

public class TestStaticClass {
	static{
		new As();
	}
	public static void main(String[] args) {
		new As();
	}
}
