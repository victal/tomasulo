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
	
	public ReorderingLine() {
		inst=null;
		busy = false;
		state = ReorderingLine.EXECUCAO;
	}
	
	public void load(Instrucao i){
		inst = i;
		busy = true;
	}
	
	public void setValue(Integer value){
		valor = value;
	}
	
	public void advancePhase(){
		if(state<2)state++;
	}
	public boolean isBusy(){
		return busy;
	}
}
