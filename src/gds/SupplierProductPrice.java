package src.gds;

public class SupplierProductPrice {
	private long sprId;
	private long pdtId;
	private double sprPdtPrice;
	
	public SupplierProductPrice (long sprId, long pdtId, double sprPdtPrice){
		this.sprId = sprId;
		this.pdtId = pdtId;
		this.sprPdtPrice = sprPdtPrice;
	}

	public long getSprId() {
		return sprId;
	}

	public void setSprId(long sprId) {
		this.sprId = sprId;
	}

	public long getPdtId() {
		return pdtId;
	}

	public void setPdtId(long pdtId) {
		this.pdtId = pdtId;
	}

	public double getSprPdtPrice() {
		return sprPdtPrice;
	}

	public void setSprPdtPrice(double sprPdtPrice) {
		this.sprPdtPrice = sprPdtPrice;
	}

	@Override
	public String toString() {
		return "SupplierProductPrice [sprId=" + sprId + ", pdtId=" + pdtId + ", sprPdtPrice=" + sprPdtPrice + "]";
	}
	
	
}
