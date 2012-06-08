package processor;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import buffers.ReorderingBuffer;
import buffers.ReorderingLine;

import registers.Reg;
import reserve.ReserveStation;

public class AddExecUnit implements ExecutionUnit {
	
	private static final Integer numStations = 3;
	private List<ReserveStation> stations;
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	private List<Reg> regs;
	private ReorderingBuffer reorder;
	
	public AddExecUnit(){
		this.stations = new ArrayList<ReserveStation>();
		for(int i = 0;i<AddExecUnit.numStations;i++){
			stations.add(new ReserveStation());
		}
	}
	
	public void runStep() {
		if(currentNumClocks<current.getInstrucao().getDuration()){
			currentNumClocks++;
			return;
		}
		else{
			String op = current.getInstrucao().getALUOp();
			Integer result;
			if(op.equals("sub")){
				result = current.getVj()-current.getVk();
			}
			else if(op.equals("add")){
				result = current.getVj()+current.getVk();
			}
			else if(op.equals("nop")){
				
			}
			Integer dest = current.getDest();
			//putInBus()
			//current=null;
		}
	}
	public void chooseStation(){
		if(current == null){
			for (ReserveStation r:stations){
				if(r.isBusy()){
					current=r;
				}
			}
		}
	}
	public void loadInst(Instrucao i){
		Integer numStation = 0;
		for(int j =0;j<AddExecUnit.numStations;j++){
			if(!stations.get(j).isBusy())numStation=j;
		}
		stations.get(numStation).load(i);
	}
	
	public void setRegs(List<Reg> regs){
		this.regs=regs;
		for(ReserveStation rs:stations){
			rs.setRegs(regs);
		}
	}
	public ReorderingBuffer getReorder(){
		return reorder;
	}

	@Override
	public void clean() {
		for(ReserveStation rs:stations){
			rs.clean();
		}
		current = null;
		currentNumClocks=1;
	}

}
