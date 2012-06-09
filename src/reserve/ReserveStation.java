package reserve;

import instructions.Instrucao;

import java.util.List;

import processor.ExecutionUnit;
import processor.Processor;
import registers.Reg;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;

public class ReserveStation {

	private ExecutionUnit eu;//OK
	private List<Reg> regs;//OK
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
		this.instrucao = i;
		ReorderingBuffer reorder = eu.getReorder();
		List<Integer> regslidos = i.getRegistradoresLidos();
		
		if(regslidos.size()>0){//add, mul, sub, div, branches,lw
			if(regs.get(regslidos.get(0)).getQi()!=0){
				if(reorder.getState(regs.get(regslidos.get(0)).getQi())==ReorderingLine.CONSOLIDAR)
					this.vj = reorder.getValue(regs.get(regslidos.get(0)).getQi());
				else this.qj = regs.get(regslidos.get(0)).getQi();
			}
			else this.vj = regs.get(regslidos.get(0)).getValue();
		}
		if(regslidos.size()==2){
			if(regs.get(regslidos.get(1)).getQi()!=0){
				if(reorder.getState(regs.get(regslidos.get(1)).getQi())==ReorderingLine.CONSOLIDAR)
					this.vk = reorder.getValue(regs.get(regslidos.get(1)).getQi());
				else this.qk = regs.get(regslidos.get(1)).getQi();
			}
			else this.vk = regs.get(regslidos.get(1)).getValue();	
		}
		if(i.getNome().equals("addi"))
			this.vk = i.getDadoImediato();
		else if(i.getNome().equals("sw"))
			this.A = i.getDadoImediato();
		if(i.isBranch()||i.isJump()){
			this.A = i.getDadoImediato();
		}
		this.dest = reorder.loadFirstEmpty(i);
		regs.get(i.getRegistradorEscrito()).setQi(dest);
	} 
	public void setRegs(List<Reg> regs){
		this.regs = regs;
	}
	public Integer getDest(){
		return dest;
	}
	public Integer getVj(){
		return vj;
	}
	public Integer getVk(){
		return vk;
	}
	public Integer getQj(){
		return qj;
	}
	public Integer getQk(){
		return qk;
	}
	public void clean(){
		qj=null;
		qk=null;
		vj=null;
		vk=null;
		A=null;
		busy=false;
		dest=null;
		instrucao = null;
	}
	public void setQj(Integer i){
		qj=i;
	}
	public void setQk(Integer i){
		qk=i;
	}
	public void setVj(Integer i){
		vj=i;
	}
	public void setVk(Integer i){
		vk=i;
	}
	public Integer getA(){
		return A;
	}
	public void setExecUnit(ExecutionUnit eu){
		this.eu=eu;
	}
}
