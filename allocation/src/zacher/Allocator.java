package zacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File; 
import java.util.*; 

public class Allocator {

	private List<FidelityAsset> fidelityAssets; 
	private List<String> emStocks; 
	private List<String> israelStocks; 
	private List<MixedAsset> mixedAssets; 
	int domesticTotal = 0; 
	int emTotal = 0; 
	int developedIntlTotal = 0; 
	int israelTotal = 0; 
	int bondsTotal = 0;
	int foreignTotal = 0; 
	int total = 0; 
	
	
	
	Allocator() {
		//assets = new LinkedList<String>();
		this.emStocks = new LinkedList<String>();
		this.israelStocks = new LinkedList<String>();
		this.fidelityAssets = new LinkedList<FidelityAsset>();
		this.mixedAssets = new LinkedList<MixedAsset>();
		loadPureEMStocks(); 
		loadPureIsraelStocks();  
		loadMixedAssets(); 
	}
	
	private void loadPureEMStocks() {
		emStocks.add("VWO");
		emStocks.add("EEM");
		emStocks.add("FNDE");
		emStocks.add("TPTB"); 
		emStocks.add("EMERGING MARKETS"); 
		emStocks.add("SchwabEmergingMarkets");
		System.out.println("Emerging Market stocks");
		for (String stock: emStocks) {
			System.out.println(stock); 
		}
	}
	
	private void loadPureIsraelStocks() {
		israelStocks.add("EIS");
		israelStocks.add("ISRA");
		israelStocks.add("ISL");
	}
	
	private void loadMixedAssets() {
		MixedAsset ma1 = new MixedAsset("VVAInternational", 0.226);
		MixedAsset ma2 = new MixedAsset("VFORX", 0.0688); 
		MixedAsset ma3 = new MixedAsset("VTIAX", 0.198);
		MixedAsset ma4 = new MixedAsset("VT", 0.093);
		MixedAsset ma5 = new MixedAsset("VTHRX", 0.056);
		
		mixedAssets.add(ma1); 	
		mixedAssets.add(ma2);
		mixedAssets.add(ma3);
		mixedAssets.add(ma4);
		mixedAssets.add(ma5);
	}
 
	public void readFidelityAssets(String inFile) {
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
					System.out.println("rawLine=" + line); 
					// fix any -$ signs in the line
					String cleanedLine = line.replaceAll("-", "");
					
					System.out.println("cleanedLine=" + cleanedLine); 
					FidelityAsset asset = new FidelityAsset(); 
					asset.setValues(cleanedLine); 
					fidelityAssets.add(asset); 
					System.out.println(asset); 
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
		System.out.println("Printing symbols"); 
		for (FidelityAsset asset: fidelityAssets) {
			System.out.println(asset.getSymbol()); 
			// Calc EM stocks
			for (String stock: this.emStocks) {
				if (stock.equals(asset.getSymbol())) {
					emTotal += asset.getSum(); 
				}
			}
			for (MixedAsset mixedAsset : mixedAssets) {
				if (mixedAsset.getSymbol().contains(asset.getSymbol().trim())) {
					mixedAsset.setIntlStock(asset.getUnknown());       
					System.out.print("adding mixed asset: ");
				    System.out.println(mixedAsset.getEmStock());
					emTotal += mixedAsset.getEmStock(); 
				}
				
			}
			// Calc Israeli stocks
			for (String stock: this.israelStocks) {
				if (stock.equals(asset.getSymbol())) {
					israelTotal += asset.getSum(); 
				}
			}
			
			// Calc domestic total for Fidelity Assets
			domesticTotal += asset.getDomesticStock(); 
			foreignTotal += asset.getForeignStock();
			bondsTotal += asset.getBonds(); 
			total += asset.getSum(); 
		}
		
		System.out.println("Domestic total: " + domesticTotal);
		System.out.println("Foreign total: " + foreignTotal);
		System.out.println("Bonds total: " + bondsTotal);
		System.out.println("EM total: " + emTotal); 
		System.out.println("Israel total: " + israelTotal); 
		System.out.println("Total: " + total); 
		System.out.println(); 
		System.out.println("Domestic allocation: " + (double) domesticTotal/total);
		System.out.println("Bond allocation: " + (double) bondsTotal/total);
		System.out.println("Foreign allocation: "  + (double) foreignTotal/total); 
		System.out.println("EM allocation: " + (double) emTotal/total); 
		System.out.println("Israel allocation: " + (double) israelTotal/total); 
		
		
		
	}
	
	public  void writeAssets() {
		for (FidelityAsset asset: fidelityAssets) {
			System.out.println(asset.getSymbol()); 
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String folder = "1-25-18";
		String parentFolder = "//home//bob//Eng//fin//";
		
		// TODO Auto-generated method stub
		System.out.println("Running AssetAllocator");
		
		Allocator allocator = new Allocator();
		// get list of files in folder.
		String srcFolder = parentFolder + folder;
		File[] files = new File(srcFolder).listFiles(); 
        for (File file: files) {
		    allocator.readFidelityAssets(file.getAbsolutePath());
        }
		
		System.out.println("Printing asset allocations"); 
		allocator.calcAllocations(); 
		//allocator.writeAssets(); 
		
	}

}
