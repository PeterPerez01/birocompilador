package biro.compilador.principal;

import javax.swing.JTextPane;

public class Ensamblador {

	public String convertToAssembler(JTextPane textPane) {
	    // Obtener el texto del JTextPane
	    String text = textPane.getText();
	    String[] lines = text.split("\\n");
	    StringBuilder result = new StringBuilder();

	    for (String line : lines) {
	        // Analizar el texto para identificar las diferentes operaciones aritméticas y de asignación
	        String[] tokens = line.split("\\s+");
	        String operator = "";
	        String leftOperand = "";
	        String rightOperand = "";

	        for (String token : tokens) {
	            if (isOperator(token)) {
	                operator = token;
	            } else if (leftOperand.isEmpty()) {
	                leftOperand = token;
	            } else {
	                rightOperand = token;
	                break;
	            }
	        }

	        // Determinar el tipo de dato de los operandos
	        String leftOperandType = getOperandType(leftOperand);
	       // String rightOperandType = getOperandType(rightOperand);

	        // Traducir la operación a su equivalente en ensamblador
	        String asm = "";
	        if (operator.equals("=")) {
	            asm = "MOV " + leftOperand + ", " + rightOperand;
	        } else if (operator.equals("+")) {
	            asm = "ADD " + leftOperand + ", " + rightOperand;
	        } else if (operator.equals("-")) {
	            asm = "SUB " + leftOperand + ", " + rightOperand;
	        } else if (operator.equals("*")) {
	            asm = "MUL " + leftOperand + ", " + rightOperand;
	        } else if (operator.equals("/")) {
	            asm = "DIV " + leftOperand + ", " + rightOperand;
	        }

	        // Agregar la convención de tipo de dato a la operación ensamblador
	        if (leftOperandType.equals("*cad")) {
	            asm = "MOV " + leftOperand + ", " + "OFFSET " + rightOperand;
	        } else {
	            asm += " " + leftOperandType;
	        }

	        // Concatenar todas las operaciones traducidas en un solo texto de ensamblador
	        result.append(asm + "\n");
	    }

	    return result.toString();
	}

	// Método auxiliar para determinar si una cadena es un operador
	private static boolean isOperator(String token) {
	    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("=");
	}

	// Método auxiliar para determinar el tipo de dato de un operando
	private static String getOperandType(String operand) {
	    if (operand.matches("^\".*\"$")) {
	        return "*cad";
	    } else if (operand.matches("^\\d+$")) {
	        return "*ent";
	    } else if (operand.matches("^\\d*\\.\\d+$")) {
	        return "*dec";
	    } else {
	        return "";
	    }
	}

	
	
}
