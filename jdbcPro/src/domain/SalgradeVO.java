package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalgradeVO {
	
	private int grade;
	private int losal;
	private int hisal;
	
	private int cnt;
	
}
