package processor;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registers.Reg;
import buffers.ReorderingBuffer;
import circuits.InstDecode;
import circuits.InstFetch;

public class ProcessorBuilder {

	public static Processor build(MemoriaInstrucao inst, MemoriaDados data){
		Processor p = new Processor();
		List<Reg> regs = buildRegs();
		p.setRegs(regs);
		p.setMemData(data);
		p.setMemInstruction(inst);
		
		buildReorderBuffer(p,data);
		buildIFandID(p,inst);
		
	}
	
	private static List<Reg> buildRegs() {
		List<Reg> regs = new ArrayList<Reg>();
		for(int i = 0;i<32;i++){
			regs.add(new Reg(i));
		}
		return regs;
	}

	private static void buildIFandID(Processor p, MemoriaInstrucao mem){
		InstFetch iF = new InstFetch();
		iF.setMem(mem);
		p.setIF(iF);
		InstDecode iD = new InstDecode();
		iD.setIF(iF);
		iD.setPredictionBuffer();
		p.setID(iD);
	}
	private static void buildReorderBuffer(Processor p, MemoriaDados data){
		ReorderingBuffer r = new ReorderingBuffer();
		r.setDataMemory(data);
		p.setReorder(r);
	}
}
