package processor;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import registers.Reg;
import buffers.PredictionBuffer;
import buffers.ReorderingBuffer;
import circuits.CommonBus;
import circuits.InstDecode;
import circuits.InstFetch;

public class ProcessorBuilder {

	public static Processor build(MemoriaInstrucao inst, MemoriaDados data, Integer predictionBufferSize){
		Processor p = new Processor();
		List<Reg> regs = buildRegs();
		p.setRegs(regs);
		p.setMemData(data);
		p.setMemInstruction(inst);
		
		
		buildReorderBuffer(p,data);
		buildIFandID(p,inst, predictionBufferSize);
		buildExecUnitsandBus(p);
		
		return p;
	}
	
	private static void buildExecUnitsandBus(Processor p) {
		AddExecUnit add = new AddExecUnit();
		MultExecUnit mult = new MultExecUnit();
		MemExecUnit mem = new MemExecUnit();
		CommonBus bus =new CommonBus();
		
		List<ExecutionUnit> eu = new ArrayList<ExecutionUnit>();
		eu.add(add);
		eu.add(mult);
		eu.add(mem);
		bus.setExecutionUnits(eu);
		bus.setReorder(p.getReorder());
		
		add.setRegs(p.getRegs());
		mult.setRegs(p.getRegs());
		mem.setRegs(p.getRegs());
		
		add.setBus(bus);
		mem.setBus(bus);
		mult.setBus(bus);
		
		add.setReorder(p.getReorder());
		mult.setReorder(p.getReorder());
		mem.setReorder(p.getReorder());
		
		mem.setDataMem(p.getDataMemory());
		
		p.setBus(bus);
		p.setAddEU(add);
		p.setMultEU(mult);
		p.setMemEU(mem);
		
	}

	private static List<Reg> buildRegs() {
		List<Reg> regs = new ArrayList<Reg>();
		for(int i = 0;i<32;i++){
			regs.add(new Reg(i));
		}
		return regs;
	}

	private static void buildIFandID(Processor p, MemoriaInstrucao mem, Integer preditorSize){
		InstFetch iF = new InstFetch();
		iF.setMem(mem);
		p.setIF(iF);
		InstDecode iD = new InstDecode();
		iD.setIF(iF);
		PredictionBuffer pb = new PredictionBuffer(preditorSize);
		iD.setPredictionBuffer(pb);
		p.getReorder().setPredictionBuffer(pb);
		p.setID(iD);
	}
	private static void buildReorderBuffer(Processor p, MemoriaDados data){
		ReorderingBuffer r = new ReorderingBuffer();
		r.setDataMemory(data);
		r.setProcessor(p);
		p.setReorder(r);
		
	}
}
