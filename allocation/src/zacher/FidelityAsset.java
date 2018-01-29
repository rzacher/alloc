package zacher;
import java.util.*;
import java.util.regex.Pattern;

public class FidelityAsset {
	String symbol;
	String description;
	String account;
	Integer domesticStock; 
	Integer foreignStock; 
	Integer bonds; 
	Integer shortTerm; 
	Integer unknown; 
	Integer other; 
	Integer convertibles; 
	Integer preferred; 

	// Parse the line
	public void FidelityAsset() {
		
	}
	
	public String getSymbol() {
		return symbol; 
	}
	
	public String getDescription() {
		return description; 
	}
	
	public void setValues(String line) {
		System.out.println(line);
		String lineCopy = new String(line); 
		
		// first we need to remove the commas defining thousands, millions, ect
		// so we look for a [d],[ddd] 

       //line.replaceAll("\\^([0-9],[)", "<sup>$1</sup>");


      // NumberFormat format = NumberFormat.getCurrencyInstance();
      // Number number = format.parse("$123,456.78");



		// split the first three strings on commas
		String[] values = line.split(","); 
		for (int i=0; i<3; i++) {
		    System.out.print(values[i] + ","); 
		}
		System.out.println("\nMoney");
		//System.out.println("hi bob");
		symbol = values[0];
		description = values[1];
		if (symbol.equals("N/A")) {
			// replace the symbol with the description
			symbol = description; 
		}
		account = values[2];
		// split the next set on $ signs
		String[] dollars = lineCopy.split(Pattern.quote("$")); 
		for (int j=0; j<dollars.length; j++) {
			//System.out.println("dollars=" + dollars[j] + ","); 
		}
		
		domesticStock = stripDollarSign(dollars[1]); 
		foreignStock = stripDollarSign(dollars[2]); 
		bonds = stripDollarSign(dollars[3]);
		shortTerm = stripDollarSign(dollars[4]); 
		unknown = stripDollarSign(dollars[5]);
		other =stripDollarSign(dollars[6]);
		convertibles = stripDollarSign(dollars[7]); 
		preferred = stripDollarSign(dollars[8]); 
		System.out.println();
	}
	
	public Integer getSum() {
		Integer sum = domesticStock + foreignStock + bonds + shortTerm + unknown + other + 
		    convertibles + preferred;  
		return sum; 
	}
	
	public String toString() {
		return "domesticStock:" + domesticStock + "foreignStock:" + foreignStock + "bonds:" + bonds + "shortTerm:" + shortTerm + "unknown:" +  unknown + "other:" + other
	   + "convertibles:" + convertibles + "preferred:" +  preferred; 
	}
	
	public Integer getForeignStock() {
		return foreignStock; 
     }
	
	public Integer getDomesticStock() {
		return domesticStock; 
     }
	
	public Integer getUnknown() {
		return unknown; 
     }
	
	private Integer stripDollarSign(String entry) {
		Integer values[] = new Integer[2]; 
		String cleanedEntry = entry.replaceAll(",", "").replaceAll("\"", "");
		
		//String[] tmp = entry.split("$");
		//System.out.print(cleanedEntry + "\t");
		Integer number = Integer.valueOf(cleanedEntry);	 
		return number; 
		
	}

}

