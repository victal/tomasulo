package reserve;

import java.util.List;

import buffers.ReorderingBuffer;

import processor.Processor;

import registers.Reg;

import instructions.Instrucao;

public class ReserveStation {

	private Instrucao instrucao;
	private Integer qj, qk; //tudo em relação ao buffer de reordenação
	private Integer vj, vk;
	private Integer A;
	private boolean busy;
	private Integer dest;
	
	public ReserveStation(){
		qj=null;
		qk=null;
		vj=null;
		vk=null;
		A=null;
		busy=false;
		dest=null;
	}
	
	public boolean isBusy(){
		return busy;
	}
	
	public Instrucao getInstrucao(){
		return instrucao;
	}
	
	public void load(Instrucao i){
		this.busy = true;
		instrucao = i;
		List<Reg> regs = Processor.getProcessor().getRegs();
		List<Integer> regslidos = i.getRegistradoresLidos();
		if(regs.get(regslidos.get(0)).getQi()!=0)
			this.qj = regs.get(regslidos.get(0)).getQi();
		else this.vj = regs.get(regslidos.get(0)).getValue();
		if(regslidos.size()>1){
			if(regs.get(regslidos.get(1)).getQi()!=0)
				this.qk = regs.get(regslidos.get(1)).getQi();
			else this.vk = regs.get(regslidos.get(1)).getValue();
		}
			
		
		ReorderingBuffer reorder = Processor.getProcessor().getReorder();
		this.dest = reorder.loadFirstEmpty(i);
		regs.get(i.getRegistradorEscrito()).setQi(dest);
	} 
	
}
