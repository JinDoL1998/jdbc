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
 * @subject	dept - update 부서수정
 * @content	up_updatedept
 */
public class Ex05_02 {

	public static void main(String[] args) {
		
		String sql = "{CALL up_updatedept(?, ?, ?)}";
		// String sql = "{CALL up_insertdept(pdname => ?)}";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int rowCount = 0;
		
		int pdeptno = 50;
		String pdname = "QC2", ploc = "SEOUL2";
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pdeptno);
			cstmt.setString(2,pdname);
			cstmt.setString(3, ploc);
			
			rowCount = cstmt.executeUpdate();
			
			if(rowCount == 1) {
				System.out.println("부서 수정 성공");
			}
			else {
				System.out.println("부서 수정 실패");
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

//CREATE OR REPLACE PROCEDURE up_updatedept
//(
//    pdeptno dept.deptno%TYPE
//    ,pdname dept.dname%TYPE := NULL
//    ,ploc dept.loc%TYPE := NULL
//)
//IS
//BEGIN
//    UPDATE dept
//    SET dname = NVL(pdname, dname), loc = NVL(ploc, loc)
//    WHERE deptno = pdeptno;
//--EXCEPTION
//END;