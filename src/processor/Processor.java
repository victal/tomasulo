package processor;

import instructions.Instrucao;

import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registers.Reg;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;
import circuits.InstDecode;
import circuits.InstFetch;

public class Processor {
	
	
	private List<Reg> regs;
	private AddExecUnit adder;
//	private List<AddExecUnit> adders;
//	private List<MultExecUnit> multipliers;
	private ReorderingBuffer reorder;
	private MemoriaDados memData;
	private MemoriaInstrucao memInst;
	private InstFetch IF;
	private InstDecode ID;
	
	public void runStep(){
		/*IF e ID*/
		
		ID.decodeInst(); // Coloca no Buffer de Reordenação tbm;
		IF.setNewPC(ID.getNewPC());

		emitirInst();
		
		/* Execução e colocação no reorderingBuffer */
		//for i in  execution units
		// i.choosestation
		// i.runstep()
		//Executar instruções nas unidades de execução (A Unidade se encarrega de pegar uma instrução da estação de reserva  
		reorder.consolidate(regs);//pode alterar o PC
		ID.putNextInst(IF.getnextInst());
	}
	
	
	private void emitirInst() {
		Instrucao i = ID.getDecodedInst();
		String inome = i.getNome();
		if(inome.equals("mul")||inome.equals("div")){
			//put in MulExecUnit
		}
		else if(inome.equals("lw")||inome.equals("sw")){
			//put in MemExecUnit
		}
		else{
			//put in AddExecUnit
		}
		reorder.updateState(i,ReorderingLine.EMITIDA);
		
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
	public void cleanExecutionUnits(){asjdlgfhaodslkfhdslkf;}
}
