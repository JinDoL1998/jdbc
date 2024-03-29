package days03;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author jinseong
 * @date 2024. 3. 19. 오전 10:10:49
 * @subject
 * @content
 */
public class Ex01 {

		
		// 부서 추가, 수정, 삭제, 검색, 조회
		public static String [] menu = {"추가", "수정", "삭제", "조회", "종료", "검색"};
		public static int selectedNumber;
		public static Connection conn;
		public static Scanner scanner = new Scanner(System.in);

		public static void main(String[] args) {
		

			// 1. DB사용할 작업
			conn = DBConn.getConnection();

			do {
				dispMenu();
				choiceMenu();
				processingMenu();			
			} while(true);


		} // main

		private static void processingMenu() {
			switch (selectedNumber) {
			case 1:	// 추가
				addDept();
				break;
			case 2:	// 수정
				modifyDept();
				break;
			case 3:	// 삭제
				deleteDept();
				break;
			case 4:	// 조회
				viewDept();
				break;
			case 5:	// 종료
				programExit();
				break;
			case 6:	// 검색
				searchDept();
				break;
			}


			pause();
		}

		/* 부서검색 [1] 
		// 부서 검색
		private static void searchDept() {

			// 검색 조건 입력 : 1(부서번호), 2(부서명), 3(지역명)
			// SELECT * FROM dept
			// WHERE REGEXP_LIKE(loc, 'lo', 'i'); -- 3
			// WHERE REGEXP_LIKE(dname, 'SL', 'i'); -- 2
			// WHERE deptno = 10;	-- 1

			System.out.print("검색 조건 입력 : 1(부서번호), 2(부서명), 3(지역명)");
			int searchCategory = scanner.nextInt();

			System.out.println("검색어 입력 : ");
			String searchKeyword = scanner.next();


			// 검색어 입력
			String sql = null;
			ArrayList<DeptVO> list = null;
			ResultSet rs = null;
			Statement stmt = null;
			if ( searchCategory == 1) {

				sql = String.format("SELECT * "
									+ "FROM dept "
									+ "WHERE deptno IN (%s) ", searchKeyword);
			}

			if ( searchCategory == 2) {

				sql = String.format("SELECT * "
									+ "FROM dept "
									+ "WHERE REGEXP_LIKE(dname, '%s', 'i') ", searchKeyword);
			}

			if ( searchCategory == 3) {

				sql = String.format("SELECT * "
									+ "FROM dept "
									+ "WHERE REGEXP_LIKE(loc, '%s', 'i') ", searchKeyword);
			}


			int deptno;
			String dname, loc;

			DeptVO vo = null;

			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					list = new ArrayList<DeptVO>();
					do {
						deptno = rs.getInt("deptno");
						dname = rs.getString("dname");
						loc = rs.getString("loc");
						vo = new DeptVO(deptno, dname, loc);
						list.add(vo);
					} while (rs.next());
					dispDept(list);
				} // 
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
		 */

