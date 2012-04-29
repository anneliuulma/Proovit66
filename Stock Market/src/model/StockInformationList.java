package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class StockInformationList extends ArrayList<StockInformation> {

	private static final long serialVersionUID = 1L;

	public void insertStockList(ErrorObject error, DatabaseClass db) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
	    String sql = "insert into stock_information (information_date, " +
	    		"ticker, name, isin, currency, " +
	    		"marketplace, listname, average_price, " +
	    		"open_price, high_price, low_price, " +
	    		"last_close_price, last_price, " +
	    		"price_change_percentage, best_bid, best_ask, " +
	    		"trades, volume, turnover) " +
	    		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";      
		
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			
			for (int i = 0; i < this.size(); i++) {
				StockInformation s = this.get(i);
				pst.setDate(1, new java.sql.Date(s.getInformationDate().getTime()));
				
				pst.setString(2, s.getTicker());
				pst.setString(3, s.getName());
				pst.setString(4, s.getIsin());
				pst.setString(5, s.getCurrency());
				pst.setString(6, s.getMarketplace());
				pst.setString(7, s.getListname());
				
				pst.setDouble(8, s.getAveragePrice().doubleValue());
				pst.setDouble(9, s.getOpenPrice().doubleValue());
				pst.setDouble(10, s.getHighPrice().doubleValue());
				pst.setDouble(11, s.getLowPrice().doubleValue());
				pst.setDouble(12, s.getLastClosePrice().doubleValue());
				pst.setDouble(13, s.getLastPrice().doubleValue());
				pst.setDouble(14, s.getPriceChangePercentage().doubleValue());
				pst.setDouble(15, s.getBestBid().doubleValue());
				pst.setDouble(16, s.getBestAsk().doubleValue());
				
				pst.setInt(17, s.getTrades().intValue());
				pst.setInt(18, s.getVolume().intValue());
				
				pst.setDouble(19, s.getTurnover().doubleValue());
				
				pst.executeUpdate();
			}
			
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
	
	public void deleteInsertTodaysStockList(ErrorObject error, DatabaseClass db) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		
		String delete = "delete from stock_information " +
				"where information_date = ?";
		
	    String sql = "insert into stock_information (information_date, " +
	    		"ticker, name, isin, currency, " +
	    		"marketplace, listname, average_price, " +
	    		"open_price, high_price, low_price, " +
	    		"last_close_price, last_price, " +
	    		"price_change_percentage, best_bid, best_ask, " +
	    		"trades, volume, turnover) " +
			"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";      
		
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
		    if(this.size() != 0) {
		    	StockInformation si = this.get(0);
		    	pst2 = conn.prepareStatement(delete);
		    	pst2.setDate(1, new java.sql.Date(si.getInformationDate().getTime()));
				pst2.executeUpdate();
		    }
		    
			pst = conn.prepareStatement(sql);
			
			for (int i = 0; i < this.size(); i++) {
				StockInformation s = this.get(i);
				pst.setDate(1, new java.sql.Date(s.getInformationDate().getTime()));
				
				pst.setString(2, s.getTicker());
				pst.setString(3, s.getName());
				pst.setString(4, s.getIsin());
				pst.setString(5, s.getCurrency());
				pst.setString(6, s.getMarketplace());
				pst.setString(7, s.getListname());
				
				pst.setDouble(8, s.getAveragePrice().doubleValue());
				pst.setDouble(9, s.getOpenPrice().doubleValue());
				pst.setDouble(10, s.getHighPrice().doubleValue());
				pst.setDouble(11, s.getLowPrice().doubleValue());
				pst.setDouble(12, s.getLastClosePrice().doubleValue());
				pst.setDouble(13, s.getLastPrice().doubleValue());
				pst.setDouble(14, s.getPriceChangePercentage().doubleValue());
				pst.setDouble(15, s.getBestBid().doubleValue());
				pst.setDouble(16, s.getBestAsk().doubleValue());
				
				pst.setInt(17, s.getTrades().intValue());
				pst.setInt(18, s.getVolume().intValue());
				
				pst.setDouble(19, s.getTurnover().doubleValue());
				
				pst.executeUpdate();
			}
			
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
	
	public void retrieveDateList(ErrorObject error, DatabaseClass db) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    String sql = "select distinct information_date  " +
	    		"from stock_information order by information_date ";       
		this.clear();
		
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				
				StockInformation si = new StockInformation();
				si.setInformationDate(rs.getDate(1));
				this.add(si);
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
	
	public void retrieveStockInformationList(ErrorObject error, DatabaseClass db, Date start) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    String sql = "select id, ticker, name, isin, currency, " +
	    		"marketplace, listname, average_price, " +
	    		"open_price, high_price, low_price, " +
	    		"last_close_price, last_price, " +
	    		"price_change_percentage, best_bid, best_ask, " +
	    		"trades, volume, turnover  " +
	    		"from stock_information " +
	    		"where information_date = ?";    
		this.clear();
		
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			long date = start.getTime();
			java.sql.Date sqlDate = new java.sql.Date(date);
			pst.setDate(1, sqlDate);
			rs = pst.executeQuery();
			while(rs.next()){
				
				StockInformation si = new StockInformation();
				si.setId(new Integer(rs.getInt(1)));
				si.setInformationDate(start);
				si.setTicker(rs.getString(2));
				si.setName(rs.getString(3));
				si.setIsin(rs.getString(4));
				si.setCurrency(rs.getString(5));
				si.setMarketplace(rs.getString(6));
				si.setListname(rs.getString(7));
				si.setAveragePrice(new Double(rs.getDouble(8)));
				si.setOpenPrice(new Double(rs.getDouble(9)));
				si.setHighPrice(new Double(rs.getDouble(10)));
				si.setLowPrice(new Double(rs.getDouble(11)));
				si.setLastClosePrice(new Double(rs.getDouble(12)));
				si.setLastPrice(new Double(rs.getDouble(13)));
				si.setPriceChangePercentage(new Double(rs.getDouble(14)));
				si.setBestBid(new Double(rs.getDouble(15)));
				si.setBestAsk(new Double(rs.getDouble(16)));
				si.setTrades(new Integer(rs.getInt(17)));
				si.setVolume(new Integer(rs.getInt(18)));
				si.setTurnover(new Double(rs.getDouble(19)));
				this.add(si);
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
	
	public void retrieve10MaxTradesStockList(ErrorObject error, DatabaseClass db, Date start, Date end) {
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
	    String sql = "select ticker, name, isin, currency, " +
	    		"marketplace, listname, sum(trades) as tradeSum " +
	    		"from stock_information " +
	    		"where information_date <= ? and information_date >= ? " +
	    		"group by ticker, name, isin, currency, marketplace, listname " +
	    		"order by tradeSum desc ";          
		this.clear();
		
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsr(), db.getPwd());
		    conn.setAutoCommit(false);
			
			pst = conn.prepareStatement(sql);
			long date1 = end.getTime();
			java.sql.Date sqlDate1 = new java.sql.Date(date1);
			pst.setDate(1, sqlDate1);
			
			long date2 = start.getTime();
			java.sql.Date sqlDate2 = new java.sql.Date(date2);
			pst.setDate(2, sqlDate2);
			
			rs = pst.executeQuery();
			int i = 0;
			while(rs.next()){
				if(i < 10) {
					StockInformation si = new StockInformation();
					si.setTicker(rs.getString(1));
					si.setName(rs.getString(2));
					si.setIsin(rs.getString(3));
					si.setCurrency(rs.getString(4));
					si.setMarketplace(rs.getString(5));
					si.setListname(rs.getString(6));
					si.setPeriodSumTrades(new Integer(rs.getInt(7)));
					this.add(si);
					i++;
				}
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
	
	public StockInformation findById(Integer Id) {
		StockInformation si = null;
		for (int i = 0; i < this.size(); i++) {
			si = (StockInformation)this.get(i);
			if(si.getId().equals(Id)) {
				return si;
			}
		}
		return null;
	}
}