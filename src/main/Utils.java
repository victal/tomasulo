package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import memorias.MemBuilder;
import memorias.MemoriaDados;
import memorias.MemoriaInstrucao;
import processor.Processor;
import processor.ProcessorBuilder;

public class Utils {

	private static String defaultFile ="/teste.mips";
	private static File currentFile = null;

	public static Processor buildProcessor(InputStream f, Integer num){
		MemoriaInstrucao meminst = MemBuilder.buildMemInstruction(lerInstrucoes(f));
		MemoriaDados memdados = new MemoriaDados();
		Processor p = ProcessorBuilder.build(meminst, memdados,num);
		return p;
	}
	public static Processor buildProcessor(Integer num){
		MemoriaInstrucao meminst; 
		if(currentFile == null){
			InputStream defInput = Class.class.getResourceAsStream(defaultFile);
			meminst = MemBuilder.buildMemInstruction(lerInstrucoes(defInput));
		} else
			try {
				meminst = MemBuilder.buildMemInstruction(lerInstrucoes(new FileInputStream(currentFile)));
			} catch (FileNotFoundException e) {
				InputStream defInput = Class.class.getResourceAsStream(defaultFile);
				meminst = MemBuilder.buildMemInstruction(lerInstrucoes(defInput));
				e.printStackTrace();
			}
		MemoriaDados memdados = new MemoriaDados();
		Processor p = ProcessorBuilder.build(meminst, memdados,num);
		return p;
	}
	
	public static List<String> lerInstrucoes(InputStream f) {
		List<String> res = new ArrayList<String>();
		try {
			BufferedReader buffer = new  BufferedReader(new InputStreamReader(f));
			String line;
			while((line=buffer.readLine())!=null){
				res.add(line.split(";")[0].trim());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static InputStream chooseFile(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Mips binary files";
			}
			public boolean accept(File arg0) {
				return arg0.getName().matches(".*[.]mips")||arg0.isDirectory();
			}
		});
		fc.showOpenDialog(null);
		InputStream f = Class.class.getResourceAsStream("/teste.mips");
		if(fc.getSelectedFile()!=null){
			try {
				f = new FileInputStream(fc.getSelectedFile());
				currentFile = fc.getSelectedFile();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		}
	
		return f;
	}
	public static Processor createProcessor(int n) {
		InputStream f = chooseFile();
		return buildProcessor(f, n);
	}
}
