package cn.com.fangself.test;
/**
 * 
 * 问题:
 * 模拟三个人买票，张某，李某和赵某买电影票，售票员只有3张5元的人民币
 * 电影票5元一张，
 * 张某拿20元一张的人民币排在李某的前面买票，李某排在赵某的前面拿一张10元的人民币买票，
 * 赵某拿一张5元的人民币买票
 * */
class Pperson extends Thread{
	String name ="";
	public Pperson(String name){
		this.name = name;
	}
	public String getNames(){
		return this.name;
	}
}
class Ticket extends Thread {
	
	int fiveAmount = 3;
	int tenAmount = 0;
	int twentyAmount = 0;
	Pperson t[] = null;
	public Ticket(Pperson[] t){
		this.t = t;
	}
	public void run(){
		for(int i=0;i<3;i++){
			if(this.t[i].getNames().equals("张某")){
				sale(20,this.t[i]);
			}else if(this.t[i].getNames().equals("李某")){
				sale(10,this.t[i]);
			}else if(this.t[i].getNames().equals("赵某")){
				sale(5,this.t[i]);
			}
		}
	}
	public synchronized void  sale(int money,Pperson temp){
		if(money == 5){
			System.out.println(temp.getName()+"买票成功");
		}
		else if(money==10){
			if(fiveAmount>1){
				fiveAmount--;
				tenAmount ++;
				System.out.println(temp.getName()+"买票成功了");
			}else{
				System.out.println("钱不够，先等等");
				try {
					temp.wait();
				} catch (InterruptedException e) {
					System.out.println("好的");
					System.out.println(temp.getName()+"买票成功了");
				}
			}
		}else if(money==20){
			if(fiveAmount>3){
				fiveAmount-=3;
				twentyAmount++;
				System.out.println(temp.getName()+"买票成功了");
			}else{
				try{
					temp.wait();
				}catch(InterruptedException e){
					System.out.println(temp.getName()+"买票成功了");
				}
			}
		}
		
		this.notifyAll();
	}
}
public class TicketItem {
	public static void main(String []args){
	
		Pperson zhangmou = new Pperson("zhangmou");
		Pperson limou = new Pperson("limou");
		Pperson zhaomou = new Pperson("zhaomou");
		Pperson array [] = {zhangmou,limou, zhaomou};
		Ticket ticket =new Ticket(array);
		ticket.start();
	}
}
