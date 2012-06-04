package processor;

import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;

import buffers.ReorderingBuffer;

import registers.Reg;

public class Processor {
	private static Processor _instance;
	public static Processor getProcessor(){
		if(Processor._instance==null){
			Processor._instance = new Processor();
		}
		return _instance;
	}
	
	private List<Reg> regs;
//	private List<AddExecUnit> adders;
//	private List<MultExecUnit> multipliers;
	private ReorderingBuffer reorder;
	private MemoriaDados memData;
	private MemoriaInstrucao memInst;
	
	public void runStep(){
		/*Emissão, IF e ID*/
		
		//ID.decodeInst();
		//IF.setPC(ID.getNewPC());
		//Instrucao i = ID.getDecodedInst();
		//ID.putNextInst(IF.getNextInst());
		//String eu = i.getEUType();
		//if(eu.equals("add")){}
		//else if(eu.equals("mul")){}
		//else{}
		
		/* Execução e colocação no reorderingBuffer */
		//for i in  execution units
		// i.choosestation
		// i.runstep()
		//Executar instruções nas unidades de execução (A Unidade se encarrega de pegar uma instrução da estação de reserva  
		//reorder.consolidate();
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
}
