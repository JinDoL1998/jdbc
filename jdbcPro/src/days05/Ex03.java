package days05;

public class Ex03 {

	public static void main(String[] args) {
		
		// int currentPage = 1;
		int numberPerPage = 10;
		
		// WHERE no BETWEEN start AND end
		int start = 1, end = 10;
		for(int currentPage = 1; currentPage <= 5; currentPage++) {
			end = currentPage * numberPerPage;
			start = end - (numberPerPage-1);
			
//			1 1		10
//			2 11	20
//			3 21	30 
//			4 31	40
//			5 41	50
			System.out.printf("currentPage = %d no BETWEEN %d AND %d \n"
									, currentPage, start, end);
		}
		
	}

}
