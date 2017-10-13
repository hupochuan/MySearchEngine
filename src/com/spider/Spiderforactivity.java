package com.spider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.DBUtilJing.ActivityDao;
import com.SystemFlow.FileOperation;

public class Spiderforactivity {

	public static void main(String[] args) {
		String urlString = "http://www.crt.com.cn/news2007/news/hswenwu/hswenwu.html";
//		String urlString = "http://www.crt.com.cn/news2007/News/hsgw.html";
//		String urlString = "http://www.crt.com.cn/news2007/News/hsrl.html";
		String URLString = "http://www.crt.com.cn";
		int i = 1;
		int j = 1;

		do {

			List<String> list = new Spiderforactivity().getAllURL(urlString);

			for (int k = 0; k <=40; k++) {
				
				String[] strs = list.get(k).split("::");
				ArrayList<String> arrayList = getDitial(URLString + strs[0]);
				String title="红色文物："+strs[1];
				String content="";
				for (int i1 = 0; i1 < arrayList.size(); i1++) {
				
					content += arrayList.get(i1) + "\r\n";
				
				}
				
				ActivityDao activitydao=new ActivityDao();
				activitydao.InsertActivity(title,content);
				
			}

			i++;
			urlString = URLString + "/news2007/News/hsgw_" + i + ".html";

		} while (i <= 1);

	}

	// 爬取链接
	public ArrayList<String> getAllURL(String URL) {
		Document doc = null;
		ArrayList<String> lists = new ArrayList<String>();
		try {

			doc = Jsoup.connect(URL).get();

			Elements list = doc.getElementsByAttributeValueContaining("width",
					"85%");
			// System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				Elements urltmp = list.get(i).getElementsByTag("a");// 取得了每个<a>

				for (int j = 0; j < urltmp.size(); j++) {
					Element eleStrong = urltmp.get(j);
					Elements as = eleStrong.getElementsByTag("a");
					for (int k = 0; k < as.size(); k++) {
						System.out.println(as.attr("href"));
						System.out.println(as.html());
						// lists.add(as.html() + "::" + as.attr("href"));
						lists.add(as.attr("href") + "::" + as.html());
					}

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lists;
	}

	/*
	 * 根据获取到的URL继续获取其详细内容 
	 */
	public static ArrayList<String> getDitial(String url) {
		Document doc = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			doc = Jsoup.connect(url).get();
			Elements sublist = doc.getElementsByAttributeValueContaining("id",
					"ContentList");
//			String source = doc.select("td[width=34%]").first()
//					.getElementsByTag("div").first().html();
//			if (source != null) {
//				source = source.split("：")[1];
//			}
//			list.add(source);
			for (int i = 0; i < sublist.size(); i++) {
				Elements P = sublist.get(i).getElementsByTag("P"); // 获取div
				for (int j = 0; j < P.size(); j++) {
					list.add(P.get(j).html());
				}

				if (P.size() == 0) {
					list.add(sublist.get(i).html());
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
