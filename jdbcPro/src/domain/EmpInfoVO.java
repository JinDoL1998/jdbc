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
public class EmpInfoVO {
	
	private int empno;
	private String dname;
	private String ename;
	private Date hiredate;
	private double pay;
	private int grade;
	
}
