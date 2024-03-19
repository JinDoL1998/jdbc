package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptEmpSalgradeVO {
	
	private int deptno;
	private String dname;
	private int empno;
	private String ename;
	private int sal;
	private int grade;
	private int losal;
	private int hisal;
	
}
