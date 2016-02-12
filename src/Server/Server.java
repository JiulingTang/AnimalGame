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
		Animal cat = new Animal();
		File f=new File(this.getServletContext().getRealPath("/")+"animals.xml");
		//System.out.println(f.getAbsolutePath());
		try {
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
	}
	
	private void doRes(HttpServletResponse response,ArrayList<Integer> list)
	{
		XStream xt=new XStream(new DomDriver());
		try {
			Query q=new Query();
			xt.alias("query", Query.class);
			q.set("The animal lives in sky?");
			q.setList(list);
			response.setContentType("text/xml");
			response.getWriter().println(xt.toXML(q));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
