package cup.example;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	
	private Map<String, SymbolTableEntry> symbolTable;

	public SymbolTable() {
		symbolTable = new HashMap<>();
	}
	
	public void addSymbolEntry(String name,SymbolTableEntry entry) {
		symbolTable.put(name, entry);
	}
	
	public void printSymbolTable() {
		for (Map.Entry<String, SymbolTableEntry>  tableElement: symbolTable.entrySet()) {
			System.err.println("Name of the entry: " + tableElement.getKey());
			
			SymbolTableEntry entry = tableElement.getValue();
			System.out.println("the entry has the following elements:");
			System.out.println("dataType of the entry: " + entry.getDataType());
			
			int symbolType = entry.getSymbolType();
			System.out.print("SymbolType of the entry is " + entry.getSymbolType());
			if(symbolType == 1) {
				System.out.println(" --> variable ");
			}else {
				System.out.println(" --> function ");
			}
			
			int symbolScope = entry.getSymbolScope();
			System.out.print("SymbolScope of the entry: " + symbolScope);
			if(symbolScope  == 0) {
				System.out.println(" --> local");
			}else {
				System.out.println(" --> global");
			}
			System.out.println("ContextName of the entry: " + entry.getContextName());
			System.out.println("----------------------------------------------------");
		}
	}

	public void extract(MultiTree tree) {
		tree.extractSymbols(this);
	}
	
	
	
	
	

}
