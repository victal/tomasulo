package buffers;

import java.util.ArrayList;
import java.util.List;

import instructions.Instrucao;

public class ReorderingBuffer {
	public static final Integer SIZE = 20;
	
	private List<ReorderingLine> buffer = new ArrayList<ReorderingLine>(ReorderingBuffer.SIZE);
	private Integer listinit=0;
	public Integer loadFirstEmpty(Instrucao inst) {
		for(int i = listinit;i<listinit+SIZE;i++){
			if(!buffer.get(i%ReorderingBuffer.SIZE).isBusy()){
				buffer.get(i%ReorderingBuffer.SIZE).load(inst);
				return i%ReorderingBuffer.SIZE;
			}
		}
		return null;//although it shouldn't
			
	}
	
}
