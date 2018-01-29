package zacher;

public class MixedAsset  {
	double emFraction; 
	double developedIntlFraction;
	double usFraction; 
	double emStock;
	double developedIntlStock;
	double foreignStock; 
	String symbol; 
	
	MixedAsset(String symbol, double emFraction, double developedIntlFraction, double usFraction) {
		this.symbol = symbol;
		this.emFraction = emFraction; 
		this.developedIntlFraction = developedIntlFraction;
		this.usFraction = usFraction; 
	}
	
	MixedAsset(String symbol, double emFraction) {
		this.symbol = symbol;
		this.emFraction = emFraction; 
		this.developedIntlFraction = 1-emFraction;
	}
	
	public String getSymbol() {
		return symbol; 
	}
	
	public void setEmFraction(double emFraction) {
		this.emFraction = emFraction; 
	}
	
	public void setIntlStock(double foreignStock) {
		this.foreignStock = foreignStock; 
	}
	
	public Integer getEmStock() {
		return (int) (foreignStock * emFraction);	
	}
	
	public Integer getDevelopedIntlStock() {
		return (int) (foreignStock * developedIntlFraction);	
	}
}
