package zacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*; 

public class Allocator {

	private List<Asset> assets; 
	private List<String> emStocks; 
	int emTotal = 0; 
	int total = 0; 
	
	Allocator() {
		//assets = new LinkedList<String>();
		this.emStocks = new LinkedList<String>();
		emStocks.add("VWO");
		emStocks.add("EEM");
		emStocks.add("FNDE");
		emStocks.add("TPTB"); 
		for (String stock: emStocks) {
			System.out.println(stock); 
		}
	}
	
	public void readAssets(String inFile) {
		assets = new LinkedList<Asset>();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			String line;
			// Dump the first line
			String firstLine = reader.readLine(); 
			String secondLine = reader.readLine();
			while ((line = reader.readLine()) != null)
			{  
				//  Ignore trailing lines that don't contain data
				if (line.contains("The data")) {
					// do nothing
				} else if (line.contains("Subtotal")) {
				} else {
					// fix any -$ signs in the line
					String cleanedLine = line.replaceAll("-", "");
					
					System.out.println("cleanedLine=" + cleanedLine); 
					Asset asset = new Asset(); 
					asset.setValues(cleanedLine); 
					assets.add(asset); 
					System.out.println("Sum=" + asset.getSum()); 
					System.out.println(); 
				}
			}
			reader.close();
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", inFile);
			e.printStackTrace();
			return;
		}

		// Open the file
	}
	
	public void calcAllocations() {
		for (Asset asset: assets) {
			System.out.println(asset.getSymbol()); 
			for (String stock: this.emStocks) {
				if (stock.equals(asset.getSymbol())) {
					emTotal += asset.getSum(); 
				}
			}
			total += asset.getSum(); 
		}
		System.out.println("EM total: " + emTotal); 
		System.out.println("Total: " + total); 
		System.out.println("EM allocation: " + (double) emTotal/total); 
		
	}
	
	public  void writeAssets() {
		for (Asset asset: assets) {
			System.out.println(asset.getSymbol()); 
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Running AssetAllocator");
		
		String inFile = "//home//bob//Eng//fin//AssetAllocation.csv"; 

		Allocator allocator = new Allocator(); 
		allocator.readAssets(inFile);
		allocator.calcAllocations(); 
		//allocator.writeAssets(); 
		
	}

}
