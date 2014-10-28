package rtcons.java.MCWrap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

import javafx.application.Platform;

public class MCServer {
    String pwd;
    Console console;
    String activeUser;
    ArrayList<String> addUsers;
    ArrayList<String> removeUsers;
    ArrayList<String> actionUsers;
    ArrayList<String> actionScripts;
    ArrayList<String> consoleBuffer;
    ScriptRunner sr;
    MCWrap parent;
    ScriptRunner scriptrunner;
    Thread t,t2;

	
	public MCServer(MCWrap mcw,String pwd) {
		// TODO Auto-generated constructor stub
		this.pwd=pwd;
		this.parent=mcw;
    	addUsers=new ArrayList<String>();
    	removeUsers=new ArrayList<String>();
    	actionUsers=new ArrayList<String>();
    	actionScripts=new ArrayList<String>();
    	consoleBuffer=new ArrayList<String>();

		console=new Console(this);
        t=new Thread(console);
	    t.start();
	    scriptrunner=new ScriptRunner(this);
	    t2=new Thread(scriptrunner);
	    t2.start();
	}
	
	public void adduser(String s) {
		this.addUsers.add(s);
				Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			          //javaFX operations should go here
			        	parent.threadaddusers();  //This needs to change.
			        }
			   });				
	}
	
	public void removeuser(String s) {
		this.removeUsers.add(s);
				Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			          //javaFX operations should go here
			        	parent.threadremoveusers(); //This needs to change.
			        }
			   });				
	}	

	
    public void send(String s) throws IOException{
    	console.send(s);
    }
    public void sendln(String s) throws IOException{
    	System.out.println(s);
    	console.send(s+"\n");
    }

	public void reloadScript() throws FileNotFoundException, NoSuchMethodException, ScriptException {
		// TODO Auto-generated method stub
      scriptrunner.loadScript("c:\\temp\\minecraft\\nomod.js"); 	
	}
	
	public void outln(String s){
		consoleBuffer.add(s);
		//ta.appendText(s);
		//ta.appendText("\n");
		if(consoleBuffer.size()>100)consoleBuffer.remove(0);
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	          //javaFX operations should go here
	        	parent.updTa();
	        }
	   });				

	}

	public void startserver() throws IOException {
		// TODO Auto-generated method stub
		console.startserver();
	}

	public void setActiveUser(String s){
		  this.activeUser=s;
	}

    public void addScript(String s){
    	parent.addbutton(s);
    }
	public void userAction(String s,String s2){
		String[] coords = s2.split(", ");
		System.out.println(s2+" "+coords.length);
		if(coords.length==3){
		  for(int i=actionUsers.size()-1;i>=0;i--){
			  if(((String)actionUsers.get(i)).equals(s)){
                      System.out.println(">"+((String)actionScripts.get(i))+" "+coords[0]+" "+coords[1]+" "+coords[2]);
				      scriptrunner.addScript(scriptrunner.new Script((String)actionScripts.get(i),coords[0],coords[1],coords[2]));
				      actionScripts.remove(i);
				      actionUsers.remove(i);
	            	
			  }
			  
		  }
		}
	}
	public void stop(){
	  System.out.println("Stop called in mcserver");
  	  try{
	   console.requestStop();
  	  }catch(IOException e){
  		  e.printStackTrace();
	  }
  	  scriptrunner.requestStop();
	}

}
