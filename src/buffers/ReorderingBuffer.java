package buffers;

import instructions.Instrucao;

import java.util.ArrayList;
import java.util.List;

import memorias.MemoriaDados;
import processor.Processor;
import registers.Reg;

public class ReorderingBuffer {
	public static final Integer SIZE = 10;

	private Processor p;//OK
	private List<ReorderingLine> buffer;//OK
	private MemoriaDados md;//OK
	private Integer listinit;//OK
	private PredictionBuffer pbuffer;//OK
	private Integer completedInstructions = 0;
	
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
		
		if(line.getState()!=ReorderingLine.CONSOLIDAR){
			return;
		}else{			
			if(line.getDest()!=null){ //Operações da ULA e Loads
				regs.get(line.getDest()).setValue(line.getValue());
				regs.get(line.getDest()).setQi(null);
			}
			else{
				Instrucao i = line.getInst();
				System.err.println("Consolidating "+line.getInst().getNome());
				if(i.isBranch()){ 
					PredictionLine pl = pbuffer.getLine(i);
					if(line.getValue()==null){
						line.setValue(pl.getInstPC());
					}
					if(!pl.getGuessPC().equals(line.getValue())){//errou, apagar tudo
						cleanAllInstructions(); 
						if(line.getValue().equals(pl.getInstPC()))
							line.setValue(line.getValue()+4);
						p.getIF().setNewPC(line.getValue());
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
		for(int i = listinit;i<listinit+ReorderingBuffer.SIZE;i++){
			buffer.get(i%ReorderingBuffer.SIZE).setFree();
		}
		p.getID().clean();
		p.cleanExecutionUnits();
	}


	public void setDataMemory(MemoriaDados mem){
		this.md = mem;
	}


	public void setPredictionBuffer(PredictionBuffer pb) {
		this.pbuffer = pb; 
		
	}
	public void setProcessor(Processor p){
		this.p = p;
	}
	public void updateState(Instrucao inst, Integer state){
		for(int i = listinit;i<listinit+ReorderingBuffer.SIZE;i++){
			if(buffer.get(i%ReorderingBuffer.SIZE).getInst().equals(inst)){
				if(state>=ReorderingLine.GRAVAR)completedInstructions++;
				buffer.get(i%ReorderingBuffer.SIZE).setState(state);
				return;
			}
		}
	}
	public Integer getState(Integer i){
		return buffer.get(i).getState();
	}
	public Integer getValue(Integer i){
		return buffer.get(i).getValue();
	}
	public List<ReorderingLine> getLines(){
		return buffer;
	}


	public void store(Integer reorderIndex, Integer value) {
		ReorderingLine line = buffer.get(reorderIndex);
		if(line.getInst().isBranch()&&value==null){
			line.setValue(pbuffer.getLine(line.getInst()).getInstPC());
		}
		else line.setValue(value);
		line.setState(ReorderingLine.CONSOLIDAR);
		
	}


	public boolean hasStoreBefore(Instrucao inst) {
		Integer index = listinit;
		boolean endlist=false;
		for(int i = listinit;i<listinit+ReorderingBuffer.SIZE&&!endlist;i++){
			if(buffer.get(i%ReorderingBuffer.SIZE).getInst()==null) endlist=true;
			else if(buffer.get(i%ReorderingBuffer.SIZE).getInst().equals(inst)){
				index = i%ReorderingBuffer.SIZE;
			}
		}
		if(index==listinit)return false;
		endlist=false;
		for(int i = listinit;i<index+ReorderingBuffer.SIZE&&!endlist;i++){
			if(buffer.get(i%ReorderingBuffer.SIZE).getInst()==null) endlist=true;
			else if(buffer.get(i%ReorderingBuffer.SIZE).getInst().getNome().equals("sw"))
				return true;
		}
		return false;
	}


	public boolean hasMemInstBefore(Instrucao inst) {
		Integer index = listinit;
		boolean endlist=false;
		for(int i = listinit;i<listinit+ReorderingBuffer.SIZE&&!endlist;i++){
			if(buffer.get(i%ReorderingBuffer.SIZE).getInst()==null) endlist=true;
			else if(buffer.get(i%ReorderingBuffer.SIZE).getInst().equals(inst)){
				index = i%ReorderingBuffer.SIZE;
			}
		}
		if(index==listinit)return false;
		endlist=false;
		for(int i = index+1;i<index+ReorderingBuffer.SIZE&&!endlist;i++){
			if(buffer.get(i%ReorderingBuffer.SIZE).getInst()==null) endlist=true;
			else {
				String nome = buffer.get(i%ReorderingBuffer.SIZE).getInst().getNome();
				if(nome.equals("sw")||nome.equals("lw"))
					return true;
			}
			
		}
		return false;
	}


	public void setAdress(Integer dest, Integer value) {
		buffer.get(dest).setAddress(value);
	}
	
	public boolean isEmpty(){
		for(ReorderingLine line:buffer){
			if(line.isBusy() && line.getInst().getNome().equals("nop"))
				return false;
		}
		return true;
	}
	public Integer getCompleted(){
		return completedInstructions;
	}
}
