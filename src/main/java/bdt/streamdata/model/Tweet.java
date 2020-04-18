package bdt.streamdata.model;

public class Tweet {
    private long id;
    private String text;
    private String lang;
    private User user;
    private Coordinates coordinates;
    private String created_at;

    public Tweet(long id, String text, String lang, User user,  Coordinates coordinates, String createdAt) {
        this.id = id;
        this.text = text;
        this.lang = lang;
        this.user = user;
        this.coordinates = coordinates;
        this.created_at = createdAt;
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String createdAt) {
		this.created_at = createdAt;
	}
}
