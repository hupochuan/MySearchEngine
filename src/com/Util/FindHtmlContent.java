package com.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




public class FindHtmlContent {
    private String startTag;
    private String endTag;
    static String url = "http://www.ithome.com/html/it/57338.htm";
    private String pageEncoding;

    public static void main(String[] args) {
    	 FindHtmlContent findHtml = new FindHtmlContent("<p class='title1'>", "<hr color=\"#C0C0C0\"", "GB2312");
		
         String content = findHtml.getContent("http://marxists.anu.edu.au/chinese/maozedong/marxist.org-chinese-mao-19391221.htm");   //关键方法，获取新闻征文
         System.out.println(content);
    
    }
    public FindHtmlContent(String startTag, String endTag, String pageEncoding) {
   
        this.startTag = startTag;
        this.endTag = endTag;
        this.pageEncoding = pageEncoding;
    }

    /**
     * http �����ȡ��ҳ���Դ��
     *
     * @param surl ����url
     * @return ҳ��Դ��
     */
    public String getStaticPage(String surl) {
        String htmlContent = "";
        try {
            java.io.InputStream inputStream;
            java.net.URL url = new java.net.URL(surl);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); //��ƭ������
            connection.connect();
            inputStream = connection.getInputStream();
            byte bytes[] = new byte[1024 * 4000];
            int index = 0;
            int count = inputStream.read(bytes, index, 1024 * 4000);
            System.out.println("count:"+count);
            while (count != -1) {
                index += count;
                count = inputStream.read(bytes, index, 1);
            }
            htmlContent = new String(bytes, pageEncoding);
            //System.out.println("htmlContent:"+htmlContent);
            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println(htmlContent.trim());
        return htmlContent.trim();
    }


    /**
     * ���url ��ȡ ��������
     *
     * @param url ָ�����ĵ�url��ַ
     * @return ����Դ��
     */
    public String getContent(String url) {
        String src = getStaticPage(url);
        int startIndex = src.indexOf(startTag);   //��ʼ��ǩ
        int endIndex = src.indexOf(endTag);        //�����ǩ
 
        if (startIndex != -1 && endIndex != -1) {
            return src.substring(startIndex, endIndex);
        }
        return "";
    }

    public void saveFile(String filePath, String content) {
        File file = new File(filePath);
        FileWriter resultFile = null;
        try {
            resultFile = new FileWriter(file);
            PrintWriter myFile = new PrintWriter(resultFile);
            myFile.println(content);
            myFile.close();
            resultFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStartTag() {
        return startTag;
    }

    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public String getPageEncoding() {
        return pageEncoding;
    }

    public void setPageEncoding(String pageEncoding) {
        this.pageEncoding = pageEncoding;
    }
}
