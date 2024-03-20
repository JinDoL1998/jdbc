package days05.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import days05.board.domain.BoardDTO;
import domain.EmpVO;

public class BoardDAOImpl implements BoardDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자를 통한 DI
	public BoardDAOImpl(Connection conn) {
		this.conn = conn;
	}
	
	// Setter를 통한 DI
	public void setConn(Connection conn) {
		this.conn = conn;
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
	}

	@Override
	public int insert(BoardDTO dto) throws SQLException {
		
		return 0;
	}

}
