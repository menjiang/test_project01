package cn.mj;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 实现连接池
 */
public class DataSourceImpl implements DataSource {
    private static Properties properties;
    private static Connection conn;
    private static InputStream in;
    private List<Connection> list;

    static {
        try {
            //加载配置文件
            properties = new Properties();
            in = DataSourceImpl.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(in);
            //注册驱动
            Class.forName(properties.getProperty("driver"));


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public Connection getConnection() throws SQLException {
        if (list.size() >= 1) {
            conn = list.get(0);
            list.remove(0);
            return conn;

        }

        if (list.size() == 0) {
            for (int i = 0; i < 10; i++) {
                conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
                if (conn != null) {
                    list.add(conn);
                }
            }
            conn = list.get(0);
            return conn;
        }

        return null;
    }

    //归还来连接
    public void close(){
       list.add(this.conn);

    }

    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
