package model;

import java.util.ArrayList;
import java.util.List;

public class Chat
{
	private Lines[] lines;

	public Chat init()
	{
		lines = new Lines[0];
		return this;
	}
	
	public Lines[] getLines()
	{
		return lines;
	}

	public void addLine(Lines line)
	{
		List<Lines> list = new ArrayList<>();
		for (Lines l : lines)
			list.add(l);
		list.add(line);
		lines = list.toArray(new Lines[0]);
	}
	public void setLines(Lines[] lines)
	{
		this.lines = lines;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Lines line : lines)
		{
			sb.append(line.getMessage());
		}
		return sb.toString();
	}

}