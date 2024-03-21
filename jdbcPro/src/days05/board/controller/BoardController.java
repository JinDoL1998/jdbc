package days05.board.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.service.BoardService;

public class BoardController {

	private Scanner scanner = null;
	private int selectedNumber;
	private BoardService boardService;



	public BoardController() {
		super();
		this.scanner = new Scanner(System.in);
	}



	// 1. 생성자를 통한 DI
	public BoardController(BoardService boardService) {
		this();
		this.boardService = boardService;
	}

	// 게시판 기능을 사용
	public void boardStart() {
		while(true) {
			dispMenu();
			choiceMenu();
			processMenu();
		}
	}



	private void processMenu() {

		switch (this.selectedNumber) {
		case 1:// 새글   
			새글쓰기();
			break;
		case 2:// 목록
			목록보기();
			break;
		case 3:// 보기
			상세보기();
			break;
		case 4:// 수정
			수정하기();
			break;
		case 5:// 삭제
			삭제하기();
			break;
		case 6:// 검색
			검색하기();
			break;
		case 7:// 종료   
			exit();
			break; 
		case 8:
			인기게시판();
			break;
		} // switch

		pause();
	} // processMenu()



	private void 인기게시판() {

		ArrayList<BoardDTO> list = this.boardService.popularService();

		// 뷰(View)-출력담당
		System.out.println("\t\t\t  게시판");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%-7s %-30s %-8s %-10s %-5s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-----------------------------------------------------------------------------------");
		if(list == null) {
			System.out.println("\t\t> 게시글 존재 X");  
		}
		else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%-7d %-30s %-8s %-10s %-5d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWriteDate(),
						dto.getReaded());  
			}
		}
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("\t\t\t [1] >");
		System.out.println("-----------------------------------------------------------------------------------");
		
	}



	private void 검색하기() {

		System.out.print(
				"> 검색 조건 : 제목(1) , 내용(2), 작성자(3), 제목+내용(4) 선택  ? ");
		int searchCondition = this.scanner.nextInt();
		System.out.print("> 검색어 입력 ? ");
		String searchWord = this.scanner.next();
		
		// 목록보기 코딩 복+붙
		System.out.print("> 현재 페이지번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();
		
		ArrayList<BoardDTO> list = this.boardService.searchService(searchCondition, searchWord);

		// 뷰(View)-출력담당
		System.out.println("\t\t\t  게시판");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%-7s %-30s %-8s %-10s %-5s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-----------------------------------------------------------------------------------");
		if(list == null) {
			System.out.println("\t\t> 게시글 존재 X");  
		}
		else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%-7d %-30s %-8s %-10s %-5d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWriteDate(),
						dto.getReaded());  
			}
		}
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("\t\t\t [1] 2 3 >");
		System.out.println("-----------------------------------------------------------------------------------");

	}



	private void 삭제하기() {

		System.out.print("> 삭제하는 글번호를 입력 ? ");
		int seq = this.scanner.nextInt();
		int rowCount = this.boardService.deleteService(seq);
		if(rowCount == 1) {
			System.out.println("> 게시글 삭제 완료!!");
		}

	}



	private void 수정하기() {

		System.out.print("> 수정할 글번호를 입력 ? ");
		int seq = this.scanner.nextInt();
		// 1. 원래 게시글 정보 출력
		// BoardDTO dto = this.boardService.viewService(seq);

		BoardDTO dto = null;
		try {
			dto = this.boardService.getDao().view(seq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 뷰(view) : 출력 담당 객체
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter());
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded());
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle());
		System.out.println("\tㅁ. 글내용 : " + dto.getContent());
		System.out.println("\tㅂ. 작성일 : " + dto.getWriteDate());

		// 2. 수정할 값 입력받는 코딩
		System.out.print("> 1. 이메일 입력 ? ");
		String email = scanner.next();
		System.out.print("> 2. 제목 입력 ? ");
		String title = scanner.next();
		System.out.print("> 3. 내용 입력 ? ");
		String content = scanner.next();

		//
		dto = BoardDTO.builder()
				.seq(seq)
				.email(email)
				.title(title)
				.content(content)
				.build();

		int rowCount = this.boardService.updateService(dto);

		if(rowCount == 1) {
			System.out.printf("> %d번 게시글 수정 완료!!!\n", seq);
		}

	}



	private void 상세보기() {

		System.out.print("> 보고자하는 글 번호를 입력 ? ");
		int seq = this.scanner.nextInt();

		BoardDTO dto = this.boardService.viewService(seq);

		if(dto == null) {
			System.out.println("> 보고자 하는 게시글이 존재 X");
			return;
		}

		// 뷰(view) : 출력 담당 객체
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter());
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded());
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle());
		System.out.println("\tㅁ. 글내용 : " + dto.getContent());
		System.out.println("\tㅂ. 작성일 : " + dto.getWriteDate());

		System.out.println("\t\n [수정] [삭제] [목록(home)]");

	}


	// 페이징 처리 필요한 필드 선언
	private int currentPage = 1;
	
	private void 목록보기() {

		System.out.print("> 현재 페이지 번호를 입력 ? ");
		this.currentPage = this.scanner.nextInt();

		ArrayList<BoardDTO> list = this.boardService.selectService();

		// 뷰(View)-출력담당
		System.out.println("\t\t\t  게시판");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.printf("%-7s %-30s %-8s %-10s %-5s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-----------------------------------------------------------------------------------");
		if(list == null) {
			System.out.println("\t\t> 게시글 존재 X");  
		}
		else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%-7d %-30s %-8s %-10s %-5d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWriteDate(),
						dto.getReaded());  
			}
		}
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("\t\t\t [1] 2 3 4 5 6 7 8 9 10 >");
		System.out.println("-----------------------------------------------------------------------------------");
	}



	private void 새글쓰기() {

		System.out.print("> writer, pwd, email, title, tag, content 입력 ? ");
		String [] datas = this.scanner.nextLine().split("\s*,\s*");
		String writer = datas[0];
		String pwd = datas[1];
		String email = datas[2];
		String title = datas[3];
		int tag = Integer.parseInt(datas[4]);
		String content = datas[5];

		BoardDTO dto = BoardDTO.builder()
				.writer(writer)
				.pwd(pwd)
				.email(email)
				.title(title)
				.tag(tag)
				.content(content)
				.build();
		int rowCount = this.boardService.insertService(dto);

		if(rowCount == 1) {
			System.out.println("> 새글 쓰기 완료 !!!");
		}
	}



	private void choiceMenu() {

		System.out.print("> 메뉴 선택하세요 ? ");
		this.selectedNumber = this.scanner.nextInt();

		this.scanner.nextLine();

	} // choiceMenu()

	private void dispMenu() {

		String [] menu = {"새글", "목록", "보기", "수정", "삭제", "검색", "종료", "인기"};
		System.out.println("[ 메뉴 ]");
		for(int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\t", i+1, menu[i]);
		}
		System.out.println();

	} // dispMenu()

	private void pause() {
		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) { 
			e.printStackTrace();
		}
	} // pause()

	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	} // exit()

} // class
