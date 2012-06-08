package buffers;

import prediction.Preditor;

public class PredictionLine {
	private Integer instPC;
	private Integer jumpPC;
	private Preditor preditor;
	
	public PredictionLine(Integer pc, Integer jumpPC, Preditor preditor){
		this.preditor = preditor;
		this.instPC = pc;
		this.jumpPC = jumpPC;
	}

	public Integer getGuessPC() {
		return preditor.shallJump() ? jumpPC : instPC;
	}
	public Integer getInstPC(){
		return instPC;
	}
	public Integer getJumpPC(){
		return jumpPC;
	}
	public void addNotJump(){
		preditor.addNotjump();
	}
	public void addJump(){
		preditor.addJump();
	}
}
