package in.live.at.vigneshchennai.expenses.ejb;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class StatisticsService {

	@PersistenceContext(name = "Expenses")
	EntityManager em;

	public double getIncomesTotal() {
		TypedQuery<Double> q = em.createQuery(
				"select sum(e.income) from Incomes e", Double.class);
		Double totalIncomes = q.getSingleResult(); 
		return (totalIncomes != null)?totalIncomes:0.0;
	}

	public Double getIncomesTotal(int fromYear, int fromMonth, int fromDate,
			int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);

		Date from = calFrom.getTime();
		Date to = calTo.getTime();
		return getIncomesTotal(from, to);
	}

	public double getIncomesTotal(Date from, Date to) {
		java.sql.Date incFrom = new java.sql.Date(from.getTime());
		java.sql.Date incTo = new java.sql.Date(to.getTime());

		TypedQuery<Double> q = em.createQuery(
				"select sum(e.income) from Incomes e"
						+ " where e.when>=:fromDate and e.when<=:toDate",
						Double.class);
		q.setParameter("fromDate", incFrom);
		q.setParameter("toDate", incTo);
		Double totalIncomes = q.getSingleResult(); 
		return (totalIncomes != null)?totalIncomes:0.0;
	}

	public double getExpensesTotal() {
		TypedQuery<Double> q = em.createQuery(
				"select sum(e.expense) from Expenses e", Double.class);
		Double totalExpenses = q.getSingleResult(); 
		return (totalExpenses != null)?totalExpenses:0.0;
	}

	public double getExpensesTotal(int fromYear, int fromMonth, int fromDate,
			int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);

		Date from = calFrom.getTime();
		Date to = calTo.getTime();
		return getExpensesTotal(from, to);
	}

	public double getExpensesTotal(Date from, Date to) {
		java.sql.Timestamp incFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp incTo = new java.sql.Timestamp(to.getTime());

		TypedQuery<Double> q = em.createQuery(
				"select sum(e.expense) from Expenses e"
						+ " where e.when>=:fromDate and e.when<=:toDate",
						Double.class);
		q.setParameter("fromDate", incFrom);
		q.setParameter("toDate", incTo);
		Double totalExpenses = q.getSingleResult(); 
		return (totalExpenses != null)?totalExpenses:0.0;
	}

	public double getCashInHand() {

		Double incomeTotal = getIncomesTotal();
		Double expenseTotal = getExpensesTotal();
		
		double incomeTotalD = (incomeTotal != null)?incomeTotal:0.0;
		double expenseTotalD = (expenseTotal != null)?expenseTotal:0.0;
		return incomeTotalD - expenseTotalD;
	}
}
