package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class Ex02 {

	public static void main(String[] args) {
		
		
		// [자바 트랜잭션 처리]
		// [오라클 트랜잭션 처리]
		// 예) 계좌 이체
		// 		A -> 인출
		//		B -> 입금
		/*
		 *  PL/SQL (저장 프로시저)
		 *  
		 *  BEGIN
		 *  	// A
		 *  	// B
		 *  
		 *  	COMMIT;
		 *  EXCEPTION
		 *  	WHEN THEN
		 *  		ROLLBACK;
		 *  END
		 * */ 
		
		// A INSERT dept 50 부서추가	: 성공
		// B INSERT dept 50 부서추가	: PK 제약조건 위배 에러
		
		String sql = "INSERT INTO dept VALUES(?, ?, ?) ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		conn = DBConn.getConnection();
		
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			// insert
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			System.out.println("첫 번째 INSERT문 : " + rowCount);
			// insert
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC2");
			pstmt.setString(3, "SEOUL2");
			rowCount = pstmt.executeUpdate();
			System.out.println("두 번째 INSERT문 : " + rowCount);
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DBConn.close();
		System.out.println("end");
		
	} // main

} // class
