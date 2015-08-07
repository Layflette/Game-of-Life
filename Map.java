
public class Map 
{
	private int width, height;
	private Cell[][] map;
	private static final int[][] NEIGHBOURS = 
		{
			{-1, -1}, {0, -1}, {1, -1},
			{-1, 0}, /*us*/	   {1, 0},
			{-1, 1},  {0, 1},  {1, 1}
		};
	
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
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	private byte countNeighbours(int x, int y)
	{
		byte sum = 0;
		for(int[] offset : NEIGHBOURS)
		{
			if(map[x + offset[0]][y + offset[1]].isAlive())
				sum++;
		}
		return sum;
	}
	
	
	public void evolve()
	{
		Cell[][] mapt;
		mapt = new Cell[this.width][this.height];
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
				mapt[x][y] = new Cell(map[x][y]);
		
		for(int y = 2; y < this.height-1; y++)
		{
			for(int x = 2; x < this.width-1; x++)
			{
				byte sum = 0;
				sum = countNeighbours(x, y);
				
				if(sum < 2 || sum > 3)
					mapt[x][y].setStatus(CellStatus.DEAD);
				else if(sum == 3)
					mapt[x][y].setStatus(CellStatus.ALIVE);
			}
		}
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
				map[x][y] = mapt[x][y];
	}

}
