package cup.example;

public class SymbolTableEntry {

	private String symbolName; //numele variabilei sau functiei
	
	private String dataType; // tipul de date al variabilei (int, double, string, void)
 	
	private int symbolType; // tipul simbolului variabila 0/ functie 1
	
	private int symbolScope;// scopul -> global 0 / local 1
	
	private String contextName; // contextul in care este declarata variabila/functia. ex.
								// Daca variabila x este declarata in interiorul 
								// functiei "test_function" in contextName se pune "test_function"

	public void createTableEntry(String symbolName, String dataType, int symbolScope, int symbolType, String contextName) {
		this.symbolName = symbolName;
		this.dataType = dataType;
		this.symbolType = symbolType;
		this.symbolScope = symbolScope;
		this.contextName = contextName;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public String getDataType() {
		return dataType;
	}

	public int getSymbolType() {
		return symbolType;
	}

	public int getSymbolScope() {
		return symbolScope;
	}

	public String getContextName() {
		return contextName;
	}
}