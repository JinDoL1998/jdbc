package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author yujin
 * @date 2024. 3. 20. 오후 2:50:51
 * @subject   자바 리플랙션(reflection)
 * @content   - 사전적 의미 : 반사, 상, 반영
 *          - JDBC 리플랙션 : 결과물(rs)에 대한
 *            정보(?)를 추출해서 사용할 수 있는 기술
 */
public class Ex06 {
	public static void main(String[] args) {
		String sql = "SELECT table_name FROM tabs";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> tnList = new ArrayList<String>();
		String tableName = null;

		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableName = rs.getString(1);
				tnList.add(tableName);
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
		// tnList 출력
		System.out.println(tnList.toString());

		//[2]
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 보고싶은 테이블명 입력 : ");
		tableName = scanner.next();
		// * 반드시 기억할 것 - 테이블명, 컬럼명은 pstmt의 매개변수로 사용 불가
		//sql = "SELECT * FROM ?";
		sql = "SELECT * FROM " + tableName;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// rs
			ResultSetMetaData rsmd = rs.getMetaData();

			System.out.println("> 컬럼수 : " + rsmd.getColumnCount());
			int columnCount = rsmd.getColumnCount();

			System.out.println("-".repeat(columnCount * 15));

			/*
         for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i);
            String columnType = rsmd.getColumnTypeName(i);
            if (columnType.equals("NUMBER")) {
               // NUMBER(p, s)
               // NUMBER(5, 2)
               // NUMBER(3)
               int precision = rsmd.getPrecision(i);
               int scale = rsmd.getScale(i);
               System.out.printf("%s %s(%d, %d)\n", columnName, columnType, precision, scale);
            } else {
               System.out.printf("%s %s\n", columnName, columnType);
            }
         }//for
			 */

			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				System.out.printf("%-10s\t", columnName);
			}//for
			
			System.out.println();
			System.out.println("-".repeat(columnCount * 15));
			
			// rs 레코드 출력
			// 2 - NUMBER
			// 12 - VARCHAR2
			// 93 - DATE
			
			while(rs.next()) {
				for(int i = 1; i <= columnCount; i++) {
					int columnType = rsmd.getColumnType(i);
					if(columnType == 2) {
						int scale = rsmd.getScale(i);
						if(scale == 0) System.out.printf("%-10d\t", rs.getInt(i)); // 정수
						else System.out.printf("%-10.2f\t", rs.getDouble(i));	// 실수
					}
					else if(columnType == 12) {
						System.out.printf("%-10s\t", rs.getString(i));
					}
					else if(columnType == 93) {
						System.out.printf("%-10tF\t", rs.getDate(i));
					}
					
				} // for
				System.out.println();
			} // while

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

		//[3]
		DBConn.close();
	}//main
}