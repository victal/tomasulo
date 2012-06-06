package instructions;

import java.util.ArrayList;
import java.util.List;

public class InstrucaoDiv extends InstrucaoSuperFormatR implements IInstrucao {
	
	public InstrucaoDiv(String instrucao) {
		super(instrucao);
	}
	

	@Override
	public String getALUOp() {
		return "div";
	}
	
	@Override
	public String getNome() {
		return "mul";
	}
	
	@Override
	public String getInstrucaoMIPS() {
		String i = getNome().toUpperCase()+" R"+getRegistradorEscrito()
					+",R"+Integer.valueOf(getRs(),2)+",R"+Integer.valueOf(getRt(),2);
		return i;
	}
	
	@Override
	public Integer getDuration(){
		return 5;
	}
	

	@Override
	public List<Integer> getRegistradoresLidos() {
		List<Integer> registradoresLidos = new ArrayList<Integer>();
		registradoresLidos.add(Integer.valueOf(this.getRs(), 2));	//Rs eh registrador lido
		registradoresLidos.add(Integer.valueOf(this.getRt(), 2));	//Rt eh registrador lido
		return registradoresLidos;
	}

	@Override
	public Integer getRegistradorEscrito() {
		return Integer.valueOf(this.getRd(), 2); // Rt eh registrador escrito
	}


}
