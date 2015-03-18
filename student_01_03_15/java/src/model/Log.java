package model;

import java.util.ArrayList;
import java.util.List;

public class Log
{
	private List<Lines> lines;
	
	public Log init()
	{
		lines = new ArrayList<>();
		return this;
	}
	
	public void addLine(Lines line)
	{
		lines.add(line);
	}
	
	public List<Lines> getLines()
	{
		return lines;
	}

	public void setLines(List<Lines> lines)
	{
		this.lines = lines;
	}

}
