package buffers;

import java.util.ArrayList;
import java.util.List;

import processor.Processor;

import memorias.MemoriaDados;

import registers.Reg;

import instructions.Instrucao;

public class ReorderingBuffer {
	public static final Integer SIZE = 20;
	
	private List<ReorderingLine> buffer = new ArrayList<ReorderingLine>(ReorderingBuffer.SIZE);
	private Integer listinit=0;
	public Integer loadFirstEmpty(Instrucao inst) {
		for(int i = listinit;i<listinit+SIZE;i++){
			if(!buffer.get(i%ReorderingBuffer.SIZE).isBusy()){
				buffer.get(i%ReorderingBuffer.SIZE).load(inst);
				return i%ReorderingBuffer.SIZE;
			}
		}
		return null;//although it shouldn't
			
	}
	public void consolidate(List<Reg> regs){
		ReorderingLine line = buffer.get(listinit);
		if(line.getState()!=ReorderingLine.CONSOLIDAR)
			return;
		else{
			if(line.getDest()!=null){ //Operações da ULA e Loads
				regs.get(line.getDest()).setValue(line.getValue());
				regs.get(line.getDest()).setQi(null); 
			}
			else{
				Instrucao i = line.getInst();
				if(i.isBranch()){ // Branches
					
				}
				else if(i.getNome().equals("sw")){//Store
					MemoriaDados md = Processor.getProcessor().getDataMemory();
					md.setValue(line.getAddress(),line.getValue());
				}
			
			}
		buffer.get(listinit).setFree();
		listinit = (listinit+1)%ReorderingBuffer.SIZE;
		}
	}
}
