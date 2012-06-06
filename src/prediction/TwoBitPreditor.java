package prediction;

public class TwoBitPreditor implements Preditor {

	private boolean bit1=false;
	private boolean bit0=false;
	@Override
	public boolean shallJump() {
		return bit1;
	}

	@Override
	public void addNotjump() {
		bit1 = bit1 && bit0;
		bit0=false;

	}

	@Override
	public void addJump() {
		bit1 = bit1 || bit0;
		bit0 = true;
	}

}
