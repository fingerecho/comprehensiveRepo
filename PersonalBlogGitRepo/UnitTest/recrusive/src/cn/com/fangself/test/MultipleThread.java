package cn.com.fangself.test;

public class MultipleThread {

	//  模拟三只小狗抢食物
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
					System.out.println("食物没有了，快要死了");
					return ;
				}
				int foodCount = 2;
				synchronizedMethod(foodCount);
			}
		}
		private synchronized void synchronizedMethod(int foodCount) {
			System.out.println(this.dogName + "  eat " + foodCount);
			this.food.lostCountity(foodCount);
			System.out.println("此时食物还有"+this.food.counitity+" ");
		}
	}
	public static void main(String[] args) {
		Food food =  (new MultipleThread()).new Food(1000);
		Thread dog_1 = new Thread((new MultipleThread()).new Dog(food,"小黑狗"));
		Thread dog_2 = new Thread((new MultipleThread()).new Dog(food,"小白狗"));
		dog_1.start();
		dog_2.start();
		
		
	}
}
