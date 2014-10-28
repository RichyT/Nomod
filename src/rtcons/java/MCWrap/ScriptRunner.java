package rtcons.java.MCWrap;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptRunner implements Runnable {
   ScriptEngine engine;	
   Invocable inv;
   MCServer parent;
   ArrayList<Script> scriptList;
   Boolean stopNow=false;;

	public ScriptRunner(MCServer mcs) {
		// TODO Auto-generated constructor stub
		this.parent=mcs;
		scriptList=new ArrayList<Script>();
	}

	@Override
	public void run(){
        ScriptEngineManager factory = new ScriptEngineManager();
        // create JavaScript engine
        engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from given file - specified by first argument
        engine.put("MCServer",this.parent);
        inv = (Invocable) engine;
        try {
			loadScript("c:\\temp\\minecraft\\nomod.js");
		} catch (FileNotFoundException | NoSuchMethodException
				| ScriptException e) {
			e.printStackTrace();
		}
        while(!stopNow){
        	ArrayList<Script> ts=popScripts();
        	for(int i=0;i<ts.size();i++){
                try {
					inv.invokeFunction(ts.get(i).s,ts.get(i).x,ts.get(i).y,ts.get(i).z);
				} catch (NoSuchMethodException | ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
	}
	
    public synchronized ArrayList<Script> popScripts(){
        ArrayList<Script> ts=scriptList;
        scriptList=new ArrayList<Script>();
        return ts;
  	}
	
	public synchronized void addScript(Script fn1){
      scriptList.add(fn1);
	}
    
	public synchronized void loadScript(String fn1) throws FileNotFoundException, ScriptException, NoSuchMethodException{
        engine.eval(new java.io.FileReader(fn1));
        inv.invokeFunction("scriptlist");
	
	}
	public class Script{
		String s;
		String x,y,z;
	  public Script(String s,String x,String y,String z){
		  this.s=s;this.x=x;this.y=y;this.z=z;
	  }
	}
	public void requestStop() {
		// TODO Auto-generated method stub
		  stopNow=true;

		
	}
}

