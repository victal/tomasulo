package registradores;

public class Reg {

	private int id;
	private Integer value;
	private String qi; 

	public Reg(int id) {
		this.id = id;
		this.value=0;
		this.qi = "";
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}
	
	public String getQi(){
		return qi;
	}
	public void setQi(String qi){
		this.qi = qi;
	}

}
