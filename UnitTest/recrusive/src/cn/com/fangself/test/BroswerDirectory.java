package cn.com.fangself.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public  class BroswerDirectory {

	static ArrayList<File> fileResultArray = new ArrayList<File>();
	static Integer howManyJavaFilesInThisComputer = new Integer(0);
	static Integer howManyClassInAllJavaFile = new Integer(0);
	
class WatchResource implements Runnable{
		
		
		
		public void run(){
			viewResource();
		}
		private synchronized void  viewResource(){

			while(true){
				System.out.println("------------------------------------------------------------------------------------");
				System.out.printf("There are %d java files in this computer \n",howManyJavaFilesInThisComputer);
				System.out.printf("\nThere are %d class in all java files \n",howManyClassInAllJavaFile);
				System.out.println("------------------------------------------------------------------------------------");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}
				/*for(int i=0;;i++){
					fileSize = fileResultArray.get(i).getUsableSpace();
					
					System.out.println(fileResultArray.get(i));
					if(i==fileResultArray.size()-1){
						i=0;
						Runtime ps;
						ps = Runtime.getRuntime();
						try {
							ps.exec("cls");
						} catch (IOException e) {
							System.out.println("IOException ");
						}
						System.out.printf("----------file space : %ld  -----------",fileSize);
						System.out.println("-------------------------------next print ---------------------------------------");
					}
					
				}*/
		
		}
	}
	
	
	class ScannerAJavaFile implements Runnable{
		
		File file = null;
		private boolean flag = false;
		public ScannerAJavaFile(File file){
			this.file = file;
			this.flag = true;
		}
		public ScannerAJavaFile(){
			this.file = new File("/");
		}
		public void setFile(File file){
			this.file = file;
		}
		public void run(){
			if(flag){
				scanner(this.file);
			}
		}
		private synchronized void scanner(File file){
			try {
				BufferedReader bufferedFileReader = new BufferedReader(new FileReader(this.file));
				String line = bufferedFileReader.readLine();
				Pattern classPattern = Pattern.compile("*class*");
				Matcher match = classPattern.matcher(line);
				System.out.println("length is : " + match.group());
				System.out.println("-----new line is ------"+line);
				
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}
	
	
	class BrowserThread implements Runnable{
		 ScannerAJavaFile attach = null;
		 public BrowserThread(ScannerAJavaFile attach){
	 	    	this.attach =  attach;
    	 }
		
		 public void run(){
			 browser(new File("/"));
		 }
		 synchronized void browser(File fileDir){
				
				File[] files = fileDir.listFiles();
				if(files==null){
					return ;
				}
				for(int i=0;files!=null&&i<files.length;i++){
					if(files[i].isDirectory()){
						
						browser(files[i]);
						continue;
					}
					String filename = new String(files[i].getPath());
					if(filename.endsWith("java")){
						howManyJavaFilesInThisComputer++;
						this.attach.setFile(files[i]);
						Thread thread  = new Thread(this.attach);
						thread.start();
						thread.setName("scaner java file Thread");
						try {
							thread.join();
						} catch (InterruptedException e) {
							System.out.println("this Scanner file thread end");
							fileResultArray.add(files[i]);
						}
						
					}
				}
			}
	}
	
	
	public static void main(String[] args) {
		File fileDir = new File("/");
		ScannerAJavaFile scannerAJavaFile = (new BroswerDirectory()).new ScannerAJavaFile();
		WatchResource watchResource = (new BroswerDirectory()).new WatchResource();
		BrowserThread browserThread = (new BroswerDirectory()).new BrowserThread(scannerAJavaFile);
		Thread t = new Thread(watchResource);
		t.setName("watchResource");
		Thread browsetT = new Thread(browserThread);
		browsetT.setName("browserThread");
		//Thread scannerT = new Thread(scannerAJavaFile);
		//scannerT.setName("scannerAJavaFile");
		browsetT.setPriority(3);
		//scannerT.setPriority(3);
		browsetT.start();
		t.start();
		//scannerT.start();
		 
	}
}
