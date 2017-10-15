package com.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.DBUtil.FAQDao;
import com.DBUtil.TempFAQDao;
import com.Model.Ariticle;
import com.Model.Result;
import com.Model.SegmentWord;
import com.SystemFlow.AnswerExtraction;
import com.SystemFlow.ContextHandling;
import com.SystemFlow.MatchingQuestions;
import com.SystemFlow.StopwordDelete;
import com.SystemFlow.SynonymExpand;
import com.SystemFlow.WordStandardization;
import com.nlpir.wordseg.WordSeg;

public class GetAnswerWs {
	
	public static void main(String[] args){
//		List<Ariticle> list = LuceneIndex.QueryIndex("毛泽东");
		Scanner input=new Scanner(System.in);
		System.out.println("输入问题");
		String question=input.next();
		question=question.trim();
		getOWLresult(question);
		
	}
	public void savefaq(String question) {
        FAQDao faqDao = new FAQDao();
        if (!faqDao.ExistQuestion(question)) {
            TempFAQDao tempFAQ = new TempFAQDao();
            tempFAQ.InsertTempQuestion(question);
        }
    }

	
	public static ArrayList<String> getOWLresult(String  question){
		

		
		ArrayList<String> result=new ArrayList<String>();
		
		//分词
		WordSeg abc=new WordSeg();
		ArrayList<SegmentWord> SegResult=abc.wordSeg(question);
		System.out.print("\n分词结果：");
		if (null == SegResult || "".equals(SegResult)) {
            System.out.println("出错了");
        }
		 for (int i = 0; i < SegResult.size(); i++) {
	    	   System.out.print(SegResult.get(i).getWord()+"  ");
		}
		
		//去停用词
		 ArrayList<SegmentWord> StopResult = new StopwordDelete().ProcessStopword(SegResult);
		 System.out.print("\n去停用词后结果：");
		 for (int i = 0; i < StopResult.size(); i++) {
	    	   System.out.print(StopResult.get(i).getWord()+"  ");
		}
		 
		 //问句标准化 把一些词语进行替换 同义词替换
		 ArrayList<SegmentWord> standardResult = new WordStandardization().sentenceStandard(StopResult);
	     System.out.print("\n用户问句标准化后结果：");
	     for (int i = 0; i < standardResult.size(); i++) {
	    	   System.out.print(standardResult.get(i).getWord()+"  ");
		}
	     
	     //同义词扩展 
	     ArrayList<SegmentWord> ExpandResult = standardResult;
	     ExpandResult = new SynonymExpand().SynonymsExpand2(standardResult);
	     System.out.print("\n问句同义词扩展后：");
	     for (int i = 0; i < ExpandResult.size(); i++) {
	    	   System.out.print(ExpandResult.get(i).getWord()+"  ");
		}
	     
	    ArrayList<String> ProcessResult=new ArrayList<String>();
	    for (int i = 0; i < ExpandResult.size(); i++) {
	    	ProcessResult.add(ExpandResult.get(i).getWord());
		}
	     
	    System.out.println("问句同义词扩展后：" + ProcessResult);
	    ArrayList<Result> resList = new MatchingQuestions().GetMatchingQuestions(ProcessResult,30);
 
	    result = new AnswerExtraction().GetTopKAnswer(resList, 6, 0.4);
	    
	    for (int i = 0; i < result.size(); i++) {
	    	System.out.println(result.get(i));
			
		}
	    
	    return result;
		 
		
		
	}
	

}
