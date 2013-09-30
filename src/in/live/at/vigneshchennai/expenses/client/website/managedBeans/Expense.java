package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.ExpenseService;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="expense")
@RequestScoped
public class Expense {

	private long id;
	private Date when;
	private String category;
	private String description;
	private float expense;
	
	@EJB
	private ExpenseService expService;
	
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
	public float getExpense() {
		return expense;
	}
	public void setExpense(float expense) {
		this.expense = expense;
	}
	public void add() {
		if(getExpService().insertExpense(when, expense, category, description)) {
			status.setStatus(true);
			status.setMsg("Expense has been successfully added");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to added Expense successfully");
		}
		clear();
	}
	public String update() {
		if(getExpService().updateExpense(id,when, expense, category, description)) {
			status.setStatus(true);
			status.setMsg("Expense has been successfully updated");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to update Expense successfully");
		}
		return "index?faces-redirect=true";
	}
	public String delete() {
		if(getExpService().deleteExpense(id)) {
			status.setStatus(true);
			status.setMsg("Expense has been successfully deleted");
		} else {
			status.setStatus(true);
			status.setMsg("Unable to delete Expense successfully");
		}
		return "index?faces-redirect=true";
	}
	public String updateField(Expense exp) {
		this.id = exp.id;
		this.category = exp.category;
		this.description = exp.description;
		this.expense = exp.expense;
		this.when = exp.when;
		return "updateExpense";
	}
	
	public void clear() {
		id=0;
		//when = null;
		//category = null;
		expense = 0.0f;
		description = null;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ExpenseService getExpService() {
		return expService;
	}
	public void setExpService(ExpenseService expService) {
		this.expService = expService;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}