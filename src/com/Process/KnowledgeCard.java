package com.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DBUtil.WendaDao;
import com.Model.SegmentWord;
import com.SystemFlow.ContextHandling;
import com.SystemFlow.StopwordDelete;
import com.SystemFlow.SynonymExpand;
import com.SystemFlow.WordStandardization;
import com.Util.HttpRequestUtil;
import com.nlpir.wordseg.WordSeg;

public class KnowledgeCard {
	
    public KnowledgeCard(String entity, String entityinfo,
			ArrayList<String> property) {
		super();
		this.entity = entity;
		this.entityinfo = entityinfo;
		this.property = property;
	}


	String entity;
	String entityinfo;
	ArrayList<String> property;
	
	
	//处理接口信息获取答案
	public  String getAnswer(){
		
		String result=null;
		
		Boolean tag=false;

	     for (int i = 0; i < property.size(); i++) {
	    	 
	    	
	    	 String regx1= "\""+property.get(i)+"\": \\[?\"?(.+?)\"\\]?"; 		 

		     Pattern p= Pattern.compile(regx1);  
		     
		     Matcher  macher =p.matcher(this.entityinfo);  
		     while(macher.find()){  
		         result=macher.group(1).trim();  
		         System.out.println(macher.group(1).trim());  
		         tag=true;
		         break;

		     }	
		     if(tag)
		    	 break;
		 }
	     return result;
	   
	}


	public  JSONObject getCardresult() {

		String profile = null;
		JSONArray classification = null;
		JSONArray pic = null;
		Object info = null;
		Object recommend = null;
		JSONObject Card_Object = new JSONObject();
		
	
		JSONObject jsonobject = new JSONObject(entityinfo);
		// System.out.println("\n"+jsonobject);
		Iterator it = jsonobject.keys();
		// 百度百科
		Object baidu_value = null;
		// 互动百科
		Object hudong_value = null;
		while (it.hasNext()) {
			String key = (String) it.next();
			// 百度百科中的信息
			if (key.equals("baidubaike")) {
				baidu_value = jsonobject.get(key);
				// System.out.println("百度百科：\n"+baidu_value);
			}
			if (key.equals("hudongbaike")) {
				hudong_value = jsonobject.get(key);
				// System.out.println("活动百科：\n"+hudong_value);
			}
		}
		System.out.println("值是：\n" + baidu_value);
		String baidubaike = baidu_value.toString();
		String hudong = hudong_value.toString();
		JSONObject baikeobject = new JSONObject(baidubaike);
		JSONObject hudongbject = new JSONObject(hudong);

		Iterator itbaike = baikeobject.keys();

		while (itbaike.hasNext()) {
			String k = (String) itbaike.next();
			if (k.equals("abstracts")) {
				// profile是简介
				profile = baikeobject.getString(k);
				// System.out.println("简介是：\n"+profile);
			}
			// if(k.equals("depiction")){
			// //pic是图片
			// pic = (JSONArray) baikeobject.get(k);
			// // System.out.println("图片是：\n"+pic);
			// }

			if (k.equals("infobox")) {
				// info是信息框
				info = baikeobject.get(k);
				// System.out.println("信息框：\n"+info);
			}
			if (k.equals("internalLink")) {
				// info是信息框
				recommend = baikeobject.get(k);
				// System.out.println("推荐是：\n"+recommend);
			}
		}
		if (profile == null) {
			Iterator hudong_it = hudongbject.keys();
			while (hudong_it.hasNext()) {
				String k_hudong = (String) hudong_it.next();
				if (k_hudong.equals("abstracts")) {
					// profile是简介
					profile = hudongbject.getString(k_hudong);
					// System.out.println("简介是：\n"+profile);
				}

			}

		}
		if (pic == null) {
			Iterator hudong_it = hudongbject.keys();
			while (hudong_it.hasNext()) {
				String k_hudong = (String) hudong_it.next();
				if (k_hudong.equals("depiction")) {
					// pic是图片
					pic = (JSONArray) hudongbject.get(k_hudong);
					// System.out.println("图片是：\n"+pic);
				}
			}
		}

		if (info == null) {
			Iterator hudong_it = hudongbject.keys();
			while (hudong_it.hasNext()) {
				String k_hudong = (String) hudong_it.next();
				if (k_hudong.equals("infobox")) {
					// info是信息框
					info = hudongbject.get(k_hudong);
					// System.out.println("信息框：\n"+info);
				}
			}
		}
		if (recommend == null) {
			Iterator hudong_it = hudongbject.keys();
			while (hudong_it.hasNext()) {
				String k_hudong = (String) hudong_it.next();
				if (k_hudong.equals("internalLink")) {
					// info是信息框
					recommend = hudongbject.get(k_hudong);
					// System.out.println("推荐是：\n"+recommend);
				}
			}
		}
		Card_Object.put("title", entity);

		Card_Object.put("abstracts", profile);

		Card_Object.put("depiction", pic);

		Card_Object.put("infobox", info);

		Card_Object.put("internalLink", recommend);

		return Card_Object;

	}

}
