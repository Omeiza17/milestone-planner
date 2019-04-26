package DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DBUtil;

import java.sql.*;

public class DAOFactory implements AutoCloseable{
	@SuppressWarnings("unused")
	static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);
	
	private static UserDAO userDAO = new UserDAO();
	private static MilestoneDAO milestoneDAO = new MilestoneDAO();
	private static ProjectDAO projectDAO = new ProjectDAO();
	private static ShareableLinkDAO shareableLinkDAO = new ShareableLinkDAO();
	private Connection connection;
	private static final String db = "jdbc:h2:~/mp;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";
	
	DBUtil dbUtil = new DBUtil();;
	
	public DAOFactory() {
		this.connection = getConnection();
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection(db, "", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static UserDAO getUserDAO() {
		return userDAO;
	}
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public static MilestoneDAO getMilestoneDAO() {
		return milestoneDAO;
	}
	
	public void setMilestoneDAO(MilestoneDAO milestoneDAO) {
		this.milestoneDAO = milestoneDAO;
	}
	
	public static ProjectDAO getProjectDAO() {
		return projectDAO;
	}
	
	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public static ShareableLinkDAO getShareableLinkDAO() {
		return shareableLinkDAO;
	}
	
	public static void setShareableLinkDAO(ShareableLinkDAO shareableLinkDAO) {
		DAOFactory.shareableLinkDAO = shareableLinkDAO;
	}
	
	@Override
	public void close() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
