package processor;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import registers.Reg;
import reserve.ReserveStation;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;
import circuits.CommonBus;

public class AddExecUnit implements ExecutionUnit {
	
	private static final Integer numStations = 3;
	private List<ReserveStation> stations;//OK
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	private ReorderingBuffer reorder;//OK
	private CommonBus bus;//OK
	
	public AddExecUnit(){
		this.stations = new ArrayList<ReserveStation>();
		for(int i = 0;i<AddExecUnit.numStations;i++){
			ReserveStation rs = new ReserveStation();
			rs.setExecUnit(this);
			stations.add(rs);
		}
	}
	
	public void runStep() {
		this.chooseStation();
		if(current==null)return;
		if(currentNumClocks<current.getInstrucao().getDuration()){
			currentNumClocks++;
			return;
		}
		else {
			String op = current.getInstrucao().getALUOp();
			Integer result=null;
			if(op.equals("sub")){
				result = current.getVj()-current.getVk();
			}
			else if(op.equals("add")){
				result = current.getVj()+current.getVk();
			}
			reorder.updateState(current.getInstrucao(), ReorderingLine.GRAVAR);
			
			if(bus.isBusy()) return;
			
			Integer dest = current.getDest();
			Instrucao inst = current.getInstrucao();
			if(inst.isJump())
				result = current.getA();
			else if(inst.isBranch()){
				if(inst.getNome().equals("ble")){
					if(result<0)result = null;
					else result = current.getA();
				}
				if(inst.getNome().equals("bne")){
					if(result!=0)result = null;
					else result = current.getA();
				}
				if(inst.getNome().equals("beq")){
					if(result==0) result = null;
					else result = current.getA();
				}
			}
			bus.sendData(inst, dest, result);
			current.unsetBusy();
			current=null;
		}
		
	}
	public void chooseStation(){
		if(current == null){
			for (ReserveStation r:stations){
				if(r.isBusy() && r.getQj()==null && r.getQk()==null){
					System.err.println(r.getInstrucao().toString() + " "+r.getDest());
					current=r;
					return;
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
	public void updateStations(Integer reorderIndex, Integer value){
		for(ReserveStation rs:stations){
			if(rs.getQj()==reorderIndex){
				rs.setQj(null);
				rs.setVj(value);
			}
			if(rs.getQk()==reorderIndex){
				rs.setQk(null);
				rs.setVk(value);
			}
		}
	}

	@Override
	public boolean isFull() {
		for(ReserveStation rs:stations){
			if(!rs.isBusy()) return false;
		}
		return true;
	}

	public void setBus(CommonBus b) {
		this.bus=b;
		
	}

	public void setReorder(ReorderingBuffer r) {
		this.reorder=r;
	}

}
