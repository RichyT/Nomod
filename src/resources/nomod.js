function scriptlist(){
  MCServer.addScript("day");
  MCServer.addScript("maze");
  MCServer.addScript("jail");
  MCServer.addScript("lhouse");

}
function maze(x,y,z){
  var maze=getmaze(9); //Should be odd number.
  for(i=1;i<20;i++){
    for(j=1;j<20;j++){
      if(maze[i][j]==1){
        MCServer.sendln("setblock " + (+i+ +x-10)+" " + (+y)+" "+(+j+ +z-10)+" stone");
        MCServer.sendln("setblock " + (+i+ +x-10)+" " + (+y+1)+" "+(+j+ +z-10)+" stone");
      }
      else{
        MCServer.sendln("setblock " + (+i+ +x-10)+" " + (+y)+" "+(+j+ +z-10)+" air");
        MCServer.sendln("setblock " + (+i+ +x-10)+" " + (+y+1)+" "+(+j+ +z-10)+" air");
      }
      MCServer.sendln("setblock " + (+i+ +x-10)+" " + (+y-1)+" "+(+j+ +z-10)+" stone");
//try
//{
//  java.lang.Thread.sleep(1000);
//}
//catch (e)
//{
  /*
   * This will happen if the sleep is woken up - you might want to check
   * if enough time has passed and sleep again if not - depending on how
   * important the sleep time is to you.
   */
//}

    }
  }

}
function day(x,y,z){
  MCServer.sendln("time set 0");
}
function lhouse(x,y,z){
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y)+" "+(+z+3)+" "+(+x-2)+" "+(+y+9)+" "+(+z+3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y)+" "+(+z-3)+" "+(+x-2)+" "+(+y+9)+" "+(+z-3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x+3)+" "+ (+y)+" "+(+z+2)+" "+(+x+3)+" "+(+y+9)+" "+(+z-2)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x-3)+" "+ (+y)+" "+(+z+2)+" "+(+x-3)+" "+(+y+9)+" "+(+z-2)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y+10)+" "+(+z+3)+" "+(+x-2)+" "+(+y+19)+" "+(+z+3)+" "+"stained_hardened_clay 14");
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y+10)+" "+(+z-3)+" "+(+x-2)+" "+(+y+19)+" "+(+z-3)+" "+"stained_hardened_clay 14");
  MCServer.sendln("fill "+ (+x+3)+" "+ (+y+10)+" "+(+z+2)+" "+(+x+3)+" "+(+y+19)+" "+(+z-2)+" "+"stained_hardened_clay 14");
  MCServer.sendln("fill "+ (+x-3)+" "+ (+y+10)+" "+(+z+2)+" "+(+x-3)+" "+(+y+19)+" "+(+z-2)+" "+"stained_hardened_clay 14");
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y+20)+" "+(+z+3)+" "+(+x-2)+" "+(+y+29)+" "+(+z+3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x+2)+" "+ (+y+20)+" "+(+z-3)+" "+(+x-2)+" "+(+y+29)+" "+(+z-3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x+3)+" "+ (+y+20)+" "+(+z+2)+" "+(+x+3)+" "+(+y+29)+" "+(+z-2)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x-3)+" "+ (+y+20)+" "+(+z+2)+" "+(+x-3)+" "+(+y+29)+" "+(+z-2)+" "+"stained_hardened_clay 0");

  MCServer.sendln("fill "+ (+x-3)+" "+ (+y+30)+" "+(+z-3)+" "+(+x+3)+" "+(+y+30)+" "+(+z+3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x-3)+" "+ (+y+38)+" "+(+z-3)+" "+(+x+3)+" "+(+y+38)+" "+(+z+3)+" "+"stained_hardened_clay 0");
  MCServer.sendln("fill "+ (+x-2)+" "+ (+y+31)+" "+(+z-2)+" "+(+x+2)+" "+(+y+37)+" "+(+z+2)+" "+"glowstone 0");

  MCServer.sendln("setblock " + (+x-3)+" " + (+y)+" "+(+z)+" air");
  MCServer.sendln("setblock " + (+x-3)+" " + (+y+1)+" "+(+z)+" air");

}

function jail(x,y,z){
  MCServer.sendln("fill "+ (+x-5)+" "+ (+y-1)+" "+(+z-5)+" "+(+x+5)+" "+(+y+5)+" "+(+z+5)+" "+"glass 0 outline");
  MCServer.sendln("setblock " + (+x+1)+" " + (+y)+" "+(+z+1)+" torch 5");
  MCServer.sendln("setblock " + (+x-1)+" " + (+y)+" "+(+z+1)+" torch 5");
  MCServer.sendln("setblock " + (+x+1)+" " + (+y)+" "+(+z-1)+" torch 5");
  MCServer.sendln("setblock " + (+x-1)+" " + (+y)+" "+(+z-1)+" torch 5");
}


function getmaze(mazespec){
  var maze=new Array();
  mazesize=3+mazespec*2;
  middle=Math.floor(mazesize/2);
  for(var i=0;i<mazesize;i++){
    maze[i]=new Array();
    for(var j=0;j<mazesize;j++){  
      maze[i][j]=(i==0||j==0||i==(mazesize-1)||j==(mazesize-1))?0:1;
    }
  }
  
  var x=middle;
  var y=middle;
  var cc=0;
  var vlist=new Array();
  maze[x][y]=0;
  cc++;
  vlist.push([x,y]);

  var c=0;
  do{

    while(maze[x+2][y]+maze[x-2][y]+maze[x][y+2]+maze[x][y-2]==0){ //Nowhere to go
      var points=vlist.pop();
      x=points[0];y=points[1];
    }

    do{
      dir=Math.floor(Math.random()*4);
      switch(dir){
        case 0:      dx=1;dy=0;      break;
        case 1:      dx=-1;dy=0;      break;
        case 2:      dx=0;dy=-1;      break;
        case 3:      dx=0;dy=1;      break;
      }
    }while(maze[x+dx*2][y+dy*2]==0);

    maze[x+dx][y+dy]=0;
    x=x+dx*2;y=y+dy*2;
    maze[x][y]=0;
    cc++;
//    document.write(" "+x+" "+y+" "+cc+" "+middle+"<br>");
    vlist.push([x,y]);
 
  }while(cc<mazespec*mazespec);
  return maze;
}