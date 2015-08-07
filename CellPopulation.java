
public class CellPopulation 
{
	private Cell[][] cells;
	private int width, height;
	public CellPopulation(Cell[][] cells, int width, int height)
	{
		this.cells = cells;
		this.width = width;
		this.height = height;
	}
	
	public int getNumberOfActiveCells()
	{
		int sum = 0;
		for(int y = 0; y < this.height; y++)
		{
			for(int x = 0; x < this.width; x++)
			{
				if(cells[x][y].isAlive())
					sum++;
			}
		}
		return sum;
	}
}
