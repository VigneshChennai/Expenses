package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.IncomeService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="incomes")
@ViewScoped
public class Incomes {

	private List<Income> incomes;
	private float totalIncome;
	@ManagedProperty(value="#{timeLine}")
	private TimeLine timeLine;
	
	@ManagedProperty(value="#{incomesCategories}")
	private IncomesCategories incomesCategories;
	@EJB
	private IncomeService incService;
	
	private List<SelectListener> selectListeners;
	
	public Incomes() {
		incomes = new LinkedList<Income>();
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
		incomesCategories.addSelectListener(selectListener);
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
	private void updateIncomesListWithResult(List<Map<String, Object>> incomes) {
		this.incomes.clear();
		float total = 0.0f;
		for(Map<String, Object> income: incomes) {
			Income inc = new Income();
			inc.setId((Long)income.get("id"));
			inc.setCategory((String)income.get("category"));
			inc.setWhen((Date) income.get("when"));
			Float e = (Float)income.get("income");
			inc.setIncome((e == null)?0.0f:e);
			total+=(Float)income.get("income");
			inc.setDescription((String)income.get("description"));
			this.incomes.add(inc);
		}
		totalIncome = total;
	}
	
	public void updateExpenses() {
		String category = incomesCategories.getSelected();
		if(category == null || category.isEmpty()) {
			List<Map<String, Object>> expenses 
						= getIncService().getIncomes(timeLine.getFromYear(),
								timeLine.getFromMonth(),
								timeLine.getFromdate(),
								timeLine.getToYear(),
								timeLine.getToMonth(),
								timeLine.getTodate());
			updateIncomesListWithResult(expenses);
		} else {
			List<Map<String, Object>> expenses 
						= getIncService().getIncomes(category, timeLine.getFromYear(),
								timeLine.getFromMonth(),
								timeLine.getFromdate(),
								timeLine.getToYear(),
								timeLine.getToMonth(),
								timeLine.getTodate());
			updateIncomesListWithResult(expenses);
		}
	}
	public List<Income> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}

	public IncomeService getIncService() {
		return incService;
	}
	public void setIncService(IncomeService incService) {
		this.incService = incService;
	}
	public float getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(float totalIncome) {
		this.totalIncome = totalIncome;
	}
	public TimeLine getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;
	}
	public IncomesCategories getIncomesCategories() {
		return incomesCategories;
	}
	public void setIncomesCategories(IncomesCategories incomesCategories) {
		this.incomesCategories = incomesCategories;
	}
	public void triggerSelectEvent() {
		for(SelectListener listener: selectListeners) {
			listener.selected();
		}
	}
}
