package data;

import java.util.ArrayList;

public class Ans{
	private boolean a;
	private ArrayList<Integer> list;
	public void set(boolean a)
	{
		this.a=a;
	}
	public boolean get()
	{
		return a;
	}
	public void setList(ArrayList<Integer> list)
	{
		this.list=new ArrayList<Integer>(list);
	}
	public ArrayList<Integer> getList()
	{
		return list;
	}
}