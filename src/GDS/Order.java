package src.GDS;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
	
	private long odr_id;
	private double odr_price;
	private int odr_discountPct;
	private String odr_clientName;
	private String odr_state;
	private Date odr_date;
	private ArrayList<Long> productIdList;
	
	public Order(long odr_id, double odr_price, int odr_discountPct, String odr_clientName, 
			String odr_state, Date odr_date, ArrayList<Long> productIdList) {
		this.odr_clientName = odr_clientName;
		this.odr_date = odr_date;
		this.odr_discountPct = odr_discountPct;
		this.odr_id = odr_id;
		this.odr_price = odr_price;
		this.odr_state = odr_state;
		this.productIdList = productIdList;
	}

}
