package days05.board.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import days05.board.domain.BoardDTO;

public interface BoardDAO {
	
	// 1. 게시글 목록 조회 + 페이징 처리 X
	ArrayList<BoardDTO> select() throws SQLException;
	
	// 2. 게시글 쓰기
	int insert(BoardDTO dto) throws SQLException;
	
}
