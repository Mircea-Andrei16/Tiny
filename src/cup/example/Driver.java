package cup.example;

import java_cup.runtime.*;

class Driver {

	public static void main(String[] args) throws Exception {
		ParserWithTree parser = new ParserWithTree();
//		parser.parse();
		parser.
		debug_parse();
		MultiTree tree = parser.getSyntaxTree();
		SymbolTable table = new SymbolTable();
		table.extract(tree);
//		table.printSymbolTable();
//		tree.printTree();
		
		tree.typeChecking(table.getSymbolTable());
	}
	
}