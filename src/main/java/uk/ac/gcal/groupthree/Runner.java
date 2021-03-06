package uk.ac.gcal.groupthree;

import uk.ac.gcal.groupthree.DAO.DAOFactory;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;
import java.util.logging.Logger;

public class Runner {
	private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
	private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());
	private static DAOFactory daoFactory;
	
	public static void main(String[] args) throws ServletException {
		Tomcat tomcat = new Tomcat();
		//The port that we should run on can be set into an environment variable
		//Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) webPort = "8080";
		
		tomcat.setPort(Integer.valueOf(webPort));
		System.out.println(WEBAPP_DIR_LOCATION);
		StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
		System.out.println("configuring app with basedir: " + new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
		
		// Declare an alternative location for your "WEB-INF/classes" dir
		// Servlet 3.0 annotation will work
		File additionWebInfClasses = new File("target/classes");
		WebResourceRoot resources = new StandardRoot(ctx);
		resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
				additionWebInfClasses.getAbsolutePath(), "/"));
		ctx.setResources(resources);
		
		try {
			tomcat.start();
			daoFactory = new DAOFactory();
		} catch (LifecycleException e) {
			LOGGER.warning(e.getMessage());
			e.printStackTrace();
		}
		tomcat.getServer().await();
	}
}
