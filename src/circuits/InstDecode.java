package circuits;

import instructions.IInstrucao;
import instructions.Instrucao;
import buffers.PredictionBuffer;

public class InstDecode {
//Former IDCircuit
	
	private PredictionBuffer buffer;
	private Instrucao toDecode;
	
	public InstDecode(PredictionBuffer buffer){
		this.buffer = buffer;
		this.toDecode = new Instrucao(IInstrucao.NOP_CODE);
	}
	
	
	public void putNextInst(Instrucao i){
		this.toDecode = i;
	}
}
