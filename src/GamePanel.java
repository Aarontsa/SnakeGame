import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEITGHT = 600;
	static final int UNIT_SIZE = 15;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEITGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int applesX;
	int applesY;
	char direction ='R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEITGHT));
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	// start
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	//panel with color
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		//background 
		if(running) {
			for (int i =0; i<SCREEN_HEITGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEITGHT);
				g.drawLine(0,i*UNIT_SIZE,SCREEN_HEITGHT,i*UNIT_SIZE);
			}
			//apples object
			g.setColor(Color.red);
			g.fillOval(applesX,applesY,UNIT_SIZE,UNIT_SIZE);
			
			// snake object
			for(int i=0; i<bodyParts; i++) {
				if(i==0) {
					g.setColor(Color.GRAY);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				} else {
					g.setColor(new Color(random.nextInt(255-128),random.nextInt(255-128),random.nextInt(255-128)));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
			// game score
			g.setColor(Color.blue);
			g.setFont(new Font("Ink Free",Font.BOLD,35));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score :" + applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score :" + applesEaten))/2,g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}
	//apple location(object)
	public void newApple() {
		applesX =random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		applesY =random.nextInt((int)(SCREEN_HEITGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	//snake move
	public void move() {
		//body move
		for(int i=bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		//body size
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if((x[0] == applesX) && (y[0] == applesY)){
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		//cannot touch body
		for (int i=bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}	
		}
		//frame boarder cannot touch
		if(x[0]<0) {
			running = false;
		}
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		if(y[0]<0) {
			running = false;
		}
		if(y[0]>SCREEN_HEITGHT) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}

	}
	// game over
	public void gameOver(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Serif",Font.BOLD,75));
		FontMetrics metricsover = getFontMetrics(g.getFont());
		g.drawString("Game over",(SCREEN_WIDTH-metricsover.stringWidth("Gameover"))/2,SCREEN_HEITGHT/2);
		// game score
		g.setColor(Color.blue);
		g.setFont(new Font("Ink Free",Font.BOLD,35));
		FontMetrics metricsscore = getFontMetrics(g.getFont());
		g.drawString("Score :" + applesEaten,(SCREEN_WIDTH-metricsscore.stringWidth("Score :" + applesEaten))/2,g.getFont().getSize());

		new GamePanel();
	}
	
	//apply move action
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction ='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction ='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction ='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'u') {
					direction ='D';
				}
				break;
			}
			
		}
		
	}

}
