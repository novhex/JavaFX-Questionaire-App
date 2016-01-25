
package com.DB;
import java.sql.*;

public class DBInfo {
Connection conn = null;
public static Connection ConnectDb()
 {
try{Class.forName("com.mysql.jdbc.Driver");
Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/qs", "root", "");
return conn;
}catch (Exception e) {
System.err.println("Cannot Establish DB Connection");
return null;
}
}
}
