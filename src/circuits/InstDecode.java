package circuits;

import instructions.IInstrucao;
import instructions.Instrucao;
import buffers.PredictionBuffer;

public class InstDecode {
//Former IDCircuit
	
	private PredictionBuffer buffer;
	private Instrucao toDecode;
	private Instrucao decoded;
	private InstFetch IF;
	
	public InstDecode(){
		this.toDecode = new Instrucao(IInstrucao.NOP_CODE);
		this.decoded=null;
	}
	public void decodeInst(){
		if(toDecode.isBranch()){
			Integer jumpPC = toDecode.getDadoImediato()*4 + IF.getPC();
			if(toDecode.getNome().equals("ble"))
				jumpPC = toDecode.getDadoImediato();
			this.buffer.addLine(toDecode, IF.getPC(), jumpPC);
		}
		this.decoded = this.toDecode;
	}
	public Integer getNewPC(){
		if(toDecode.isJump()){
			return toDecode.getDadoImediato();
		}
		else if(toDecode.isBranch()){
			return buffer.getGuessPC(toDecode);
		}
		return IF.getPC()+4;
	}
	
	public void putNextInst(Instrucao i){
		this.toDecode = i;
	}
	
	public void setIF(InstFetch IF){
		this.IF = IF;
	}
	public void setPredictionBuffer(PredictionBuffer buf){
		this.buffer = buf;
	}
}
