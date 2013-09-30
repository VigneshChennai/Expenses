package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="user")
@SessionScoped
public class User {
	private String password;
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	private boolean loggedIn=false;
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void loginCheck() {
		try {
			if(!loggedIn) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String verifyPassword() {
		if(this.password.equalsIgnoreCase("sabarisri5")) {
			loggedIn = true;
			return "index";
		}
		msg="Wrong password";
		return "login";
	}
}