		// 부서검색 [2]
		private static void searchDept() {

			int searchCondition = 1;	// 1,2,3, 검색 조건
			String searchWord = null;	// 검색어

			System.out.print("> 검색조건 (1)부서번호 / (2)부서이름 / (3)loc / (4)부서이름, LOC >>>");
			searchCondition = scanner.nextInt();
			System.out.print("> 검색어 입력하세요 : ");
			searchWord = scanner.next().trim();

			// 부서조회 함수 복붙
			ArrayList<DeptVO> list = null;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String sql = "SELECT * "
					+ "FROM dept "
					+ "WHERE ";
			
			if (searchCondition == 1) {
				sql += "deptno = ? ";
			}
			
			else if (searchCondition == 4){
				sql += "REGEXP_LIKE(dname, ?, 'i') OR REGEXP_LIKE(loc, ?, 'i')";
				
			}
			else { // 2, 3
				sql += String.format(" REGEXP_LIKE( %s, ?, 'i' ) ", searchCondition==2 ? "dname" : "loc");
			}
			
			System.out.println(sql);
			
			int deptno;
			String dname, loc;

			DeptVO vo = null;

			try {
				pstmt = conn.prepareStatement(sql);
				
				if(searchCondition == 1) {
					pstmt.setString(1, searchWord);
				}
				else if(searchCondition == 2) {
					pstmt.setString(1, searchWord);
				}
				else if(searchCondition == 3) {
					pstmt.setString(1, searchWord);
				}
				else if(searchCondition == 4) {
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
				}
				
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					list = new ArrayList<DeptVO>();
					do {
						deptno = rs.getInt("deptno");
						dname = rs.getString("dname");
						loc = rs.getString("loc");
						vo = new DeptVO(deptno, dname, loc);
						list.add(vo);
					} while (rs.next());
					dispDept(list);
				} // 
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					rs.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}
			

		}

		// 부서 삭제
		private static void deleteDept() {

			String deptno ;
			System.out.print("> 삭제할 부서번호를 입력 ? ");
			deptno = scanner.nextLine();
			
			String[] deptnoList = deptno.trim().split("\s*,\s*");
			
			String sql = 
					"DELETE FROM dept "
				  + "WHERE deptno  IN ( ";
			
			for(int i = 0; i < deptnoList.length; i++) {
				sql += "?, ";
			}
			
			sql = sql.substring(0, sql.length()-2);
			sql += " )";
			

			System.out.println( sql );

			PreparedStatement pstmt = null;

			try { 
				pstmt = conn.prepareStatement(sql); 
				
				for(int i = 0; i < deptnoList.length; i++) {
					pstmt.setString(i+1, deptnoList[i]);
				}
				
				int rowCount = pstmt.executeUpdate();

				if( rowCount >= 1 ) {
					System.out.println(" 부서 삭제 성공!!!");
				} 

			} catch (SQLException e) { 
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}


		}

		/* [4] 부서수정
		private static void modifyDept() {

			int deptno = 0;
			String dname = null, loc = null;

		      System.out.print("> 수정할 부서번호, 부서명, 지역명 입력하세요 ? ");
		      String record = scanner.next();

		      String sql = null ;

		      if ( dname.equals("")) {
		         sql = String.format(
		                 " UPDATE  dept "
		               + " SET loc = '%s' "
		               + " WHERE deptno = %d ", loc, deptno );
		      } else if (loc.equals("")) {
		         sql = String.format(
		                 " UPDATE  dept "
		               + " SET dname = '%s' "
		               + " WHERE deptno = %d ", dname, deptno );
		      } else {
		         sql = String.format(
		                 " UPDATE  dept "
		               + " SET dname = '%s',loc = '%s' "
		               + " WHERE deptno = %d ", dname, loc, deptno );
		      }

		      System.out.println( sql );  // 쿼리 확인

		      Statement stmt = null;

		      try { 
		         stmt = conn.createStatement(); 
		         int rowCount = stmt.executeUpdate(sql);

		         if( rowCount == 1 ) {
		            System.out.println(" 부서 수정 성공!!!");
		         }

		         // COMMIT or ROLLBACK 
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
		 */

		// [3] 부서수정
		private static void modifyDept() {

			System.out.print("> 수정할 부서번호 입력하세요 ? ");
			int deptno = scanner.nextInt(); 
			scanner.nextLine();  // \r\n

			System.out.print("> 수정할 부서명 입력하세요 ? ");
			String dname = scanner.nextLine(); // QC\r\n       ""
			
			System.out.print("> 수정할 지역명 입력하세요 ? ");
			String loc = scanner.nextLine();

			String sql = null ;

			if ( dname.equals("")) {
				sql = 
						" UPDATE  dept "
						+ " SET loc = ? "
						+ " WHERE deptno = ? ";
			} else if (loc.equals("")) {
				sql = 
						" UPDATE  dept "
						+ " SET dname = ? "
						+ " WHERE deptno = ? ";
			} else {
				sql = 
						" UPDATE  dept "
						+ " SET dname = ?,loc = ? "
						+ " WHERE deptno = ? ";
			}

			// System.out.println( sql );  // 쿼리 확인

			// Statement stmt = null;
			PreparedStatement pstmt = null;
			
			try { 
				pstmt = conn.prepareStatement(sql);
				// ? 매개변수 설정
				if(dname.equals("")) {
					pstmt.setString(1, loc);
					pstmt.setInt(2, deptno);
				}
				else if(loc.equals("")) {
					pstmt.setString(1, dname);
					pstmt.setInt(2, deptno);
				}
				else {
					pstmt.setString(1, dname);
					pstmt.setString(2, loc);
					pstmt.setInt(3, deptno);
				}
				
				int rowCount = pstmt.executeUpdate();

				if( rowCount == 1 ) {
					System.out.println(" 부서 수정 성공!!!");
				}

				// COMMIT or ROLLBACK 
			} catch (SQLException e) { 
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}


		}


		/* [2] 부서수정 
		private static void modifyDept() throws NumberFormatException, IOException {

			System.out.println("> 수정할 부서번호, 부서명, 지역명 입력하세요");
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println("부서 번호 입력하세요 ");
		    int deptno = Integer.parseInt(br.readLine());

		    System.out.println("부서명 입력하세요 ");
		    String dname = "";
		    dname = br.readLine();

		    System.out.println("지역명 입력하세요 ");
		    String loc = "";
		    loc = br.readLine();

		    String sql = "SELECT * FROM dept WHERE deptno = " + deptno;
		    Statement stmt = null;
		    try {
		         ResultSet rs = null;
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);

		         rs.next();

		         dname = dname.isEmpty() ? rs.getString("dname") : dname;
		         loc = loc.isBlank() ? rs.getString("loc") : loc;

		         sql = "";
		         sql = String.format("UPDATE dept "
		               + " SET dname = '%s', loc = '%s'"
		               + " WHERE deptno = %d ", dname,loc,deptno);
		         int rowcount = stmt.executeUpdate(sql);
		         if(rowcount == 1) {
		            System.out.println(rowcount + "수정 성공");
		         } else {
		            System.out.println("실패");
		         }
		         stmt.close();
		         rs.close();

		      } catch (SQLException e) {
		         e.printStackTrace();
		      }


		}
		 */

		/* [1] 부서수정 
		private static void modifyDept() {

			System.out.print("> 수정할 부서번호, 부서명, 지역명 입력하세요 : ");
			int deptno = scanner.nextInt();
			String dname = scanner.next();
			String loc = scanner.next();

			String sql = String.format("UPDATE dept "
									+ "SET dname = '%s', loc = '%s' "
									+ "WHERE deptno = %d ", dname, loc, deptno);

			Statement stmt = null;
			System.out.println(sql);

			try {
				// conn.setAutoCommit(true);
				stmt = conn.createStatement();
				int rowCount = stmt.executeUpdate(sql);

				if(rowCount == 1) {
					System.out.println("부서 수정 성공!!!");
				}

				// COMMIT or ROLLBACK
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
		 */

		private static void addDept() {

			System.out.print("> 부서번호, 부서명, 지역명 입력하세요 : ");
			int deptno = scanner.nextInt();
			String dname = scanner.next();
			String loc = scanner.next();
			String sql = "INSERT INTO dept (deptno, dname, loc) "
						+ "VALUES (?, ?, ?) ";

			// System.out.println(sql) 쿼리확인
			// Statement stmt = null;
			PreparedStatement pstmt = null;
			
			try {
				// stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				
				// 인덱스에서 누락된 IN 또는 OUT 매개변수:: 1
				pstmt.setInt(1, deptno);
				pstmt.setString(2, dname);
				pstmt.setString(3, loc);
				int rowCount = pstmt.executeUpdate();

				if(rowCount == 1) {
					System.out.println("부서 추가 성공!!!");
				}

				// COMMIT or ROLLBACK
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		private static void pause() {

			System.out.println("엔터치면 계속합니다.");
			try {
				System.in.read();
				System.in.skip(System.in.available());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private static void viewDept() {

			ArrayList<DeptVO> list = null;
			ResultSet rs = null;
			//Statement stmt = null;
			PreparedStatement pstmt = null;
			String sql = "SELECT * "
					+ "FROM dept "
					+ "ORDER BY deptno ";

			int deptno;
			String dname, loc;

			DeptVO vo = null;

			try {
				// stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				// rs = stmt.executeQuery(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					list = new ArrayList<DeptVO>();
					do {
						deptno = rs.getInt("deptno");
						dname = rs.getString("dname");
						loc = rs.getString("loc");
						vo = new DeptVO(deptno, dname, loc);
						list.add(vo);
					} while (rs.next());
					dispDept(list);
				} // 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					rs.close();
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}


		}

		private static void dispDept(ArrayList<DeptVO> list) {

			Iterator<DeptVO> ir = list.iterator();
			System.out.println("-".repeat(40));
			System.out.printf("deptno\t  dname\t\t    loc\n");
			System.out.println("-".repeat(40));

			while (ir.hasNext()) {
				DeptVO vo = ir.next();
				System.out.printf("%d%15s%15s\n", vo.getDeptno(), vo.getDname(), vo.getLoc());

			}
			System.out.println("-".repeat(40));
		}

		private static void programExit() {
			// 1. DB닫기
			DBConn.close();
			// 2. 종료 메시지 출력
			System.out.println("프로그램 종료!!!");
			// 3.
			System.exit(-1);
		}

		private static void choiceMenu() {

			// Scanner scanner = new Scanner(System.in);

			try { 
				System.out.println("> 메뉴 선택하세요 ");
				selectedNumber = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private static void dispMenu() {
			System.out.printf("[메뉴]\n");
			for(int i = 0; i < menu.length; i++) {
				System.out.printf("%d. %s\n", i+1, menu[i]);
			}

		}

	} // class
