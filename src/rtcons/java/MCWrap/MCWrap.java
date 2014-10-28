package rtcons.java.MCWrap;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MCWrap extends Application{
  TextArea ta;
  VBox vbr;
  HBox hbb;
  MCServer activeMCServer;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
        launch(args);
	}
	
	public void updTa(){
	  ArrayList<String> amccb = activeMCServer.consoleBuffer;
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<amccb.size();i++){
			sb.append((String)amccb.get(i));
			sb.append("\n");
		}
		ta.setText(sb.toString());
	}
	public void stop(){
	  activeMCServer.stop();
	}
    public void start(Stage primaryStage) throws Exception{
    	activeMCServer=new MCServer(this,"c:\\temp\\minecraft");
    	
    	BorderPane border = new BorderPane();    	
    	VBox vbl=new VBox();
    	border.setLeft(vbl);
    	
    	vbr=new VBox();
    	border.setRight(vbr);

    	hbb=new HBox();
    	border.setBottom(hbb);
    	
    	ta = new TextArea();
    	border.setCenter(ta);
    	
        Button btn1 = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();

        btn1.setText("Stop");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
            	try{
                  activeMCServer.sendln("/stop");
            	}catch(IOException e){
            	  e.printStackTrace();
            	}
            }
        });
        btn2.setText("Start");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event){
            	try{

                  activeMCServer.startserver();
            	}catch(IOException e){
            	  System.out.println(e.getStackTrace());
            	}
            }
        });
        btn3.setText("Script");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event){
            	try{
                    activeMCServer.reloadScript();

            	}catch(IOException|ScriptException | NoSuchMethodException e){
            	  System.out.println(e.getStackTrace());
            	}
            }
        });

    	vbl.getChildren().add(btn1);
    	vbl.getChildren().add(btn2);
    	vbl.getChildren().add(btn3);

        
        StackPane root = new StackPane();
        
        root.getChildren().add(border);
//        root.getChildren().add(ta);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Nomod v0.0.1");
        primaryStage.setScene(scene);
        primaryStage.show();
	
        
		// TODO Auto-generated method stub
		System.out.println("Here we go");
	    /*
        //while(true){
//    		System.out.println("Here we go");
        	//Thread.sleep(2000);
//         
        //}
         *  
         */
	}
    public void addbutton(String s){
        Button btnx = new Button();
        btnx.setText(s);
        btnx.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event){
            	try{
              	  System.out.println(((Button)event.getTarget()).getText());
              	  activeMCServer.actionUsers.add(activeMCServer.activeUser);
              	  activeMCServer.actionScripts.add(((Button)event.getTarget()).getText());
              	  activeMCServer.sendln("tp "+activeMCServer.activeUser+" ~0 ~0 ~0");
//                  inv.invokeFunction(((Button)event.getTarget()).getText());
            	}catch(Exception e){
              	  System.out.println("Something went wrong");
            	  e.printStackTrace();
            	}
            }
        });
    	vbr.getChildren().add(btnx);
        

    
    }

	
	public void threadaddusers(){
		// TODO Auto-generated method stub
		for(int i=0;i<activeMCServer.addUsers.size();i++){  //Needs to change
        Button btnx = new Button();
        btnx.setText(activeMCServer.addUsers.get(i));
        Image ti=new Image("http://s3.amazonaws.com/MinecraftSkins/oreoborus.png");
//        Image ti2=new Image("http://s3.amazonaws.com/MinecraftSkins/oreoborus.png",ti.getWidth()*4,0,true,false);;
//        ImageView tiv=new ImageView(ti2);
//        tiv.setViewport(new Rectangle2D(32,32,32,32));
        Canvas canvas = new Canvas(64,64);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(ti,8,8,8,8,0,0,64,64 );
        gc.drawImage(ti,40,8,8,8,0,0,64,64 );

        btnx.setGraphic(canvas);
        btnx.setContentDisplay(ContentDisplay.TOP);
        btnx.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event){
            	try{
              	  System.out.println(((Button)event.getTarget()).getText());
                  activeMCServer.setActiveUser(((Button)event.getTarget()).getText());
            	}catch(Exception e){
              	  System.out.println("Something went wrong");
            	  e.printStackTrace();
            	}
            }
        });
        //Not in thread
    	hbb.getChildren().add(btnx);	
		}
		activeMCServer.addUsers.clear(); //Now empty the list
	}
	
	public void threadremoveusers(){
		// TODO Auto-generated method stub
		ObservableList<Node> l = hbb.getChildren();
		for(int i=0;i<activeMCServer.removeUsers.size();i++){
		  for(int i2=0;i2<l.size();i2++){
			if(((Button)l.get(i2)).getText().equals((String)activeMCServer.removeUsers.get(i))){
				l.remove(i2);
			}
		  }
		}
		activeMCServer.removeUsers.clear();
	}

	

	
}
