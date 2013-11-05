import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Pokeball: An Amazing Adventure");
		window.setContentPane(new GamePanel());
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
	}

}
