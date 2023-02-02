package cup.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeCheckUtil {

	private static final int POSITION_IN_OPERATORS_LIS_FOR_DIVIDE = 3;
	private static final int OPERAND = 1;
	private static final int RESULT_IN_ASSIGN = 0;
	private static final String[] operatorsList = {"PLUS","MINUS","TIMES","DIVIDE"};
	
	/**
	 * Entry point for type checking
	 * @param symbolTable
	 * @param childrenNode
	 */
	public static void check(Map<String, SymbolTableEntry> symbolTable, List<MultiTreeNode> childrenNode) {
		//check if the assignment element is declared
		String resultVariable = childrenNode.get(RESULT_IN_ASSIGN).getData();
		if(resultVariable.contains("error")) {
			boolean isDeclared = checkVariableIsDeclared(symbolTable, resultVariable);
			throwErrorDeclaration(isDeclared,resultVariable);
		}
		//check if the left side is in order
		checkRightSide(symbolTable,childrenNode);
	}
	
	/**
	 * Throw error declaration if variable is not declared.
	 * @param isDeclared
	 * @param variable
	 */
	private static void throwErrorDeclaration(boolean isDeclared, String variable) {
		if(!isDeclared && !variable.contains("error")) {
			System.err.println("Variable " + variable + " is not declared.");
		}
	}

	/**
	 * Checks if a variable is declared.
	 * 
	 * @param symbolTable  map with all declared variables
	 * @param variable    the variable that needs to be checked
	 */
	private static boolean checkVariableIsDeclared(Map<String, SymbolTableEntry> symbolTable, String variable) {
		 return symbolTable.containsKey(variable);
	}

	/**
	 * Check expression on the right side of an assign.
	 * @param symbolTable
	 * @param children
	 */
	private static void checkRightSide(Map<String, SymbolTableEntry> symbolTable, List<MultiTreeNode> children) {
		String possibleExpressionOrVariable = children.get(OPERAND).getData();
		String resultSymbol = children.get(RESULT_IN_ASSIGN).getData();
		SymbolTableEntry resultEntry = symbolTable.get(resultSymbol);
		if(possibleExpressionOrVariable.equals("Expression")) {
			if(resultEntry != null) {
				checkExression(children.get(OPERAND).getChildren(),symbolTable,resultEntry.getDataType());
			}
		}else {
			if(resultEntry != null) {
				checkDataTypeConstant(resultEntry.getDataType(), possibleExpressionOrVariable);
			}
		}
	}

	/**
	 * check right expression
	 * @param children
	 * @param symbolTable
	 * @param resultDataType
	 */
	private static void checkExression(MultiTreeNode[] children, Map<String, SymbolTableEntry> symbolTable, String resultDataType) {
		boolean isDivision = false;
		for(int i = 0; i< children.length; i++) {
			//check is variable is declared
			String operandSymbol = children[i].getData();
			if(checkIsOperandIsNotOperator(operandSymbol)) {
				checkOperands(operandSymbol, symbolTable, resultDataType);
				checkNumberAfterDivision(isDivision, operandSymbol);
			} else {
				isDivision = checkIfIsDivision(operandSymbol);
			}
		}
	}

	/**
	 * Check if the number is 0 we can not divide by 0
	 * @param isDivision
	 * @param operandSymbol 
	 */
	private static void checkNumberAfterDivision(boolean isDivision, String operandSymbol) {
		if(isDivision && operandSymbol.equals("0")) {
			System.err.println("You can not divide by 0");
		}
		
	}

	/**
	 * check is operator is division
	 * @param operandSymbol
	 * @return
	 */
	private static boolean checkIfIsDivision(String operandSymbol) {
		return operandSymbol.equals(operatorsList[POSITION_IN_OPERATORS_LIS_FOR_DIVIDE]);
	}

	/**
	 * Check is a element from an expression is not an operator
	 * @param operandSymbol
	 * @return
	 */
	private static boolean checkIsOperandIsNotOperator(String operandSymbol) {
		for (String operator : operatorsList) {
			if (operandSymbol.equals(operator)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if an operand is correct
	 * @param operandSymbol
	 * @param symbolTable
	 * @param resultDataType
	 */
	private static void checkOperands(String operandSymbol, Map<String, SymbolTableEntry> symbolTable, String resultDataType) {
		checkVariableDeclaration(symbolTable, operandSymbol);
		//check if they have the same dataType
		SymbolTableEntry variableEntry = symbolTable.get(operandSymbol);
		if(variableEntry != null) {
			checkDataType(operandSymbol,resultDataType,variableEntry.getDataType());
		}
	}

	/**
	 * Check if constants have the same dataType as the left side
	 * @param resultDataType
	 * @param variable    constant variable
	 */
	private static void checkDataTypeConstant(String resultDataType,String variable) {
		boolean isInteger = isConstantInteger(variable);
		if(isInteger) {
		  checkDataType(variable,resultDataType,"INT");
		}
		
		boolean isChar = isConstantChar(variable);
		if(isChar) {
			checkDataType(variable,resultDataType,"CHAR");
		}
	}

	private static void checkDataType(String variableName, String resultDataType, String variableDataType) {
		boolean checked = checkCorrelationDataType(variableDataType,resultDataType);
		  if(!checked) {
			  throwDifferentDataType(variableName);
		  }
	}

	/**
	 * Throw error if variable has a different dataType than left side of operation
	 * @param variable
	 */
	private static void throwDifferentDataType(String variable) {
		System.err.println(variable + " has a different dataType that the left side");
		
	}

	/**
	 * Check is the correlation between data types is correct
	 * @param constantDataType
	 * @param resultDataType
	 * @return
	 */
	private static boolean checkCorrelationDataType(String constantDataType, String resultDataType) {
		return constantDataType.equals(resultDataType);
		
	}

	private static void checkVariableDeclaration(Map<String, SymbolTableEntry> symbolTable, String variable) {
		//check if variable is declared
		boolean isDeclared = checkVariableIsDeclared(symbolTable, variable);
		if(!isDeclared) {
			boolean isConstant = checkVariableIsConstant(variable);
			if(!isConstant) {
				throwErrorDeclaration(isDeclared, variable);
			}
		}
	}

	/**
	 * check a variable is constant
	 * @param variable
	 * @return
	 */
	private static boolean checkVariableIsConstant(String variable) {
		return isConstantInteger(variable) || isConstantChar(variable);
	}

	/**
	 * check is a variable is a constant char
	 * @param variable
	 * @return
	 */
	private static boolean isConstantChar(String variable) {
		try {
			Integer.parseInt(variable);
			return false;
		}catch (NumberFormatException nfe) {
			return variable.length() == 1;
		}
	}

	/**
	 * check is a varible is integer constant
	 * @param variable
	 * @return
	 */
	private static boolean isConstantInteger(String variable) {
		try {
			Integer.parseInt(variable);
			return true;
		}catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * check if in statements the expression represesnts boolean
	 * @param symbolTable
	 * @param children
	 * @param statementName 
	 */
	public static void checkStatements(List<MultiTreeNode> children, String statementName) {
		String possibleExpressionOrVariable = children.get(0).getData();
		if(possibleExpressionOrVariable.equals("Expression")) {
				checkExpressionInStatments(children.get(0).getChildren(), statementName);
		}
	}

	/**
	 * check for improper definitions in statements
	 * @param children
	 * @param symbolTable
	 */
	private static void checkExpressionInStatments(MultiTreeNode[] children,String statementName) {
		boolean booleanStatement = false;
		for(int i = 0; i< children.length; i++) {
			String operandSymbol = children[i].getData();
			if(operandSymbol.equals("EQUAL") || operandSymbol.equals("NEQUAL")) {
				booleanStatement = true;
			}
		}
		if(!booleanStatement) {
			System.err.println("A boolean value is compulsory in " + statementName);
		}
	}
}
