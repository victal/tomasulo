package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

	private static File currentFile = new File("teste.mips");;
	public static void main(String[] args) {

		File f = new File("teste.mips");
		currentFile = f;
		MemoriaInstrucao meminst = MemBuilder.buildMemInstruction(lerInstrucoes(f));
		MemoriaDados memdados = new MemoriaDados();
		Processor p = ProcessorBuilder.build(meminst, memdados,0);
//		while(!p.isFinished()){
//			p.runStep();
//			System.err.println(p.getIF().getPC());
//		}
	}
	public static Processor buildProcessor(File f, Integer num){
		MemoriaInstrucao meminst = MemBuilder.buildMemInstruction(lerInstrucoes(f));
		MemoriaDados memdados = new MemoriaDados();
		Processor p = ProcessorBuilder.build(meminst, memdados,num);
		return p;
	}
	public static Processor buildProcessor(Integer num){
		MemoriaInstrucao meminst = MemBuilder.buildMemInstruction(lerInstrucoes(currentFile));
		MemoriaDados memdados = new MemoriaDados();
		Processor p = ProcessorBuilder.build(meminst, memdados,num);
		return p;
	}
	
	public static List<String> lerInstrucoes(File f) {
		List<String> res = new ArrayList<String>();
		try {
			BufferedReader buffer = new  BufferedReader(new FileReader(f));
			String line;
			while((line=buffer.readLine())!=null){
				res.add(line.split(";")[0].trim());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static File chooseFile(){
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
		File f;
		if(fc.getSelectedFile()!=null){
			f = fc.getSelectedFile();
		}
		else  f = new File("resources/teste.mips");
		
		return f;
	}
	public static Processor createProcessor(int n) {
		File f = chooseFile();
		currentFile = f;
		return buildProcessor(f, n);
	}
}
