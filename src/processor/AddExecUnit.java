package processor;

import java.util.List;

import reserve.ReserveStation;

public class AddExecUnit implements ExecutionUnit {
	
	private List<ReserveStation> stations;
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	public Integer runStep() {
		if(currentNumClocks<current.getInstrucao().getDuration())
			return null;
		else{
			String op = current.getInstrucao().getALUOp();
			Integer result;
			if(op.equals("sub")){
				result = current.getVj()-current.getVk();
			}
			else{
				result = current.getVj()+current.getVk();
			}//tem outros casos ainda			
			Integer dest = current.getDest();
			
		}
	}
	public void chooseStation(){
		if(current == null)
			for (ReserveStation r:stations){
	//			if(r.
			}
	}

}
