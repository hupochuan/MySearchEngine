package com.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Model.Word;

/**
 *
 * @author Administrator
 */
public class PoemDao {

    /**
     * ��ѯ�õ����е������
     */
    public ArrayList<String> GetAllSymptom() {
        ArrayList<String> symList = new ArrayList<String>();
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select word from word");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                symList.add(rs.getString("word").trim());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return symList;
    }
    
     public boolean ExistWord(String queryword) {
        boolean flag = false;
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select * from word where word=?");
            ps.setString(1, queryword);
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
     public ArrayList<Word> getWord(String queryword) {
    	 ArrayList<Word> wordList = new ArrayList<Word>();
         boolean flag = false;
         Connection con = null;
         con = DbUtil.getCurrentConnection();
         PreparedStatement ps;
         try {
             ps = con.prepareStatement("select * from word where word=?");
             ps.setString(1, queryword);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
            	 Word tmp=new Word(rs.getString("word").trim(),rs.getString("belongto").trim(),rs.getInt("type"));
            	 wordList.add(tmp);
             }
         } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } finally {
             DbUtil.closeCurrentConnection();
         }
         return wordList;
     }
     
       public void InsertPoem(String value1,String value2,String value3) {
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            String sql = "insert into poem_ariticle(title,author,content,type) values(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, value1);
            ps.setString(2, value2);
            ps.setString(3, value3);
            ps.setInt(4, 1);
            ps.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
    }
}

