package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.SalgradeVO;

public class Ex03 {

	public static void main(String[] args) {
		
		String sql = "SELECT grade, losal, hisal "
	            + " , COUNT (*) cnt "
	            + " FROM salgrade s JOIN emp e ON e.sal BETWEEN s.losal AND s.hisal"
	            + " GROUP BY grade, losal, hisal"
	            + " ORDER BY grade";
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      SalgradeVO vo = null;
	      ArrayList<SalgradeVO> list = null;
	      
	      conn = DBConn.getConnection();
	      
	      try {
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            list = new ArrayList<SalgradeVO>();
	            
	         }
	         do {
	            
	            vo = new SalgradeVO(
	                   rs.getInt("grade")
	                  ,rs.getInt("losal")
	                  ,rs.getInt("hisal")
	                  ,rs.getInt("cnt")
	                  );
	            list.add(vo);
	         } while (rs.next());
	         dispSalgrade(list);
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            pstmt.close();
	            conn.close();
	            
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	         
	      }
	      

	   }

	   private static void dispSalgrade(ArrayList<SalgradeVO> list) {
	      Iterator<SalgradeVO> ir = list.iterator();
	      while (ir.hasNext()) {
	         SalgradeVO vo = (SalgradeVO) ir.next();
	         System.out.printf("%d등급 ( %d~%d ) - %d명\n", vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
	      }
	   }


} // class
