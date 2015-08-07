import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class MainClass 
{
	public static void main(String[] args) 
	{
		try 
		{
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) 
		{
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
		
		GameOfLife game = new GameOfLife("Game of Life", 775, 659);
		game.start();
	}
}
