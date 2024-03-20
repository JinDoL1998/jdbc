package days04;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

public class Ex03 {

	public static void main(String[] args) {
		
		// CallableStatement - 저장프로시저 , 저장함수
		// [저장 프로시저]	- 입력받은 ID를 사용 여부 체크하는 프로시저
		//		ㄴ 회원가입
		//			아이디		: [  hong   ] <ID 중복체크 버튼>
		//			비밀번호
		//			이메일
		//			주소
		//			연락처
		//			등등
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("> 중복체크할 ID(empno)를 입력 : ");
		int id = scanner.nextInt();	// 7369, 8888
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int check = 0;
		
		String sql = "{call up_idChe ck (?, ?)} ";
//		String sql = "{call up_idcheck (pid=>?, pcheck=>?)} ";
		
		conn = DBConn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, id);	// IN
			// OUT 설정 X cstmt 출력용 매개변수
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.executeQuery();
			
			check = cstmt.getInt(2);
			
			if(check == 0) {
				System.out.println("사용 가능한 ID(empno) 입니다");
			}
			else {
				System.out.println("이미 사용중인 아이디 입니다.");
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
