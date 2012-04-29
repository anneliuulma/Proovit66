package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import model.DatabaseClass;
import model.DateManipulation;
import model.ErrorObject;
import model.StockInformation;
import model.StockInformationList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MyScheduler {
	
    @Scheduled(fixedRate = 1800000) //30*60*1000 ms
    public void process() {
        System.out.println("Invoking scheduled process " + new Date());
        
        DatabaseClass db = new DatabaseClass();
		ErrorObject error = new ErrorObject();
        
		//last 10 days
        Date date = new Date();
        
        for(int i = 0; i < 10; i++) {
        	date = DateManipulation.addDaysToDate(date, -1);
        	System.out.println("Checking date "+date);
        	
        	//check data
        	StockInformation si = new StockInformation();
        	si.setInformationDate(date);
        	si.retrieveIdPerDate(error, db);
        	
        	if(error.isError()) {
        		System.out.println("Error: "+error.getMessage());
        	}
        	//no data for this date - insert
        	if(si.getId() == null) {
        		System.out.println("	No data found -> download");
        		String dateString = DateManipulation.dateToString(date);
        
        		StockInformationList siList = new StockInformationList();
        		try {
        			URL url = new URL("http://www.nasdaqomxbaltic.com/market/?pg=mainlist&market=&date="+dateString+"&downloadcsv=1&csv_style=baltic");
        	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-16"));
        	        
        	        String current;
        	        int row = 0;
					while((current = in.readLine()) != null) {
						row++;
						if(row != 1) {
							StockInformation s = new StockInformation();
							s.setInformationDate(date);
							s.getDataFromFileLine(current);
							siList.add(s);
						}
        	        }
					in.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
        		if(siList.size() != 0) {
        			System.out.println("	Data downloaded, insert to db");
        			siList.insertStockList(error, db);
        			if(error.isError()) {
        				System.out.println("Error: "+error.getMessage());
        			}
        		}
        	}
        }
        
        //today
        StockInformationList siList = new StockInformationList();
		
        date = new Date();
        System.out.println("Today: "+date+" -> download and insert");
		String dateString = DateManipulation.dateToString(date);
		
		try {
			URL url = new URL("http://www.nasdaqomxbaltic.com/market/?pg=mainlist&market=&date="+dateString+"&downloadcsv=1&csv_style=baltic");
	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	         
	        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-16"));
	        
	        String current;
	        int row = 0;
			while((current = in.readLine()) != null) {
				row++;
				if(row != 1) {
					StockInformation s = new StockInformation();
					s.setInformationDate(date);
					s.getDataFromFileLine(current);
					siList.add(s);
				}
	        }
			in.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		siList.deleteInsertTodaysStockList(error, db);
		if(error.isError()) {
    		System.out.println("Error: "+error.getMessage());
    	}
		System.out.println("Today´s data updated");
		System.out.println("End of scheduled process");
    }
}