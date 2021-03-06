package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import data.*;
import Http.HTTP;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import data.*;

public class Client {
	public String url;
	public 	Client()
	{
		XStream xt = new XStream(new DomDriver());
		xt.alias("query", Query.class);
		xt.alias("ans", Ans.class);
		String inp = null;
		BufferedReader bf=null;

		System.out.print("Server url: ");
    	
    	try {
			url=new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		do
		{
			System.out.println("Start Game? Yes or No\n");
			bf=new BufferedReader(new InputStreamReader(System.in));
			try {
				inp=bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (inp.equals("Yes")||inp.equals("No"))
				break;
		}while (true);
		if (inp.equals("Yes"))
		{
			String xml=HTTP.get(url);
			while (true)
			{
				Query q=new Query();
				q=(Query)xt.fromXML(xml);
				String input = null;
				if (q.getList().size()<2)
				{
					System.out.println(q.get());
					break;
				}
				else
				{
					do
					{
						System.out.println(q.get());
						try 	{
							input=bf.readLine();
						} 	catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while (!input.equals("Yes")&&(!input.equals("No")));
					Ans ans=new Ans();
					ans.setS(q.get());
					ans.set(input.equals("Yes")?true:false);
					ans.setList(q.getList());
					xml=HTTP.post(url, xt.toXML(ans));
					try {
						FileWriter so=new FileWriter(new File("sampeAns.xml"));
						xt.toXML(ans, so);;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
			
			
	}
	public static void main(String[] args)
	{
		new Client();
	}

}
