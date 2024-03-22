package days05;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import days05.board.controller.BoardController;
import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;
import days05.board.service.BoardService;

/**
 * @author jinseong
 * @date 2024. 3. 20. 오후 4:03:27
 * @subject
 * @content
 */
public class Ex02 {

	public static void main(String[] args) {
		
		// 페이징 처리
		// 1. 한 페이지에
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService boardService = new BoardService(dao);
		BoardController boardController = new BoardController(boardService);
		
		boardController.boardStart();
		
		
	} // main

} // class
 