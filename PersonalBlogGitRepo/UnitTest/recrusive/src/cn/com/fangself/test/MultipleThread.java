package cn.com.fangself.test;

public class MultipleThread {

	//  ģ����ֻС����ʳ��
	class  Food{
		 int counitity;
		public Food(int countity ){
			this.counitity = countity;
		}
		public int getCounitity(){
			return this.counitity;
		}
		public void lostCountity(int count){
			this.counitity -=count;
		}
	}
	public class Dog implements Runnable{
		String dogName = null;
		Food food = null;
		public Dog(Food food, String name){
			this.dogName = name;
			this.food = food;
			Thread.currentThread().setName(name);
		}
		public  void run(){
			while(true){
				if(this.food.counitity<0){
					System.out.println("ʳ��û���ˣ���Ҫ����");
					return ;
				}
				int foodCount = 2;
				synchronizedMethod(foodCount);
			}
		}
		private synchronized void synchronizedMethod(int foodCount) {
			System.out.println(this.dogName + "  eat " + foodCount);
			this.food.lostCountity(foodCount);
			System.out.println("��ʱʳ�ﻹ��"+this.food.counitity+" ");
		}
	}
	public static void main(String[] args) {
		Food food =  (new MultipleThread()).new Food(1000);
		Thread dog_1 = new Thread((new MultipleThread()).new Dog(food,"С�ڹ�"));
		Thread dog_2 = new Thread((new MultipleThread()).new Dog(food,"С�׹�"));
		dog_1.start();
		dog_2.start();
		
		
	}
}