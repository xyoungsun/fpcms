package com.fpcms.common.blog_post;

import java.util.HashMap;
import java.util.Map;

public class Blog {
	
	private String username;
	private String password;
	private String title;
	private String content;
	private String metaDescription;
	private Map<String,String> ext = new HashMap<String,String>();
	
	public Blog() {
	}
	
	public Blog(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Map<String, String> getExt() {
		return ext;
	}
	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}
	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	
}
