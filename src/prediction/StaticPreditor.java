package prediction;

public class StaticPreditor implements Preditor{
	private boolean predition = false;
	
	public boolean shallJump(){
		return predition;
	}

	public void addNotjump() {}

	public void addJump() {}
}
