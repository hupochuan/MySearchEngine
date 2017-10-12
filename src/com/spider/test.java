package com.spider;

import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "八七会议举行的时间" ;
		long startTime=System.currentTimeMillis(); 
		//程序运行时间： 1839ms
		//System.out.println(BaseAnalysis.parse(str));
		//程序运行时间： 1887ms
		//System.out.println(ToAnalysis.parse(str));
		
		System.out.println(DicAnalysis.parse(str));
//		System.out.println(IndexAnalysis.parse(str));
//		System.out.println(NlpAnalysis.parse(str));
		long endTime=System.currentTimeMillis(); //获取结束时间  
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");   

	}

}
