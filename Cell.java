
public class Cell 
{
	private CellStatus status;
	
	public Cell()
	{
		this.status = CellStatus.DEAD;
	}
	
	public Cell(Cell c)
	{
		this.status = c.getStatus();
	}
	
	public boolean isAlive()
	{
		if(status == CellStatus.ALIVE)
			return true;
		return false;
	}
	
	
	public void setStatus(CellStatus status)
	{
		this.status = status;
	}
	
	public CellStatus getStatus()
	{
		return this.status;
	}
	
}
