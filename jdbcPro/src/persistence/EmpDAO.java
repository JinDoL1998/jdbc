package persistence;

import java.util.ArrayList;

import domain.EmpVO;

public interface EmpDAO {
	
	// 1. 모든 사원정보 조회 추상메서드
	public abstract ArrayList<EmpVO> getEmpSelect();
	
	// 2. 사원 추가 추상메서드
	int addEmp(EmpVO evo);
	
	// 3. 사원 수정
	int updateEmp(EmpVO evo);
	
	// 4. 사원 삭제
	int deleteEmp(int empno);
	// int deleteEmp(int[] empnos);
	// int deleteEmp(ArrayList<Integer> empnos);
	
	// 5. 사원 검색
	ArrayList<EmpVO> searchEmp(int searchCategory, String searchWord);
	
	// 6. 한 사원 정보
	EmpVO getEmp(int empno);
	
} // interface
