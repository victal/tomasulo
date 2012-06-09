package processor;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;

import registers.Reg;
import reserve.ReserveStation;
import circuits.CommonBus;
import instructions.Instrucao;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;

public class MemExecUnit implements ExecutionUnit {
	
	private static final Integer numStations = 3;
	private List<ReserveStation> stations;//OK
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	private ReorderingBuffer reorder;//OK
	private CommonBus bus;//OK
	private MemoriaDados mem;//OK
	
	public MemExecUnit(){
		this.stations = new ArrayList<ReserveStation>();
		for(int i = 0;i<MemExecUnit.numStations;i++){
			ReserveStation rs = new ReserveStation();
			rs.setExecUnit(this);
			stations.add(rs);
		}
	}
	
	@Override
	public void chooseStation() {
		if(current == null){
			for (ReserveStation r:stations){
				if(r.isBusy() && r.getQj()==null && r.getQk()==null){
					boolean hasStore = reorder.hasStoreBefore(r.getInstrucao());
					if(r.getInstrucao().getNome().equals("lw")&& !hasStore){
						current=r;
						return;
					}
					boolean hasMem = reorder.hasMemInstBefore(r.getInstrucao());
					if(!hasMem){
						current=r;
						return;
					}
				}
			}
		}
	}

	@Override
	public void runStep() {
		this.chooseStation();
		if(current==null)return;
		if(currentNumClocks<current.getInstrucao().getDuration()){
			currentNumClocks++;
			return;
		}
		String op = current.getInstrucao().getNome();
		Integer result=null;
		if(op.equals("lw")){
			result = mem.getValue(current.getA()+current.getVj());
		}
		else {
			reorder.setAdress(current.getDest(),current.getA()+current.getVj());
			result = current.getVk();
		}
		reorder.updateState(current.getInstrucao(), ReorderingLine.GRAVAR);
		if(bus.isBusy()) return;
		Integer dest = current.getDest();
		bus.sendData(current.getInstrucao(), dest, result);
		current.unsetBusy();
		current=null;
	}

	@Override
	public void clean() {
		for(ReserveStation rs:stations){
			rs.clean();
		}
		current = null;
		currentNumClocks=1;
	}
	
	@Override
	public void loadInst(Instrucao i) {
		Integer numStation = 0;
		for(int j =0;j<MemExecUnit.numStations;j++){
			if(!stations.get(j).isBusy())numStation=j;
		}
		stations.get(numStation).load(i);

	}

	@Override
	public ReorderingBuffer getReorder() {
		return reorder;
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
	
	public void setRegs(List<Reg> regs){
		for(ReserveStation rs:stations){
			rs.setRegs(regs);
		}
	}
	public void setBus(CommonBus b){
		this.bus = b;
	}
	public void setReorder(ReorderingBuffer r) {
		this.reorder=r;
	}

	public void setDataMem(MemoriaDados dataMemory) {
		this.mem=dataMemory;
	}
}
