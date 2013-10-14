package stock;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Bank
 */
public class Bank extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("--------");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		response.setContentType("text/xml");
		String total=(String)session.getAttribute("total");
		double cost = Double.parseDouble(total);
		System.out.println(cost);
		String user=(String)session.getAttribute("username");
		String action=session.getAttribute("action").toString();
		System.out.println("user: "+user);
		//BankCustomer customer = BankCustomer.getCustomer(user);
		BankSOAP b=new BankSOAP();
		String FirstName=b.getValue(b.getResponseFromWebService("sdandi", "getFirstName",user), "getFirstName");
		String LastName=b.getValue(b.getResponseFromWebService("sdandi", "getLastName",user),"getLastName");
		String Balance=b.getValue(b.getResponseFromWebService("sdandi", "getBalance",user),"getBalance");
		System.out.println(FirstName);
		System.out.println(LastName);
		System.out.println(Balance);
		double balance=Double.parseDouble(Balance);

		//System.out.println("cust: "+customer.getFirstName());
		System.out.println(action);
		System.out.println(cost);


		if (cost > balance && action.equals("buy")){
			//			out.print("absc");
			out.println("<bank_response>");
			out.println("<FirstName>"+FirstName+"</FirstName>");
			out.println("<LastName>"+LastName+"</LastName>");
			out.println("<Id>"+user+"</Id>");
			out.println("<Balance>"+Balance+"</Balance>");
			out.println("<Status>Unstable</Status>");
			out.println("</bank_response>");
		}


		if(cost < balance && action.equals("buy")){
			out.println("<bank_response>");
			out.println("<FirstName>"+FirstName+"</FirstName>");
			out.println("<LastName>"+LastName+"</LastName>");
			out.println("<Id>"+user+"</Id>");
			out.println("<Balance>"+Balance+"</Balance>");

			b.setBalanceInWebService("sdandi", "setBalance",user,balance-cost);
			String Balance1=b.getValue(b.getResponseFromWebService("sdandi", "getBalance",user), "getBalance");
			out.println("<Status>Stable</Status>");
			out.println("<NewBalance>"+Balance1+"</NewBalance>");
			out.println("</bank_response>");
		}
		else
		{
			System.out.println(action.equals("sell"));
			if(action.equals("sell")){
				out.println("<bank_response>");
				out.println("<FirstName>"+FirstName+"</FirstName>");
				out.println("<LastName>"+LastName+"</LastName>");
				out.println("<Id>"+user+"</Id>");
				out.println("<Balance>"+Balance+"</Balance>");
				b.setBalanceInWebService("sdandi", "setBalance",user,balance+cost);
				String Balance1=b.getValue(b.getResponseFromWebService("sdandi", "getBalance",user), "getBalance");

				out.println("<Status>Stable</Status>");
				out.println("<NewBalance>"+Balance1+"</NewBalance>");
				out.println("</bank_response>");
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
