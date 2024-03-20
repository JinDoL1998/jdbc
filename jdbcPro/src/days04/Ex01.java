package days04;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

import domain.DeptEmpVO;

public class Ex01 {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		conn = DBConn.getConnection();
		
		String sql = "SELECT dname, d.deptno, empno, ename, hiredate, sal+NVL(comm,0) pay "
					+ "FROM dept d RIGHT JOIN emp e ON d.deptno = e.deptno "
					+ "ORDER BY deptno ASC";
		
		String dname, ename;
		int deptno, empno;
		double pay;
		Date hiredate;
		
		List<DeptEmpVO> list = null;
		DeptEmpVO devo = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				list = new ArrayList<DeptEmpVO>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					hiredate = rs.getDate("hiredate");
					pay = rs.getDouble("pay");
					
					devo = new DeptEmpVO(deptno, dname, empno, ename, hiredate, pay);
					
					list.add(devo);
					
				} while (rs.next());
				
				dispDeptCount(list);
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

	private static void dispDeptCount(List<DeptEmpVO> list) {
		
		Collections.sort(list, Comparator.comparingInt(DeptEmpVO::getDeptno));
		Map<Integer, List<DeptEmpVO>> deptEmpMap = new HashMap<>();
		
		for(DeptEmpVO devo : list) {
			int deptno = devo.getDeptno();
			
			if(!deptEmpMap.containsKey(deptno)) {
				deptEmpMap.put(deptno, new ArrayList<>());
			}
			
			deptEmpMap.get(deptno).add(devo);
			
		}
		
		for(int deptno : deptEmpMap.keySet()) {
			List<DeptEmpVO> deptEmpList = deptEmpMap.get(deptno);
			int empCount = deptEmpList.size();
			String dname = deptEmpList.get(0).getDname();
			
			System.out.printf("%s(%d) - %d명\n", dname, deptno, empCount);
			
			for(DeptEmpVO devo : deptEmpList) {
				System.out.printf("%10d%10s%15s%10.2f\n"
						, devo.getEmpno(), devo.getEname(), devo.getHiredate(), devo.getPay());
			}
		}
		
	}

} // class
