package cup.example;

public class ParserWithTree extends Parser{

	public ParserWithTree() 
	{
		super();
	}
	
 	/**
 	 * Function Declaration. Line 87
 	 */
	protected MultiTreeNode createFunctionDeclarationNode(MultiTreeNode typeSpecifier, String name, MultiTreeNode paramsList, MultiTreeNode block) 	
 	{ 
 		MultiTreeNode newNode = new MultiTreeNode("FunctionDeclaration", name);
 		newNode.addChild(typeSpecifier);
 		if (paramsList != null)
 			newNode.addChild(paramsList);
 		newNode.addChild(block);
 		return newNode; 
 	}
	
 	/**
 	 * Creates lists
 	 */
	protected MultiTreeNode createListNode(String listName, MultiTreeNode firstChild)
	{
		MultiTreeNode newNode = new MultiTreeNode(listName);
		if(firstChild != null) {
			newNode.addChild(firstChild);
		}
		return newNode;
	}
  	
  	/**
  	 * Used for var declaration. line95
  	 */
	protected MultiTreeNode createVarDeclaration(MultiTreeNode typeSpecifier, String identifierName)
	{
		MultiTreeNode newNode = new MultiTreeNode("Var Declaration", identifierName);
		newNode.addChild(typeSpecifier);		
		return newNode;
	}
	
	/**
	 * Creates type specifiers from String
	 */
  	protected MultiTreeNode createTypeSpecifier(String typeName)
  	{ 
  		MultiTreeNode newNode = new MultiTreeNode("TypeSpecifier", typeName);
  		return newNode;
  	}
  	
  	/**
	 * Creates type specifiers from type and expression
	 */
  	protected MultiTreeNode createTypeSpecifier(MultiTreeNode type,MultiTreeNode expression)
  	{ 
  		MultiTreeNode newNode = new MultiTreeNode("TypeSpecifier from expression");
  		if(type != null) {
  			newNode.addChild(type);
  		}
  		if(expression != null) {
  			newNode.addChild(expression);
  		}
  		return newNode;
  	}
	
	/**
	 * Used for block. Line 98
	 */
	protected MultiTreeNode createBlock(MultiTreeNode declarations, MultiTreeNode instructions)
	{
		MultiTreeNode newNode = new MultiTreeNode("Block");
		if (declarations != null)
			newNode.addChild(declarations);
		if (instructions != null)
			newNode.addChild(instructions);
		return newNode;
	}
	
	/**
	 * If statement
	 * @param condition
	 * @param ifInstructions
	 * @param elseInstructions
	 * @return
	 */
	protected MultiTreeNode createIfStatement(MultiTreeNode condition, MultiTreeNode ifInstructions, MultiTreeNode elseInstructions)
	{
		MultiTreeNode newNode = new MultiTreeNode("If Statement");
		newNode.addChild(condition);
		newNode.addChild(ifInstructions);
		if (elseInstructions != null)
			newNode.addChild(elseInstructions);
		return newNode;
	}
	
	/**
	 * Simple statement
	 * @param operation
	 * @param identifier
	 * @param exp
	 * @return
	 */
	protected MultiTreeNode createStatement(String name, MultiTreeNode ex, MultiTreeNode stm) {
		MultiTreeNode expr = new MultiTreeNode(name);
		if(ex != null) {
			expr.addChild(ex); 
		}
		if(stm != null) {
			expr.addChild(stm);
		}
		return expr;
	}
	
	/**
	 * Simple statement
	 * @param operation
	 * @param identifier
	 * @param exp
	 * @return
	 */
	protected MultiTreeNode createStatementList(String indicator, String name, MultiTreeNode stm) {
		MultiTreeNode indicatorNode = new MultiTreeNode(indicator);
		MultiTreeNode nameNode = new MultiTreeNode(name);
		indicatorNode.addChild(nameNode);
		if(stm != null) {
			indicatorNode.addChild(stm);
		}
		return indicatorNode;
	}
	
	/**
	 * create return statement statement
	 * @param exp
	 * @return
	 */
	protected MultiTreeNode createReturnStatement(MultiTreeNode exp) {
		MultiTreeNode rtrn = new MultiTreeNode("Return");
		if (exp != null) {
			rtrn.addChild(exp);
		}
		return rtrn;
	}
	
	/**
	 * Block Statement
	 * @param exp
	 * @return
	 */
	protected MultiTreeNode createBlockStatement(MultiTreeNode exp) {
		MultiTreeNode blockStatement = new MultiTreeNode("Block Stement");
		if (exp != null) {
			blockStatement.addChild(exp);
		}
		return blockStatement;
	}
	
	protected MultiTreeNode createInOutStatement(String ioOP, MultiTreeNode exp) {
		MultiTreeNode inOutStatement = new MultiTreeNode(ioOP);
		if (exp != null) {
			if(exp.getDescendentsCount() > 0) {
				for (MultiTreeNode child : exp.getChildren()) {
					if(child.getDescendentsCount() == 0) {
						inOutStatement.addChild(child);
					}
				}
			}else {
				inOutStatement.addChild(exp);
			}
		}
		return inOutStatement;
	}
	protected MultiTreeNode createLExpression(MultiTreeNode lexp, MultiTreeNode exp) {
		MultiTreeNode leftExpression = lexp;
		if (exp != null) {
			leftExpression.addChild(exp);
		}
		return leftExpression;
	}
	
	protected MultiTreeNode createExpression(MultiTreeNode lexp, MultiTreeNode operation,MultiTreeNode ex) {
		MultiTreeNode expression = new MultiTreeNode("Expression");
		if(lexp != null && ex != null) {
			expression.addChild(operation);
			if(!"Expression".equals(lexp.getData())){
				expression.addChild(lexp);
			}else {
				for (MultiTreeNode  lChild : lexp.getChildren()) {
					if(lChild.getDescendentsCount() == 0) {
						expression.addChild(lChild);
					}
				}
			}
			if(ex.getDescendentsCount() > 0) {
			for (MultiTreeNode  rChild : ex.getChildren()) {
				if(rChild.getDescendentsCount() == 0) {
					expression.addChild(rChild);
				}
			}
			}else {
				expression.addChild(ex);
			}
		}else {
		
			if (lexp != null) {
				expression.addChild(lexp);
			}
			if (operation != null) {
				expression.addChild(operation);
			}
			if (ex != null) {
				expression.addChild(ex);
			}
		}
		return expression;
	}
	/**
	 * create unar expression node
	 */
	protected MultiTreeNode createUExpression(MultiTreeNode operation,MultiTreeNode ex) {
		MultiTreeNode uExpression = new MultiTreeNode("Unar Expression");
		if (operation != null) {
			uExpression.addChild(operation);
		}
		if (ex != null) {
			uExpression.addChild(ex);
		}
		return uExpression;
	}
	/**
	 * create an instance node from integer 
	 */
	protected MultiTreeNode createInstance(Integer instance) {
		return new MultiTreeNode(instance.toString());
	}
	/**
	 * create an instance node from string
	 */
	protected MultiTreeNode createInstance(String instance) {
		return new MultiTreeNode(instance);
	}
	/**
	 * create an operation node
	 */
	protected MultiTreeNode createOperation(String operation) {
		return new MultiTreeNode(operation);
	}
	/**
	 * create a variable node from name
	 */
	protected MultiTreeNode createVariable(String name) {
		return new MultiTreeNode(name);
	}
	/**
 	 * Creates error message node
 	 */
	protected MultiTreeNode createErrorNode(String errorMessage)
	{
		return new MultiTreeNode(errorMessage);
	}
}
