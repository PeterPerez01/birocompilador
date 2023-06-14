package biro.compilador.principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BiroInterpretar {
    String code;
    private HashMap<String, String> variables;
    private HashMap<String, Function> functions;
    HashMap<String, Function> functionStack;
    private ArrayList<String> errors;
    private ArrayList<String> imprimir;
    public BiroInterpretar() {
        variables = new HashMap<>();
        functions = new HashMap<>();
        errors = new ArrayList<>();
        imprimir = new ArrayList<>();
        functionStack = new HashMap<>();
        imprimir.add("Token,Lexema,Linea,Descripcion");
    }

    public void interpret(String code) {
        this.code = code;
        String[] lines = code.split("\n");
        
        
        
        int token=0;
        String tokenado="ErrSem ";

        Pattern variableDeclarationPattern = Pattern.compile("(\\*\\w+)\\s+(\\.\\w+)\\s*(=\\s*(.*))?;");
        Pattern variableAssignmentPattern = Pattern.compile("(\\.\\w+)\\s*=\\s*(.*?);");
        Pattern functionDeclarationPattern = Pattern.compile("function\\s+(\\\\w+)\\s+(\\.\\w+)\\s\\((.?)\\)\\s\\{");
        Pattern functionCallPattern = Pattern.compile("(\\.\\w+)\\s*\\((.*?)\\);");
        Pattern returnTypePattern = Pattern.compile("return\\s+\\(?(.*?)\\)?;");


        boolean insideFunction = false;
        StringBuilder functionBody = new StringBuilder();
        String currentFunctionName = "";
        Function currentFunction = null;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();//.replace(",", "");

            if (line.startsWith("//")) {
                continue;
            }
            
            if(line.equals("")||line.equals(" ")||line.equals(null)) {
            	continue;
            }

            if (insideFunction) {
                if (line.startsWith("}")) {
                    insideFunction = false;
                    currentFunction.setBody(functionBody.toString());
                    //System.out.println(functionBody.toString());
                    functions.put(currentFunctionName, currentFunction);
                    functionStack.remove(currentFunctionName);
                    functionBody.setLength(0);
                    continue;
                } else {
                    functionBody.append(line).append("\n");
                }
                continue;
            }

            Matcher variableDeclarationMatcher = variableDeclarationPattern.matcher(line);
            Matcher variableAssignmentMatcher = variableAssignmentPattern.matcher(line);
            Matcher functionDeclarationMatcher = functionDeclarationPattern.matcher(line);
            Matcher functionCallMatcher = functionCallPattern.matcher(line);
            Matcher returnTypeMatcher = returnTypePattern.matcher(line);
            
            if(line.startsWith("*cad")||line.startsWith("*dec")||line.startsWith("*ent")&&line.contains(",")) {
            	String[] variables = line.split("\\,");
            	String type="";
            	for (String var : variables) {
            		String[] vars = var.split("\\s");
            		if(vars[0]!=null&&vars[0]!="") {
            			type=vars[0];}
            			String variable=vars[1].replace(";", "");
            		/*String type = getType(variable);
                    if (!isValidValue(type, value)) {
                    	errors.add("Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                    	token++;
                        String tokn=tokenado+token;
                        imprimir.add(tokn+","+variable+","+(i+1)+","+"Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                    } else {
                    variables.put(var + "_value", value);
                    }*/
            		
            		System.out.println("Tipo: "+type +", Variable: "+variable);
            		
            		this.variables.put(variable, type);
				}
            	
            }

            if (variableDeclarationMatcher.matches()) {
                String type = variableDeclarationMatcher.group(1);
                String variable = variableDeclarationMatcher.group(2);
                String value = variableDeclarationMatcher.group(4);

                if (variables.containsKey(variable) && !isCompatibleType(type, getType(variable))) {
                    errors.add("Error de compatibilidad de tipos en la linea " + (i + 1) + ": la variable " + variable + " no es de tipo " + type);
                    token++;
                    String tokn=tokenado+token;
                    imprimir.add(tokn+","+variable+","+(i+1)+","+"Error de compatibilidad de tipos en la linea " + (i + 1) + ": la variable " + variable + " no es de tipo " + type);
                } else if (value != null && !isValidValue(type, value)) {
                    errors.add("Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                    token++;
                    String tokn=tokenado+token;
                    imprimir.add(tokn+","+variable+","+(i+1)+","+"Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                } else {
                    variables.put(variable, type);
                    if (value != null) {
                        variables.put(variable + "_value", value);
                    }
                }
            } else if (variableAssignmentMatcher.matches()) {
                String variable = variableAssignmentMatcher.group(1);
                String value = variableAssignmentMatcher.group(2);

                if (!variables.containsKey(variable)) {
                    errors.add("Error de variable no definida en la linea " + (i + 1) + ": la variable " + variable + " no ha sido definida");
                    token++;
                    String tokn=tokenado+token;
                    imprimir.add(tokn+","+variable+","+(i+1)+","+"Error de variable no definida en la linea " + (i + 1) + ": la variable " + variable + " no ha sido definida");
                } else {

                    //Pattern functionCallPattern2 = Pattern.compile("([*a-zA-Z0-9]+)\\s*([=+\\-*/%])\\s*\\.([a-zA-Z]+)\\s*\\(((\\.?([a-zA-Z]+|\\d+|\\s|\\,)*)*?)\\)");
                    //Matcher functionCallMatcher2 = functionCallPattern2.matcher(line);
                	if(value.contains("(")||value.contains("+")||value.contains("-")||value.contains("/")||value.contains("*")||value.contains("%")||value.contains(",")) {continue;}
                    String type = getType(variable);
                    if (!isValidValue(type, value)) {
                    	errors.add("Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                    	token++;
                        String tokn=tokenado+token;
                        imprimir.add(tokn+","+variable+","+(i+1)+","+"Error Semantico en la linea " + (i + 1) + ": el valor " + value + " asignado a la variable " + variable + " no es vï¿½lido");
                    } else {
                    variables.put(variable + "_value", value);
                    }
                    }
                    } else if (functionDeclarationMatcher.matches()&&line.contains("function")) {
                    	
                    String returnType = functionDeclarationMatcher.group(1);
                    String functionName = functionDeclarationMatcher.group(2);
                    String args = functionDeclarationMatcher.group(3);
                    currentFunctionName = functionName;
                    currentFunction = new Function(returnType, args, (i+1));
                    insideFunction = true;
                    functionStack.put(currentFunctionName, currentFunction);
                    continue;
                } else if (functionCallMatcher.matches()) {
                    String functionName = functionCallMatcher.group(1);
                    String args = functionCallMatcher.group(2);

                    Function function = functions.get(functionName);
                    if (function == null) {
                        errors.add("Error de funciï¿½n no definida en la linea " + (i + 1) + ": la funciï¿½n " + functionName + " no ha sido definida");
                        token++;
                        String tokn=tokenado+token;
                        imprimir.add(tokn+","+functionName+","+(i+1)+","+"Error de funciï¿½n no definida en la linea " + (i + 1) + ": la funciï¿½n " + functionName + " no ha sido definida");
                    } else {
                        String[] argValues = args.split(",");
                        function.execute(argValues, this);
                    }
                } else if (returnTypeMatcher.matches()) {
                	currentFunction = functionStack.get(currentFunctionName);
                	//System.out.println("AAAAAAAAA3: "+currentFunction+currentFunctionName);
                	String returnValue = returnTypeMatcher.group(1);
                	try {
                		returnValue = returnTypeMatcher.group(1);
                	//System.out.println(returnValue+" ");
                	
                	if(returnValue.startsWith(".")) {
                		//Si esta asignando una variable
                		if(isCompatibleType(functions.toString(), getType(returnValue))) {
                			variables.put(".return_value", returnValue);
                		} else {
                			//System.out.println("AAAAAAAAA: "+currentFunction.getReturnType());
                		}
                	}
                	//variables.put(".return_value", returnValue);
                	                	
                	if (!isValidValue(currentFunction.getReturnType(), returnValue)) {
                		
                		errors.add("Error de tipo de retorno en la linea " + (i + 1) + ": el valor " + returnValue + " no es vï¿½lido para el tipo de retorno " + currentFunction.getReturnType());
                		token++;
                        String tokn=tokenado+token;
                        imprimir.add(tokn+","+returnValue+","+(i+1)+","+"Error de tipo de retorno en la linea " + (i + 1) + ": el valor " + returnValue + " no es vï¿½lido para el tipo de retorno " + currentFunction.getReturnType());
                        
                	} else {
                		variables.put(".return_value", returnValue);
    }
                	} catch(Exception exc) {
                		if(currentFunction!=null) {
                		String tokn=tokenado+token;
                		errors.add("Error de inicializacion de funcion en linea " + (i + 1) + ": la funcion no ha sido definida correctamente "+exc.toString());
                		imprimir.add(tokn+","+returnValue+","+(i+1)+","+"Error de inicializacion de funcion en linea " + (i + 1) + ": la funcion no ha sido definida correctamente");
                		}
                	}
}
                
                else {
                    //errors.add("Error Semantico en la linea " + (i + 1) + ": la linea no coincide con ninguna instruccion valida");
                    token++;
                    String tokn=tokenado+token;
                    
                    if(line.startsWith("*ent")||line.startsWith("*dec")||line.startsWith("*cad")||line.startsWith(".")&&line.contains(",")) {} else {
                    imprimir.add(tokn+","+line+","+(i+1)+","+"Error Semantico en la linea " + (i + 1) + ": la linea no coincide con ninguna instruccion valida");
                    	}
                }
            }
        }
    	Pattern integerPattern = Pattern.compile("^1\\d*1$");
        Pattern decimalPattern = Pattern.compile("^1\\d*(\\.\\d*1$)?");
        Pattern stringPattern = Pattern.compile("\".*\"");
        
        String getType(String variable) {
        	Matcher integerMatcher = integerPattern.matcher(variable);
            Matcher decimalMatcher = decimalPattern.matcher(variable);
            Matcher stringMatcher = stringPattern.matcher(variable);
        	if(integerMatcher.matches()&&(variable.startsWith("1")&&variable.endsWith("1"))) {return "*ent";}
        	else if(decimalMatcher.matches()&&(variable.startsWith("1")&&variable.endsWith("1"))) {return "*dec";}
        	else if(stringMatcher.matches()) {return "*cad";}
        
        	else {return variables.get(variable);}
        }
        
        
        
         
        /*
        Pattern integerPattern = Pattern.compile("^-?\\d+$");
        Pattern decimalPattern = Pattern.compile("^[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?$");
        Pattern stringPattern = Pattern.compile("^\".*\"$");
*/
        
        boolean isValidValue(String type, String value) {
            Matcher integerMatcher = integerPattern.matcher(value);
            Matcher decimalMatcher = decimalPattern.matcher(value);
            Matcher stringMatcher = stringPattern.matcher(value);

            if (type.equals("*ent") && integerMatcher.matches()) {
                return true;
            } else if (type.equals("*dec") && (decimalMatcher.matches() || integerMatcher.matches())) {
                return true;
            } else if (type.equals("*cad") && stringMatcher.matches()) {
                return true;
            } else if (variables.containsKey(value) && isCompatibleType(type, getType(value))) {
                return true;
            }

            return false;
        }


        boolean isCompatibleType(String type1, String type2) {
            return type1.equals(type2) || (type1.equals("*dec") && type2.equals("*ent"));
        }

        public HashMap<String, String> getVariables() {
            return variables;
        }

        public ArrayList<String> getErrors() {
            return errors;
        }
        
        public HashMap<String,Function> getFunctions() {
            return functions;
        }
        
        
        public ArrayList<String> getImprimir() {
            return imprimir;
        }
        
        public String getErrorsAsString() {
            StringBuilder builder = new StringBuilder();
            for (String fila : imprimir) {
            	fila.replace(",", "");
                builder.append(fila).append("\n");
            }
            return builder.toString();
        }

        public static void main(String[] args) {
            BiroInterpretar bi = new BiroInterpretar();
            bi.interpret("// Codigo Ejemplo\r\n"
            		+ "*ent .entero1, .entero2, .entero3;\r\n"
            		+ "*dec .real1, .real2, .real3;\r\n"
            		+ "*cad .cadena1, .cadena2, .cadena3;\r\n"
            		+ "\r\n"
            		+ "function *dec .funcion (*ent .arg1 , *ent .arg2) {\r\n"
            		+ ".arg2 = .arg1 + .arg2;\r\n"
            		+ ".arg2 = .arg2 / 2;\r\n"
            		+ "return ( .arg2 );\r\n"
            		+ "}\r\n"
            		+ ".real1 = .funcion(131.121, .entero9, 141);\r\n"
            		+ ".cadena1 = \"hola\";\r\n"
            		+ ".cadena2 = \"mundo\";\r\n"
            		+ ".cadena3 = \"lya\";\r\n"
            		+ ".real1 = 121;\r\n"
            		+ ".real2 = 131;\r\n"
            		+ ".real3 = 141.1551;\r\n"
            		+ ".entero3 = .entero2 + .entero1;");


            System.out.println("Variables:");
            for (String var : bi.getVariables().keySet()) {
                System.out.println(var + " = " + bi.getVariables().get(var));
            }

            System.out.println("Errores:");
            for (String error : bi.getErrors()) {
                System.out.println(error);
            }
            
            
            System.out.println("Funciones:"+bi.getFunctions().toString());
            
            
        }
}
class Function {
    private String returnType;
    private String[] argTypes;
    private String[] argNames;
    private String body;
    private BiroInterpretar interpreter;
    private int numlinea;
    int conta=0;
    String token="FunctErr";

    public Function(String returnType, String args, int numlinea) {
        this.returnType = returnType;
        this.setLineNumber(numlinea); // Establece el lineNumber
        String[] argPairs = args.split(",");
        argTypes = new String[argPairs.length];
        argNames = new String[argPairs.length];
        for (int i = 0; i < argPairs.length; i++) {
            String[] pair = argPairs[i].trim().split("\\s+");
            argTypes[i] = pair[0];
            argNames[i] = pair[1];
        }
    }

    public String getReturnType() {
		// TODO Auto-generated method stub
		return returnType;
	}

	public void setBody(String body) {
        this.body = body;
    }

	public Object execute(String[] argValues, BiroInterpretar interpreter) {
	    if (argValues.length != argNames.length) {
	        conta++;
	        interpreter.getErrors().add("Error de nï¿½mero de argumentos: la funciï¿½n requiere " + argNames.length + " argumentos pero se proporcionaron " + argValues.length);
	        interpreter.getImprimir().add(token + conta + "," + argNames[1] + "," + numlinea + "," + "Error de nï¿½mero de argumentos: la funciï¿½n requiere " + argNames.length + " argumentos pero se proporcionaron " + argValues.length);
	        return null;
	    }

	    this.setInterpreter(interpreter);
	    HashMap<String, String> originalVariables = new HashMap<>(interpreter.getVariables());

	    for (int i = 0; i < argValues.length; i++) {
	        String argType = argTypes[i];
	        String argName = argNames[i];
	        String argValue = argValues[i];
	        String pivote1 = argTypes[i].trim().replace(",", "").replace(";", ""),
	                pivote2 = argValues[i].trim().replace(",", "").replace(";", "");
	        if (!interpreter.isValidValue(pivote1, pivote2)) {
	            interpreter.getErrors().add("Error de tipo de argumento: el valor " + argValue + " no es vï¿½lido para el argumento " + argName + " de tipo " + argType);
	            interpreter.getImprimir().add(token + conta + "," + argName + "," + numlinea + "," + "Error de tipo de argumento: el valor " + argValue + " no es vï¿½lido para el argumento " + argName + " de tipo " + argType);
		        
	            return null;
	        } else if (argValue.trim().startsWith("1")&&argValue.trim().endsWith("1")) {
	        	
	        } else {
	        	interpreter.getErrors().add("Error de tipo de argumento: el valor " + argValue + " no es vï¿½lido para el argumento " + argName + " de tipo " + argType);
	            interpreter.getImprimir().add(token + conta + "," + argName + "," + numlinea + "," + "Error de tipo de argumento: el valor " + argValue + " no es vï¿½lido para el argumento " + argName + " de tipo " + argType);
		        
	        }

	        interpreter.getVariables().put(argName, argType);
	        interpreter.getVariables().put(argName + "_value", argValue);
	    }

	    interpreter.interpret(body);
	    
	    Object returnValue = interpreter.getVariables().get(".return_value");
	    
	    
	    String[] lines = body.split("\n");
	    String returnLine = "";

	    for (String line : lines) {
	        if (line.trim().startsWith("return")) {
	            returnLine = line.replace("return", "").replace("(", "").replace(")", "").replace(";", "").trim();
	            //System.out.println(returnLine);
	            break;
	        }
	    }
	    if(interpreter.isValidValue(returnType, returnLine)&&interpreter.isCompatibleType(returnType, interpreter.getType(returnLine))) {
	    	//JOptionPane.showConfirmDialog(null, returnLine);
	    } else {
	    	interpreter.getErrors().add("Error de tipo de retorno: el valor " + returnLine + " no es vï¿½lido para el tipo de retorno " + returnType);
	    	interpreter.getImprimir().add(token + conta + "," + returnLine + "," + numlinea + "," + "Error de tipo de retorno: el valor " + returnLine + " no es vï¿½lido para el tipo de retorno " + returnType);
	        
	    }
	    if (returnLine == null && !returnType.equals("*void")||returnLine.equals("") && !returnType.equals("*void")) {
	        interpreter.getErrors().add("Error de retorno: la función no devuelve ningún valor");
	        interpreter.getImprimir().add(token + conta + "," + "return" + "," + numlinea + "," + "Error de retorno: la funciï¿½n no devuelve ningï¿½n valor");
	        
	    }
	    
	    
	    

	    interpreter.getVariables().clear();
	    interpreter.getVariables().putAll(originalVariables);
		//System.out.println(returnValue);
	    return returnValue;
	    
	}


	public BiroInterpretar getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(BiroInterpretar interpreter) {
		this.interpreter = interpreter;
	}

	public int getLineNumber() {
		return numlinea;
	}

	public void setLineNumber(int lineNumber) {
		this.numlinea = lineNumber;
	}
}

