package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;
import oracle.jdbc.OracleTypes;

/**
 * @author jinseong
 * @date 2024. 3. 20. 오후 2:02:04
 * @subject	dept - insert 부서삽입
 * @content	up_insertdept
 */
public class Ex05_01 {

	public static void main(String[] args) {
		
		String sql = "{CALL up_insertdept(?, ?)}";
		// String sql = "{CALL up_insertdept(pdname => ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		String pdname = "QC", ploc = "SEOUL";
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1,pdname);
			cstmt.setString(2, ploc);
			rowCount = cstmt.executeUpdate();
			
			if(rowCount == 1) {
				System.out.println("부서 추가 성공");
			}
			else {
				System.out.println("부서 추가 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		DBConn.close();
		System.out.println("end");
		

	} // main

} // class

/*
 *CREATE OR REPLACE PROCEDURE up_selectdept
(
    pdeptcursor OUT SYS_REFCURSOR
)
IS
BEGIN
    -- OPEN 커서 FOR문
    OPEN pdeptcursor FOR SELECT * FROM dept;
    
--EXCEPTION
END; 
*/