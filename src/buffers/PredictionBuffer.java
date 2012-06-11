package buffers;

import instructions.Instrucao;

import java.util.HashMap;

import prediction.OneBitPreditor;
import prediction.Preditor;
import prediction.StaticPreditor;
import prediction.TwoBitPreditor;

public class PredictionBuffer {
	
	private HashMap<Integer,PredictionLine> bufferlines;
	private Class preditorType;
	public PredictionBuffer(Integer preditorSize){
		bufferlines = new HashMap<Integer,PredictionLine>(); 
		if(preditorSize == 0)
			preditorType = StaticPreditor.class;
		else if(preditorSize==1)
			preditorType = OneBitPreditor.class;
		else if(preditorSize==2)
			preditorType = TwoBitPreditor.class;
	}
	//Line: Instruction, PC da instrucao, PC de pulo e Preditor
	public void addLine(Instrucao inst, Integer pc, Integer jumpPC){
		Preditor p;
		try {
			p = (Preditor) preditorType.newInstance();
			PredictionLine pl = new PredictionLine(inst, pc, jumpPC, p);
			bufferlines.put(pc, pl);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public Integer getGuessPC(Integer i){
		return bufferlines.get(i).getGuessPC();
	}
	public PredictionLine getLine(Integer i){
		return bufferlines.get(i);
	}
	public PredictionLine getLine(Instrucao i){
		for(PredictionLine pl: bufferlines.values()){
			if(pl.getInst().equals(i)){
				return pl;
			}
		}
		return null;
	}
}
