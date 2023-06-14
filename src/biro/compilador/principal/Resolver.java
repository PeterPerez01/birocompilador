package biro.compilador.principal;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resolver {
	public void analizar(String codigo) {
	    // Dividimos el código en líneas
	    String[] lineas = codigo.split("\n");
	    
	    // Creamos un diccionario para guardar las variables
	    HashMap<String, Object> variables = new HashMap<>();
	    
	    // Recorremos cada línea del código
	    for (String linea : lineas) {
	        // Si la línea es un comentario, la ignoramos
	        if (linea.startsWith("//")) {
	            continue;
	        }
	        
	        // Si la línea es un comentario de varias líneas, buscamos el final del comentario
	        if (linea.startsWith("/*")) {
	            int indiceFinComentario = codigo.indexOf("*/", codigo.indexOf("/*") + 2);
	            if (indiceFinComentario >= 0) {
	                // Ignoramos el comentario
	                continue;
	            }
	        }
	        
	        // Buscamos una operación aritmética en la línea
	        Matcher matcher = Pattern.compile("[\\+\\-\\*\\/\\%]").matcher(linea.replace("+", "\\+"));
	        if (matcher.find()) {
	            // Si encontramos una operación aritmética, la interpretamos
	            String[] partes = linea.split(matcher.group());
	            String var1 = partes[0].trim();
	            String var2 = partes[1].trim();
	            String operador = matcher.group();
	            
	            // Obtenemos el valor de la primera variable
	            Object valor1 = obtenerValorVariable(var1, variables);
	            
	            // Obtenemos el valor de la segunda variable
	            Object valor2 = obtenerValorVariable(var2, variables);
	            
	            // Realizamos la operación aritmética
	            Object resultado = null;
	            if (valor1 instanceof Integer && valor2 instanceof Integer) {
	                // Si ambas variables son enteras, realizamos la operación como entero
	                int num1 = (int) valor1;
	                int num2 = (int) valor2;
	                switch (operador) {
	                    case "+":
	                        resultado = num1 + num2;
	                        break;
	                    case "-":
	                        resultado = num1 - num2;
	                        break;
	                    case "*":
	                        resultado = num1 * num2;
	                        break;
	                    case "/":
	                        resultado = num1 / num2;
	                        break;
	                    case "%":
	                        resultado = num1 % num2;
	                        break;
	                }
	            } else if (valor1 instanceof Double && valor2 instanceof Double) {
	                // Si ambas variables son reales, realizamos la operación como real
	                double num1 = (double) valor1;
	                double num2 = (double) valor2;
	                switch (operador) {
	                    case "+":
	                        resultado = num1 + num2;
	                        break;
	                    case "-":
	                        resultado = num1 - num2;
	                        break;
	                    case "*":
	                        resultado = num1 * num2;
	                        break;
	                    case "/":
	                        resultado = num1 / num2;
	                        break;
	                    case "%":
	                        // No se puede calcular el módulo de un número real
	                        System.out.println("No se puede calcular el módulo de un número real");
	                        continue;
	                }
	            } else {
	                // Si las variables son de distinto tipo, no podemos realizar la operación
	                System.out.println("No se puede realizar la operación aritmética porque las variables son de distinto tipo");
						continue;
					}
		            // Guardamos el resultado en una variable temporal
		            String tempVariable = ".temp" + UUID.randomUUID().toString().replace("-", "");
		            variables.put(tempVariable, resultado);
		            
		            // Imprimimos el resultado en consola
		            System.out.println(tempVariable + " = " + resultado.toString());
		        }
		    }
	}
					/**	Método auxiliar para obtener el valor de una variable en base a su nombre y el diccionario de variables
					@param nombreVariable el nombre de la variable
					@param variables el diccionario de variables
					@return el valor de la variable, o null si la variable no existe o no tiene un valor válido
					 */
					private Object obtenerValorVariable(String nombreVariable, HashMap<String, Object> variables) {
						if (variables.containsKey(nombreVariable)) {
							Object valor = variables.get(nombreVariable);
							if (valor instanceof String) {
								// Si la variable es una cadena, la convertimos a entero o real si es posible
								String valorStr = (String) valor;
								try {
									return Integer.parseInt(valorStr);
								} catch (NumberFormatException e) {
									try {
										return Double.parseDouble(valorStr);
									} catch (NumberFormatException e2) {
										System.out.println("La variable " + nombreVariable + " no tiene un valor válido");
										return null;
									}
								}
							} else if (valor instanceof Integer || valor instanceof Double) {
								// Si la variable es entero o real, retornamos su valor directamente
								return valor;
							} else {
								// Si la variable no es una cadena, entero o real, no podemos obtener su valor
								System.out.println("La variable " + nombreVariable + " no tiene un valor válido");
								return null;
							}
						} else {
							// Si la variable no existe, no podemos obtener su valor
							System.out.println("La variable " + nombreVariable + " no existe");
							return null;
						}
					}
}