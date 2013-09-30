package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.IncomeService;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="income")
@RequestScoped
public class Income {

	private long id;
	private Date when;
	private String category;
	private String description;
	private float income;
	
	@EJB
	private IncomeService incService;
	
	@ManagedProperty(value="#{status}")
	private Status status;
	@PostConstruct
	void init() {
		when = Calendar.getInstance().getTime();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public float getIncome() {
		return income;
	}
	public void setIncome(float income) {
		this.income = income;
	}
	public void add() {
		if(getIncService().insertIncome(when, income, category, description)) {
			status.setStatus(true);
			status.setMsg("Income has been successfully added");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to added Income successfully");
		}
		clear();
	}
	public String update() {
		if(getIncService().updateIncome(id,when, income, category, description)) {
			status.setStatus(true);
			status.setMsg("Income has been successfully updated");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to update Income successfully");
		}
		return "index?faces-redirect=true";
	}
	public String delete() {
		if(getIncService().deleteIncome(id)) {
			status.setStatus(true);
			status.setMsg("Income has been successfully deleted");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to delete Income successfully");
		}
		return "index?faces-redirect=true";
	}
	public String updateField(Income inc) {
		this.id = inc.id;
		this.category = inc.category;
		this.description = inc.description;
		this.income = inc.income;
		this.when = inc.when;
		return "updateIncome";
	}
	
	public void clear() {
		id=0;
		//when = null;
		category = null;
		income = 0.0f;
		description = null;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public IncomeService getIncService() {
		return incService;
	}
	public void setIncService(IncomeService incService) {
		this.incService = incService;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}