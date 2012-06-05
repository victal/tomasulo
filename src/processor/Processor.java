package processor;

import java.util.List;

import circuits.InstDecode;
import circuits.InstFetch;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;

import buffers.ReorderingBuffer;

import registers.Reg;

public class Processor {
	private static Processor _instance;
	public static Processor getProcessor(){
		return _instance;
	}
	public static Processor makeProcessor(MemoriaInstrucao inst, MemoriaDados data){
		if(Processor._instance==null){
			Processor._instance = ProcessorBuilder.build(inst,data);
		}
		return _instance;
	}
	
	private List<Reg> regs;
//	private List<AddExecUnit> adders;
//	private List<MultExecUnit> multipliers;
	private ReorderingBuffer reorder;
	private MemoriaDados memData;
	private MemoriaInstrucao memInst;
	private InstFetch IF;
	private InstDecode ID;
	
	public void runStep(){
		/*IF e ID*/
		
		//ID.decodeInst();
		//IF.setNewPC(ID.getNewPC());
		//Instrucao i = ID.getDecodedInst();
		ID.putNextInst(IF.getnextInst());
				
		/*Colocar na estação de reserva*/
		//String eu = i.getEUType();
		//if(eu.equals("add")){}
		//else if(eu.equals("mul")){}
		//else{}
		
		/* Execução e colocação no reorderingBuffer */
		//for i in  execution units
		// i.choosestation
		// i.runstep()
		//Executar instruções nas unidades de execução (A Unidade se encarrega de pegar uma instrução da estação de reserva  
		reorder.consolidate(regs);
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
}
