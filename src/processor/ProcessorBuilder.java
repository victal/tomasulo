package processor;

import buffers.PredictionBuffer;
import buffers.ReorderingBuffer;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;

public class ProcessorBuilder {

	public static Processor build(MemoriaInstrucao inst, MemoriaDados data){
		Processor p = new Processor();
		List<Reg> regs = buildRegs();
		p.setRegs(regs);
		p.setMemData(data);
		p.setMemInstruction(inst);
		
		buildReorderBuffer(p,data);
		buildID(p,)
		buildIF(p,inst);
		
	}
}
