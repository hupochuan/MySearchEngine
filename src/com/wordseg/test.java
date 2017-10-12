package com.wordseg;

import java.io.UnsupportedEncodingException;  


//import utils.SystemParas;  
import com.sun.jna.Library;  
import com.sun.jna.Native;  
public class test {
	public interface CLibrary extends Library {  
        CLibrary Instance = (CLibrary) Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR",CLibrary.class);  
        public int NLPIR_Init(String sDataPath,int encoding,String sLicenceCode);  
        public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);  
        // ����û��ʻ�  
        public int NLPIR_AddUserWord(String sWord);  
        // ɾ���û��ʻ�  
        public int NLPIR_DelUsrWord(String sWord);  
        // �����û��ʻ㵽�û��ʵ�  
        public int NLPIR_SaveTheUsrDic();   
        // �����û��Զ���ʵ䣺�Զ���ʵ�·����bOverwrite=true��ʾ���ǰ���Զ���ʵ䣬false��ʾ��ӵ���ǰ�Զ���ʵ��    
        public int NLPIR_ImportUserDict(String sFilename, boolean bOverwrite);   
        public String NLPIR_GetLastErrorMsg();  
        public void NLPIR_Exit();  
    }  
      
    public static String transString(String aidString,String ori_encoding,String new_encoding) {  
        try {  
            return new String(aidString.getBytes(ori_encoding),new_encoding);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    public static void main(String[] args) throws Exception {  
        String argu = "";  
        // String system_charset = "UTF-8";  
        int charset_type = 1;                  
        int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");      
        String nativeBytes;  
  
        // ��ʼ��ʧ����ʾ  
        if (0 == init_flag) {  
            nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();   
            System.err.println("��ʼ��ʧ�ܣ�ԭ��"+nativeBytes);  
            return;  
        }  
          
        String sInput = "����һ��������Ϣ�������飬�������Ͼ���ѧ�ġ�";   
        try {  
            nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);  // �ִʺ����Ƿ��ע����  
            System.out.println("ԭʼ�ķִʽ��Ϊ�� " + nativeBytes);  
              
            // ��������û��ʻ㣬�˴�Ϊ������ӷ���  
            CLibrary.Instance.NLPIR_AddUserWord("��Ϣ���� n");  // nΪ����  
            CLibrary.Instance.NLPIR_AddUserWord("�Ͼ���ѧ n");  
            nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);  
            System.out.println("���Ӵʻ����Ϊ�� " + nativeBytes);  
              
            CLibrary.Instance.NLPIR_DelUsrWord("�Ͼ���ѧ");    // ɾ������һ���ʻ�  
            nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);  
            System.out.println("ɾ��ʻ����Ϊ�� " + nativeBytes);  
              
            // CLibrary.Instance.NLPIR_SaveTheUsrDic();  // �����û��Զ���ʻ㣬���鲻��  
              
            int nCount = CLibrary.Instance.NLPIR_ImportUserDict(System.getProperty("user.dir")+"\\source\\userdic.txt",true);   
            System.out.println(String.format("�ѵ���%d���û��ʻ�", nCount)); 
            CLibrary.Instance.NLPIR_SaveTheUsrDic();
            nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sInput, 1);  
            System.out.println("����ʵ����Ϊ�� " + nativeBytes);  
              
            CLibrary.Instance.NLPIR_Exit();     // �˳�  
              
        } catch (Exception ex) {  
            // TODO Auto-generated catch block  
            ex.printStackTrace();  
        }  
    }  

}
