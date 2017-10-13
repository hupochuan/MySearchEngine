package com.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.DBUtil.PoemDao;
import com.DBUtilJing.ActivityDao;
import com.SystemFlow.FileOperation;

public class Spiderfordata {

	public static void main(String[] args) throws IOException {

		String urlString = "http://marxists.anu.edu.au/chinese/maozedong/index.htm";
		String URLString = "http://www.shicimingju.com/";
		int i = 1;
		int j = 1;
		
		List<String> list = new Spiderfordata().getAllURL(urlString);
		for (int k = 0; k <list.size(); k++) {
			
			String[] strs = list.get(k).split("::");
			String content = getDitial(URLString + strs[0]);
		
			System.out.println(strs[1]+" "+content+" 毛泽东");
			new PoemDao().InsertPoem(strs[1], "毛泽东", content);
			
		}
		
		i++;
		urlString = URLString + "/chaxun/zuozhe/91_" + i + ".html#chaxun_miao";

	}

	// 爬取链接
	public ArrayList<String> getAllURL(String URL) {
		Document doc = null;
		ArrayList<String> lists = new ArrayList<String>();
		try {

			doc = Jsoup.connect(URL).get();
			Element titlediv=doc.select("div.shicilist").first();
			Elements list=titlediv.select("ul");
			for (int i = 0; i < list.size(); i++) {
				Element as = list.get(i).getElementsByTag("a").first();// 取得了每个<a>
				
				System.out.println(as.attr("href"));
				System.out.println(as.html());
				lists.add(as.attr("href") + "::" + as.html());
				
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
