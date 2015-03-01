package problem3.SupplierStrategy;

import java.util.ArrayList;

import problem3.ProductTypeStrategy.Product;

public abstract class SupplierFile {

	public abstract ArrayList<Product> parse();
}
