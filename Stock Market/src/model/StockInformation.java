package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;

public class StockInformation {

	Integer id;
	Date informationDate;
	String ticker;
	String name;
	String isin;
	String currency;
	String marketplace;
	String listname;
	Double averagePrice;
	Double openPrice;
	Double highPrice;
	Double lowPrice;
	Double lastClosePrice;
	Double lastPrice;
	Double priceChangePercentage;
	Double bestBid;
	Double bestAsk;
	Integer trades;
	Integer volume;
	Double turnover;
	Integer periodSumTrades;
	
	public void retrieveIdPerDate(ErrorObject error, DatabaseClass db) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    String sql = "select id "+
	    		"from stock_information " +
	    		"where information_date = ? ";
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			long date1 = this.getInformationDate().getTime();
			java.sql.Date sqlDate1 = new java.sql.Date(date1);
			pst.setDate(1, sqlDate1);
			
			rs = pst.executeQuery();
			boolean found = rs.next();
			if (found) {
				this.setId(new Integer(rs.getInt(1)));
			}
			else {
				this.setId(null);
			}
			rs.close();
			conn.commit();
			pst.close();
			conn.close();
		}
		catch(SQLException e){
			System.out.println("DB Error: "+e.getMessage());
			error.setError(true);
			error.setMessage(e.getMessage());
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e2) {}
			}
		}
	}
	
	public void getDataFromFileLine(String line) {
		
		StringTokenizer st = new StringTokenizer(line, "\t");
		
		this.setTicker(st.nextToken());
		this.setName(st.nextToken());
		this.setIsin(st.nextToken());
		this.setCurrency(st.nextToken());
		this.setMarketplace(st.nextToken());
		this.setListname(st.nextToken());
		this.setAveragePrice(new Double(st.nextToken().replace(",", ".")));
		this.setOpenPrice(new Double(st.nextToken().replace(",", ".")));
		this.setHighPrice(new Double(st.nextToken().replace(",", ".")));
		this.setLowPrice(new Double(st.nextToken().replace(",", ".")));
		this.setLastClosePrice(new Double(st.nextToken().replace(",", ".")));
		this.setLastPrice(new Double(st.nextToken().replace(",", ".")));
			
		String pr = st.nextToken().replace(",", ".");
		pr = pr.substring(0, (pr.length()-1));
		if(pr.equals("-")) pr = "0.0";
		this.setPriceChangePercentage(new Double(pr));
			
		this.setBestBid(new Double(st.nextToken().replace(",", ".")));
		this.setBestAsk(new Double(st.nextToken().replace(",", ".")));
		this.setTrades(new Integer(st.nextToken()));
		this.setVolume(new Integer(st.nextToken()));
		this.setTurnover(new Double(st.nextToken().replace(",", ".")));	
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getInformationDate() {
		return informationDate;
	}
	public void setInformationDate(Date informationDate) {
		this.informationDate = informationDate;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}
	public String getListname() {
		return listname;
	}
	public void setListname(String listname) {
		this.listname = listname;
	}
	public Double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public Double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}
	public Double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public Double getLastClosePrice() {
		return lastClosePrice;
	}
	public void setLastClosePrice(Double lastClosePrice) {
		this.lastClosePrice = lastClosePrice;
	}
	public Double getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}
	public Double getPriceChangePercentage() {
		return priceChangePercentage;
	}
	public void setPriceChangePercentage(Double priceChangePercentage) {
		this.priceChangePercentage = priceChangePercentage;
	}
	public Double getBestBid() {
		return bestBid;
	}
	public void setBestBid(Double bestBid) {
		this.bestBid = bestBid;
	}
	public Double getBestAsk() {
		return bestAsk;
	}
	public void setBestAsk(Double bestAsk) {
		this.bestAsk = bestAsk;
	}
	public Integer getTrades() {
		return trades;
	}
	public void setTrades(Integer trades) {
		this.trades = trades;
	}
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public Double getTurnover() {
		return turnover;
	}
	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
	public Integer getPeriodSumTrades() {
		return periodSumTrades;
	}
	public void setPeriodSumTrades(Integer periodSumTrades) {
		this.periodSumTrades = periodSumTrades;
	}
}