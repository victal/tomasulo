package registers;

public class Reg {

	private int id;
	private Integer value;
	private Integer qi; 

	public Reg(int id) {
		this.id = id;
		this.value=0;
		this.qi = null;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}
	
	public Integer getQi(){
		return qi;
	}
	public void setQi(Integer qi){
		this.qi = qi;
	}

}
