package Maze;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage; 
import java.awt.image.DataBufferInt; 

public class maze1 extends Canvas implements Runnable {

	private JFrame frame;
    private static String title;
	private BufferedImage image = new BufferedImage(1200,700,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();//stores color data in array
	private boolean running;
	private Thread thread;
	private Screen screen;
	private Keyboard key;
	private int color;
	private boolean gameover=false;
	private int r;
	private double theta;
	private boolean touch=false;
	
	
	public synchronized void start() {
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running =false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime= System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; 
	    double delta =0.0;
	    int frames = 0;
	    int updates= 0;
	    running=true;
	    requestFocus();
	    while(running) {
	    	long now= System.nanoTime();
	    	delta = delta + (now-lastTime)/ns;
	    	lastTime=now; //keeps updating now and lastTime
	    	while (delta>= 1) {
	    		update();
	    		updates++;
	    		delta--;
	    	}
	    	render();
	    	frames++;
	    	if(System.currentTimeMillis()-timer> 1000) {
	    		timer = timer +1000;
	    		frame.setTitle(title + "|" + updates + "ups" + frames + "fps");//shows  updates per sec and frames per sec
	    		updates= 0;
	    		frames = 0;
	    	}
		}
	    stop();
	}

	public maze1() {
		title= "Game title";
		frame = new JFrame();
		screen= new Screen();
		r=500;
		theta=3.0;
		key= new Keyboard();
		addKeyListener(key);
		color= 0x0000ff;
	}
	public void update(){
		key.update();
		
		if(!gameover){
			if(key.up) {
				r+=2;
			}
			if(key.down){
				r-=2;
			}
		    //angle rotates counter clockwise
			if(key.left){
				theta=(theta-0.02+6.28)%6.28;//mods angle
			}
			//angle rotates clockwise
			if(key.right){
				theta=(theta+0.02)%6.28;//mods angle
			}
			
			//the following checks the boundries
			//inner ring
			if(r<=66 && r>=34 && theta<1.34 ||r<66 && r>64 && theta>1.779 ) {
				touch=true;
			}
			//in mid ring
			//1
			if(r<=116 && r>=84 && theta<4.5 && theta>3.4) {
				touch=true;
			}
			if(r>=84 && r<=56 && theta<4.5 && theta>3.4) {
				touch=true;
			}
			//2
			if(r<=116 && r>=84 && theta<6.079 && theta>4.919) {
				touch=true;
			}
			if(r>=84 && r<=56 && theta<6.079 && theta>4.919) {
				touch=true;
			}
			//3
			if(r<=116 && r>=84 && theta<3.119 && theta>0.019) {
				touch=true;
			}
			
			//out mid
			if(r<=216 && r>=184 && theta<4.6 ||r<=216 && r>=184 && theta>5.0) {
				touch=true;
			}
			
			//out
			if(r<=316 && r>=285 && theta<2.8 || r<=316 && r>=285 && theta>3.35) {
				touch=true;
			}
			
			//the following checks the lines
			//line1
			if(r<82 && r>65 && theta<4.139 && theta>3.79){
				touch=true;
			}
			//line 2
			if(r<82 && r>65 && theta<0.87 && theta>0.42){
				touch=true;
			}
			//line 3
			if(r<182 && r>114 && theta<4.17 && theta>3.96){
				touch=true;
			}
			//line4
			if(r<182 && r>114 && theta<5.74 && theta>5.54){
				touch=true;
			}
		}
	}
	
	//draws everthing
	public void render() { 
		BufferStrategy bs = getBufferStrategy();
		
		if(bs==null) {
			createBufferStrategy(3);// stores up to 3 screens in memory
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//sets backround color
		screen.clear(0x000000);
		//draws middle ball
		screen.drawBall(555,305,0xff0000,45);
		//sets ball, while (int)(r*Math.cos(theta)+585),(int)(r*Math.sin(theta)+335) gets the 
		//circular motion
		screen.drawBall((int)(r*Math.cos(theta)+585),(int)(r*Math.sin(theta)+335), color,15);
		
		
		for(int i =0; i< 1200; i++) {
			for (int j=0; j <700; j++) {
				pixels[i + 1200*j]= screen.pix[i][j];//Draws ball from the middle 
			}
		}
		
		
	   g.drawImage(image, 0,0, 1200, 700, null);
	   g.setColor(Color.YELLOW);
	   g.drawArc(300,50, 600, 600, 195 , 330);
	   g.drawArc(400,150, 400, 400, 100 , 340);
	   
	   
	   //draws lines
	   g.drawLine(530,280,564,315);
	   g.drawLine(640,380,680,410);
	   g.drawLine(680,290,760,230);
	   g.drawLine(540,270,480,190);
	  
	   
	   //draws circles
	   g.drawArc(500,250, 200, 200, 190 , 160);//botom
	   g.drawArc(500,250, 200, 200, 110 , 50);//left up
	   g.drawArc(500,250, 200, 200, 20 , 50);//right up
	  
	   g.drawArc(550,300, 100, 100, 300 , 300);
	   
	   //draws instructions
	   g.drawString("Up= Away from center", 900, 200);
	   g.drawString("Down= Towards center", 900, 220);
	   g.drawString("Left= Moves Counter Clockwise", 900, 240);
	   g.drawString("Right= Moves Clockwise", 900, 260);
	   
	   //checks to see if you win
	   if(r<30) {
		   g.setColor(Color.RED);
		   g.setFont(new Font("Verdana",0,20));
		   g.drawString("You Win!!", 100, 200);
		   gameover=true; 
	   }
	   //checks to see if you touch the wall or lines
		if(touch){
			g.setColor(Color.RED);
			g.setFont(new Font("Verdana",0,20));
			g.drawString("Gameover", 100, 200);
			gameover=true;
		}
		g.dispose();
		bs.show();
	}
	
public static void main(String[] args) {
	maze1 game = new maze1();
	game.frame.setResizable(false);  
	game.frame.setTitle(title);
	game.frame.setSize(1200,700);
	game.frame.add(game);
	game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	game.frame.setVisible(true);
	game.start();
   }
}
