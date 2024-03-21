package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {
	
	
//	@Test
//	void test() {
//		Connection conn = DBConn.getConnection();
//		BoardDAO dao = new BoardDAOImpl(conn);
//		ArrayList<BoardDTO> list = null;
//		try {
//			list = dao.select();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("사원수 : " + list.size() + "명");
//		DBConn.close();
//		
//	}
	
	@Test
	void test() {
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);

		BoardDTO dto = null;
		try {
			dto = dao.view(152);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.printf("%d %s %s %s %s %d"
				, dto.getSeq(), dto.getTitle(), dto.getEmail(), dto.getWriter(), dto.getWriteDate(), dto.getReaded());
		DBConn.close();
		
	}
	
	
//	@Test
//	void testInsert() {
//		BoardDTO dto = BoardDTO.builder()
//				.title("첫 번째 게시글")
//				.writer("홍길동")
//				.pwd("1234")
//				.email("kim@sist.com")
//				.tag(0)
//				.content("첫 번째 게시글 내용")
//				.build();
//		
//		Connection conn = DBConn.getConnection();
//		BoardDAO dao = new BoardDAOImpl(conn);
//		int rowCount = 0;
//		try {
//			rowCount = dao.insert(dto);
//			if(rowCount == 1) System.out.println("게시글 작성 완료!!");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBConn.close();			
//		}
//		System.out.println("end");
//	}

}
