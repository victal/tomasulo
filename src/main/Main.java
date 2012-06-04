package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

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

}
