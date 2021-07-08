package com.arpankarki.lockedmecredentialstore.user;

public class UserCredential {

	private String url;
	private String userName;
	private String password;

	public UserCredential(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "url=" + url + ", userName=" + userName + ", password=" + password;
	}

}
