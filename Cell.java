
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
		return this.status == CellStatus.ALIVE;
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
