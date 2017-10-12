/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SystemFlow;

import com.DBUtil.StandardWordDao;
import com.Model.SegmentWord;

import java.util.ArrayList;

/**
 * 
 * @author Administrator
 */
public class WordStandardization {

	public String getStandardWord(String word) {
		String resultString = "";
		StandardWordDao standardwordDao = new StandardWordDao();
		resultString = standardwordDao.getStandardWord(word);
		return resultString;
	}

	public ArrayList<SegmentWord> sentenceStandard(ArrayList<SegmentWord> list) {
		ArrayList<SegmentWord> resultList = new ArrayList<SegmentWord>();
		ArrayList<String> tmpList = new ArrayList<String>();

		for (SegmentWord word : list) {
			String tempString = getStandardWord(word.getWord());
			if (tempString.equals("") || tempString == null) {
				if (!tmpList.contains(word)) {
					resultList.add(word);
					tmpList.add(word.getWord());
				}

			} else {
				if (!tmpList.contains(word)) {
					word.setWord(tempString);
					resultList.add(word);
					tmpList.add(word.getWord());
				}
			}
		}
		return resultList;
	}

	public ArrayList<SegmentWord> sentenceStandard2(SegmentWord list[][]) {
		ArrayList<SegmentWord> resultList = new ArrayList<SegmentWord>();
		ArrayList<String> tmpList = new ArrayList<String>();
		String []type={"��ɫ����","��ɫ����","��ɫ����","��ɫʫ��"};
        int tag=0;
		for (int i = 0; i < list.length; i++) {
			int length = list[i].length;
			
			for (SegmentWord word : list[i]) {
				String tempString = getStandardWord(word.getWord());
				if (tempString.equals("") || tempString == null) {
//					if (!tmpList.contains(word)) {
//						resultList.add(word);
//						tmpList.add(word.getWord());
//					}
					if(length>1){
						break;	
					}
					else{
						if (!tmpList.contains(word)) {
							resultList.add(word);
							tmpList.add(word.getWord());
						}
						
					}

				} else {
					if (!tmpList.contains(tempString)) {
						word.setWord(tempString);
						resultList.add(word);
						tmpList.add(word.getWord());
						if(tempString!="��ɫʫ��"&&tempString!="��ɫ����"&&tempString!="��ɫ����"&&tempString!="��ɫ����"){
							tag=1;
						}
					}
				}
			}
		}
		// for (int i = 0; i < tmpList.size(); i++) {
		// System.out.println(tmpList.get(i));
		// }

		return resultList;
	}
}
