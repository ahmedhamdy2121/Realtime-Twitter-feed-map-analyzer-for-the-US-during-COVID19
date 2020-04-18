package bdt.streamdata.hbase;

import java.io.Serializable;

import bdt.streamdata.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TweetRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String location;
	private String createdAt;
	private String country;
	public static TweetRecord of(String id, String location, String createdAt, String country) {
		return new TweetRecord(id, location, createdAt, country);
	}
	public static TweetRecord of(Tweet tw) {
		String id = String.valueOf(tw.getId());
		String location = getLocation(tw.getUser().getLocation());
		String createdAt = tw.getCreatedAt();
		String country = getCountry(tw.getUser().getLocation());
		return new TweetRecord(id, location, createdAt, country);
	}
	private static String getCountry(String location2) {
		if (location2 != null & location2.split(", ").length >=2)			
			return location2.split(", ")[location2.split(", ").length - 1];
		return "";
	}
	private static String getLocation(String location2) {
		if (location2 != null & location2.split(", ").length >=2)			
			return location2.split(", ")[0];
		return "";
	}


}
