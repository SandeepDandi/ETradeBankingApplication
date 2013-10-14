package stock;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import stock.dcc;

/**
 * Servlet implementation class TransactionConfirmationServlet
 */
public class TransactionConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransactionConfirmationServlet() {
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
		session=request.getSession();
		Connection con;

		if (session != null) {
			String user=(String)session.getAttribute("username");
			String sname=session.getAttribute("sname").toString();
			String quantity=session.getAttribute("quantity").toString();
			String action=session.getAttribute("action").toString();
			String price=session.getAttribute("price").toString();
			String total=session.getAttribute("total").toString();


			Statement s=null;
			ResultSet rs;
			String stockquantity=null; 
			Integer newstockquantity=0;
			String sql="SELECT * FROM stocks where username='"+user+"' and stockname='"+sname+"'";
			try
			{
				con=dcc.getconnection();
				s=con.createStatement();
				rs=s.executeQuery(sql);
				if(rs.next())
				{
					stockquantity=rs.getString("stockquantity");
				}


				if(action.equalsIgnoreCase("Sell"))
				{
					newstockquantity=Integer.parseInt(stockquantity)-Integer.parseInt(quantity);
				}
				if(action.equalsIgnoreCase("Buy"))
				{
					newstockquantity=Integer.parseInt(stockquantity)+Integer.parseInt(quantity);
				}


				Statement statement = con.createStatement();
				String sql1="UPDATE stocks SET stockquantity='"+newstockquantity+"' WHERE stockname='"+sname+"' and username='"+user+"'";
				statement.executeUpdate(sql1);



				response.setContentType("text/html");
				out.println("<html>"+
						"<head>"+
						"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"+
						"<title>TransactionConfirmationServlet</title>" +
						"<script type='text/javascript' src='bankJavaScript.js'></script>"+
						"</head>"+
						"<body>"+
						"<center>"+
						"<form action=''>"+
						"<table width='1000' border='1' bordercolor='black' cellspacing='0' cellpadding='5'>"+
						"<tr>"+
						"<td bgcolor='#0B3861' align='center' colspan='5'><font color='white'>Transaction Confirmation For: "+user+"</font></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#0B3861' width='*' align='center'><font color='white'>Stock Name</font></td>"+
						"<td bgcolor='#0B3861' width='*' align='center'><font color='white'>Action</font></td>"+
						"<td bgcolor='#0B3861' width='*' align='center'><font color='white'>Quantity</font></td>"+
						"<td bgcolor='#0B3861' width='*' align='center'><font color='white'>Price Per Share</font></td>"+
						"<td bgcolor='#0B3861' width='*' align='center'><font color='white'>Total Value</font></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA' width='*'><input type='text' value='"+sname+"'></td>"+
						"<td bgcolor='#58ACFA' width='*' align='center'><input type='text' value='"+action+"'></td>"+
						"<td bgcolor='#58ACFA' width='*' align='center'><input type='text' value='"+quantity+"'></td>"+
						"<td bgcolor='#58ACFA' width='*' align='center'><input type='text' value='$"+price+"'></td>"+
						"<td bgcolor='#58ACFA' width='*' align='center'>$"+total+"</td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA' width='*' colspan='5' align='center'>"+
						"<form name='f'>"+
						"<input type='hidden' name='user' id='u1' value='"+user+"'>"+
						"<input type='hidden' name='cost' id='c1' value='"+total+"'>"+
						"<input type='hidden' name='action' id='ac1' value='"+action+"'>"+
						"<input type='button' onclick='message()' value='Charge Account'>"+
						"</form>" +
						""+
						"</td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA' width='*' align='center' colspan='5'>"+
						"<div id='results'></div>"+
						"</td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#0B3861' align='center' colspan='4'></td>"+
						"<td bgcolor='#0B3861' align='center' colspan='1'><a href='ListCategoriesServlet'><font color='white'>[<u>Categories</u>]</font></a><font color='white'> <a href='LogoutServlet'><font color='white'>[<u>Log Out</u>]</font></a></font></td>"+
						"</tr>"+
						"</table>"+
						"</form>"+
						"</center>"+
						"</body>"+
						"</html>");
				session.setAttribute("action1",action);
				session.setAttribute("total",total);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			out.println("<html><head></head><body>Your session does not exist");
			out.println("To Login  <a href='LoginServlet'>click here</a></body</html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
