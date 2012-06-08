package processor;

import instructions.Instrucao;


public interface ExecutionUnit {
	
	public void chooseStation();
	public Integer runStep();
	public void clean();
	public void loadInst(Instrucao i);	
}
