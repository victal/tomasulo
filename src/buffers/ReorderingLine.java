package buffers;

import instructions.Instrucao;

public class ReorderingLine {
	public static final Integer EXECUCAO = 0;
	public static final Integer GRAVAR = 1;
	public static final Integer CONSOLIDAR = 2;
	private boolean busy;
	private Instrucao inst;
	private Integer state;
	private Integer valor;
	private Integer dest;
	private Integer address; //Só pro Store, mas está nos slides
	
	public ReorderingLine() {
		inst=null;
		busy = false;
		state = ReorderingLine.EXECUCAO;
	}
	
	public void load(Instrucao i){
		inst = i;
		busy = true;
		dest = i.getRegistradorEscrito();
		valor = null;
		dest = null;
		address = null;
	}
	
	public Integer getDest(){
		return dest;
	}
	
	public void setValue(Integer value){
		valor = value;
	}
	public Integer getValue(){
		return valor;
	}
	public void advancePhase(){
		if(state<2)state++;
	}
	public boolean isBusy(){
		return busy;
	}
	public Integer getState(){
		return state;
	}
	public Instrucao getInst(){
		return inst;
	}
	public void setAddress(Integer address){
		this.address = address;
	}
	public Integer getAddress(){
		return this.address;
	}
	public void setFree(){
		this.busy = false;
	}
}
