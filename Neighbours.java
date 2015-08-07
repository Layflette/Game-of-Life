
public class Neighbours 
{
	private static final int[][] NEIGHBOURS = 
		{
			{-1, -1}, {0, -1}, {1, -1},
			{-1, 0}, /*us*/	   {1, 0},
			{-1, 1},  {0, 1},  {1, 1}
		};
	
	
	private Cell[][] map;
	
	public Neighbours(Cell[][] map)
	{
		this.map = map;
	}
	
	public byte countNeighbours(int x, int y)
	{
		byte sum = 0;
		for(int[] offset : NEIGHBOURS)
		{
			if(this.map[x + offset[0]][y + offset[1]].isAlive())
				sum++;
		}
		return sum;
	}

}
