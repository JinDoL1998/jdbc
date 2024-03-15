package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

/**
 * @author jinseong
 * @date 2024. 3. 15. 오후 3:08:35
 * @subject 내일 복습 문제 (메모장)
 * @content
 */

public class Ex02_01 {

	public static void main(String[] args) {
		
		// Ex02.java <--> localhost oracle 서버에 연결
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		
		// DataSource 객체
		OracleDataSource ds;
		
		try {
			ds = new OracleDataSource();
			ds.setURL(url);
			conn = ds.getConnection(user, password);
			
			System.out.println(conn.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		System.out.println(" end ");
		 
	}

}
