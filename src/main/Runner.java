package main;

import javax.swing.SwingWorker;

import processor.Processor;

public class Runner extends SwingWorker<Object, Void> {

	private Gui gui;
	private Processor mips;
	private boolean running = false;
	private boolean toRun=false;
	public Runner(Gui mipsGui, Processor mips) {
		this.mips=mips;
		this.gui=mipsGui;
	}

	public void setRun(boolean b){
		toRun=b;
	}
	
	public void runStep(){
		gui.updateInfos();
		mips.runStep();	
	}
	
	public void runProc(){
		running=true;
		while(!mips.isStopped()&&!mips.isFinished()&&!isCancelled()){
			runStep();
		}
		running=false;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		while(!isCancelled()){
	         if(toRun&&!isRunning()){
	        	 this.runProc();
	         }
	      }
		return null;
	}
	public boolean isRunning(){
		return running && !mips.isStopped();
	}
	public void resetMips(Processor mips){
		this.mips=mips;
		this.running=false;
	}

}
