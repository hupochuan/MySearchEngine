package com.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.DBUtil.PoemDao;

public class spiderfortmp {
	public static void main(String[] args) throws IOException {

		String urlString = "http://www.microbell.com/microns_1_0.html";
		String URLString = "http://www.microbell.com/";
		int i = 1;
		int j = 1;
		do {
			List<String> list = new spiderfortmp().getAllURL(urlString);
			for (int k = 0; k <list.size(); k++) {
				
				String[] strs = list.get(k).split("::");
			
				
			}
			
			i++;
			urlString = URLString + "microns_1_"+i+".html";
			
		} while (true);
		

	}

	// 爬取链接
	public ArrayList<String> getAllURL(String URL) {
		Document doc = null;
		ArrayList<String> lists = new ArrayList<String>();
		try {

			doc = Jsoup.connect(URL).get();
			Element table=doc.getElementById("tableList");
			Elements titles=table.select("span.tab_lta");
			Elements info=table.select("td.td_spantxt");
//			for (int i = 0; i < list.size(); i++) {
//				Element as = list.get(i).getElementsByTag("a").first();// 取得了每个<a>
//				
//				System.out.println(as.attr("href"));
//				System.out.println(as.html());
//				lists.add(as.attr("href") + "::" + as.html());
//				
//			}
			for (int i = 0; i < titles.size(); i++) {
				System.out.println(titles.get(i).getElementsByTag("a").first().html());
				//System.out.println(titles.html());
				
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
	public static String getDitial(String url) {
		Document doc = null;
		String list =null;
		try {
			doc = Jsoup.connect(url).get();
			Element sublist = doc.getElementsByAttributeValueContaining("id",
					"shicineirong").first();
			
			list=sublist.html();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
