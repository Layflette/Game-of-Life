
public class Map 
{
	private int width, height;
	private Cell[][] map;
	
	public Map(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		map = new Cell[this.width][this.height];
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
				map[x][y] = new Cell();
			
	}
	
	public Cell getCell(int x, int y)
	{
		return map[x][y];
	}
	public Cell[][] getCells()
	{
		return map;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void clearMap()
	{
		for(int y = 0; y < this.height; y++)
		{
			for(int x = 0; x < this.width; x++)
				map[x][y].setStatus(CellStatus.DEAD);
		}
	}
}
