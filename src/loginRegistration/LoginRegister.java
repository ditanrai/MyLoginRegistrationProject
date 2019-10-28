package loginRegistration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns="/loginRegister")
public class LoginRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
   
    public LoginRegister() {
        super();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		String userName=request.getParameter("username");
		String password=request.getParameter("password1");
		String submitType=request.getParameter("submit");
		
		Customer c=new Customer();
		
		 c.setUsername(userName); 
		 c.setPassword(password);
		 HttpSession session=request.getSession(true);
		 
		CustomerDAOImpl cd=new CustomerDAOImpl();
		Customer cc = cd.getCustomer(userName, password);

		if (submitType.equals("login") && cc != null && cc.getName() != null) {
			request.setAttribute("message", cc.getName());

			
			session.setAttribute("loggedin", "true");
			response.setStatus(HttpServletResponse.SC_OK);
			//response.sendRedirect("session.jspf");
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
			
		}else if(submitType.equals("register")) {
			c.setName(request.getParameter("name"));
			c.setPassword(password);
			c.setUsername(userName);
			cd.insertCustomer(c);
			request.setAttribute("successMessage","Registration successful, please login to continue.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		}else {
			request.setAttribute("message","Data not found, click on Register.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}

}
