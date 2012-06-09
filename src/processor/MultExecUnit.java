package processor;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import reserve.ReserveStation;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;
import circuits.CommonBus;

public class MultExecUnit implements ExecutionUnit {

	private static final Integer numStations = 5;
	private List<ReserveStation> stations;
	private ReserveStation current = null;
	private int currentNumClocks = 1;
	private ReorderingBuffer reorder;
	private CommonBus bus;
	
	public MultExecUnit(){
		this.stations = new ArrayList<ReserveStation>();
		for(int i = 0;i<MultExecUnit.numStations;i++){
			stations.add(new ReserveStation());
		}
	}
	
	@Override
	public void chooseStation() {
		if(current == null){
			for (ReserveStation r:stations){
				if(r.isBusy()){
					current=r;
				}
			}
		}
	}

	@Override
	public void runStep() {
		if(currentNumClocks<current.getInstrucao().getDuration()){
			currentNumClocks++;
			return;
		}
		String op = current.getInstrucao().getALUOp();
		Integer result=null;
		if(op.equals("mul")){
			result = current.getVj()*current.getVk();
		}
		else if(op.equals("div")){
			result = current.getVj()/current.getVk();
		}
		reorder.updateState(current.getInstrucao(), ReorderingLine.GRAVAR);
		if(bus.isBusy()) return;
		Integer dest = current.getDest();
		bus.sendData(current.getInstrucao(), dest, result);
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
		for(int j =0;j<MultExecUnit.numStations;j++){
			if(!stations.get(j).isBusy())numStation=j;
		}
		stations.get(numStation).load(i);
	}

	@Override
	public ReorderingBuffer getReorder() {
		return reorder;
	}

	@Override
	public void updateStations(Integer reorderIndex, Integer value) {
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
}
