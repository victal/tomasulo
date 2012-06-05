package processor;
//import reserve.ReserveStation;
//import java.util.List;

public interface ExecutionUnit {
	
	public void chooseStation();
	public Integer runStep();
	public List<Reg> getRegs();
	
}
