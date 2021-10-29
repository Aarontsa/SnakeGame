import javax.swing.JFrame;
import java.awt.*;

public class Frame extends JFrame {
	Frame(){
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		}

}
