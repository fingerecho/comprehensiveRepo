package cn.com.fangself.test;

/**
 * ���쵥������쳲�������ʱ�򣬷����������Ŷ�����������Ǿ��ٴ�ѧϰ�˶��߳�
 * */
public class FeiBoNaQi implements Runnable{

	
	public static int f(int n){
		if(n==0){
			return 0;
		}
		if(n==1){
			return 1;
		}
		return f(n-1)+f(n-2);
	}
	
	public static void main(String args[]){
		//System.out.println(FeiBoNaQi.f(10));
		
		System.out.println("Thread starting ");
		Thread t = new Thread(new FeiBoNaQi());
		t.start();
		for(int i=0;i<3;i++){
			System.out.println("main thread running "+ i);
		}
	
		System.out.println("thread ending");
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println("another thread running "+ i);
		}
	}
}