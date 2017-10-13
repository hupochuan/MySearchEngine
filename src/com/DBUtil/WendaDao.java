/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Model.Wenda;

/**
 *
 * @author Administrator
 */
public class WendaDao {

    
    public ArrayList<String> GetAllSymptom() {
        ArrayList<String> symList = new ArrayList<String>();
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select noun from wenda");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                symList.add(rs.getString("noun").trim());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return symList;
    }
    
     public boolean ExistWenda(String queryword) {
        boolean flag = false;
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select * from wenda where wenda=?");
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
     public String getWord(String queryword) {
    	 
    	 String property = null;
    	 boolean flag = false;
    	 Connection con = null;
         con = DbUtil.getCurrentConnection();
         PreparedStatement ps;
         try{
        	
        	ps = con.prepareStatement("select belongto from wenda where noun=?");
        	ps.setString(1, queryword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	
            	property = rs.getString("belongto").trim();
            }
         }
         catch (SQLException e){     	 
        	 e.printStackTrace();
           } finally {
               DbUtil.closeCurrentConnection();
           }
    	 return property;
     }
    	 
    	 
    	 
    	 
//    	 ArrayList<Wenda> wordList = new ArrayList<Wenda>();
//         boolean flag = false;
//         Connection con = null;
//         con = DbUtil.getCurrentConnection();
//         PreparedStatement ps;
//         try {
//             ps = con.prepareStatement("select * from wenda where noun=?");
//             ps.setString(1, queryword);
//             ResultSet rs = ps.executeQuery();
//             while (rs.next()) {
//            	 Wenda tmp=new Wenda(rs.getString("noun").trim(),rs.getString("belongto").trim(),rs.getInt("type"));
//            	 wordList.add(tmp);
//             }
//         } catch (SQLException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         } finally {
//             DbUtil.closeCurrentConnection();
//         }
//         return wordList;
     
       public void InsertSymptom(String value) {
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            String sql = "insert into symptom(noun) values(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ps.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
    }
}
