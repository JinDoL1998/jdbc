package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

import domain.DeptEmpSalgradeVO;

public class Ex04 {
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		conn = DBConn.getConnection();

		String sql = "SELECT d.deptno, dname, empno, ename, sal, grade, losal, hisal "
				+ "FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
				+ "            JOIN salgrade s ON e.sal BETWEEN s.losal AND s.hisal "
				+ "ORDER BY grade ";

		ArrayList<DeptEmpSalgradeVO> list = null;

		int deptno, empno, sal, losal, hisal, grade;
		String dname, ename;
		DeptEmpSalgradeVO desvo = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				list = new ArrayList<DeptEmpSalgradeVO>();
				do {
					deptno = rs.getInt("deptno");
					empno = rs.getInt("empno");
					sal = rs.getInt("sal");
					grade = rs.getInt("grade");
					dname = rs.getString("dname");
					ename = rs.getString("ename");
					losal = rs.getInt("losal");
					hisal = rs.getInt("hisal");
					desvo = new DeptEmpSalgradeVO(deptno, dname, empno, ename, sal, grade, losal, hisal);
					list.add(desvo);

				} while (rs.next());

				dispDeptEmpSal(list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}






	} // main

	private static void dispDeptEmpSal(ArrayList<DeptEmpSalgradeVO> list) {

		int tempGrade = -1;
		int count = 1;
		int tempCount = 0;
		
		for(DeptEmpSalgradeVO desvo : list) {
			int grade = desvo.getGrade();
			
			if (tempGrade == grade) {
				System.out.printf("%d%13s%8d%10s%8d\n"
						, desvo.getDeptno(), desvo.getDname(), desvo.getEmpno(), desvo.getEname(), desvo.getSal());
				tempCount++;
			}
			
			else {
				System.out.println();
				System.out.printf("-%d등급 (%d ~ %d) - %d명\n", desvo.getGrade(), desvo.getLosal(), desvo.getHisal(), count);
				System.out.printf("%d%13s%8d%10s%8d\n"
						, desvo.getDeptno(), desvo.getDname(), desvo.getEmpno(), desvo.getEname(), desvo.getSal());
				tempGrade = grade;

			}
			
			count++;

		}

	}

} // class