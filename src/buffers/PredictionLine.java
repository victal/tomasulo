package buffers;

import prediction.Preditor;

public class PredictionLine {
	private Integer instPC;
	private Integer JumpPC;
	private Preditor preditor;
	
	public PredictionLine(Integer pc, Integer jumpPC, Preditor preditor){
		this.preditor = preditor;
		this.instPC = pc;
		this.JumpPC = jumpPC;
	}
}
