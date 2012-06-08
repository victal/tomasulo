package buffers;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;

import registers.Reg;

import instructions.Instrucao;

public class ReorderingBuffer {
	public static final Integer SIZE = 20;

	private List<ReorderingLine> buffer;
	private MemoriaDados md;
	private Integer listinit;
	private PredictionBuffer pbuffer;
	public ReorderingBuffer(){
		buffer = new ArrayList<ReorderingLine>();
		for(int i = 0;i<ReorderingBuffer.SIZE;i++){
			buffer.add(new ReorderingLine());
		}
		this.listinit = 0;
	}


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
				if(i.isBranch()){ 
					PredictionLine pl = pbuffer.getLine(i);
					if(!pl.getGuessPC().equals(line.getValue())){//errou, apagar tudo
						cleanAllInstructions(); 
						setNewPC(line.getValue());
					}
					if(line.getValue().equals(pl.getInstPC())){
						pl.addNotJump();
					}
					else{
						pl.addJump();
					}
				}
				else if(i.getNome().equals("sw")){//Store
					md.setValue(line.getAddress(),line.getValue());
				}

			}
		buffer.get(listinit).setFree();
		listinit = (listinit+1)%ReorderingBuffer.SIZE;
		}
	}

	private void cleanAllInstructions() {
		for(int i = listinit;i<listinit+SIZE;i++){
			buffer.get(i).setFree();
		}
		//TODO:erase instructions on ID and reserveStations
	}


	public void setDataMemory(MemoriaDados mem){
		this.md = mem;
	}


	public void setPredictionBuffer(PredictionBuffer pb) {
		this.pbuffer = pb; 
		
	}
}
