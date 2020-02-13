package gameBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import javax.swing.JFrame;

public class Bird implements ActionListener,MouseListener
{
	
	public static Bird bird;
	
	public final int WIDTH=800,HEIGHT =800;
	
	public CompositionJpanel compositionJpanel ;
	//motion of bird
	public int ticks,yMotion,score;
	
	public Rectangle rectangle;
	public ArrayList<Rectangle>columns;
	public Random random;
	public boolean gameOver,started;
	 
	public Bird() 
	{
		JFrame jframe=new JFrame();
		
		Timer timer=new Timer(20,this);
	    compositionJpanel=new CompositionJpanel();
	     random = new Random();
	    
	    
		jframe.add(compositionJpanel);
		jframe.setTitle("Bird Game");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
	jframe.setResizable(false);
		//I want to make visible
	jframe.setVisible(true);
		
		rectangle=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
		columns=new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		//it performing more than twice 
		timer.start();
	}
	
	public void addColumn(boolean start) {
		int space=300;
	      int width=100;
	      int height=50+random.nextInt(300);
	      
	      if(start) {
	    	  //its for downward column
	     columns.add(new Rectangle(WIDTH+width+columns.size()*300,HEIGHT-height-120,width,height));
	      //its for upward column
	      columns.add(new Rectangle(WIDTH+width+(columns.size()-1)*300,0,width,HEIGHT-height-space));
	      }
	      else {
	    	  //its getting columns from columns array list.its getting position at column size,there is one column inside/
	    	  //inside the size return 1,when we do get it start position of 0 and second it start at position of 1,we wanna subtract this by 1(-1)
	    	 //we are getting x adding 600 to that
	    	  columns.add(new Rectangle(columns.get(columns.size()-1).x+600,HEIGHT-height-120,width,height));
	    	  //we are taking previsious x here
		      columns.add(new Rectangle(columns.get(columns.size()-1).x,0,width,HEIGHT-height-space));
	      }
	      }
	
	public void paintColumn(Graphics g,Rectangle columns) {
		g.setColor(Color.GREEN);
		g.fillRect(columns.x, columns.y, columns.width,columns. height);
	}
	public void jump() {
		if(gameOver) {
			rectangle=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
			columns.clear();
			yMotion=0;
			score=0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameOver=false;
		}
		if(!started) {
			started =true;
		}else if(!gameOver) {
			if(yMotion>0){
				//the bird stays in its position it will not go downward motion thats whay yMotion =0
				yMotion=0;
			}
			//each pressed key will do -10 i.e if we press the mouse than it will move upward by -10.
			yMotion-=10;
		}
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int speed =10;
		ticks++;
		//ticks help to bird fall slowley..if we do ticks%4 and 5.it will fall slowely
		if(started) {
			
		
		for(int i=0;i<columns.size();i++) {
			Rectangle column =columns.get(i);
			column.x-=speed;
		}
		//yMotion just adding whats bird coordinate is .if we do +2 it will do down little in screen +3 it will be more down little
		if(ticks % 2==0 && yMotion<15) {
			yMotion +=2;
		}
		for(int i=0;i<columns.size();i++) {
			Rectangle column =columns.get(i);
			if(column.x +column.width<0) {
				columns.remove(column);
				//if its top column add another column like buttom column
				if(column.y==0) {
					addColumn(false);
				}
			}
		}
		rectangle.y+=yMotion;
		
		for(Rectangle column:columns) {
			//if bird have successfully pass the column than it will going to increase the score
			if(column.y == 0 && rectangle.x+rectangle.width/2>column.x+column.width/2-10 && rectangle.x+rectangle.width/2<column.x+column.width/2+10 )
			{
				score++;
			}
			
			if(column.intersects(rectangle)) {
				gameOver=true;
				
				rectangle.x=column.x-rectangle.width;
			}
		}
		if(rectangle.y>HEIGHT-120||rectangle.y<0) {
			gameOver=true;
		}
		if(rectangle.y+yMotion>=HEIGHT-120) {
			rectangle.y=HEIGHT-120-rectangle.height;
		}
		}
		compositionJpanel.repaint();
	}
	
	
	
	//this is asking for the color
	public void repaint(Graphics g) {
		//System.out.println("hello");
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.RED);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		
		for(Rectangle columns:columns) {
			paintColumn(g, columns);
		}
		g.setColor(Color.CYAN);
		g.fillRect(0, HEIGHT-120, WIDTH,120);
		g.setColor(Color.GRAY);
		g.fillRect(0, HEIGHT-120, WIDTH,20);
		
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",1,100));
		
		if(!started) {
			g.drawString("Click to start!", 75,HEIGHT/2 -50 );
		}
		
		if(gameOver) {
			g.drawString("Game Over!", 100,HEIGHT/2 -50 );
		}
		
	}
	
	public static void main(String[]args) 
	{
		bird=new Bird();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	
	
}