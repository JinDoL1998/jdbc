package domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptEmpVO {
	
	private int deptno;
	private String dname;
	private int empno;
	private String ename;
	private Date hiredate;
	private double pay;
	
}
