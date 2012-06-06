package circuits;

import instructions.IInstrucao;
import instructions.Instrucao;
import memorias.MemoriaInstrucao;

public class InstFetch {
	private MemoriaInstrucao mem;
	private int pc;
	
	public InstFetch() {
		this.pc = 0;
	}

	public void setMem(MemoriaInstrucao mem) {
		this.mem = mem;
	}

	public void setNewPC(Integer newPC){
		this.pc = newPC;
	}
	
	public Instrucao getnextInst(){
		Instrucao i = this.mem.get(pc);
		if(i==null) i= new Instrucao(IInstrucao.NOP_CODE);
		return i;
	}
	
	public Integer getPC(){
		return pc;
	}
}
