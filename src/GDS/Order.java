package src.GDS;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
	
	private long id;
	private double price;
	private int discountPct;
	private String clientName;
	private String state;
	private Date date;
	private ArrayList<Long> productIdList;
	
	public Order(long id, double price, int discountPct, String clientName, 
			String state, Date date, ArrayList<Long> productIdList) {
		this.clientName = clientName;
		this.date = date;
		this.discountPct = discountPct;
		this.id = id;
		this.price = price;
		this.state = state;
		this.productIdList = productIdList;
	}

}
