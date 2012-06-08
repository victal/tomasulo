package buffers;

import instructions.Instrucao;

public class ReorderingLine {
	public static final Integer EMITIDA = 0;
	public static final Integer EXECUCAO = 1;
	public static final Integer GRAVAR = 2;
	public static final Integer CONSOLIDAR = 3;
	private boolean busy;
	private Instrucao inst;
	private Integer state;
	private Integer valor;
	private Integer dest;
	private Integer address; //Só pro Store, mas está nos slides
	
	public ReorderingLine() {
		inst=null;
		busy = false;
		state = ReorderingLine.EMITIDA;
	}
	
	public void load(Instrucao i){
		inst = i;
		busy = true;
		dest = i.getRegistradorEscrito();
		valor = null;
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
	public void setState(Integer state){
		if(state>=0 && state<=3)this.state=state;
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
