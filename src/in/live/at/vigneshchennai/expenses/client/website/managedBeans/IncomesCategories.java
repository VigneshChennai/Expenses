package in.live.at.vigneshchennai.expenses.client.website.managedBeans;

import in.live.at.vigneshchennai.expenses.ejb.IncomeService;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="incomesCategories")
@ViewScoped
public class IncomesCategories {
	
	private List<String> categories;
	private String selected;
	
	@ManagedProperty(value="#{timeLine}")
	private TimeLine timeLine;
	
	@EJB
	private IncomeService incService;
	public IncomesCategories() {
		selectListeners = new LinkedList<SelectListener>();
	}
	private List<SelectListener> selectListeners;
	
	
	@PostConstruct
    public void init() {
		updateCategories();
		timeLine.addSelectListener(new SelectListener() {
			@Override
			public void selected() {
				updateCategories();
			}
		});
	}
	
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
		triggerSelectEvent();
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
		
		triggerSelectEvent();
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
	public void updateCategories() {
		categories = incService.getCategories(timeLine.getFromYear(),
								timeLine.getFromMonth(),
								timeLine.getFromdate(),
								timeLine.getToYear(),
								timeLine.getToMonth(),
								timeLine.getTodate());
	}
	public TimeLine getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(TimeLine timeLine) {
		this.timeLine = timeLine;
	}
	
	public void triggerSelectEvent() {
		for(SelectListener listener: selectListeners) {
			listener.selected();
		}
	}
}
