package com.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Model.SegmentWord;
import com.SystemFlow.ContextHandling;
import com.SystemFlow.StopwordDelete;
import com.SystemFlow.SynonymExpand;
import com.SystemFlow.WordStandardization;
import com.Util.HttpRequestUtil;
import com.nlpir.wordseg.WordSeg;

public class KnowledgeCard {
	public static void main(String[] args){
//		List<Ariticle> list = LuceneIndex.QueryIndex("毛泽东");
		Scanner input=new Scanner(System.in);
		System.out.println("输入问题");
		String question=input.next();
		question=question.trim();
		getCardresult(question);
	}
	
	
public static ArrayList<SegmentWord> getCardresult(String  question){
		

		
		ArrayList<String> result=new ArrayList<String>();
		
		//分词
		WordSeg abc=new WordSeg();
		ArrayList<SegmentWord> SegResult=abc.wordSeg(question);
		if (null == SegResult || "".equals(SegResult)) {
            System.out.println("出错了");
        }
		String name = null;
		String nameclass=null;
		for (int i = 0; i < SegResult.size(); i++) {
			if(i==0){
				name=SegResult.get(i).getWord();
				nameclass=SegResult.get(i).getWordclass();
			}   
	    	   
		}
		if(nameclass.equals("ns")){
			System.out.println("\n"+name);
			
		}
		else{
			String entity=HttpRequestUtil.sendGet("http://zhishi.me/api/entity/"+name," ");
			JSONObject jsonobject = new JSONObject(entity);
//			System.out.println("\n"+jsonobject);
			Iterator it=jsonobject.keys(); 
			//百度百科
			Object baidu_value=null;
			//互动百科
			Object hudong_value=null;
			while(it.hasNext()){
				String key=(String) it.next();
				//百度百科中的信息
				if(key.equals("baidubaike")){
					baidu_value = jsonobject.get(key); 					
					System.out.println("百度百科：\n"+baidu_value);		
				}
				if(key.equals("hudongbaike")){
					hudong_value = jsonobject.get(key); 					
					System.out.println("活动百科：\n"+baidu_value);		
				}	
			} 
			System.out.println("值是：\n"+baidu_value);
			String baidubaike=baidu_value.toString();
			JSONObject baikeobject = new JSONObject(baidubaike);
			System.out.println("百科：\n"+baikeobject);
			Iterator itbaike=baikeobject.keys();
			String profile=null;
			JSONArray classification=null;
			JSONArray pic=null;
			Object info=null;
			while(itbaike.hasNext()){
				String k=(String) itbaike.next();
				if(k.equals("abstracts")){
					//profile是简介
					profile = baikeobject.getString(k);				
					System.out.println("简介是：\n"+profile);		
				}
				if(k.equals("depiction")){
					//pic是图片
					pic = (JSONArray) baikeobject.get(k);			
					System.out.println("图片是：\n"+pic);		
				}
				
				if(k.equals("infobox")){
					//info是信息框
					info = baikeobject.get(k);			
					System.out.println("信息框：\n"+info);		
				}
							
			} 
			
			
		
		}
		 

		return null;
		
		
	}
	


}
