package problem3.ProductTypeStrategy;

import java.util.HashMap;

public abstract class Product {

	public String tableName;
	
	private String id;
	private int quantity;
	
	public HashMap<String, String> OtherCols;
	
	public String getId(){return id;}
	public void setId(String id){}
	
	public int getQuantity(){return quantity;}
	public void setQuantity(String quantity){}
}
