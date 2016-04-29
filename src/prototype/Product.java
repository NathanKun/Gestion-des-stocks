package prototype;
import java.sql.Date;

public class Product {

	private int pdt_id;

	private String pdt_name;
	
	private int pdt_supplier;
	
	private double pdt_price;
	
	public Product (int pdt_id, String pdt_name, int pdt_supplier, double pdt_price){
		this.pdt_id = pdt_id;
		this.pdt_name = pdt_name;
		this.pdt_supplier = pdt_supplier;
		this.pdt_price = pdt_price;
	}

	public int getPdt_id() {
		return pdt_id;
	}


	public void setPdt_id(int pdt_id) {
		this.pdt_id = pdt_id;
	}


	public String getPdt_name() {
		return pdt_name;
	}


	public void setPdt_name(String pdt_name) {
		this.pdt_name = pdt_name;
	}


	public int getPdt_supplier() {
		return pdt_supplier;
	}


	public void setPdt_supplier(int pdt_supplier) {
		this.pdt_supplier = pdt_supplier;
	}


	public double getPdt_price() {
		return pdt_price;
	}


	public void setPdt_price(double pdt_price) {
		this.pdt_price = pdt_price;
	}

	@Override
	public String toString() {
		return "Product [pdt_id=" + pdt_id + ", pdt_name=" + pdt_name
				+ ", pdt_supplier=" + pdt_supplier + ", pdt_price=" + pdt_price
				+ "]";
	}
	
}
