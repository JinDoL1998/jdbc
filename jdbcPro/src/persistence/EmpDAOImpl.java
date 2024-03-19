package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import domain.EmpVO;

public class EmpDAOImpl implements EmpDAO{

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자를 통한 DI
	public EmpDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// Setter를 통한 DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ArrayList<EmpVO> getEmpSelect() {
		ArrayList<EmpVO> list = null;
		int empno;
		String ename;
		String job;
		int mgr;
		Date hiredate;
		Double sal;
		Double comm;
		int deptno;
		String sql = "SELECT * "
					+ "FROM emp ";
		try {

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt(1);
					ename = rs.getString(2);
					job = rs.getString(3);
					mgr = rs.getInt(4);
					hiredate = rs.getDate(5);
					sal = rs.getDouble(6);
					comm = rs.getDouble(7);
					deptno = rs.getInt(8);

					EmpVO evo = new EmpVO(empno,ename,job,mgr,hiredate,sal,comm,deptno);
					list.add(evo);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return null;
	}

	@Override
	public int addEmp(EmpVO vo) {
		int rowCount  = 0;

		String sql = "INSERT INTO emp ( deptno, empno, ename, job, mgr, hiredate, sal, comm  )"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

		// System.out.println(sql);

		try {         
			pstmt = conn.prepareStatement(sql);
			// deptno, empno, ename, job, mgr, hiredate, sal, comm
			pstmt.setInt(1, vo.getDeptno());
			pstmt.setInt(2, vo.getEmpno());
			pstmt.setString(3, vo.getEname());
			pstmt.setString(4, vo.getJob());
			pstmt.setInt(5, vo.getMgr());
			// java.util.Date -> java.sql.Date 변환
			// pstmt.setDate(6, (java.sql.Date) vo.getHiredate());            
			long ms = vo.getHiredate().getTime() ;  // java.util.Date
			java.sql.Date sdHiredate = new java.sql.Date(ms);
			pstmt.setDate(6, sdHiredate);         
			pstmt.setDouble(7, vo.getSal());
			pstmt.setDouble(8, vo.getComm());

			rowCount = pstmt.executeUpdate();

		} catch (SQLException e) {
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

		return rowCount;
	}

	@Override
	public int updateEmp(EmpVO evo) {
		// TODO Auto-generated method stub

		return 0;
	}

	@Override
	public int deleteEmp(int empno) {
		// TODO Auto-generated method stub

		return 0;
	}

	@Override
	public ArrayList<EmpVO> searchEmp(int searchCategory, String searchWord) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public EmpVO getEmp(int empno) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
}
