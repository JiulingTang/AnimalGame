package data;

import java.util.ArrayList;

public class Animal
{
	public String name;
	public ArrayList<Boolean> matches;
	public String toString()
	{
		String r=name;
		for (int i=0;i<matches.size();i++)
		r=r+' '+matches.get(i);
		return r;
	}
}
