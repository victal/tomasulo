package processor;

import java.util.List;

import reserve.ReserveStation;

public class AddExecUnit implements ExecutionUnit {
	public final static int numClocks = 1;
	private List<ReserveStation> stations;
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	public Integer runStep() {
		if(currentNumClocks<AddExecUnit.numClocks)
			return null;
		else{
		//	if(current.
		}
	}
	public void chooseStation(){
		if(current == null)
			for (ReserveStation r:stations){
	//			if(r.
			}
	}

}
