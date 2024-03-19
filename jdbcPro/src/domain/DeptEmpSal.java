package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptEmpSal {
	
	private int empno;
	private String ename;
	private int deptno;
	private String dname;
	private double sal;
	
	
	
}
