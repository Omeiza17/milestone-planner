package domain.model;

public class Milestone {
	
	private String id;
	private String title;
	private String date;
	private String description;
	
	public Milestone(String id, String title, String date, String description) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
