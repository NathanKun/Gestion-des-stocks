package gds;

/**
 * class represents a row in table sprpdtlist_spl. An object contains the
 * supplierId, productId and the price of this product by this supplier.
 * 
 * @author HE Junyang
 *
 */
public class SupplierProductPrice {
	private long sprId;
	private long pdtId;
	private double sprPdtPrice;

	/**
	 * Create SupplierProductPrice or get it from database.
	 * 
	 * @param sprId
	 *            supplier id
	 * @param pdtId
	 *            product id
	 * @param sprPdtPrice
	 *            the price of this product by this supplier
	 */
	public SupplierProductPrice(long sprId, long pdtId, double sprPdtPrice) {
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
