package days05.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import days05.board.domain.BoardDTO;
import domain.EmpVO;

public class BoardDAOImpl implements BoardDAO {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	Scanner scanner = new Scanner(System.in);

	// 1. 생성자를 통한 DI
	public BoardDAOImpl(Connection conn) {
		this.conn = conn;
	}

	// 2. Setter를 통한 DI
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// 2-2 Getter
	public Connection getConn() {
		return this.conn;
	}

	@Override
	public ArrayList<BoardDTO> select() throws SQLException {
		ArrayList<BoardDTO> list = null;

		long seq;
		String title;
		String writer;
		String email;
		Date writeDate;
		int readed;

		String sql = "SELECT seq, title, writer, email, writeDate, readed "
				+ "FROM tbl_cstvsboard "
				+ "ORDER BY seq DESC";
		try {

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getLong(1);
					title = rs.getString(2);
					writer = rs.getString(3);
					email = rs.getString(4);
					writeDate = rs.getDate(5);
					readed = rs.getInt(6);

					BoardDTO dto = BoardDTO.builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.writeDate(writeDate)
							.readed(readed)
							.build();
					list.add(dto);

				} while (rs.next());
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


		return list;
	} // select


	@Override
	public int insert(BoardDTO dto) throws SQLException {

		// writedate  default  sysdate
		// readed     default  0
		int rowCount = 0;
		String sql = "INSERT INTO tbl_cstvsboard "
				+ " (seq, writer, pwd, email, title, tag, content ) "
				+ "VALUES "
				+ " (seq_tbl_cstvsboard.NEXTVAL, ?, ?, ?, ?, ?, ? ) ";

		this.pstmt = conn.prepareStatement(sql);      
		pstmt.setString(1,  dto.getWriter() );
		pstmt.setString(2,  dto.getPwd() );
		pstmt.setString(3,  dto.getEmail() );
		pstmt.setString(4,  dto.getTitle() );
		pstmt.setInt(5, dto.getTag());
		pstmt.setString(6,  dto.getContent() );
		rowCount = this.pstmt.executeUpdate();
		this.pstmt.close();

		return rowCount;

	} // insert

	@Override
	public int increaseReaded(long seq) throws SQLException {
		String sql = "UPDATE tbl_cstvsboard "
				+ "SET readed = readed + 1 "
				+ "WHERE seq = ? ";
		int rowCount = 0;

		this.pstmt = conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();

		return rowCount;
	}

	@Override
	public BoardDTO view(long seq) throws SQLException {

		BoardDTO dto = null;

		String title;
		String writer;
		String email;
		Date writeDate;
		int readed;
		String content;

		String sql = "SELECT seq, title, writer, email, writeDate, readed, content "
				+ "FROM tbl_cstvsboard "
				+ "WHERE seq = ?";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, seq);
			rs = pstmt.executeQuery();



			if(rs.next()) {
				seq = rs.getLong(1);
				title = rs.getString(2);
				writer = rs.getString(3);
				email = rs.getString(4);
				writeDate = rs.getDate(5);
				readed = rs.getInt(6);
				content = rs.getString(7);

				dto = BoardDTO.builder()
						.seq(seq)
						.title(title)
						.writer(writer)
						.writeDate(writeDate)
						.readed(readed)
						.content(content)
						.build();
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


		return dto;

	}

	@Override
	public int delete(long seq) throws SQLException {

		int rowCount = 0;
		String sql = "DELETE FROM tbl_cstvsboard "
				+ "WHERE seq = ? ";

		this.pstmt = conn.prepareStatement(sql);
		this.pstmt.setLong(1, seq);
		rowCount = this.pstmt.executeUpdate();

		this.pstmt.close();
		return rowCount;
	}

	@Override
	public int update(BoardDTO dto) throws SQLException {

		int rowCount = 0;
		String sql = "UPDATE tbl_cstvsboard "
				+ "SET email=?, title=?, content=? "
				+ "WHERE seq = ? ";

		this.pstmt = conn.prepareStatement(sql);      
		pstmt.setString(1,  dto.getEmail() );
		pstmt.setString(2,  dto.getTitle() );
		pstmt.setString(3,  dto.getContent() );
		pstmt.setLong(4,  dto.getSeq() );
		rowCount = this.pstmt.executeUpdate();
		this.pstmt.close();

		return rowCount;

	}

	@Override
	public ArrayList<BoardDTO> search(int searchCondition, String searchWord) {
		ArrayList<BoardDTO> list = null;

		long seq;
		String title;
		String writer;
		String email;
		Date writeDate;
		int readed;

		String sql = "SELECT seq, title, writer, email, writeDate, readed "
				+ "FROM tbl_cstvsboard ";

		// WHERE 검색조건 STARTswitch ( searchCondition) {
		switch ( searchCondition) {
		case 1:  // 제목
			sql += " WHERE REGEXP_LIKE( title, ?, 'i') ";
			break;
		case 2: // 내용
			sql += " WHERE REGEXP_LIKE( content, ?, 'i') ";
			break;
		case 3: // 작성자
			sql += " WHERE REGEXP_LIKE( writer, ?, 'i') ";
			break;         
		case 4: // 제목 + 내용 
			sql += " WHERE REGEXP_LIKE( title, ?, 'i') OR  REGEXP_LIKE( content, ?, 'i') ";
			break;
		} // switch
		
		sql += "ORDER BY seq ASC ";
		try {

			pstmt = conn.prepareStatement(sql);
			this.pstmt.setString(1, searchWord);
			if (searchCondition == 4) {
				this.pstmt.setString(2, searchWord);
			}
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getLong(1);
					title = rs.getString(2);
					writer = rs.getString(3);
					email = rs.getString(4);
					writeDate = rs.getDate(5);
					readed = rs.getInt(6);

					BoardDTO dto = BoardDTO.builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.writeDate(writeDate)
							.readed(readed)
							.build();
					list.add(dto);

				} while (rs.next());
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


		return list;
	}

	@Override
	public ArrayList<BoardDTO> popular() throws SQLException {
		
		ArrayList<BoardDTO> list = null;
		long seq;
		String title;
		String writer;
		String email;
		Date writeDate;
		int readed;
		
		String sql = "SELECT seq, title, writer, email, writeDate, readed "
				+ "FROM tbl_cstvsboard "
				+ "WHERE readed >= 1 "
				+ "ORDER BY readed DESC ";
		try {

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getLong(1);
					title = rs.getString(2);
					writer = rs.getString(3);
					email = rs.getString(4);
					writeDate = rs.getDate(5);
					readed = rs.getInt(6);

					BoardDTO dto = BoardDTO.builder()
							.seq(seq)
							.title(title)
							.writer(writer)
							.email(email)
							.writeDate(writeDate)
							.readed(readed)
							.build();
					list.add(dto);

				} while (rs.next());
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


		return list;
	}

} // class
