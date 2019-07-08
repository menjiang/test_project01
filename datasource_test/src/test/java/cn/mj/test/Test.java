package cn.mj.test;

import cn.mj.DataSourceImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) throws SQLException {
        DataSource ds = new DataSourceImpl();
        Connection conn = ds.getConnection();

        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery("select * from account");

        while(rs.next()){
            //获取数据
            //6.2 获取数据
            int id = rs.getInt(1);
            String name = rs.getString(2);
            float money = rs.getFloat(3);

            System.out.println(id + "---" + name + "---" + money);
        }
    }
}
