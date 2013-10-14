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
 * Servlet implementation class TransactionalDetailsServlet
 */
public class TransactionalDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionalDetailsServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		PrintWriter out= response.getWriter();
		HttpSession session = request.getSession(false);
		Connection con=null;
				
		if (session != null) {
			String user=(String)session.getAttribute("username");
			String sname=request.getParameter("sname");
			if(sname==null)
			{
				sname=session.getAttribute("sname").toString();
			}
			String action=request.getParameter("action");
			System.out.println(action);
			if(action==null)
			{
				action=session.getAttribute("action").toString();
			}
			String quantity=request.getParameter("quantity");
			if(quantity==null)
			{
				quantity=session.getAttribute("quantity").toString();
			}
			Statement s;
			ResultSet rs;
			String sql="select stockprice,stockquantity from stocks where username='"+user+"' and stockname='"+sname+"'";
			System.out.println(""+sname);
			String price ="";
			String dquantity="";
			String errormessage="";
			int i=0;
			try
			{
				con=dcc.getconnection();
				s=con.createStatement();
				rs=s.executeQuery(sql);
				if(rs.next())
				{
					price=rs.getString("stockprice");
					dquantity=rs.getString("stockquantity");
				}
				System.out.println(Double.parseDouble(quantity));
				
				System.out.println(Double.parseDouble(dquantity));
				if(((Double.parseDouble(quantity))>(Double.parseDouble(dquantity)))&& action.equals("sell"))
				{
					i++;
					errormessage="you dont have enough stocks to sell, change the quantity of stocks to sell the stocks.";
				}
				if(Double.parseDouble(quantity)==0 && action.equals("buy"))
				{
					i++;
					errormessage="you cannot buy zero stocks.";
				}
				
				double total=((Double.parseDouble(price))*(Double.parseDouble(quantity)));
				response.setContentType("text/html");
				out.println("<html>"+
						"<head>"+
						"<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"+
						"<title>TransactionalDetailsServlet</title>"+
						"</head>"+
						"<body>"+
						"<center>"+
						"<div id=\"error\" align=\"center\"><font color=\"red\">"
					     + errormessage
					     + "</font></div>"+
						"<form action='TransactionalDetailsServlet' method='get'>"+
						"<table  width=1000 border='1' bordercolor='black' cellspacing='0' cellpadding='5'>"+
						"<tr>"+
						"<td bgcolor='#0B3861' align='center' colspan=5><font color='white'>Transactional Details For: "+user+"</font></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Stock Name</font></td>"+
						"<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Action</font></td>"+
						"<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Quantity</font></td>"+
						"<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Price Per Share</font></td>"+
						"<td bgcolor='#0B3861'  width='*' align='center'><font color='white'>Total Value</font></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA'  width='*'><input type='text'  value='"+sname+"'/></td>"+
						"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text'  value='"+action+"'/></td>"+
						"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text' name='quantity' value='"+quantity+"' size='3'/><input type='Submit' value='change'/></td>"+
						"<td bgcolor='#58ACFA'  width='*' align='center'><input type='text'  value='$"+price+"'/></td>"+
						"<td bgcolor='#58ACFA'  width='*' align='center'>$"+total+"</td>"+
						"</tr>"+
						"</form>");
						out.println("<form action='TransactionConfirmationServlet' method='get'>"+
						"<tr>"+
						"<td bgcolor='#0B3861' align='center' colspan=5><font color='white'>Bank Account Information</font></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA'  width='50%'colspan='3'>Account Holder Name:</td>"+
						"<td bgcolor='#58ACFA'  width='50%' colspan='2'><input type='text' name='AHname' value='"+user+"'/></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA'  width='50%' colspan='3'>Routing Number:</td>"+
						"<td bgcolor='#58ACFA'  width='50%' colspan='2'><input type='text' name='Rnumber' value='0001232143'/></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#58ACFA'  width='50%' colspan='3'>Account Number:</td>"+
						"<td bgcolor='#58ACFA'  width='50%' colspan='2'><input type='text' name='anumber' value='3214314342354'/></td>"+
						"</tr>"+
						"<tr>"+
						"<td bgcolor='#0B3861' align='center' colspan=4>");
					if(i==0)
					{
						out.println("<input type='submit' value='Confirm Transaction'/>");
					}
					else
						{
						out.println("<input type='button' value='Confirm Transaction'/>");
						i--;
						}
						
						errormessage="";
						out.println("<input type='reset' value='Reset'/></td>"+
						"<td bgcolor='#0B3861' align='center' colspan=1><a href='ListCategoriesServlet'><font color='white'>[<u>Categories</u>]</a> <a href='LogoutServlet'><font color='white'>[<u>Log Out</u>]</font></a></td>"+
						"</tr>"+
						"</table>"+
						"</form>"+
						"</center>"+
						"</body>"+
						"</html>");
					
				session.setAttribute("quantity",quantity);
	            session.setAttribute("action", action);
	            session.setAttribute("sname", sname);
	            session.setAttribute("price",price);
	            session.setAttribute("total",total);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					
					con.close();
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				} 
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
		
	}

}
