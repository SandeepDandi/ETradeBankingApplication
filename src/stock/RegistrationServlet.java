package stock;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import stock.dcc;
import java.sql.*;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reqUserName = request.getParameter("name");
		String pwd = request.getParameter("password");
		String confPwd = request.getParameter("cpassword");
		String errormessage = "";
		HttpSession session=null;
		// Iterator iterator=userMap.keySet().iterator();
		if(reqUserName != null && pwd != null && confPwd != null)
		{
		Connection con=null;
		Statement s=null;
		ResultSet rs=null;
		String sql="select username from login where username='"+reqUserName+"'";
		
		try
		{
			con=dcc.getconnection();
			s=con.createStatement();
			rs=s.executeQuery(sql);
			if(rs.next())
			{
				errormessage=("A user with this already exists.Please choose a different username");
			}
			else
			{
				if(pwd.equals(confPwd))
				{
					String sql1="insert into login values('"+reqUserName+"','"+pwd+"')";
					s.executeQuery(sql1);
					session=request.getSession();
					session.setAttribute("username", reqUserName);
					String sql2="insert into stocks values('Technology Stocks','NVIDIA Corporation(NVDA)','200','"+reqUserName+"','0')";
					String sql3="insert into stocks values('Technology Stocks','CISCO SYSINC(CISCO)','300','"+reqUserName+"','0')";
					String sql4="insert into stocks values('Technology Stocks','Advanced Micro Devices(AMD)','150','"+reqUserName+"','0')";
					String sql5="insert into stocks values('Technology Stocks','APPLE INC(AAPL)','250','"+reqUserName+"','0')";
					String sql6="insert into stocks values('Utility Stocks','Gas Utility','150','"+reqUserName+"','0')";
					String sql7="insert into stocks values('Utility Stocks','Water Utility','250','"+reqUserName+"','0')";
					String sql8="insert into stocks values('Utility Stocks','Electric Utility','200','"+reqUserName+"','0')";
					String sql9="insert into stocks values('Utility Stocks','Natural Gas Utility','300','"+reqUserName+"','0')";
					String sql10="insert into stocks values('Small Cap Stocks','Alliant Energy Corporation','300','"+reqUserName+"','0')";
					String sql11="insert into stocks values('Small Cap Stocks','Black Hills Corporation','200','"+reqUserName+"','0')";
					String sql12="insert into stocks values('Small Cap Stocks','Duke Energy Corporation','250','"+reqUserName+"','0')";
					String sql13="insert into stocks values('Small Cap Stocks','Pepco Holdings Inc','200','"+reqUserName+"','0')";
					s.executeQuery(sql2);
					s.executeQuery(sql3);
					s.executeQuery(sql4);
					s.executeQuery(sql5);
					s.executeQuery(sql6);
					s.executeQuery(sql7);
					s.executeQuery(sql8);
					s.executeQuery(sql9);
					s.executeQuery(sql10);
					s.executeQuery(sql11);
					s.executeQuery(sql12);
					s.executeQuery(sql13);
					
					response.sendRedirect("ListCategoriesServlet");
				}
				else
				{
                   errormessage="Incorrect Passwords";
				}
			}
			s.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		}
		if ((reqUserName == null && pwd == null && confPwd == null)	|| errormessage != "") {
			PrintWriter out = response.getWriter();
			// response.setContentType("text/html");
			out.println("<html>"+
					"<head>"+
					"<meta http-equiv=\'Content-Type' content='text/html; charset=ISO-8859-1'>"+
					"<title>RegistrationServlet</title>"+
					"<script language=\"javascript\">"+
					"function r()"+
					"{"+
					"var username=document.getElementById('name').value;"+
					"var password=document.getElementById('password').value;"+
					"var cpassword=document.getElementById('cpassword').value;"+
					"if(username == null || username ==\"\")"+
					"{"+
					"alert(\"Username can't be blank\");"+
					"return false;"+
					"}"+
					"else"+
					"{"+
					"if(password == null || password ==\"\")"+
					"{"+
					"alert(\"Password can't be blank\");"+
					"return false;"+
					"}"+
					"else"+
					"{"+
					"if(cpassword == null || cpassword ==\"\")"+
					"{"+
					"alert(\"Confirm Password can't be blank\");"+
					"return false;"+
					"}"+
					"else"+
					"{"+
					"return true;"+
					" }"+ 				        
					"   }"+
					"  }"+
					"}"+
					"</script>"+
					"</head>"+
					"<body>"+
					"<center>"+
					"<div id=\"error\" align=\"center\" name=\"error\" ><font color=\"red\">"+ errormessage + "</div>"+
					"<form action=\"RegistrationServlet\" method=\"post\" onsubmit=\"return r()\">"+
					"<table  border=\"1\" bordercolor=\"black\" cellspacing=\"0\" cellpadding=\"5\">"+
					"<tr>"+
					"<td bgcolor=\"#0B3861\" align=\"left\" colspan=2><font color=\"white\">Register Account Information</font></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\">Requested Username:</td>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\"><input type=\"text\" name=\"name\"/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\">Password:</td>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\"><input type=\"password\" name=\"password\"/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\">Confirm Password:</td>"+
					"<td bgcolor=\"#58ACFA\"  width=\"50%\"><input type=\"password\" name=\"cpassword\"/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor=\"#0B3861\"  colspan=2><center><input type=\"submit\" value=\"Submit\"/><input type=\"reset\" value=\"Reset\" /></center></td>"+
					"</tr>"+
					"</table>"+
					"</form>"+
					"</center>"+
					"</body>"+
					"</html>");
		}
	}

}
