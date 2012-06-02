package buffers;

import instructions.Instrucao;

public class ReorderingLine {
	public static final Integer EXECUCAO = 0;
	public static final Integer GRAVAR = 1;
	public static final Integer CONSOLIDAR = 2;
	private boolean busy;
	private Instrucao inst;
	private Integer state;
	private Integer valor;
}
