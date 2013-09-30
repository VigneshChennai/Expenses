package in.live.at.vigneshchennai.expenses.entity;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name="Expenses")
public class Expense {

	@Id
	@GeneratedValue
	private long id;
	private Timestamp when;
	private String category;
	@Column(length=500)
	private String description;
	private float expense;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getWhen() {
		return when;
	}
	public void setWhen(Timestamp when) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
