package memorias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaDados {

	private List<Integer> data;
	private Integer lastAddress;
	private Integer lastValue;
	private static final Integer MAX_POSITION = 32 * 1024;

	public MemoriaDados() {
		this.data = buildEmptyDataMem();
		lastAddress = null;
		lastValue = null;
	}
	private List<Integer> buildEmptyDataMem() {
		List<Integer> dataMem = new ArrayList<Integer>();
		for (int i = 0; i < MAX_POSITION; ++i)
			dataMem.add(0);
		
		return dataMem;
	}

	/*
	 * memoria de acesso aleatorio.
	 * Precisa informar a posicao
	 */
	public void setValue(Integer position, Integer value) {
		if (position < 0 || position >= MAX_POSITION)
			throw new InvalidMemoryAddressException();
		lastAddress = position;
		lastValue = value;
		this.data.set(position, value);
		
	}

	public Integer getValue(Integer position) {
		if (position < 0 || position >= MAX_POSITION)
			throw new InvalidMemoryAddressException();
		lastAddress = position;
		lastValue = this.data.get(position);
		return lastValue;
	}
	public Integer getLastAddress(){
		return lastAddress;
	}
	public Integer getLastValue(){
		return lastValue;
	}

}
