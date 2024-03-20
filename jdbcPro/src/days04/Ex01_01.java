package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptEmpVO;
import domain.DeptVO;
import domain.EmpVO;

public class Ex01_01 {

	public static void main(String[] args) {
		
		String deptSql = "SELECT d.deptno, dname, COUNT(*) cnt "
				+ "FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
				+ "WHERE d.deptno IS NOT NULL OR dname IS NULL "
				+ "GROUP BY d.deptno, dname "
				+ "ORDER BY d.deptno ASC ";
		
		String empSql = "SELECT empno, ename, hiredate, sal "
				+ "FROM emp "
				+ "WHERE deptno = ?";
		
		Connection conn = null;
		PreparedStatement deptPstmt = null, empPstmt = null;
		ResultSet deptRs = null, empRs = null;
		DeptVO dvo = null;
		EmpVO evo = null;
		
		conn = DBConn.getConnection();
		try {
			deptPstmt = conn.prepareStatement(deptSql);
			deptRs = deptPstmt.executeQuery();
			if(deptRs.next()) {
				do {
					int deptno = deptRs.getInt("deptno");
					dvo = DeptVO
							.builder()
							.deptno(deptno)
							.dname(deptRs.getString("dname"))
							.cnt(deptRs.getInt("cnt"))
							.build();
					System.out.printf("%s(%d) - %d명\n"
								, dvo.getDname(), dvo.getDeptno(), dvo.getCnt());
					
					// START
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, deptno);
					empRs = empPstmt.executeQuery();
					if(empRs.next()) {
						do {
							evo = EmpVO.builder()
									.empno(empRs.getInt("empno"))
									.ename(empRs.getString("ename"))
									.hiredate((java.util.Date)empRs.getDate("hiredate"))
									.sal(empRs.getDouble("sal"))
									.build();
							System.out.printf("%10d%10s%15s%10.2f\n"
											,evo.getEmpno(), evo.getEname(), evo.getHiredate(), evo.getSal());
						} while (empRs.next());
					}
					
					empPstmt.close();
					empRs.close();
					// END
					
				} while (deptRs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				deptPstmt.close();
				deptRs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
	} // main

} // class
