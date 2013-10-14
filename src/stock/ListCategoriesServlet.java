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
 * Servlet implementation class ListCategoriesServlet
 */
public class ListCategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListCategoriesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out= response.getWriter();
		HttpSession session = request.getSession(false);
		double tecstocks = 0;
		double utistocks=0;
		double smallstocks=0;
		double total=0;
		Connection con=null;
		if(session == null)
		{
			out.println("<html><head></head><body>Your session does not exist");
			out.println("To Login  <a href='LoginServlet'>click here</a></body</html>");
		}
		if (session != null) {
			String user=(String)session.getAttribute("username");
			String tecstock="SELECT sum(stockquantity*stockprice) as count1 FROM stocks where username='"+user+"' and stocktype='Technology Stocks'";
			String utistock="SELECT sum(stockquantity*stockprice) as count2 FROM stocks where username='"+user+"' and stocktype='Utility Stocks'";
			String smallstock="SELECT sum(stockquantity*stockprice) as count3 FROM stocks where username='"+user+"' and stocktype='Small Cap Stocks'";
			String totalstock="SELECT sum(stockquantity*stockprice) as count4 FROM stocks where username='"+user+"'";
			Statement s;
			ResultSet rs;
			
			try
			{
				
				con=dcc.getconnection();
				s=con.createStatement();
				rs=s.executeQuery(totalstock);
                while(rs.next()){
                	total=rs.getDouble("count4");
                }
                rs=s.executeQuery(tecstock);
                while(rs.next()){
                	tecstocks=rs.getDouble("count1");
                }
                rs=s.executeQuery(utistock);
                while(rs.next()){
                	utistocks=rs.getDouble("count2");
                }
                rs=s.executeQuery(smallstock);
                while(rs.next()){
                	smallstocks=rs.getDouble("count3");
                }
				
			response.setContentType("text/html");
			out.println("<html>"+
					"<head>"+
					"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"+
					"<title>ListCategoriesServlet</title>"+
					"</head>"+
					"<body>"+
					"<center>"+
					"<form action=''>"+
					"<table  width=500 border='1' bordercolor='black' cellspacing='0' cellpadding='5'>"+
					"<tr>"+
					"<td bgcolor='#0B3861' align='center' ><font color='white'>Welcome "+session.getAttribute("username")+"</font></td>"+
					"</tr>"+
					"<tr> "+
					"<td bgcolor='#58ACFA' align='center'>You currently have a total of $"+total+" invested.</td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor='#0B3861' align='center'><font color='white'>Please select from the list of stock categories below.</font></td>"+
					"</tr>"+
					"</table>"+
					"<br>"+
					"<table  width=400 border='1' bordercolor='black' cellspacing='0' cellpadding='5'>"+
					"<tr>"+
					"<td bgcolor='#0B3861' align='center' ><font color='white'>Type of stock</font></td>"+
					"<td bgcolor='#0B3861' align='center' ><font color='white'>Invested in category</font></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor='#58ACFA'  width='50%'><u><a href='ServletStocks?category=Technology Stocks'>Technology Stocks</a></u></td>"+
					"<td bgcolor='#58ACFA'  width='50%' align='center'><input type='text'  value ='$"+tecstocks+"'/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor='#58ACFA'  width='50%'><u><a href='ServletStocks?category=Utility Stocks'>Utility Stocks</a></u></td>"+
					"<td bgcolor='#58ACFA'  width='50%' align='center'><input type='text'  value='$"+utistocks+"'/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor='#58ACFA'  width='50%'><u><a href='ServletStocks?category=Small Cap Stocks'>Small Cap Stocks</a></u></td>"+
					"<td bgcolor='#58ACFA'  width='50%' align='center'><input type='text'  value='$"+smallstocks+"'/></td>"+
					"</tr>"+
					"<tr>"+
					"<td bgcolor='#0B3861' align='right' colspan=2><a href='LogoutServlet'><font color='white'>[<u>Log Out</u>]</font></a></td>"+
					"</tr>"+
					"</table>"+
					"</form>"+
					"</center>"+
					"</body>"+
					"</html>");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
