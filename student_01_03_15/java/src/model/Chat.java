package model;

import java.util.List;

public class Chat
{

	private Lines[] lines;

	public Lines[] getLines()
	{
		return lines;
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