package processor;

import java.util.List;

import buffers.ReorderingBuffer;

import registers.Reg;

public class Processor {
	private static Processor _instance;
	public static Processor getProcessor(){
		if(Processor._instance==null){
			Processor._instance = new Processor();
		}
		return _instance;
	}
	
	private List<Reg> regs;
//	private List<AddExecUnit> adders;
//	private List<MultExecUnit> multipliers;
	private ReorderingBuffer reorder;
}
