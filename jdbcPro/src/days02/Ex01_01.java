package days02;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;
import domain.EmpVO;
import domain.EmpInfoVO;

/**
 * @author jinseong
 * @date 2024. 3. 18. 오전 11:27:43
 * @subject DBConn 사용
 * @content
 */
public class Ex01_01 {

	public static void main(String[] args) {
		
		String ipAddress = "192.168.10.173";
		String sid = "xe";
		String url = String.format("jdbc:oracle:thin:@%s:1521:%s", ipAddress, sid);
		String user = "scott";
		String password = "tiger";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int empno;
		String dname;
		String ename;
		Date hiredate;
		double pay;
		int grade;
		
		Scanner scanner = new Scanner(System.in);
		String searchWord = null;
		System.out.print("> 검색할 사원명 입력 ? ");
		searchWord = scanner.next();
		
		try {
			conn = DBConn.getConnection(url, user, password);
			ArrayList<EmpInfoVO> list = null;
		    EmpInfoVO eVo = null;

			String sql = String.format(
				    "SELECT empno, dname, ename, hiredate, sal+NVL(comm,0) pay, grade "
				    + "FROM emp e LEFT JOIN dept d ON e.deptno = d.deptno "
				    + "JOIN salgrade s ON e.sal + NVL(e.comm,0) BETWEEN s.losal AND s.hisal "
				    + "WHERE REGEXP_LIKE (ename, '%s', 'i')", searchWord);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if( rs.next() ) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt("empno");
					dname = rs.getString("dname");
					ename = rs.getString("ename");
					hiredate = rs.getDate("hiredate");
					pay = rs.getDouble("pay");
					grade = rs.getInt("grade");
					
					eVo = new EmpInfoVO(empno, dname, ename, hiredate, pay, grade);
					list.add(eVo);
					
				} while(rs.next());
				
			}
			
			dispEmp(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				//conn.close();
				DBConn.close(); // 꼭 기억
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
	} // main


	private static void dispEmp(ArrayList<EmpInfoVO> list) {
		if(list == null) {
			System.out.println("사원이 존재하지 않습니다.");
			return;
		}
		
		Iterator<EmpInfoVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpInfoVO empInfoVO = ir.next();
			System.out.println(empInfoVO.toString());
			
		}
	}



} // class
