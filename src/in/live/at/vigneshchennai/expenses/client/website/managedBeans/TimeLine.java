package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="timeLine")
@ViewScoped
public class TimeLine {

	private int fromYear;
	private int fromMonth;
	private int fromDate;
	
	private int toYear;
	private int toMonth;
	private int toDate;
	
	private String timeLineDesc;
	
	private List<SelectListener> selectListeners;
	
	private List<Integer> yearList = new LinkedList<Integer>();
	private List<Integer> monthList = new LinkedList<Integer>();
	private List<Integer> dateList = new LinkedList<Integer>();
	
	private String timelineMode;
	
	public TimeLine() {
		selectListeners = new LinkedList<SelectListener>();
		
	}
	
	private String[][] timeLineDescriptions = {
							{"thisweek", "This Week"},
							{"thismonth", "This Month"},
							{"thisyear", "This Year"},
							{"lastweek", "Last Week"},
							{"lastmonth", "Last Month"},
							{"lastyear", "Last Year"},
	}; 
	
	@PostConstruct
    public void init() {
		Calendar cal = Calendar.getInstance();
		toYear = cal.get(Calendar.YEAR);
		toMonth = cal.get(Calendar.MONTH) + 1;
		toDate = cal.get(Calendar.DATE);
		
		fromYear = toYear;
		fromMonth = toMonth;
		fromDate = 1;
		timeLineDesc = "thismonth";
		timelineMode = "description";
		for(int i=1900; i<2100; i++) {
			yearList.add(i);
		}
		for(int i=1; i<=12; i++) {
			monthList.add(i);
		}
		for(int i=1; i<=31; i++) {
			dateList.add(i);
		}
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
	public int getFromYear() {
		return fromYear;
	}
	public void setFromYear(int fromYear) {
		this.fromYear = fromYear;
	}
	public int getFromMonth() {
		return fromMonth;
	}
	public void setFromMonth(int fromMonth) {
		this.fromMonth = fromMonth;
	}
	public int getFromdate() {
		return fromDate;
	}
	public void setFromdate(int fromdate) {
		this.fromDate = fromdate;
	}
	public int getToYear() {
		return toYear;
	}
	public void setToYear(int toYear) {
		this.toYear = toYear;
	}
	public int getToMonth() {
		return toMonth;
	}
	public void setToMonth(int toMonth) {
		this.toMonth = toMonth;
	}
	public int getTodate() {
		return toDate;
	}
	public void setTodate(int todate) {
		this.toDate = todate;
	}

	public String getTimeLineDesc() {
		return timeLineDesc;
	}

	public void setTimeLineDesc(String timeLineDesc) {
		this.timeLineDesc = timeLineDesc;
		Calendar cal = Calendar.getInstance();
        if (timeLineDesc.equalsIgnoreCase("thisweek")) {
            toYear = cal.get(Calendar.YEAR);
            toMonth = cal.get(Calendar.MONTH) + 1;
            toDate = cal.get(Calendar.DATE);
            cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK) + 1);
            fromYear = cal.get(Calendar.YEAR);
            fromMonth = cal.get(Calendar.MONTH) + 1;
            fromDate = cal.get(Calendar.DATE);
        } else if (timeLineDesc.equalsIgnoreCase("thismonth")) {
            toYear = cal.get(Calendar.YEAR);
            toMonth = cal.get(Calendar.MONTH) + 1;
            toDate = cal.get(Calendar.DATE);

            fromYear = toYear;
            fromMonth = toMonth;
            fromDate = 1;
        } else if (timeLineDesc.equalsIgnoreCase("lastweek")) {
            cal.add(Calendar.DATE, -1 * cal.get(Calendar.DAY_OF_WEEK));
            toYear = cal.get(Calendar.YEAR);
            toMonth = cal.get(Calendar.MONTH) + 1;
            toDate = cal.get(Calendar.DATE);
            cal.add(Calendar.DATE, -7);
            fromYear = cal.get(Calendar.YEAR);
            fromMonth = cal.get(Calendar.MONTH) + 1;
            fromDate = cal.get(Calendar.DATE);
        } else if (timeLineDesc.equalsIgnoreCase("lastmonth")) {
            cal.add(Calendar.DATE, -1 * cal.get(Calendar.DATE));
            toYear = cal.get(Calendar.YEAR);
            toMonth = cal.get(Calendar.MONTH) + 1;
            toDate = cal.get(Calendar.DATE);

            fromYear = toYear;
            fromMonth = toMonth;
            fromDate = 1;
        } else if (timeLineDesc.equalsIgnoreCase("thisyear")) {
            toYear = cal.get(Calendar.YEAR);
            toMonth = 12;
            toDate = 31;
            fromYear = toYear;
            fromMonth = 1;
            fromDate = 1;
        } else if (timeLineDesc.equalsIgnoreCase("lastyear")) {
            toYear = cal.get(Calendar.YEAR) - 1;
            toMonth = 12;
            toDate = 31;

            fromYear = toYear;
            fromMonth = 1;
            fromDate = 1;
        }
		System.out.println("From Year : " + fromYear + "\nFrom Month : " + fromMonth + "\n From Date : " + fromDate
					+ "\nTo Year : " + toYear + "\nTo Month : " + toMonth + "\nTo Date : " + toDate);
		triggerSelectEvent();
	}

	public void togglerTimeLineMode() {
		if(timelineMode.equalsIgnoreCase("description")) {
			timelineMode = "range";
		} else {
			timelineMode = "description";
		}
	}
	public void triggerSelectEvent() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, fromMonth - 1);
		cal.set(Calendar.YEAR, fromYear);
		fromDate = Math.min(cal.getActualMaximum(Calendar.DAY_OF_MONTH), fromDate);
		cal.set(Calendar.MONTH, toMonth - 1);
		cal.set(Calendar.YEAR, toYear);
		toDate = Math.min(cal.getActualMaximum(Calendar.DAY_OF_MONTH), toDate);
		for(SelectListener listener: selectListeners) {
			listener.selected();
		}
	}
	public String[][] getTimeLineDescriptions() {
		return timeLineDescriptions;
	}

	public void setTimeLineDescriptions(String[][] timeLineDescriptions) {
		this.timeLineDescriptions = timeLineDescriptions;
	}

	public String getTimelineMode() {
		return timelineMode;
	}

	public void setTimelineMode(String timelineMode) {
		this.timelineMode = timelineMode;
	}

	public List<Integer> getYearList() {
		return yearList;
	}

	public void setYearList(List<Integer> yearList) {
		this.yearList = yearList;
	}

	public List<Integer> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<Integer> monthList) {
		this.monthList = monthList;
	}

	public List<Integer> getDateList() {
		return dateList;
	}

	public void setDateList(List<Integer> dateList) {
		this.dateList = dateList;
	}
	
}
