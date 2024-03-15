package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.pool.OracleDataSource;

/**
 * @author jinseong
 * @date 2024. 3. 15. 오후 3:44:41
 * @subject	[내일 복습 문제]
 * @content scott 접속 + dept 테이블 SELECT
 */
public class Ex03 {

	public static void main(String[] args) {
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int deptno;
		String dname;
		String loc;
		
		try {
			// 1. Class.forName() JDBC 드라이버 로딩
			Class.forName(className);
			
			// 2. Connection 객체 생성 - DriverManager.getConnection(url, id, pass)
			conn =  DriverManager.getConnection(url, user, password);
			
			// 3. 필요한 작업(CRUD)
			String sql = "SELECT * FROM dept";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			
			while (rs.next()) {
				/*
				deptno = rs.getInt(1);
				dname = rs.getString(2);
				loc = rs.getString(3);
				*/
				
				deptno = rs.getInt("deptno");
				dname = rs.getString("dname");
				loc = rs.getString("loc");
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 4. Connection 닫기 (close)
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.println(" end ");
		
	}

}
