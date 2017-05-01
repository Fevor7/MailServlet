package by.bsu.mail.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import action.MailThead;

@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties properties = new Properties();
		ServletContext context = getServletContext();
		String filename = context.getInitParameter("mail");
		properties.load(context.getResourceAsStream(filename));
		MailThead mailOperator =
				new MailThead(request.getParameter("to"),
							  request.getParameter("subject"),
                              request.getParameter("body"),
							  properties);
		mailOperator.start();	
		request.getRequestDispatcher("/send.jsp").forward(request,response);
		
	}

}
