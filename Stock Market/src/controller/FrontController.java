package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DatabaseClass;
import model.DateManipulation;
import model.ErrorObject;
import model.StockInformation;
import model.StockInformationList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class FrontController {
	
    @RequestMapping("/stockDates")
    public ModelAndView getDates() {
    	
    	ModelAndView mav = new ModelAndView("stockMarketDates");
    	
    	DatabaseClass db = new DatabaseClass();
		ErrorObject error = new ErrorObject();
		mav.addObject("error", error);
		
    	StockInformationList sil = new StockInformationList();
    	sil.retrieveDateList(error, db);
    	
    	List<String> dates = new ArrayList<String>();
    	for(int i = 0; i < sil.size(); i++) {
    		StockInformation si = sil.get(i);
    		dates.add(DateManipulation.dateToString(si.getInformationDate()));
    	}
    	mav.addObject("dateList", dates);
    	
        return mav;
    }
    
    @RequestMapping("/stockInformation")
    public ModelAndView getInformationByDate(@RequestParam("date") String date) {
    	
    	ModelAndView mav = new ModelAndView("stockMarketInformation");
    	
    	DatabaseClass db = new DatabaseClass();
		ErrorObject error = new ErrorObject();
		mav.addObject("error", error);
		mav.addObject("date", date);
		
		StockInformationList si = new StockInformationList();
		si.retrieveStockInformationList(error, db, DateManipulation.stringToDate(date));
		mav.addObject("stockInformationList", si);
		
		return mav;
    }
    
    @RequestMapping("/mostTradedStocks")
    public ModelAndView searchMostTradedStocks(HttpServletRequest request, HttpServletResponse response) {
    	
    	ModelAndView mav = new ModelAndView("mostTradedStockSearch");
    	ErrorObject error = new ErrorObject();
    	mav.addObject("error", error);
    	StockInformationList si = new StockInformationList();
    	mav.addObject("stockInformationList", si);
    	
    	//display form
    	if(request.getMethod().equals("GET")) {
    		mav.addObject("date1", "");
    		mav.addObject("date2", "");
    	}
    	//search
    	else if(request.getMethod().equals("POST")) {
    		
    		DatabaseClass db = new DatabaseClass();
    		
    		String date1 = request.getParameter("date1");
    		String date2 = request.getParameter("date2");
    		mav.addObject("date1", date1);
    		mav.addObject("date2", date2);
    		
    		//check dates
    		if(date1.equals("") || date2.equals("")) {
    			error.setError(true);
    			error.setMessage("Insert both search dates");
    		}
    		else if(DateManipulation.dateValid(date1) == false) {
    			error.setError(true);
    			error.setMessage("Date is incorrect, insert both dates 'dd.mm.yyyy'");
    		}
    		else if(DateManipulation.dateValid(date2) == false) {
    			error.setError(true);
    			error.setMessage("Date is incorrect, insert both dates 'dd.mm.yyyy'");
    		}
    		else {
        		si.retrieve10MaxTradesStockList(error, db, DateManipulation.stringToDate(date1), DateManipulation.stringToDate(date2));
    		}
    	}
        return mav;
    }
}