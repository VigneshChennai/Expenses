package in.live.at.vigneshchennai.expenses.ejb;

import in.live.at.vigneshchennai.expenses.entity.Income;

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
public class IncomeService {

	@PersistenceContext(name = "Expenses")
	EntityManager em;

	public static List<Map<String, Object>> repackFetchedData(
			List<Income> fetchedData) {
		List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
		for (Income inc : fetchedData) {
			Map<String, Object> data = new TreeMap<String, Object>(
					String.CASE_INSENSITIVE_ORDER);
			data.put("id", inc.getId());
			data.put("when", new java.util.Date(inc.getWhen().getTime()));
			data.put("income", inc.getIncome());
			data.put("category", inc.getCategory());
			data.put("description", inc.getDescription());
			result.add(data);
		}
		return result;
	}

	public boolean insertIncome(Date when, float income, String category,
			String description) {
		Income inc = new Income();
		inc.setCategory(category);
		inc.setWhen(new java.sql.Timestamp(when.getTime()));
		inc.setIncome(income);
		inc.setDescription(description);
		em.persist(inc);
		return true;
	}

	public List<String> getCategories() {
		TypedQuery<String> q = em.createQuery(
				"select distinct e.category from Incomes e", String.class);
		if (q.getMaxResults() > 0) {
			List<String> rs = q.getResultList();
			return rs;
		}
		return new LinkedList<String>();
	}

	public boolean updateIncome(long id, Date when, float income,
			String category, String description) {
		Income inc = em.getReference(Income.class, new Long(id));
		inc.setCategory(category);
		inc.setWhen(new java.sql.Timestamp(when.getTime()));
		inc.setIncome(income);
		inc.setDescription(description);
		em.merge(inc);
		return true;
	}

	public boolean deleteIncome(long id) {
		Income inc = em.getReference(Income.class, new Long(id));
		em.remove(inc);
		return true;
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
		java.sql.Timestamp expFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp expTo = new java.sql.Timestamp(to.getTime());
		TypedQuery<String> q = em
				.createQuery("select distinct e.category from Incomes e where "
						+ "e.when>=:fromDate and e.when<=:toDate", String.class);
		q.setParameter("fromDate", expFrom);
		q.setParameter("toDate", expTo);
		if (q.getMaxResults() > 0) {
			List<String> rs = q.getResultList();
			return rs;
		}
		return new LinkedList<String>();
	}

	public List<Map<String, Object>> getIncomes(String category) {

		TypedQuery<Income> q = em.createQuery("select e from Incomes e"
				+ " where e.category=:category", Income.class);
		q.setParameter("category", category);
		if (q.getMaxResults() > 0) {
			List<Income> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

	public List<Map<String, Object>> getIncomes(String category, int fromYear,
			int fromMonth, int fromDate, int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);
		Date from = calFrom.getTime();
		Date to = calTo.getTime();

		return getIncomes(category, from, to);
	}

	public List<Map<String, Object>> getIncomes(String category, Date from,
			Date to) {
		java.sql.Timestamp incFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp incTo = new java.sql.Timestamp(to.getTime());

		TypedQuery<Income> q = em
				.createQuery(
						"select e from Incomes e"
								+ " where e.category=:category and e.when>=:fromDate and e.when<=:toDate",
						Income.class);
		q.setParameter("category", category);
		q.setParameter("fromDate", incFrom);
		q.setParameter("toDate", incTo);

		if (q.getMaxResults() > 0) {
			List<Income> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

	public List<Map<String, Object>> getIncomes(int fromYear, int fromMonth,
			int fromDate, int toYear, int toMonth, int toDate) {
		Calendar calFrom = Calendar.getInstance();
		calFrom.set(fromYear, fromMonth - 1, fromDate);
		Calendar calTo = Calendar.getInstance();
		calTo.set(toYear, toMonth - 1, toDate);
		Date from = calFrom.getTime();
		Date to = calTo.getTime();
		return getIncomes(from, to);
	}

	public List<Map<String, Object>> getIncomes(Date from, Date to) {

		java.sql.Timestamp expFrom = new java.sql.Timestamp(from.getTime());
		java.sql.Timestamp expTo = new java.sql.Timestamp(to.getTime());

		TypedQuery<Income> q = em.createQuery("select e from Incomes e"
				+ " where e.when>=:fromDate and e.when<=:toDate", Income.class);
		q.setParameter("fromDate", expFrom);
		q.setParameter("toDate", expTo);

		if (q.getMaxResults() > 0) {
			List<Income> rs = q.getResultList();
			return repackFetchedData(rs);
		}
		return new LinkedList<Map<String, Object>>();
	}

}
