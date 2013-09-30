package in.live.at.vigneshchennai.expenses.ejb;

import in.live.at.vigneshchennai.expenses.entity.Expense;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ExpenseService {
	
	@PersistenceContext(name = "Expenses")
	EntityManager em;
	
	public static List<Map<String, Object>> repackFetchedData(List<Expense> fetchedData) {
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		for (Expense exp : fetchedData) {
			Map<String, Object> data = new TreeMap<String, Object>(
					String.CASE_INSENSITIVE_ORDER);
			data.put("id", exp.getId());
			data.put("when", new java.util.Date(exp.getWhen().getTime()));
			data.put("expense", exp.getExpense());
			data.put("category", exp.getCategory());
			data.put("description", exp.getDescription());
			result.add(data);
		}
		return result;
	}

	public boolean insertExpense(Date when, float expense, String category, String description) {
		Expense exp = new Expense();
		exp.setCategory(category);
		exp.setWhen(new java.sql.Timestamp(when.getTime()));
		exp.setExpense(expense);
		exp.setDescription(description);
		em.persist(exp);
		return true;
	}
	public boolean updateExpense(long id, Date when, float expense, String category, String description) {
		Expense exp = em.getReference(Expense.class, new Long(id));
		exp.setCategory(category);
		exp.setWhen(new java.sql.Timestamp(when.getTime()));
		exp.setExpense(expense);
		exp.setDescription(description);
		em.merge(exp);
		return true;
	}
	public boolean deleteExpense(long id) {
		Expense exp = em.getReference(Expense.class, new Long(id));
		em.remove(exp);
		return true;
	}
	public List<String> getCategories() {
		TypedQuery<String> q = em.createQuery(
				"select distinct e.category from Expenses e", String.class);
		if (q.getMaxResults() > 0) {
			List<String> rs = q.getResultList();
			return rs;
		}
		return new LinkedList<String>();
	}
	public List<String> getCategories(int fromYear, int fromMonth,
			int fromDate, int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);
		
		Date from = calFrom.getTime();
		Date to = calTo.getTime();
		return getCategories(from, to);
	}
	
	public List<String> getCategories(Date from, Date to) {
		System.out.println("From Date : " + from);
		System.out.println("To Date : " + to);
		java.sql.Date expFrom = new java.sql.Date(from.getTime());
		java.sql.Date expTo = new java.sql.Date(to.getTime());
		TypedQuery<String> q = em.createQuery(
				"select distinct e.category from Expenses e where " +
				"e.when>=:fromDate and e.when<=:toDate", String.class);
		q.setParameter("fromDate", expFrom);
		q.setParameter("toDate", expTo);
		if (q.getMaxResults() > 0) {
			List<String> rs = q.getResultList();
			return rs;
		}
		return new LinkedList<String>();
	}
	public List<Map<String, Object>> getExpenses(String category) {

		TypedQuery<Expense> q = em.createQuery("select e from Expenses e"
				+ " where e.category=:category", Expense.class);
		q.setParameter("category", category);
		if (q.getMaxResults() > 0) {
			List<Expense> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

	public List<Map<String, Object>> getExpenses(String category, int fromYear, int fromMonth,
			int fromDate, int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);
		Date from = calFrom.getTime();
		Date to = calTo.getTime();
	
		return getExpenses(category, from, to);
	}

	public List<Map<String, Object>> getExpenses(String category, Date from,
			Date to) {
		java.sql.Timestamp expFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp expTo = new java.sql.Timestamp(to.getTime());

		TypedQuery<Expense> q = em
				.createQuery(
						"select e from Expenses e"
								+ " where e.category=:category and e.when>=:fromDate and e.when<=:toDate",
						Expense.class);
		q.setParameter("category", category);
		q.setParameter("fromDate", expFrom);
		q.setParameter("toDate", expTo);

		if (q.getMaxResults() > 0) {
			List<Expense> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

	public List<Map<String, Object>> getExpenses(int fromYear, int fromMonth,
			int fromDate, int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);
		Date from = calFrom.getTime();
		Date to = calTo.getTime();
		return getExpenses(from, to);
	}

	public List<Map<String, Object>> getExpenses(Date from, Date to) {

		java.sql.Timestamp expFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp expTo = new java.sql.Timestamp(to.getTime());

		TypedQuery<Expense> q = em
				.createQuery("select e from Expenses e"
						+ " where e.when>=:fromDate and e.when<=:toDate",
						Expense.class);
		q.setParameter("fromDate", expFrom);
		q.setParameter("toDate", expTo);

		if (q.getMaxResults() > 0) {
			List<Expense> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

}
