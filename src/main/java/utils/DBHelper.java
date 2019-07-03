package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

	public static final String url = "jdbc:mysql://xx.xx.xx.2:3306/test?useSSL=false";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "xxxxx";

	public static Connection conn = null;

	public static Connection getConn() {
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
