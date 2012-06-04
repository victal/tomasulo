package processor;

import java.util.List;

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
	
	public void runStep(){
		//IF
		//ID
		//Emitir instrução - Colocar na unidade de execuão e no buffer de reordenaçao
		//for i in  execution units
		// i.choosestation
		// i.runstep()
		//Executar instruções nas unidades de execução (A Unidade se encarrea de pegar uma instrução da estação de reserva  
		//Consolidar instruções "consolidáveis" e retirar do buffer de reordenação
	}
	
	
	public ReorderingBuffer getReorder() {
		return reorder;
	}

	public List<Reg> getRegs() {
		return regs;
	}
}
