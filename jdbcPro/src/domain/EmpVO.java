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
public class EmpVO {

	
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private Date hiredate;
//	private LocalDateTime hiredate;
	private double sal;
	private double comm;
	private int deptno;

	
}
