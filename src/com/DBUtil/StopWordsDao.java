/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBUtil;

import com.Model.StopWords;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class StopWordsDao {
    
      /**
     * ��ѯ��������ͣ�ô�
     *
     * @author zhuwangnan
     *
     */
    public ArrayList<StopWords> GetAllStopWords(String synon) {
        ArrayList<StopWords> arrayList = new ArrayList<StopWords>();
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        StopWords qu;
        try {
            ps = con.prepareStatement("select * from stopword where stopword=?");
            ps.setString(1, synon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                qu = new StopWords();
                qu.setStopword_id(rs.getInt("stopword_id"));
                qu.setStopword(rs.getString("stopword"));
                arrayList.add(qu);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return arrayList;
    }
    
        /**
     * ɾ��ͣ�ôʱ���ͣ�ôʣ���ͣ�ôʱ����ά��
     *
     * @author zhuwangnan
     *
     */
    public boolean DeleteStopWord(String keyword) {
        boolean flag = false;
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select * from stopword where stopword=?");
            ps.setString(1, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return flag;
    }
    
     /**
     * �ж�ͣ�ôʱ����Ƿ���ڸ�ͣ�ô�
     *
     */
    public boolean ExistStopWord(String word) {
        boolean flag = false;
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select * from stopword where stopword=?");
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return flag;
    }
    
     /**
     * ������ͣ�ôʵ�ͣ�ôʱ���
     *
     * @author zhuwangnan
     *
     */
    public void InsertStopWord(String word) {

        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into stopword(stopword) values(?)");
            ps.setString(1, word);
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }

    }
    
}
