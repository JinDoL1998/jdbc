package days05;

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
		//		ㄴ domain
		//				ㄴ BoardDTO.java
		//		ㄴ persistence
		//				ㄴ BoardDAO.java 인터페이스
		//				ㄴ BoardDAOImpl.java 클래스
		//		ㄴ service
		//		ㄴ controller
		
//		create table cstVSBoard (
//				  seq int identity (1, 1) not null primary key clustered,
//				  writer varchar (20) not null ,
//				  pwd varchar (20) not null ,
//				  email varchar (100) null ,
//				  title varchar (200) not null ,
//				  writedate smalldatetime not null default (getdate()),
//				  readed int not null default (0),
//				  mode tinyint not null ,
//				  content text null
//				)
		
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