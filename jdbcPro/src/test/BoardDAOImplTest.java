package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {

	@Test
	void test() {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		ArrayList<BoardDTO> list = null;
		try {
			list = dao.select();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("사원수 : " + list.size() + "명");
		DBConn.close();
		
	}

}
