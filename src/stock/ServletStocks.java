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
 * Servlet implementation class ServletStocks
 */
public class ServletStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletStocks() {
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
		String category= request.getParameter("category");
		Connection con=null;
		
		if(session==null)
		{
			out.println("<html><head></head><body>Your session does not exist");
			out.println("To Login  <a href='LoginServlet'>click here</a></body</html>");
		}
		if (session != null) {
			String user=(String)session.getAttribute("username");
				String sql="select * from stocks where username='"+user+"' and stocktype='"+category+"'";
				Statement s;
				ResultSet rs;
				try
				{
					con=dcc.getconnection();
					s=con.createStatement();
					rs=s.executeQuery(sql);

					response.setContentType("text/html");

					out.println("<html>"+
							"<head>"+
							"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"+
							"<title>"+category+"</title>"+
							/*"<script language='javascript'>"+
							"function f()"+
							"{"+
							"var oq=document.getElementById('ownquantity').value;"+
							"var action=document.getElementById('action').value;"+
							"System.out.println(\"action\"+action);"+
							"var q=document.getElementById('quantity').value;"+
							"if(\"SEll\".equalsIgnoreCase(action))"+ 
							"{"+
							"if(q<=oq)"+
							"{"+

									"return true;"+
									"}"+
									"else"+
									"{"+
									"alert(\"User does not have enough stocks to sell.\");"+
									"return false;"+
									"}"+
									"}"+
									"}"+

						     "</script>"+*/
						     "</head>"+
						     "<body>"+
						     "<center>"+
						     
						     "<table  width=800 border='1' bordercolor='black' cellspacing='0' cellpadding='5'>"+
						     "<tr>"+
						     "<td bgcolor='#0B3861' align='center' colspan=8 ><font color='white'>"+category+"</font></td>"+
						     "</tr>"+
						     "<tr>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Stock Name</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Currently owned shares</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Price per share</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Total value</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Action</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Quantity</font></td>"+
						     "<td bgcolor='#0B3861'  width='*' align='center'></td>"+
							"</tr>");
					while(rs.next())
					{				
						String stockname=rs.getString("stockname");
						String quantity=rs.getString("stockquantity");
						String price=rs.getString("stockprice");
						double total=(Integer.parseInt(quantity)*Double.parseDouble(price));
						out.print(
								"<form action='TransactionalDetailsServlet' method='get'>"+
								"<tr>"+
								"<td bgcolor='#58ACFA'  width='*'>"+stockname+"</td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center' id='ownquantity' >"+quantity+"</td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text'  name='price' value='$"+price+"'/></td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text'  value='$"+total+"'/></td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'></td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'>"+
								"  <select name='action' id='action' value='buy' >"+
								"  <option value='buy'>Buy</option>"+
								"  <option value='sell'>Sell</option>"+
								"  </select>"+
								"  </td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text' name='quantity' value='0' size='3'/></td>"+
								"<td bgcolor='#58ACFA'  width='*' align='center'><input type='submit' value='Make Transaction'/></td>"+
								"<input type=\"hidden\" name=\"sname\" value='"+stockname+"'>"+
								
								"</tr>"+
						"</form>");
						
					}
					out.print(			
							"<tr>"+
									"<td bgcolor='#0B3861' align='left' colspan=6><font color='white' >User: "+user+"</font></td>"+
									"<td bgcolor='#0B3861' align='right' colspan=2><a href='ListCategoriesServlet'><font color='white'>[<u>Categories</u>]</a> <a href='LogoutServlet'><font color='white'>[<u>Log Out</u>]</font></a></td>"+
									"</tr>"+
									"</table>"+
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
		super.doPost(request, response);
	}

}
