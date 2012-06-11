package processor;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registers.Reg;
import reserve.ReserveStation;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;
import circuits.CommonBus;
import circuits.InstDecode;
import circuits.InstFetch;

public class Processor {
	
	
	private List<Reg> regs;//OK
	private AddExecUnit adder;
	private MultExecUnit multiplier;
	private MemExecUnit memUnit;
	private ReorderingBuffer reorder;
	private MemoriaDados memData;//OK
	private MemoriaInstrucao memInst;//OK
	private InstFetch IF;//OK
	private InstDecode ID;//OK
	private CommonBus bus;
	private List<Instrucao> idBuffer = new ArrayList<Instrucao>();
	private Integer clock =0;
	
	public void runStep(){
		/*IF e ID*/
		ID.decodeInst(); 
		IF.setNewPC(ID.getNewPC());
		emitirInst();// Coloca no Buffer de Reordenação tbm;
		/* Execução e colocação no reorderingBuffer */
		
		multiplier.runStep();

		memUnit.runStep();
		
		adder.runStep();

		//Executar instruções nas unidades de execução (A Unidade se encarrega de pegar uma instrução da estação de reserva  
		reorder.consolidate(regs);//pode alterar o PC
		ID.putNextInst(IF.getnextInst());
		bus.unsetBusy();
		clock++;
	}
	
	public boolean isFinished(){
		//IF
		boolean IFDone = IF.getnextInst()==null;
		IFDone = IFDone || memInst.get(IF.getPC())==null;
		//ID
		boolean IDDone = ID.isEmpty();
		//ID buffer
		boolean idBufferEmpty = idBuffer.isEmpty();
		if(!idBufferEmpty)
			for(Instrucao i:idBuffer){
				if(!i.getNome().equals("nop"))
					idBufferEmpty=false;
			}
		//Reorder => Exec Units também
		boolean reorderEmpty = reorder.isEmpty();
		return IFDone && IDDone && idBufferEmpty && reorderEmpty;
	}
	private void emitirInst() {
		if(ID.getDecodedInst()!=null)
			idBuffer.add(ID.getDecodedInst());
		if(idBuffer.isEmpty())return;
		if(reorder.isFull())return;
		Instrucao i = idBuffer.get(0);
		String inome = i.getNome();
		Integer dest = null;
		if(inome.equals("mul")||inome.equals("div")){
			if(!multiplier.isFull()){
				dest=multiplier.loadInst(i);
				idBuffer.remove(0);
			}
		}
		else if(inome.equals("lw")||inome.equals("sw")){
			if(!memUnit.isFull()){
				dest=memUnit.loadInst(i);
				idBuffer.remove(0);
			}
		}
		else{
			if(!adder.isFull()){
				dest=adder.loadInst(i);
				idBuffer.remove(0);
			}
		}
		if(dest!=null){reorder.updateState(dest,ReorderingLine.EMITIDA);}
	}


	public ReorderingBuffer getReorder() {
		return reorder;
	}
	public List<Reg> getRegs() {
		return regs;
	}
	public MemoriaDados getDataMemory() {
		return memData;
	}
	public void setMemInstruction(MemoriaInstrucao inst) {
		this.memInst = inst;
	}
	public void setMemData(MemoriaDados data) {
		this.memData = data;
	}
	public void setIF(InstFetch IF){
		this.IF=IF;
	}
	public void setReorder(ReorderingBuffer r) {
		this.reorder=r;
	}
	public void setRegs(List<Reg> r) {
		this.regs = r;
	}
	public void setID(InstDecode id) {
		this.ID=id;
	}
	public InstFetch getIF(){
		return IF;
	}
	public InstDecode getID(){
		return ID;
	}
	public void setBus(CommonBus bus){
		this.bus=bus;
	}
	public void cleanExecutionUnits(){
		memUnit.clean();
		multiplier.clean();
		adder.clean();
		for(Reg r:regs){
			r.setQi(null);
		}
		idBuffer.clear();
	}


	public void setMemEU(MemExecUnit mem) {
		this.memUnit=mem;
	}


	public void setMultEU(MultExecUnit mult) {
		this.multiplier=mult;
	}


	public void setAddEU(AddExecUnit add) {
		this.adder=add;
	}

	public List<ReserveStation> getReserveStations() {
		List<ReserveStation> rs = new ArrayList<ReserveStation>();
		rs.addAll(adder.getStations());
		rs.addAll(multiplier.getStations());
		rs.addAll(memUnit.getStations());
		return rs;
	}
	public Integer getClock(){
		return clock;
	}
}
