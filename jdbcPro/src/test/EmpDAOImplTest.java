package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import domain.EmpVO;
import persistence.EmpDAO;
import persistence.EmpDAOImpl;


class EmpDAOImplTest {

	@Test
	void testAddEmp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date hiredate = null;
		try {
			hiredate = sdf.parse("2017-01-01");
		} catch (ParseException e) { 
			e.printStackTrace();
		}

		Connection conn = DBConn.getConnection();
		EmpDAO dao = new EmpDAOImpl(conn);
		EmpVO evo = new EmpVO(7777, "jinseong", "student", 7369, hiredate, 1000, 500, 40);
		int rowCount = dao.addEmp(evo);
		// 출력확인
		if (rowCount == 1) {
			System.out.println("사원 추가 성공!!!");
			DBConn.close();
		}

	}

//		@Test
//		void test() {
//			Connection conn = DBConn.getConnection();
//			EmpDAO dao = new EmpDAOImpl(conn);
//			
//			ArrayList<EmpVO> list = dao.getEmpSelect();
//			
//			System.out.println("사원수 : " + list.size() + "명");
//			DBConn.close();
//		}

}
