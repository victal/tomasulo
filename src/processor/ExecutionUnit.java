package processor;

import buffers.ReorderingBuffer;
import instructions.Instrucao;


public interface ExecutionUnit {
	
	public void chooseStation();
	public void runStep();
	public void clean();
	public void loadInst(Instrucao i);
	public ReorderingBuffer getReorder();
	public void updateStations(Integer reorderIndex, Integer value);
	public boolean isFull();
}
