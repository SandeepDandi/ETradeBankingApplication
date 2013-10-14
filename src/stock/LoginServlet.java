package stock;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import stock.dcc;
import java.sql.*;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public LoginServlet() {
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out= response.getWriter();
		HttpSession session;
		String username = request.getParameter("uid");
		String password = request.getParameter("pwd");


		String cookieName = "prevUser";//cookie.getName();
		String cookieUser = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {
				if (cookieName.equals(cookies[i].getName()))
				{
					cookieUser = cookies[i].getValue();
					System.out.println("retriving Cookie:"+cookieUser);
				}
			}

		String errormessage = "";
		if(username!=null && password!=null)
		{
			Connection con=null;
			ResultSet rs=null;
			//PreparedStatement pst=null;
			String password1;
			String sql="select password from login where username='"+username+"'";
			try
			{

				con=dcc.getconnection();
				Statement s = con.createStatement();
				rs=s.executeQuery(sql);
				if(rs.next())
				{
					password1=rs.getString("password");
					if(password.equals(password1))
					{
						System.out.println(""+password1);
						session= request.getSession();
						session.setAttribute("username", username);
						String rememberme=request.getParameter("rememberMe");
						if("on".equalsIgnoreCase(rememberme))
						{
						Cookie cookie=new Cookie("Prevuser",username);
						cookie.setMaxAge(24*60*60);
						response.addCookie(cookie);
						}
					response.sendRedirect("ListCategoriesServlet");
					}
					else
					{
						
						errormessage ="Invalid Credentials";
					}
				}
				else
				{
					errormessage="You are not a registered user ";
				}
				s.close();
				con.close();
			}
			catch (Exception e) 
			{

				e.printStackTrace();
			}
		}
		
		if((username==null && password==null) || errormessage!="")
		{
			//response.setContentType("text/html;charset=UTF-8"); 
			out.println("<html>"+
					"<head>"+
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"+
					"<title>LoginServlet</title>"+
					"<script language='javascript'>"+
					"function f()"+
					"{"+
					"var username=document.getElementById('uid').value;"+
					"var password=document.getElementById('pwd').value;"+
					"if(username == null || username == \"\")"+
					"{"+
					"alert(\"Username can't be blank\");"+
					"return false;"+
					"}"+
					"else"+
					"{"+
					"if(password == null || password == \"\")"+
					"{"+
					"alert(\"Password can't be blank\");"+
					"return false;"+
					"}"+
					"else"+
					"{"+
					" return true;"+
					"}"+

				     "}"+
				     "}"+
				     "</script>"+
				     "</head>"+
				     "<body>"+
				     "<center>"+
				     "<div id=\"error\" align=\"center\"><font color=\"red\">"
				     + errormessage
				     + "</font></div>"+
				     "<form name=\"login\" action=\"LoginServlet\" method=\"post\" onsubmit=\"return f()\" >"+
				     "<table  width=400 border=\"1\" bordercolor=\"black\" cellspacing=\"0\" cellpadding=\"5\">"+
				     "<tr>"+
				     "<td bgcolor=\"#0B3861\" align=\"left\" colspan=2><font color=\"white\">Login</font></td>"+
				     "</tr>"+
				     "<tr>"+
				     "<td bgcolor=\"#58ACFA\"  width=\"50%\">Username:</td>"+
				     "<td bgcolor=\"#58ACFA\"  width=\"50%\"><input type=\"text\" id=\"uid\" name=\"uid\"  value=\""+cookieUser+"\"></td>"+
				     "</tr>"+
				     "<tr>"+
				     "<td bgcolor=\"#58ACFA\"  width=\"50%\">Password:</td>"+
				     "<td bgcolor=\"#58ACFA\"  width=\"50%\"><input type=\"password\" id=\"pwd\" name=\"pwd\"></td>"+
				     "</tr>"+
				     "<tr>"+
				     "<td bgcolor=\"#0B3861\"   colspan=2><center><input type=\"submit\" value=\"Submit\"/><input type=\"reset\" value=\"Reset\" /></center></td>"+
				     "</tr>"+
				     "</table>"+
				     "<br>"+	 "<br>"+
				     "<input  type=\"checkbox\" name=\"rememberMe\">Remember Me on this Computer</input>"+
				     "</form>"+

				"<a href=\"RegistrationServlet\" ><font color=\"black\"><u>New users click here to register</u></font></a>"+
				"</center>"+
				"</body>"+
					"</html>");
			errormessage="";
		}


	}

}
