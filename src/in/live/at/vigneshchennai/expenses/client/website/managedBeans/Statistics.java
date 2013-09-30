package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.StatisticsService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="statistics")
@ViewScoped
public class Statistics {

	private double totalExpenses;
	private double totalIncomes;
	private double savings;
	
	private double cashInHand;
	
	@ManagedProperty(value="#{timeLine}")
	private TimeLine timeLine;
	
	@EJB
	private StatisticsService statisticsService;
	
	
	public Statistics() {
	}
	
	@PostConstruct
    public void init() {
		updateTimeDependedStatistics();
		updateTimeIndependedStatistics();
		SelectListener selectListener = new SelectListener() {
			@Override
			public void selected() {
				updateTimeDependedStatistics();
			}
		};
		getTimeLine().addSelectListener(selectListener);
	}

	
	public void updateTimeDependedStatistics() {
		this.setTotalExpenses(getStatisticsService().getExpensesTotal(getTimeLine().getFromYear(),
								getTimeLine().getFromMonth(),
								getTimeLine().getFromdate(),
								getTimeLine().getToYear(),
								getTimeLine().getToMonth(),
								getTimeLine().getTodate()));
		
		this.setTotalIncomes(getStatisticsService().getIncomesTotal(getTimeLine().getFromYear(),
				getTimeLine().getFromMonth(),
				getTimeLine().getFromdate(),
				getTimeLine().getToYear(),
				getTimeLine().getToMonth(),
				getTimeLine().getTodate()));
		
		this.setSavings(getTotalIncomes() - getTotalExpense());
		
	}

	public void updateTimeIndependedStatistics() {
		setCashInHand(statisticsService.getCashInHand());
	}
	
	public void updateStatistics() {
		updateTimeDependedStatistics();
		updateTimeIndependedStatistics();
	}
	
	public double getTotalIncomes() {
		return totalIncomes;
	}
	
	public void setTotalIncomes(double totalIncomes) {
		this.totalIncomes = totalIncomes; 
	}

 
	public double getTotalExpense() {
		return totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public StatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}

	public double getCashInHand() {
		return cashInHand;
	}

	public void setCashInHand(double cashInHand) {
		this.cashInHand = cashInHand;
	}

	public TimeLine getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;
	}
}
