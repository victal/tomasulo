package circuits;

import instructions.IInstrucao;
import instructions.Instrucao;
import buffers.PredictionBuffer;

public class InstDecode {
//Former IDCircuit
	
	private PredictionBuffer buffer;//OK
	private InstFetch IF;//OK
	private Instrucao toDecode;
	private Instrucao decoded;
	
	
	public InstDecode(){
		this.toDecode = null;
		this.decoded=null;
	}
	public void decodeInst(){
		if(toDecode==null) return;//POG clock = 0;
		if(toDecode.isBranch()){
			/*Integer jumpPC = toDecode.getDadoImediato()*4 + IF.getPC();
			if(toDecode.getNome().equals("ble"))*/
			Integer jumpPC = toDecode.getDadoImediato();
			this.buffer.addLine(toDecode, IF.getPC(), jumpPC);
		}
		this.decoded = this.toDecode;
	}
	public Integer getNewPC(){
		if(toDecode==null)return IF.getPC();
		if(toDecode.isJump()){
			return toDecode.getDadoImediato();
		}
		else if(toDecode.isBranch()){
			return buffer.getGuessPC(toDecode);
		}
		if(IF.getPC()==0 && toDecode==null) return 0;//POG clock = 0;
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
	public void clean(){
		this.toDecode=null;
		this.decoded=null;
	}
	public Instrucao getDecodedInst(){
		return decoded;
	}
}
