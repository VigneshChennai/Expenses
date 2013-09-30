package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.ExpenseService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="expenses")
@ViewScoped
public class Expenses {

	private List<Expense> expenses;
	private float totalExpense;
	@ManagedProperty(value="#{timeLine}")
	private TimeLine timeLine;
	@ManagedProperty(value="#{expensesCategories}")
	private ExpensesCategories expensesCategories;
	@EJB
	private ExpenseService expService;
	
	private List<SelectListener> selectListeners;
	
	public Expenses() {
		expenses = new LinkedList<Expense>();
		selectListeners = new LinkedList<SelectListener>();
	}
	@PostConstruct
    public void init() {
		updateExpenses();
		SelectListener selectListener = new SelectListener() {
			@Override
			public void selected() {
				updateExpenses();
			}
		};
		timeLine.addSelectListener(selectListener);
		expensesCategories.addSelectListener(selectListener);
	}
	public void addSelectListener(SelectListener listener) {
		selectListeners.add(listener);
	}
	public void removeSelectListener(SelectListener listener) {
		selectListeners.remove(listener);
	}
	public void removeAllSelectListener() {
		selectListeners.clear();
	}
	private void updateExpensesListWithResult(List<Map<String, Object>> expenses) {
		this.expenses.clear();
		float total = 0.0f;
		for(Map<String, Object> expense: expenses) {
			Expense exp = new Expense();
			exp.setId((Long)expense.get("id"));
			exp.setCategory((String)expense.get("category"));
			exp.setWhen((Date) expense.get("when"));
			Float e = (Float)expense.get("expense");
			exp.setExpense((e == null)?0.0f:e);
			total+=(Float)expense.get("expense");
			exp.setDescription((String)expense.get("description"));
			this.expenses.add(exp);
		}
		totalExpense = total;
	}
	
	public void updateExpenses() {
		String category = expensesCategories.getSelected();
		if(category == null || category.isEmpty()) {
			List<Map<String, Object>> expenses 
						= getExpService().getExpenses(timeLine.getFromYear(),
								timeLine.getFromMonth(),
								timeLine.getFromdate(),
								timeLine.getToYear(),
								timeLine.getToMonth(),
								timeLine.getTodate());
			updateExpensesListWithResult(expenses);
		} else {
			List<Map<String, Object>> expenses 
						= getExpService().getExpenses(category, timeLine.getFromYear(),
								timeLine.getFromMonth(),
								timeLine.getFromdate(),
								timeLine.getToYear(),
								timeLine.getToMonth(),
								timeLine.getTodate());
			updateExpensesListWithResult(expenses);
		}
	}
	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public ExpenseService getExpService() {
		return expService;
	}
	public void setExpService(ExpenseService expService) {
		this.expService = expService;
	}
	public float getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(float totalExpense) {
		this.totalExpense = totalExpense;
	}
	public TimeLine getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;
	}
	public ExpensesCategories getExpensesCategories() {
		return expensesCategories;
	}
	public void setExpensesCategories(ExpensesCategories expensesCategories) {
		this.expensesCategories = expensesCategories;
	}
	public void triggerSelectEvent() {
		for(SelectListener listener: selectListeners) {
			listener.selected();
		}
	}
}
