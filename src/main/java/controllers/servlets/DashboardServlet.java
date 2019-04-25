package controllers.servlets;

import DAO.H2Project;
import DAO.H2db;
import controllers.services.UserService;
import domain.model.Project;
import domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {
//  H2Project h2Project= new H2Project();

  @Override
  protected void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null) {
      User usr = (User) session.getAttribute("userobj");
      System.out.println("Dashboard Servlet - Session username: " + usr.getUserName());
      H2Project dao= new H2Project();
      List<Project> projectList = dao.findProjects(usr.getId());
      request.setAttribute("projectList", projectList);
      request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
      response.sendRedirect("/login");
    }

  }

  @Override
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

  }
}
