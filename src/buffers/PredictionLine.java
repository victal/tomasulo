package buffers;

import instructions.Instrucao;
import prediction.Preditor;

public class PredictionLine {
	private Integer instPC;
	private Integer jumpPC;
	private Preditor preditor;
	private Instrucao i;
	
	public PredictionLine(Instrucao i, Integer pc, Integer jumpPC, Preditor preditor){
		this.preditor = preditor;
		this.instPC = pc;
		this.jumpPC = jumpPC;
		this.i = i;
	}

	public Integer getGuessPC() {
		return preditor.shallJump() ? jumpPC : instPC+4;
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

	public Object getInst() {
		// TODO Auto-generated method stub
		return i;
	}
}
