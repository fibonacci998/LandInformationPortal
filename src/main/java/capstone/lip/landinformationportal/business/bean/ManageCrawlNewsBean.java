package capstone.lip.landinformationportal.business.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import capstone.lip.landinformationportal.business.service.Interface.ICrawledNewsService;
import capstone.lip.landinformationportal.common.constant.StatusCrawledNewsConstant;
import capstone.lip.landinformationportal.common.dto.LazyCrawledNew;
import capstone.lip.landinformationportal.common.entity.CrawledNews;

@Named
@ViewScoped
public class ManageCrawlNewsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String timerCrawl;

	@Autowired 
	private ICrawledNewsService crawledNewService;
	
	private LazyDataModel<CrawledNews> lazyNews;
	
	private boolean statusCrawlSchedule;
	
	private List<CrawledNews> listNewsSelected;
	
	@PostConstruct
	public void init() {
		timerCrawl = "";
		timerCrawl = crawledNewService.initCrawlJob();
		if (timerCrawl.isEmpty()) {
			statusCrawlSchedule = false;
		}
		else {
			statusCrawlSchedule = true;
		}
		lazyNews = new LazyCrawledNew(crawledNewService);
	}
	
	public void setTimerButtonClick() {
		try {
			int timer= Integer.valueOf(timerCrawl);
			if (timer<=0) {
				throw new Exception();
			}
			crawledNewService.setTimeCrawlJob(timer*60*60);
		}catch(Exception e) {
			setMessage(FacesMessage.SEVERITY_ERROR, "Thời gian không phù hợp");
		}
		
	}

	public void turnOffCrawler() {
		crawledNewService.turnOffCrawler();
		statusCrawlSchedule = false;
	}
	public void turnOnCrawler() {
		if (timerCrawl.isEmpty()) {
			setMessage(FacesMessage.SEVERITY_ERROR, "Chưa cài đặt thời gian");
			return;
		}
		crawledNewService.initCrawlJob();
		crawledNewService.turnOnCrawler();
		statusCrawlSchedule = true;
	}
	public void setMessage(FacesMessage.Severity severityType, String message) {
		
		FacesMessage msg = new FacesMessage();
		if (severityType == FacesMessage.SEVERITY_ERROR) {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Lỗi", message);
		} else if (severityType == FacesMessage.SEVERITY_WARN) {
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Lưu ý", message);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công", message);
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	public void crawlNow() {
		boolean test = crawledNewService.crawlNow();
		if (test==true)
			setMessage(FacesMessage.SEVERITY_INFO, "Hoàn thành crawl ngay");
	}

	public String getTimerCrawl() {
		return timerCrawl;
	}

	public void setTimerCrawl(String timerCrawl) {
		this.timerCrawl = timerCrawl;
	}

	public void refreshData() {
		lazyNews = new LazyCrawledNew(crawledNewService);
	}

	CrawledNews newsClick;
	public void setNews(CrawledNews news) {
		newsClick = news;
	}

	public void acceptListNews() {
		for (CrawledNews news:listNewsSelected) {
			news.setCrawledNewsStatus(StatusCrawledNewsConstant.DISPLAY);
		}
		crawledNewService.saveAll(listNewsSelected);
		refreshData();
	}
	
	public void deleteListNews() {
		crawledNewService.delete(listNewsSelected);
		refreshData();
	}

	public boolean checkEmptyListSelected() {
		if (listNewsSelected.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public LazyDataModel<CrawledNews> getLazyNews() {
		return lazyNews;
	}

	public boolean isStatusCrawlSchedule() {
		return statusCrawlSchedule;
	}

	public void setStatusCrawlSchedule(boolean statusCrawlSchedule) {
		this.statusCrawlSchedule = statusCrawlSchedule;
	}

	public void setLazyNews(LazyDataModel<CrawledNews> lazyNews) {
		this.lazyNews = lazyNews;
	}

	public List<CrawledNews> getListNewsSelected() {
		return listNewsSelected;
	}

	public void setListNewsSelected(List<CrawledNews> listNewsSelected) {
		this.listNewsSelected = listNewsSelected;
	}
	
}
