package rtcons.java.MCWrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Console implements Runnable {
	BufferedWriter stdin;
	BufferedReader stdout;
	MCServer mcServer;
	Process p;
	Boolean stopNow=false;
	
	public Console(MCServer m) {
		// TODO Auto-generated constructor stub
		this.mcServer=m;
	}

	@Override
	public void run() {
		int i;
		// TODO Auto-generated method stub
		try{
		  String line1;
          startserver();
          while(!stopNow){
            while ((line1 = stdout.readLine()) != null) {
              System.out.println(line1);
              if((i=line1.indexOf(" joined the game"))>-1){ //TBD: Need to also check this is a server output not /say
            	  int j=line1.lastIndexOf(" ", i-1);
            	  System.out.println(""+i+" "+j);
            	  try{
            	    mcServer.adduser(line1.substring(j+1,i));
            	  }
            	  catch(IndexOutOfBoundsException e){
          			e.printStackTrace();
            	  }
              }
              if((i=line1.indexOf(" left the game"))>-1){ //TBD: Need to also check this is a server output not /say
            	  int j=line1.lastIndexOf(" ", i-1);
            	  System.out.println(""+i+" "+j);
            	  try{
            	    mcServer.removeuser(line1.substring(j+1,i));
            	  }
            	  catch(IndexOutOfBoundsException e){
          			e.printStackTrace();
            	  }
              }
              if((i=line1.indexOf("Teleported"))>-1){
            	  int j=line1.indexOf(" ",i+11);
            	  System.out.println(""+i+" "+j);
            	  try{
            		String fp=line1.substring(i+11,j);
            		String sp=line1.substring(j+4);
               	    System.out.println("*"+fp+"="+sp+"*");
              	    mcServer.userAction(fp,sp);
              	  }
              	  catch(IndexOutOfBoundsException e){
            			e.printStackTrace();
              	  }

            	  
              }

              mcServer.outln(line1);
            }
            Thread.sleep(1000); //Not really the way to do this.
          }
          
          

		}catch(InterruptedException|IOException e){
			e.printStackTrace();
		}
		
	}
	public void startserver()throws IOException{
		Runtime rt=Runtime.getRuntime();
        p=rt.exec("java -Xmx1024M -Xms1024M -jar c:\\temp\\minecraft\\minecraft_server.1.8.jar nogui",null,new File("c:\\temp\\minecraft"));
        stdin=new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
	}
	public void requestStop() throws IOException{ //Probably should recast
	  System.out.println("Stop called");
	  stopNow=true;
	  send("quit\n");
	  try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  p.destroy();
	  
	}
	public void send(String s) throws IOException{
	  stdin.write(s);
	  stdin.flush();
	}
	
}
