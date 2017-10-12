package com.wordseg;

import java.util.ArrayList;
import java.util.Scanner;

import com.Model.SegmentWord;
import com.sun.jna.Native;

public class WordSeg {
	public static ArrayList<SegmentWord> wordSeg(String question){
		
		 ArrayList<SegmentWord> result=new ArrayList<>();
//		 CLibrary instance = (CLibrary)Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR", CLibrary.class);

		 CLibrary instance = (CLibrary)Native.loadLibrary("D:\\workspace\\MySearchEngine\\source\\NLPIR", CLibrary.class);
		 int init_flag = instance.NLPIR_Init("", 1, "0");
         String resultString = null;
         if (0 == init_flag) {
             resultString = instance.NLPIR_GetLastErrorMsg();
             System.err.println("NLPIR初始化失败！\n"+resultString);
             return result;
         }
//         
//         int num=instance.NLPIR_ImportUserDict(System.getProperty("user.dir")+"\\source\\userdic.txt");
         
         int num=instance.NLPIR_ImportUserDict("D:\\workspace\\MySearchEngine\\source\\userdic.txt");
         
//         instance.NLPIR_AddUserWord("南京大学 nrete");
         resultString = instance.NLPIR_ParagraphProcess(question, 1);
//         System.out.println(resultString);
         String[] tmp1=resultString.split(" ");
       
         for (int i = 0; i < tmp1.length; i++) {
        	SegmentWord tmp=new SegmentWord(tmp1[i]);
			result.add(tmp);
			
		}
        
         
         
         for (int i = 0; i < result.size(); i++) {
			System.out.print(result.get(i).getWord()+":"+result.get(i).getWordclass()+" ");
         
		}
         
         instance.NLPIR_Exit();
         
         return result;    
	}
	 public static void main(String args[]) {

		Scanner input=new Scanner(System.in);
		System.out.println("输入问题");
		String question=input.next();
		question=question.trim();
		long startTime=System.currentTimeMillis(); 
		new WordSeg().wordSeg(question);
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");   
	}
	

}