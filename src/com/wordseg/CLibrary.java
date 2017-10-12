package com.wordseg;

import com.sun.jna.Library;


public interface CLibrary extends Library{
	 public int NLPIR_Init(String sDataPath, int encoding, String sLicenceCode);
	    //���ַ���зִ�
	    public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
	    //��TXT�ļ����ݽ��зִ�
	    public double NLPIR_FileProcess(String sSourceFilename,String sResultFilename, int bPOStagged);
	    //���ַ�����ȡ�ؼ�� 
	    public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);
	    //��TXT�ļ�����ȡ�ؼ�� 
	    public String NLPIR_GetFileKeyWords(String sLine, int nMaxKeyLimit,boolean bWeightOut);
	    //��ӵ����û��ʵ�
	    public int NLPIR_AddUserWord(String sWord);
	    //ɾ�����û��ʵ�
	    public int NLPIR_DelUsrWord(String sWord);
	    //��TXT�ļ��е����û��ʵ�
	    public int NLPIR_ImportUserDict(String sFilename);
	    //���û��ʵ䱣����Ӳ��
	    public int NLPIR_SaveTheUsrDic();
	    //���ַ��л�ȡ�´�
	    public String NLPIR_GetNewWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);
	    //��TXT�ļ��л�ȡ�´�
	    public String NLPIR_GetFileNewWords(String sTextFile,int nMaxKeyLimit, boolean bWeightOut);
	    //��ȡһ���ַ��ָ��ֵ
	    public long NLPIR_FingerPrint(String sLine);
	    //����Ҫʹ�õ�POS map
	    public int NLPIR_SetPOSmap(int nPOSmap);
	    //��ȡ������־
	    public String NLPIR_GetLastErrorMsg();
	    //�˳�
	    public void NLPIR_Exit();    

}
