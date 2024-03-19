package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import domain.EmpVO;

/**
 * @author jinseong
 * @date 2024. 3. 15. 오후 5:04:44
 * @subject
 * @content
 */
public class Ex04 {

	public static void main(String[] args) {
		
		// 문제
		// 부서번호( deptno )를 입력받아서
		// emp 테이블에서 SELECT 하는 코딩
		// EmpVO
		// dispEmp(ArrayList<EmpVO> list)
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int empno;
		String ename;
		String job;
		int mgr;
		Date hiredate;
		Double sal;
		Double comm;
		int deptno;
		
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			
			String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE deptno = 30";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getDate ("hiredate");
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				
				
				EmpVO eVo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				list.add(eVo);
			}
			
			dispEmp(list);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		
	} // main

	private static void dispEmp(ArrayList<EmpVO> list) {
		
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO empVO = ir.next();
			System.out.println(empVO.toString());
			
		}
	}

} // class
