package pl.admund.MVM_Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.admund.MVM_Servlet.DBConnect.OracleDBConnector;
import pl.admund.MVM_Servlet.HTTPRespond.HTTPRespond;
import pl.admund.Security.HTTPSecurity;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private boolean DEBUG = true;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(DEBUG) System.out.println("GET");
		if(DEBUG) System.out.println("JEST ZAPYTANKO!!!");
		if(DEBUG) System.out.println("DB Conn: " + OracleDBConnector.getInstance().connectToDB());
		
		String hashRequest = request.getParameter("q");
		String params = HTTPSecurity.decrypt( hashRequest );
		if(DEBUG) System.out.println("PARAMS: " + params + " HASH: " + hashRequest);
		String myResponse = HTTPRespond.createRespondString(params);
		if(DEBUG) System.out.println("\n\t\tresponse: \n" + myResponse);
		
		OutputStream os = response.getOutputStream();
		os.write(myResponse.getBytes());
		os.flush();
		os.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(DEBUG) System.out.println("POST");
		if(DEBUG) System.out.println("JEST ZAPYTANKO!!!");
		if(DEBUG) System.out.println("DB Conn: " + OracleDBConnector.getInstance().connectToDB());
		
		/*Enumeration<String> enums = request.getParameterNames();
		String params = "";
		while(enums.hasMoreElements())
		{
			String paramName = enums.nextElement();
			String value = request.getParameter(paramName);
			params += paramName + "=" + value;
			if(enums.hasMoreElements())
				params += "&";
		}*/
		String hashRequest = request.getParameter("q");
		String params = HTTPSecurity.decrypt( hashRequest );
		
		if(DEBUG) System.out.println("PARAMS: " + params + " HASH: null");
		String myResponse = HTTPRespond.createRespondString(params);
		if(DEBUG) System.out.println("\n\t\tresponse: \n" + myResponse);
		
		OutputStream os = response.getOutputStream();
		os.write(myResponse.getBytes());
		os.flush();
		os.close();
	}
}
