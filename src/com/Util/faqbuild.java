package com.Util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.DBUtil.FAQDao;
import com.DBUtil.RevertIndexDao;
import com.Model.Question;
import com.Model.SegmentWord;
import com.Process.Revertindex;
import com.SystemFlow.StopwordDelete;
import com.nlpir.wordseg.WordSeg;

public class faqbuild {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		updatefaq();
		buildrevertindex();

	}

	public static void getquestion() {
		String content = "1、20世纪初期中、外哪两大政治事件促进了中国共产党的诞生？答：俄国1917年十月社会主义革命的胜利和中国1919年的五四运动，这两大政治事件对中国共产党的诞生起了重要的促进作用。2、中国共产党是什么时候成立的？答：1921年7月1日。3、中国共产党成立的历史意义是什么？答：中国共产党的成立，是近代中国革命历史上划时代的里程碑。有了中国共产党，灾难深重的中国人民有了可以信赖的组织者和领导者，中国革命有了坚强的领导力量。正如毛泽东所讲，自从有了中国共产党，中国革命的面目就焕然一新了。4、党的第二次全国代表大会的中心内容和重要成果是什么？答：大会的中心内容是讨论中国革命的纲领问题，大会的重要成果是制定了党的最高纲领和最低纲领。";

		String regx1 = "、(.+?)\\？答：(.+?)。\\d+、";

		// String regx1= "([\u4e00-\u9fa5]+)";
		Pattern p = Pattern.compile(regx1);

		Matcher macher = p.matcher(content);
		while (macher.find()) {
			System.out.println(macher.group(1).trim() + "?");
			System.out.println(macher.group(2).trim() + "。");
		}

	}
	public static void buildrevertindex(){
		FAQDao dao=new FAQDao();
		RevertIndexDao rdao=new RevertIndexDao();
		rdao.TruncateRevertIndex();
		ArrayList<Question> questions=new ArrayList<>();
		questions=dao.GetAllQuestion();
		for (int i = 0; i < questions.size(); i++) {
			String wordseg=questions.get(i).getQuestion_seg();
			String[] tmp=wordseg.split(" ");
			for (int j = 0; j < tmp.length; j++) {
				String seg=tmp[j].trim();
				if(rdao.ExistIndexWord(seg)){
					String oldindex=rdao.GetIndex(seg);
					rdao.UpdateIndex(seg, questions.get(i).getQuestion_id()+";"+oldindex);
					
				}else{
					rdao.AddIndexWord(seg);
					rdao.UpdateIndex(seg, questions.get(i).getQuestion_id()+"");	
				}
				
			}
			
		}
	}
	public static void updatefaq(){
		FAQDao dao=new FAQDao();
		ArrayList<Question> questions=new ArrayList<>();
		questions=dao.GetAllQuestion();
		for (int i = 0; i < questions.size(); i++) {
			WordSeg abc = new WordSeg();
			ArrayList<SegmentWord> SegResult = abc.wordSeg(questions.get(i).getQuestion());
		
			

			// 去停用词
			ArrayList<SegmentWord> StopResult = new StopwordDelete()
					.ProcessStopword(SegResult);
			
			dao.SetQuestionVSM(questions.get(i).getQuestion(), StopResult);
			
		}
		
		
		
		
	}

}
