package processor;

import java.util.List;

import registers.Reg;
//import reserve.ReserveStation;

public interface ExecutionUnit {
	
	public void chooseStation();
	public Integer runStep();
	public List<Reg> getRegs();
	
}
