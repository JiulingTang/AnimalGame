package data;
import java.util.ArrayList;

public class Query {
	private String s;
	private ArrayList<Integer> list;
	public void set(String s)
	{
		this.s=s;
	}
	public String get()
	{
		return s;
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

