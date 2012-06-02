package reserve;

import java.util.List;

import buffers.ReorderingBuffer;

import processor.Processor;

import registers.Reg;

import instructions.Instrucao;

public abstract class ReserveStation {

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
	
	public void load(Instrucao i){
		this.busy = true;
		instrucao = i;
		List<Reg> regs = Processor.getProcessor().getRegs();
		List<Integer> regslidos = i.getRegistradoresLidos();
		for(Integer j:regslidos){
			if(regs.get(j).getQi()!=0)
				this.qj = regs.get(j).getQi();
			else this.vj = regs.get(j).getValue();
		}
		ReorderingBuffer reorder = Processor.getProcessor().getReorder();
		this.dest = reorder.loadFirstEmpty(i);
		regs.get(i.getRegistradorEscrito()).setQi(dest);
	} 
	
}
