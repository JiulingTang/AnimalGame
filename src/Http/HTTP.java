package Http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HTTP {

	public static String get(String url)
	{
	HttpClient httpClient=new DefaultHttpClient();
	HttpGet httpget = new HttpGet(url);
	HttpResponse response = null;
	try {
		response = httpClient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in=entity.getContent();
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String rt="";
		String r;
		while ((r=br.readLine())!=null)
		{
			rt+=r+"\n";
		}
		return rt;
	}  catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	}
	
}
