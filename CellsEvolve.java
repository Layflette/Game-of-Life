
public class CellsEvolve 
{
	private Neighbours neighbours;
	private CellPopulation population;
	private Cell[][] cells;
	private int width, height;
	public CellsEvolve(Cell[][] cells, int width, int height)
	{
		this.cells = cells;
		this.width = width;
		this.height = height;
		this.neighbours = new Neighbours(this.cells);
		this.population = new CellPopulation(cells, width, height);
	}
	
	public CellPopulation getPopulation()
	{
		return this.population;
	}
	
	public void evolve()
	{
		Cell[][] mapt;
		mapt = new Cell[this.width][this.height];
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
				mapt[x][y] = new Cell(cells[x][y]);
		
		for(int y = 2; y < this.height-1; y++)
		{
			for(int x = 2; x < this.width-1; x++)
			{
				byte sum = 0;
				sum = this.neighbours.countNeighbours(x, y);
				
				if(sum < 2 || sum > 3)
					mapt[x][y].setStatus(CellStatus.DEAD);
				else if(sum == 3)
					mapt[x][y].setStatus(CellStatus.ALIVE);
			}
		}
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++)
				cells[x][y] = mapt[x][y];
	}
}
