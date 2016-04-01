package src.GDS;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Product product=new Product(1,"savon");
		Supplier supplier= new Supplier(1,"CCC");
		supplier.addProduct(product,250.5);
		System.out.println("prixVerif:"+product.getPrice());
		System.out.println("FOURNISSEUR id"+supplier.getId()+" "+supplier.getName());
		System.out.println("p Id:"+product.getId());
		supplier.setProductPrice(1, 800.00);
		System.out.println("prix:"+product.getPrice());
	}

}
