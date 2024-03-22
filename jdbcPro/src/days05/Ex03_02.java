package days05;

public class Ex03_02 {

	public static void main(String[] args) {
		
		// int currentPage = 1;
		int numberPerPage = 10;
		int numberOfPageBlock = 10;
		
		// 페이징 블럭 : [1]  2 3 4 5 6 7 8 9 10 >
		
		// 1) 총 게시글 수 : 152
		// 2) 총 페이지 수 : 16
		
		int totalPage = 32;
		System.out.println(totalPage);
		
		for(int currentPage = 1; currentPage <= totalPage; currentPage++) {
			int start = (currentPage - 1)/numberOfPageBlock * numberOfPageBlock + 1;
			int end = start + numberOfPageBlock - 1;
			
			if(end > totalPage) end = totalPage+1;
			
			System.out.printf("currentPage = %d ", currentPage);
			
			if(start != 1) System.out.print(" < ");
			for(int i = start; i < end; i++) {
				if( i == currentPage ) System.out.printf(" [%d] ", i);
				else System.out.printf(" %d ", i);
					
			}
			if(end != totalPage) System.out.printf(" > ");
			System.out.println();
		}
		
	} // main
	
}
