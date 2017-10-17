package com.searchengine.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DBUtil.FAQDao;
import com.DBUtil.WendaDao;
import com.Jing.model.Activity;
import com.KnowledgeGraph.GetKnowledge;
import com.Model.Ariticle;
import com.Model.Question;
import com.Model.Result;
import com.Model.SegmentWord;
import com.Process.GetAnswerWs;
import com.Process.KnowledgeCard;
import com.Process.LuceneIndex;
import com.Process.OwlProcess;
import com.Process.Similarity;
import com.SystemFlow.AnswerExtraction;
import com.SystemFlow.MatchingQuestions;
import com.SystemFlow.StopwordDelete;
import com.SystemFlow.SynonymExpand;
import com.SystemFlow.WordStandardization;
import com.Util.HttpRequestUtil;
import com.Util.LuceneActivityIndex;
import com.nlpir.wordseg.WordSeg;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WebServiceForPHP {
	public static void main(String[] args) throws Exception {
//		WendaDao dao=new WendaDao();
//		if(dao.getWord("事件内容")!=null){
//			System.out.println("success");
//		};
		//System.out.println(new WebServiceForPHP().getAnswerService("毛泽东生日",1,5));
//		 //改几行代码试试看
		Endpoint.publish("http://127.0.0.1:1233/searchengine/getAnswer",
				new WebServiceForPHP());

	}

	public String getAnswerService(String question, int pagenumber, int pagesize) {

		ArrayList<String> result = new ArrayList<String>();
		

		// 分词
		WordSeg abc = new WordSeg();
		ArrayList<SegmentWord> SegResult = abc.wordSeg(question);
		System.out.print("\n分词结果：");
		if (null == SegResult || "".equals(SegResult)) {
			System.out.println("出错了");
		}
		for (int i = 0; i < SegResult.size(); i++) {
			System.out.print(SegResult.get(i).getWord() + "  ");
		}

		// 去停用词
		ArrayList<SegmentWord> StopResult = new StopwordDelete()
				.ProcessStopword(SegResult);
		System.out.print("\n去停用词后结果：");
		for (int i = 0; i < StopResult.size(); i++) {
			System.out.print(StopResult.get(i).getWord() + "  ");
		}

		// 问句标准化 把一些词语进行替换 同义词替换
		ArrayList<SegmentWord> standardResult = new WordStandardization()
				.sentenceStandard(StopResult);
		System.out.print("\n用户问句标准化后结果：");
		for (int i = 0; i < standardResult.size(); i++) {
			System.out.print(standardResult.get(i).getWord() + "  ");
		}

		// 同义词扩展
		ArrayList<SegmentWord> ExpandResult = standardResult;
		ExpandResult = new SynonymExpand().SynonymsExpand2(standardResult);
		System.out.print("\n问句同义词扩展后：");
		for (int i = 0; i < ExpandResult.size(); i++) {
			System.out.print(ExpandResult.get(i).getWord() + "  ");
		}

		ArrayList<String> ProcessResult = new ArrayList<String>();
		for (int i = 0; i < ExpandResult.size(); i++) {
			ProcessResult.add(ExpandResult.get(i).getWord());
		}


		// 获取词组中的实体和属性
		ArrayList<String> entity = new ArrayList<>();
		ArrayList<String> property = new ArrayList<>();
		ArrayList<String> poetry = new ArrayList<>();
		
		int tag = 0;
		WendaDao dao = new WendaDao();
		for (int i = 0; i < ExpandResult.size(); i++) {
			String type = ExpandResult.get(i).getWordclass();
			String word = ExpandResult.get(i).getWord();

			if (type.equals("nr") || type.equals("ns") || type.equals("nz")
					|| type.equals("nd")||type.equals("nt")) {

				entity.add(word);
				if(type.equals("nd")){
					poetry.add(word);

				}

			}
//			else{
				
				
				if (dao.getWord(word) != null) {
				
					String[] tmp=dao.getWord(word).split(" ");
					for (int j = 0; j < tmp.length; j++) {
						if(!property.contains(tmp[j].trim())){
							property.add(tmp[j].trim());
						}
					
					}
					

				}
//			}
		}
		System.out.println("实体"+entity+" 属性"+property);
		String entityinfo=null;
		KnowledgeCard knowledge = null;
		for (int i = 0; i < entity.size(); i++) {
			entityinfo = HttpRequestUtil.sendGet(
					"http://zhishi.me/api/entity/" + entity.get(i), "");
			if(entityinfo ==null || entityinfo.isEmpty()){
				
			}
			else{
				knowledge = new KnowledgeCard(entity.get(i), entityinfo,
						property);
				break;
			}
		}

		// 1.知识卡片和知识推荐的结果;2.答案抽取的结果;
		JSONObject Card_Object = null;
		String answer = null;
		if (knowledge != null) {
			Card_Object = knowledge.getCardresult();
			answer = knowledge.getAnswer();
		}
        String newques=question;
		if (answer == null) {
			ArrayList<Result> resList = new MatchingQuestions()
					.GetMatchingQuestions(ProcessResult, 30);

			if (null == resList || resList.isEmpty()) {
				
			}else{
				result = new AnswerExtraction().GetTopKAnswer(resList, 6, 0.4);
				if(result.size()>=1){
					FAQDao faqdao=new FAQDao();
					Question ques=faqdao.getQuestionByContent(result.get(0));
					if(ques!=null){
						answer=ques.getAnswer();
						newques=ques.getQuestion();
						//question=ques.getQuestion();
					}
					
					
				}
			}

		}

		// Lucene查询
		
		List<Ariticle> list = LuceneIndex.QueryIndex(question);
		// 拼凑JSON对象
		JSONArray jArray = new JSONArray();
		int start = (pagenumber - 1) * pagesize;

		int end = list.size() > start + pagesize - 1 ? start + pagesize - 1
				: list.size() - 1;

		for (int i = start; i <= end; i++) {
			Map<String, String> ariticle = new HashMap<String, String>();
			ariticle.put("title", list.get(i).getTitle());
			ariticle.put("source", list.get(i).getSource());
			ariticle.put("url", list.get(i).getUrl());
			ariticle.put("content", list.get(i).getContent());
			ariticle.put("score", Float.toString(list.get(i).getScore()));
			jArray.put(ariticle);
		}

		JSONObject jObject = new JSONObject();
		jObject.put("poetry", poetry);
		
		jObject.put("Ariticles", jArray);

		jObject.put("OWLresult", answer);

		jObject.put("question", question);
		jObject.put("newquestion", newques);

		jObject.put("pagenumber", pagenumber);

		jObject.put("total", list.size());
		
		jObject.put("Card_Object", Card_Object);

		return jObject.toString();

	}

	public String getRelatedKnowledge(String title) {
		title = title.replace(" ", "");
		String result = new GetKnowledge().getKeyWord(title);
		return result;
		// return result;
	}

	public String getKnowledgeDetail(String title) throws Exception {

		System.out.println("要请求的标题" + title);
		title = title.trim();
		String entitylist = HttpRequestUtil.sendGet(
				"http://knowledgeworks.cn:30001/", "p=" + title);

		System.out.println("接口请求::" + "http://knowledgeworks.cn:30001/?" + "p="
				+ title);
		System.out.println("返回值::" + entitylist);
		// 去掉两边多余字符
		String[] entitys = entitylist.replace("[\"", "").replace("\"]", "")
				.split("\", \"");
		JSONArray jArray = new JSONArray();

		for (int i = 0; i < entitys.length; i++) {
			int tag = 0;
			String detail = HttpRequestUtil.sendGet(
					"http://knowledgeworks.cn:20313/cndbpedia/api/entityAVP",
					"entity=" + entitys[i]);
			String tmp1 = detail.replace("{\"av pair\": [[\"", "");
			String tmp2 = tmp1.replace("\"]]}", "");
			String[] tmp3 = tmp2.split("\"\\], \\[\"");
			Map<String, String> knowledge = new HashMap<String, String>();
			knowledge.put("标题", entitys[i]);
			for (int j = 0; j < tmp3.length; j++) {
				String[] temp = tmp3[j].split("\", \"");
				if (temp.length != 2) {
					break;
				} else {

					if (temp[0].equals("书名") || temp[0].equals("定价"))
						tag = 1;
					knowledge.put(temp[0], temp[1]);
				}

			}
			if (tag == 0)
				jArray.put(knowledge);

		}
		title = OwlProcess.GetRelatedWord(title);
		ArrayList<Activity> activities = LuceneActivityIndex.QueryIndex(title);

		JSONArray search = new JSONArray();

		for (int i = 0; i < activities.size(); i++) {
			search.put(activities.get(i).getId());

		}
		JSONObject jObject = new JSONObject();
		jObject.put("Knowledgedetail", jArray);

		jObject.put("searchresult", search);

		System.out.println(jObject.toString());

		return jObject.toString();
	}
}
