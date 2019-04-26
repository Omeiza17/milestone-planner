package controllers.servlets;

import DAO.DAOFactory;
import DAO.MilestoneDAO;
import domain.model.Milestone;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controllers.servlets.MilestoneServlet.getFullURL;

@WebServlet(urlPatterns = "/project/edit/*")
public class EditMilestoneServlet extends HttpServlet {
  MilestoneDAO dao = DAOFactory.getMilestoneDAO();
  @Override
  protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
    String ptitle = request.getParameter("title");
    System.out.println("Editing Milestone from "+ getFullURL(request));
    System.out.println(request.getParameter("mlStatus"));
    System.out.println("------------------------");
    System.out.println("Status is "+request.getParameter("mlStatus"));
        System.out.println(request.getParameter("mlID")+
        request.getParameter("mlTitle")+
        request.getParameter("mlDescription")+
        request.getParameter(request.getParameter("mlStatus"))+
        request.getParameter("mlStartDate")+
        request.getParameter("mlDueDate"));
//        request.getParameter("mlEndDate"));

        Milestone newML = new Milestone(
        request.getParameter("mlID"),
        request.getParameter("mlTitle"),
        request.getParameter("mlDescription"),
        request.getParameter("mlStatus"),
        request.getParameter("mlStartDate"),
        request.getParameter("mlDueDate"),
        ptitle
        );
//    System.out.println(dao.milestoneExists(newML.getId()));
    if(dao.milestoneExists(newML.getId())) {
      System.out.println("EXISTS "+newML.getTitle()+ newML.getId());
      System.out.println(dao.editMilestone(newML));
    }
    System.out.println("project title"+ptitle);
    request.removeAttribute("mlID");
    response.sendRedirect("/project/?title="+ptitle);
//    System.out.println("--put--");
    System.out.println(request.getPathTranslated());
//    request.getRequestDispatcher("/WEB-INF/views/project.jsp").forward(request, response);


  }
}
