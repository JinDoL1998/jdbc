package days02;

import java.sql.Connection;
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

public class Ex03 {

	public static Scanner scanner = new Scanner(System.in);
	public static Connection conn = null;

	public static void main(String[] args) {

		// 6명
		// 7명

		// [팀 문제]
		// emp 사원테이블 - 
		// 1) 사원추가
		// 2) 사원수정
		// 3) 사원삭제
		// 4) 사원검색
		// 5) 사원조회

		conn = DBConn.getConnection();

		System.out.print("항목 입력 : (1)사원추가 / (2)사원수정 / (3)사원삭제 / (4)사원검색 / (5)사원조회");
		int selectNumber = scanner.nextInt();
		scanner.nextLine();

		if(selectNumber == 5) viewEmp();
		else if (selectNumber == 3) deleteEmp();
		else if (selectNumber == 1) addEmp();
		else if (selectNumber == 4) searchEmp();
//		else if (selectNumber == 2) modifyEmp();



	} // main


	// 검색
	private static void searchEmp() {
		System.out.print("검색 조건 입력 : 1(사원번호), 2(사원명), 3(부서번호)");
		int searchCategory = scanner.nextInt();

		System.out.println("검색어 입력 : ");
		String searchKeyword = scanner.next();
		
		String sql = null;
		ArrayList<EmpVO> list = null;
		ResultSet rs = null;
		Statement stmt = null;
		if ( searchCategory == 1) {

			sql = String.format("SELECT * "
								+ "FROM emp "
								+ "WHERE empno IN ( '%s' ) ", searchKeyword);
		}

		if ( searchCategory == 2) {

			sql = String.format("SELECT * "
								+ "FROM emp "
								+ "WHERE REGEXP_LIKE(ename, '%s', 'i') ", searchKeyword);
		}

		if ( searchCategory == 3) {

			sql = String.format("SELECT * "
								+ "FROM emp "
								+ "WHERE REGEXP_LIKE(deptno, '%s', 'i') ", searchKeyword);
		}
		
		int empno, mgr, sal, comm, deptno;
		String ename, job;
		Date hireDate;

		EmpVO vo = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hireDate = rs.getDate("hiredate");
					sal = rs.getInt("sal");
					comm = rs.getInt("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, ename, job, mgr, hireDate, sal, comm, deptno);
					list.add(vo);
				} while (rs.next());
				dispEmp(list);
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
	}



	// 사원 삭제
	private static void deleteEmp() {

		String empno;
		System.out.print("> 삭제할 회원번호를 입력 ? ");

		empno = scanner.nextLine();


		String sql =  String.format(
				"DELETE FROM emp "
						+ "WHERE empno IN ( %s ) ", empno);

		System.out.println( sql );

		Statement stmt = null;

		try { 
			stmt = conn.createStatement(); 
			int rowCount = stmt.executeUpdate(sql);

			if( rowCount == 1 ) {
				System.out.println(" 회원 삭제 성공!!!");
			} 

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}

	}

	// 사원 조회
	private static void viewEmp() {

		ArrayList<EmpVO> list = null;
		ResultSet rs = null;
		Statement stmt = null;
		String sql = "SELECT * "
				+ "FROM emp";

		int empno, mgr, sal, comm, deptno;
		String ename, job;
		Date hireDate;

		EmpVO vo = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				list = new ArrayList<EmpVO>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hireDate = rs.getDate("hiredate");
					sal = rs.getInt("sal");
					comm = rs.getInt("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, ename, job, mgr, hireDate, sal, comm, deptno);
					list.add(vo);
				} while (rs.next());
				dispEmp(list);
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


	}

	private static void dispEmp(ArrayList<EmpVO> list) {
		Iterator<EmpVO> ir = list.iterator();
		System.out.println("-".repeat(100));
		System.out.printf("empno\tename\tjob\t\tmgr\thiredate\tsal\tcomm\tdeptno\n");
		System.out.println("-".repeat(100));

		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.printf("%d\t%s\t%s    \t%d\t%s\t%d\t%d\t%d\n", vo.getEmpno(), vo.getEname(), vo.getJob(), vo.getMgr(), vo.getHiredate()
					, vo.getSal(), vo.getComm(), vo.getDeptno());

		}
		System.out.println("-".repeat(100));
	}

	private static void addEmp() {

		System.out.print("> 추가할 사원의 부서번호를 입력하세요 ? ");
		int deptno = scanner.nextInt();
		scanner.nextLine();

		System.out.println("> 추가할 사원의 사원번호를 입력하세요 ? ");
		int empno = scanner.nextInt();

		System.out.println("> 추가할 사원의 사원이름을 입력하세요 ? ");
		String ename = scanner.next();

		System.out.println("> 추가할 사원의 사원직업을 입력하세요 ? ");
		String job = scanner.next();

		System.out.println("> 추가할 사원의 사수번호을 입력하세요 ? ");
		int mgr = scanner.nextInt();

		System.out.println("> 추가할 사원의 입사날짜를 입력하세요 ? ");
		String hiredate = scanner.next();

		System.out.println("> 추가할 사원의 임금급액을 입력하세요 ? ");
		int sal = scanner.nextInt();

		System.out.println("> 추가할 사원의 커밋금액을 입력하세요 ? ");
		int comm = scanner.nextInt();



		String sql = String.format(" INSERT INTO emp ( deptno, empno, ename, job, mgr, hiredate, sal, comm  )"
				+ " VALUES ( %d, %d, '%s', '%s', %d, '%s', %d, %d )"
				, deptno, empno, ename, job, mgr, hiredate, sal, comm);

		System.out.println(sql);

		Statement stmt = null;

		try {
			stmt = conn.createStatement();         
			int rowCount = stmt.executeUpdate(sql);

			if (rowCount == 1) {
				System.out.println("사원 추가 성공!!");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

} // class
