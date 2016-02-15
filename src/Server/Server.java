package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.lang.Math;

import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.glass.ui.Application;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;




/**
 * Servlet implementation class Server
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Animals anis;
    private ArrayList<String> ques;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Server() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init()
    {
    	XStream xt = new XStream(new DomDriver());
		xt.alias("animals", Animals.class);
		xt.alias("animal", Animal.class);
		xt.alias("ques", ArrayList.class);
		Animal cat = new Animal();
		ques=new ArrayList<String>();
		File f=new File(this.getServletContext().getRealPath("/")+"animals.xml");
		File f2=new File (this.getServletContext().getRealPath("/")+"questions.xml");
		//System.out.println(f.getAbsolutePath());
		try {
			ques=(ArrayList<String>)xt.fromXML(f2);
			anis=(Animals)xt.fromXML(f);
			//System.out.println(anis.list.get(0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Integer> list=new ArrayList<Integer>();
		for (int i=0;i<anis.list.size();i++)
			list.add(i);
		doRes(response,list);
		
  

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		XStream xt=new XStream(new DomDriver());
		xt.alias("ans",Ans.class );
		InputStreamReader rd=new InputStreamReader(request.getInputStream());
		BufferedReader bf=new BufferedReader(rd);
		String xml="";
		String s=null;
		while ((s=bf.readLine())!=null)
		{
			xml+=s+"\n";
		}
		Ans ans=new Ans();
		ans=(Ans)xt.fromXML(xml);
		String qs=ans.getS();
		int qid = 0;
		for (int i=0;i<ques.size();i++)
		{
			if (ques.get(i).equals(qs))
			{
				qid=i;
			}
		}
		ArrayList<Integer> list=new ArrayList<Integer>(ans.getList());
		ArrayList<Integer> nList=new ArrayList<Integer>();		
		for (int i=0;i<list.size();i++)
		{
			int aid=list.get(i);
			if (anis.list.get(aid).matches.get(qid).equals(ans.get()))
			{
				nList.add(aid);
			}
		}
		System.out.println("post");
		doRes(response,nList);
	}
	
	private void doRes(HttpServletResponse response,ArrayList<Integer> list)
	{
	
		XStream xt=new XStream(new DomDriver());
		try {
			Query q=new Query();
			xt.alias("query", Query.class);
			if (list.size()==1)
			{
				int aid=list.get(0);
				q.set("The animal is "+anis.list.get(aid).name);
				q.setList(list);
			}
			else if (list.size()==0)
			{
				q.set("I don't know the animal");
				q.setList(new ArrayList<Integer>());
			}
			else
			{
				int c=1000000;
				int b = 0;
				for (int i=0;i<ques.size();i++)
				{
					int z1=0;
					int z2=0;
					for (int j=0;j<list.size();j++)
					{
						int aid=list.get(j);
						if (anis.list.get(aid).matches.get(i))
							z1++;
						else
							z2++;
					}
					if (Math.abs(z1-z2)<c)
					{
						b=i;
						c=Math.abs(z1-z2);
					}
				}
				q.set(ques.get(b));
				q.setList(list);
			}
			response.setContentType("text/xml");
			response.getWriter().println(xt.toXML(q));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
