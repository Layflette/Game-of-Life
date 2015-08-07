import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseControl implements MouseListener, MouseMotionListener
{
	
	private int x, y;
	private boolean pressed = false;
	private boolean eraseMode = false;
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public boolean isPressed()
	{
		return this.pressed;
	}
	
	public boolean isEraseMode()
	{
		return this.eraseMode;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		pressed = true;
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON3) // right 
			eraseMode = true;
		pressed = true;
		x = e.getX();
		y = e.getY();
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		pressed = false;
		eraseMode = false;
	}

}
