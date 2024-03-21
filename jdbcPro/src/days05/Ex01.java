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
public class Ex01 {

	public static void main(String[] args) {
		
		// days05
		//	ㄴ board
		//		ㄴ domain		- DTO, VO
		//				ㄴ BoardDTO.java
		//		ㄴ persistence	- DAO
		//				ㄴ BoardDAO.java 인터페이스
		//				ㄴ BoardDAOImpl.java 클래스
		//		ㄴ service		-
		//		ㄴ controller	-
		
//		create table cstVSBoard (
//			seq int identity (1, 1) not null primary key clustered,
//			writer varchar (20) not null ,
//			pwd varchar (20) not null ,
//			email varchar (100) null ,
//			title varchar (200) not null ,
//			writedate smalldatetime not null default (getdate()),
//			readed int not null default (0),
//			mode tinyint not null ,
//			content text null
//		)
		
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService boardService = new BoardService(dao);
		BoardController boardController = new BoardController(boardService);
		
		boardController.boardStart();
		
		
	} // main

} // class

//CREATE TABLE tbl_cstVSBoard(
//	    seq NUMBER NOT NULL PRIMARY KEY -- 글 일련번호(PK)
//	    ,writer VARCHAR2(20) NOT NULL
//	    ,pwd VARCHAR2(20) NOT NULL
//	    ,email VARCHAR2(100)
//	    ,title VARCHAR2(200) NOT NULL
//	    ,writedate DATE DEFAULT SYSDATE
//	    ,readed NUMBER DEFAULT 0
//	    ,tag NUMBER(1) NOT NULL
//	    ,content CLOB 
//	);

/* 									BoardDTO
 * 사용자(user) -> BoardController.java -> BoardService.java
 * 					게시글 쓰기 : 작성		ㄴ 게시글쓰기() BoardDTO
 * 						return int ?
 * 					수정							1) DB처리		  -> BoardDAOImpl.java	-> 오라클 DB서버 연동
 * 					삭제							2) 로그기록	    		ㄴ int insert(dto)
 * 					상세보기						3)    ?
 * 					BoardDAO 인터페이스
 * 						ㄴ int increaseReaded(long seq) throws SQLException;
 * 						ㄴ BoardDTO view(long seq) throws SQLException;
 * 					목록보기()						ArrayList<> selectService()	-> ArrayList<> select();
 * 					메뉴 선택						1+2+3) 논리적인 처리작업
 * 													커밋, 롤백(트랜잭션 처리)
 *
 * */
 