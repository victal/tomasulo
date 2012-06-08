package buffers;

import instructions.Instrucao;

import java.util.HashMap;

import prediction.OneBitPreditor;
import prediction.Preditor;
import prediction.StaticPreditor;
import prediction.TwoBitPreditor;

public class PredictionBuffer {
	
	private HashMap<Instrucao,PredictionLine> bufferlines;
	private Class preditorType;
	public PredictionBuffer(Integer preditorSize){
		bufferlines = new HashMap<Instrucao,PredictionLine>(); 
		if(preditorSize == 0)
			preditorType = StaticPreditor.class;
		else if(preditorSize==1)
			preditorType = OneBitPreditor.class;
		else if(preditorSize==2)
			preditorType = TwoBitPreditor.class;
	}
	//Line: Instruction, PC da instrucao, PC de pulo e Preditor
	public void addLine(Instrucao i,Integer pc, Integer jumpPC){
		Preditor p;
		try {
			p = (Preditor) preditorType.newInstance();
			PredictionLine pl = new PredictionLine(pc, jumpPC, p);
			bufferlines.put(i, pl);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public Integer getGuessPC(Instrucao i){
		return bufferlines.get(i).getGuessPC();
	}
	public PredictionLine getLine(Instrucao i){
		return bufferlines.get(i);
	}
}
