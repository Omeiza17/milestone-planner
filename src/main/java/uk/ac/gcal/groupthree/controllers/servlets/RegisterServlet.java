package uk.ac.gcal.groupthree.controllers.servlets;

import uk.ac.gcal.groupthree.controllers.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register", name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
	
	private AuthenticationService authenticationService = new AuthenticationService();
	
	@Override
	protected void doGet(HttpServletRequest request,
	                     HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("displayError", "hidden");
		request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
	                      HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		String errorMessage = null;
		if(!authenticationService.validateUsernamePassword(username, password)) {
			System.out.println("Invalid credentials");
			errorMessage = "Password must be more than 8 characters and username must be only alphanumeric";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
//			request.setAttribute("errorMessage", errorMessage);
//			request

		}else{
			if (authenticationService.register(username, password)) {
				response.sendRedirect("/login");

			} else{
				System.out.println("Invalid credentials");
				errorMessage = "This username already exists!";
				request.setAttribute("errorMessage", errorMessage);
				request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
			}
		}

	}
}
