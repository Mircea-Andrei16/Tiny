package cup.example;

import java.util.ArrayList;

public class MultiTreeNode {
	
	private String data;
	private String extraData;
	private ArrayList<MultiTreeNode> children;	
	private int descendentsCount = 0; 
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;	
		
	}
	
	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public int getDescendentsCount()
	{		
		return descendentsCount;
	}

	public MultiTreeNode[] getChildren() {
		MultiTreeNode[] childrenArray = new MultiTreeNode[children.size()];		
		return children.toArray(childrenArray);
	}	
	
	public MultiTreeNode(String data, String extraData)
	{
		this.data = data;
		this.extraData = extraData;
		children = new ArrayList<MultiTreeNode>();
	}
	
	public MultiTreeNode(String data) 
	{
		this(data, "");
	}
	
	public MultiTreeNode addChild(String childData)
	{
		MultiTreeNode addedNode = new MultiTreeNode(childData);
		children.add(addedNode);
		return addedNode;
	}
	
	public void addChild(MultiTreeNode node)
	{
		children.add(node);
		descendentsCount += node.descendentsCount + 1;
	}
	
	public void printNode(int level)
	{
		for (int i = 0; i < level; i++)
		{
			System.out.print(" ");
		}
		System.out.print(data);
		if (extraData != null && extraData.length() > 0)
		{
			System.out.print(" - " + extraData + " - ");
		}
		System.out.println("");
		
		for (MultiTreeNode multiTreeNode : children) {
			multiTreeNode.printNode(level + 1);
		}
	}
	
	public void extractSymbols(SymbolTable symbolTable, String context) {
	
		if(data.equals("Var Declaration")) {
			createTableEntry(symbolTable,context,1);
		}
		
		if(data.equals("FunctionDeclaration")) {
			createTableEntry(symbolTable,context,0);
			context = extraData;
		}
		
		for (MultiTreeNode multiTreeNode : children) {
			multiTreeNode.extractSymbols(symbolTable, context);
		}
	}
	
	private void createTableEntry(SymbolTable symbolTable, String context, int symbolType) {
		MultiTreeNode typeSpecifier = children.get(0);
		
		SymbolTableEntry newEntry = new SymbolTableEntry();
		if(context.equals("Program")) {
			newEntry.createTableEntry(extraData, typeSpecifier.getExtraData(), 1, symbolType, context);
		}else {
			newEntry.createTableEntry(extraData, typeSpecifier.getExtraData(), 0, symbolType, context);
		}
		symbolTable.addSymbolEntry(extraData, newEntry);
	}
}
