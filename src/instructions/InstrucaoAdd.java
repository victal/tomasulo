package instructions;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoAdd extends InstrucaoSuperFormatR implements IInstrucao {

	public InstrucaoAdd(String instrucao) {
		super(instrucao);
	}
	
	public String getNome() {		
		return "add";
	}
	
		
	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido
		registradoresLidos.add(Integer.valueOf(this.getRt(), 2));	//Rt eh registrador lido
		return registradoresLidos;
	}
	
	public Integer getRegistradorEscrito() {
		return Integer.valueOf(this.getRd(), 2); // Rd eh registrador escrito
	}

	public String getALUOp() {
		return "add";
	}

	public String getInstrucaoMIPS() {
		String i = getNome().toUpperCase()+" R"+getRegistradorEscrito()
					+",R"+Integer.valueOf(getRs(),2)+",R"+Integer.valueOf(getRt(),2);
		return i;
	}
	
	
	


}
