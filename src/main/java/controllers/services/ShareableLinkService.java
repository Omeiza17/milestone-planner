package controllers.services;
/*
ebenezergraham created on 4/26/19
*/

import DAO.DAOFactory;
import DAO.MilestoneDAO;
import DAO.ShareableLinkDAO;
import domain.model.LinkDetails;
import domain.model.Milestone;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareableLinkService {
	
	ShareableLinkDAO dao = DAOFactory.getShareableLinkDAO();
	MilestoneDAO milestoneDAO = DAOFactory.getMilestoneDAO();
	
	public String generateLink(String projectId) {
		String token = null;
		LinkDetails details = dao.loadByProjectId(projectId);
		if (details == null){
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[32];
			random.nextBytes(bytes);
			Base64.Encoder encoder = Base64.getUrlEncoder();
			token = encoder.encodeToString(bytes);
			if (dao.store(projectId, token)) return null;
		}else {
			token = details.getLink();
		}
		return token;
	}
	
	public Map<String, Object> getMilestones(String id) {
		Map<String, Object> items = new HashMap<>();
		LinkDetails details = dao.load(id);
		List<Milestone> milestones = milestoneDAO.findMilestones(details.getProjectId());
		System.out.println("milestone list: " + milestones.size());
		System.out.println(details.toString());
		items.put("title", details.getProjectId());
		items.put("milestones", milestones);
		return items;
	}
}