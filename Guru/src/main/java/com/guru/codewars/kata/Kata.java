package com.guru.codewars.kata;

import java.util.ArrayList;
import java.util.List;

 public class Kata {
	 
	 private float totalCompleted;
	 private List<String> languages = new ArrayList<>();
	 private String publishedAt;
	 private float totalAttempts;
	 private ApprovedBy ApprovedByObject;
	 private String description;
	 private String approvedAt;
	 private String url;
	 private List<String> tags = new ArrayList<>();
	 private String createdAt;
	 private Unresolved UnresolvedObject;
	 private float voteScore;
	 private CreatedBy CreatedByObject;
	 private float totalStars;
	 private String name;
	 private Rank RankObject;
	 private String id;
	 private String category;
	 private String slug;
	 private boolean contributorsWanted;
	 
	public float getTotalCompleted() {
		return totalCompleted;
	}
	public Kata(float totalCompleted, List<String> languages2, String publishedAt, float totalAttempts,
			ApprovedBy approvedByObject, String description, String approvedAt, String url, List<String> tags2,
			String createdAt, Unresolved unresolvedObject, float voteScore, CreatedBy createdByObject, float totalStars,
			String name, Rank rankObject, String id, String category, String slug, boolean contributorsWanted) {
		super();
		this.totalCompleted = totalCompleted;
		this.languages = languages2;
		this.publishedAt = publishedAt;
		this.totalAttempts = totalAttempts;
		ApprovedByObject = approvedByObject;
		this.description = description;
		this.approvedAt = approvedAt;
		this.url = url;
		this.tags = tags2;
		this.createdAt = createdAt;
		UnresolvedObject = unresolvedObject;
		this.voteScore = voteScore;
		CreatedByObject = createdByObject;
		this.totalStars = totalStars;
		this.name = name;
		RankObject = rankObject;
		this.id = id;
		this.category = category;
		this.slug = slug;
		this.contributorsWanted = contributorsWanted;
	}
	public String getPublishedAt() {
		return publishedAt;
	}
	public float getTotalAttempts() {
		return totalAttempts;
	}
	public ApprovedBy getApprovedByObject() {
		return ApprovedByObject;
	}
	public String getDescription() {
		return description;
	}
	public String getApprovedAt() {
		return approvedAt;
	}
	public String getUrl() {
		return url;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public Unresolved getUnresolvedObject() {
		return UnresolvedObject;
	}
	public float getVoteScore() {
		return voteScore;
	}
	public CreatedBy getCreatedByObject() {
		return CreatedByObject;
	}
	public float getTotalStars() {
		return totalStars;
	}
	public String getName() {
		return name;
	}
	public Rank getRankObject() {
		return RankObject;
	}
	public String getId() {
		return id;
	}
	public String getCategory() {
		return category;
	}
	public String getSlug() {
		return slug;
	}
	public boolean isContributorsWanted() {
		return contributorsWanted;
	}
	public void setTotalCompleted(float totalCompleted) {
		this.totalCompleted = totalCompleted;
	}
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	public void setTotalAttempts(float totalAttempts) {
		this.totalAttempts = totalAttempts;
	}
	public void setApprovedByObject(ApprovedBy approvedByObject) {
		ApprovedByObject = approvedByObject;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public void setUnresolvedObject(Unresolved unresolvedObject) {
		UnresolvedObject = unresolvedObject;
	}
	public void setVoteScore(float voteScore) {
		this.voteScore = voteScore;
	}
	public void setCreatedByObject(CreatedBy createdByObject) {
		CreatedByObject = createdByObject;
	}
	public void setTotalStars(float totalStars) {
		this.totalStars = totalStars;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRankObject(Rank rankObject) {
		RankObject = rankObject;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public void setContributorsWanted(boolean contributorsWanted) {
		this.contributorsWanted = contributorsWanted;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setLanguages(ArrayList<String> languages) {
		this.languages = languages;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	 



 
 
 
}
