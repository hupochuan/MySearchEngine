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

/**
 *
 * @author Administrator
 */
public class TempFAQDao {

    /**
     * ɾ��tempfaq���е��ʾ�
     */
    public void DeleteTempQuestion(String queString) {
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("delete  from tempfaq where temp_question=?");
            ps.setString(1, queString);
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
    }

    /**
     * �ж���ʱFAQ�����Ƿ���ڸ��ʾ�
     *
     * @author liling
     */
    public boolean ExistTempQuestion(String question) {
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        boolean flag = false;
        try {
            ps = con.prepareStatement("select count(*) as num from tempfaq where temp_question=?");
            ps.setString(1, question);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
     * ����û�еõ�������û��ʾ�洢����ʱFAQ����
     */
    public void InsertTempQuestion(String question) {

        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into tempfaq(temp_question) values(?)");
            ps.setString(1, question);
            ps.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
    }
    
     /**
     * ����tempFAQ���е�������Ϣ
     *
     * @author zhuwangnan
     *
     */
    public ArrayList<String> GetAllTempQuestion() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Connection con = null;
        con = DbUtil.getCurrentConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select * from tempfaq where temp_answer is null");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arrayList.add(rs.getString("temp_question").trim());

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeCurrentConnection();
        }
        return arrayList;
    }
    
    
    

}
