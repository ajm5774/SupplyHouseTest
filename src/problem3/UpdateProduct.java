package problem3;

import problem3.SupplierStrategy.SupplierAFile;
import problem3.SupplierStrategy.SupplierFile;

public class UpdateProduct implements Runnable{
	
	public void run()
	{
		
	}
	
	public String[] getFileNames(String rootPath){return new String[]{};}
	
	public SupplierFile createSupFileParser(String fileName){return new SupplierAFile();};
}
