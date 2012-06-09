package circuits;

import instructions.Instrucao;

import java.util.List;

import processor.ExecutionUnit;
import buffers.ReorderingBuffer;

public class CommonBus {
	private List<ExecutionUnit> execUnits;
	private ReorderingBuffer buffer;
	private boolean busy = false;
	
	public boolean isBusy(){
		return busy;
	}
	public void unsetBusy(){
		busy=false;
	}
	public void sendData(Instrucao i, Integer reorderIndex, Integer value){
		this.busy=true;
		if(!i.isBranch()&&!i.isJump() && reorderIndex!=null && !i.getNome().equals("sw")){
			for(ExecutionUnit eu: execUnits){
				eu.updateStations(reorderIndex,value);
			}
		}
		buffer.store(reorderIndex,value);
	}
}
