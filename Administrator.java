package com.wipro.sales.service;

import java.sql.*;
import java.util.ArrayList;
import com.wipro.sales.dao.*;
import com.wipro.sales.bean.*;

public class Administrator {
	
	private static StockDao stockDao = new StockDao();
	private static SalesDao salesDao = new SalesDao();
	
	public synchronized String insertStock(Stock stockobj) {
		if (stockobj != null && stockobj.getProductName().length() >= 2) {
			String productID = stockDao.generateProductID(stockobj.getProductName());
			stockobj.setProductID(productID);
			if (stockDao.insertStock(stockobj) == 1)
				return productID;
			else
				return "Data not Valid for insertion";
		} else {
			return "Data not Valid for insertion";
		}
	}
	
	
	public String deleteStock(String productID) {
		if (stockDao.deleteStock(productID) == 1)
			return "deleted";
		else 
			return "record cannot be deleted";
	}
	
	
	public String insertSales(Sales salesobj) {
		if (salesobj == null) 
			return "Object not valid for insertion";
		
		if (stockDao.getStock(salesobj.getProductID()) == null)
			return "Unknown Product for sales";
		
		if (stockDao.getStock(salesobj.getProductID()).getQuantityOnHand() < salesobj.getQuantitySold())
			return "Not enough stock on hand for sales";
		
		if (salesobj.getSalesDate().before(new Date(0)))
			return "Invalid date";
		
		String salesID = salesDao.generateSalesID(salesobj.getSalesDate());
		salesobj.setSalesID(salesID);
		
		if (salesDao.insertSales(salesobj) == 1) {
			if (stockDao.updateStock(salesobj.getProductID(), salesobj.getQuantitySold()) == 1)
				return "sales record inserted successfully";
			else 
				return "Error";
		} else {
			return "Error";
		}
	}
	
	
	public ArrayList<SalesReport> getSalesReport(){
		return salesDao.getSalesReport();
	}
}
