package instructions;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoLw extends InstrucaoSuperFormatI  implements IInstrucao {

	public InstrucaoLw(String instrucao) {
		super(instrucao);
	}

	public String getNome() {
		return "lw";
	}
	
	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido 
		return registradoresLidos;
	}
	
	public Integer getRegistradorEscrito() {
		return Integer.valueOf(this.getRt(), 2); // Rt eh registrador escrito
	}
	
	public String getALUOp() {
		return "add";
	}

	@Override
	public String getInstrucaoMIPS() {
		String i = getNome().toUpperCase()
				+" R"+Integer.valueOf(getRt(),2)+",("+getDadoImediato()+")R"+Integer.valueOf(getRs(),2);
		return i;
	}

}
