package processor;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registers.Reg;
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
	int i =0;
	public void runStep(){
		/*IF e ID*/
		System.err.println(i);
		ID.decodeInst(); 
		IF.setNewPC(ID.getNewPC());
		System.err.println("emissão");
		emitirInst();// Coloca no Buffer de Reordenação tbm;
		System.err.println("depois da emissão");
		/* Execução e colocação no reorderingBuffer */
		
		multiplier.runStep();
		
		memUnit.runStep();
		
		adder.runStep();

		//Executar instruções nas unidades de execução (A Unidade se encarrega de pegar uma instrução da estação de reserva  
		reorder.consolidate(regs);//pode alterar o PC
		ID.putNextInst(IF.getnextInst());
		bus.unsetBusy();
		i++;
	}
	
	
	private void emitirInst() {
		if(ID.getDecodedInst()!=null)
			idBuffer.add(ID.getDecodedInst());
		if(idBuffer.isEmpty())return;
		Instrucao i = idBuffer.get(0);
		String inome = i.getNome();
		if(inome.equals("mul")||inome.equals("div")){
			if(!multiplier.isFull()){
				multiplier.loadInst(i);
				idBuffer.remove(0);
			}
		}
		else if(inome.equals("lw")||inome.equals("sw")){
			if(!memUnit.isFull()){
				memUnit.loadInst(i);
				idBuffer.remove(0);
			}
		}
		else{
			if(!adder.isFull()){
				adder.loadInst(i);
				idBuffer.remove(0);
			}
		}
		if(idBuffer.isEmpty()||!i.equals(idBuffer.get(0)))reorder.updateState(i,ReorderingLine.EMITIDA);
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
}
