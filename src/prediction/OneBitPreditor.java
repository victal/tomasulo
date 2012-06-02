package prediction;

public class OneBitPreditor implements Preditor {
		private boolean preditionbit = false;
	@Override
	public boolean shallJump() {
		return preditionbit;	
	}

	public void addNotjump() {
		preditionbit = false;	
	}

	public void addJump() {
		preditionbit = true;
	}

}
