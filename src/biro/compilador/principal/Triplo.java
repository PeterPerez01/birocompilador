package biro.compilador.principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Triplo {
	Ensamblador ens = new Ensamblador();
    static int tempCounter = 1;
    static int iniciofunc, finfunc=0, iniciocode=0, fincode, retornofunc=0, call=0;
    List<String> triplo = new ArrayList<>();
    String triplolimpio="", finalizado= "";
    String malon="";
    int n=0;
    public String processCode(String code) {
        String[] lines = code.split("\n");
        String retorno="";
        String[] results={};
        
        
        String tempVar = "T" + tempCounter;

        Pattern pattern = Pattern.compile("([*a-zA-Z0-9]+)\\s*([=+\\-*/%])\\s*([*a-zA-Z0-9.]+)(?:\\s*([=+\\-*///%])\\s*([*a-zA-Z0-9.]+))?");
        Pattern functionPattern = Pattern.compile("function\\s*(\\*dec|\\*ent)\\s*\\.([a-zA-Z0-9]+)\\s*\\(");
        Pattern functionCallPattern = Pattern.compile("([*a-zA-Z0-9]+)\\s*([=+\\-*/%])\\s*\\.([a-zA-Z]+)\\s*\\(((\\.?([a-zA-Z]+|\\d+|\\s|\\,)*)*?)\\)");

        Pattern pattern2 = Pattern.compile("([*a-zA-Z0-9]+)\\s*([=+\\-*/%])\\s*([*a-zA-Z0-9.]+)");
        
        //BORRAR ESTO AL FINAL SOLO SIRVE PARA DEBUG
        System.out.println("DEBUG\n-------------------------------------------------------");
        for (int i = 0; i < lines.length; i++) {
        	
        	
        	if(lines[i].contains("\"")) {
        		String[] partes=lines[i].split("\\s");
        		System.out.println("Variable: "+partes[0]+" OP: "+partes[1]+ "Var2: "+partes[2]);
        		
        		String var1 = partes[0];
                String op = partes[1];
                String valor = partes[2].replace(";", "").trim().replace("\n", "");
                //String op3 = matcher.group(5);
                //String operador2 = matcher.group(4);
                
                
                
                n++;
                iniciocode=n+1;
                triplo.add(n+","+tempVar + ",'" + valor + "'," + op);
                triplolimpio+=op+" "+tempVar+" "+ valor+"\n";
                n++;
                
                triplo.add(n+","+var1 + "," + tempVar + ",=");
                triplolimpio+="="+" "+var1+" "+ tempVar+"\n";
        	}
        	
        	
        	if(lines[i].startsWith("*")) {
        		// Si es una declaracion de variable ignoramos xd
        		continue;
        	}
        	
        	if(lines[i].contains("(")) {
        		//System.out.println(lines[i]+" <-- contiene (");
        		
            Matcher functionMatcher = functionPattern.matcher(lines[i]);
            if (functionMatcher.find()) {
            	n++;
            	iniciofunc=n+1;
                String returnType = functionMatcher.group(1);
                String quitar = (functionMatcher.group(0));
                String result = lines[i].replace(quitar, "");
                result=result.replace(")", "").replace(returnType, "").replace("{", "").trim().replace("*cad", "").replace("*dec", "").replace("*ent", "").trim();
                if(result.contains(",")) {
                	results = result.split("\\,");
                	System.out.println("R1: "+results[0]+" R2: "+results[1]);
                } else {
                	results[0] = result;
                }
                System.out.println("Result: "+result);
                
                
                
                //String argumento1 = functionMatcher.group(3);
                //System.out.println("Funcion arg: "+argumento1);
                
                triplo.add(n+","+" ," + "X" + "," + "JMP birotes");
                triplolimpio+=""+" "+"X"+" "+ "JMP birotes\n";
                continue;
            }
            
            if(lines[i].contains("return")&& lines[i].contains("(")&&lines[i].contains(")")) {
            	int num=0;
            	retorno = lines[i].replace("return", "").trim().replace("(", "").replace(")", "").replace(";", "");
            	for (String ln : triplo) {
					ln.contains(retorno);
					num=n;
				}
            	retornofunc=num;
            	System.out.println("El retorno esta en: " + retornofunc + ": " + retorno);
            	
            }
            
            Matcher functionCallMatcher = functionCallPattern.matcher(lines[i]);
            if (functionCallMatcher.find()) {
            	String operando = functionCallMatcher.group(1);
            	String argumento = functionCallMatcher.group(4);
            	System.out.println("ARGS: "+argumento);
            	
            	
            	
            	n++;
            	triplo.add(n+","+" ," + "CALL" + ",JMP");
            	triplolimpio+=" "+" "+"CALL"+" "+ "JMP"+"\n";
            	if(argumento.contains(",")) {
            		String[] args= argumento.split("\\,");
            		int it=0;
            		for (String arg : args) {
            			
            			n++;
                        triplo.add(n+","+ tempVar + "," + arg + ",=");
                        triplolimpio+="="+" "+tempVar+" "+ arg+"\n";
                        n++;
                        triplo.add(n+","+ results[it] + "," + tempVar + ",=");
                        triplolimpio+="="+" "+results[it]+" "+ tempVar+"\n";
                        it++;
                        
                        call=n+1;
                        
                        
				}
            	} else {
            	
            	n++;
                triplo.add(n+","+ tempVar + "," + argumento + ",=");
                triplolimpio+="="+" "+tempVar+" "+ argumento+"\n";
            	}
                
                
                
                
                
                
                n++;
                triplo.add(n+","+" ," + "X" + ",JMP pondio");
                triplolimpio+=""+" "+"X"+" "+ "JMP pondio"+"\n";
                n++;
                triplo.add(n+","+ tempVar + "," + retorno + ",=");
                triplolimpio+="="+" "+tempVar+" "+ retorno+"\n";
                n++;
                triplo.add(n+","+ operando + "," + tempVar + ",=");
                triplolimpio+="="+" "+operando+" "+ tempVar+"\n";
                //continue;
            }
            
            
            
            
            
            
            
            
            
        			} else {  //SI NO CONTIENE PARENTESIS
        				
	        if(lines[i].contains("}")||lines[i].startsWith("}")) {
	        	finfunc=n+1;
	        }
        				
	        Matcher matcher = pattern.matcher(lines[i]);
	        Matcher matcher2 = pattern2.matcher(lines[i]);
            
            if (matcher.find()) {
                String op1 = matcher.group(1);
                String operator = matcher.group(2);
                String op2 = matcher.group(3);
                String op3 = matcher.group(5);
                String operador2 = matcher.group(4);
                
                
                n++;
                iniciocode=n+1;
                triplo.add(n+","+tempVar + "," + op2 + "," + operator);
                triplolimpio+=operator+" "+tempVar+" "+ op2+"\n";
                n++;
                if(op3!=null&&operador2!=null) {
                triplo.add(n+","+tempVar + "," + op3 + ","+operador2);
                triplolimpio+=operador2+" "+tempVar+" "+ op3+"\n";
                n++;}
                
                triplo.add(n+","+"."+op1 + "," + tempVar + ",=");
                triplolimpio+="="+" "+op1+" "+ tempVar+"\n";
                continue;
            }
            																														
            if (matcher2.find()) {
                String op1 = matcher.group(1);
                String operator = matcher.group(3);
                String op2 = matcher.group(2);
                //String op3 = matcher.group(5);
                //String operador2 = matcher.group(4);
                
                
                
                n++;
                iniciocode=n+1;
                triplo.add(n+","+tempVar + "," + op2 + "," + operator);
                triplolimpio+=operator+" "+tempVar+" "+ op2+"\n";
                n++;
                
                triplo.add(n+","+"."+op1 + "," + tempVar + ",=");
                triplolimpio+="="+" "+op1+" "+ tempVar+"\n";
            }
            
        			}
            
            
            
        }
        //BORRAR ESTO AL FINAL, SOLO SIRVE PARA DEBUG
            System.out.println("\n-------------------------------------------------------\n");
            n++;
            triplo.add(n+" ... "+" " + " " + " " + " ");

        StringBuilder triploCsv = new StringBuilder("N,Dato Objeto,Dato Fuente,Operador\n");
        for (String t : triplo) {
        	if(t.contains("birotes")) {
        		t=t.replace("birotes", "");
        		finalizado+=t.replace("birotes", "");
        		for (String line : triplo) {
					if(line.startsWith(""+finfunc)) {
						if(line.contains("JMP")) {
							finfunc++;
						}
					}
				}
        		
        		t=t.replace("X", ""+finfunc);
        		finalizado+=t.replace("X", ""+finfunc);
        	}
        	
        	if(t.contains("pondio")) {
        		t=t.replace("pondio", "");
        		t=t.replace("X", ""+iniciofunc);
        		
        	}
        	
        	if(t.contains("CALL")) {
        		for (String line : triplo) {
					if(line.startsWith(""+call)) {
						if(line.contains("JMP")) {
							call++;
						}
					}
				}
        	t=t.replace("CALL", ""+call);
        	finalizado+=t.replace("CALL", ""+call);
        	}
        	
        	
        	finalizado+=t;
            triploCsv.append(t).append("\n");
        }
        
        
        
        
        for (String t : triplolimpio.split("\n")) {
        	if(t.contains("birotes")) {
        		t=t.replace("birotes", "");

        		for (String line : triplo) {
					if(line.startsWith(""+finfunc)) {
						if(line.contains("JMP")) {
							finfunc++;
						}
					}
				}
        		
        		t=t.replace("X", ""+finfunc);
        		
        	}
        	
        	if(t.contains("pondio")) {
        		t=t.replace("pondio", "");
        		t=t.replace("X", ""+iniciofunc);
        		
        	}
        	
        	if(t.contains("CALL")) {
        		for (String line : triplo) {
					if(line.startsWith(""+call)) {
						if(line.contains("JMP")) {
							call++;
						}
					}
				}
        	t=t.replace("CALL", ""+call);
        	}
        	
        	System.out.println(t);
        	malon+=t.replace("  ", " ")+"\n";
        }
        
        this.reinicioCont();
        
        ens.Ensamblar(malon);
        
        System.out.println("MALON");
        System.out.println(malon);
        return triploCsv.toString();
    }
    
    public void reinicioCont() {
    	tempCounter = 1;
    }
    
    
    public static void main(String[] args) {
		Triplo trp = new Triplo();
		
		String codigo = "// Codigo Ejemplo\r\n"
				+ "*ent .entero1, .entero2, .entero3;\r\n"
				+ "*dec .real1, .real2, .real3;\r\n"
				+ "*cad .cadena1, .cadena2, .cadena3;\r\n"
				+ "\r\n"
				+ "function *dec .funcion ( *ent .arg1 , *ent .arg2 ) {\r\n"
				+ ".arg2 = .arg1 + .arg2;\r\n"
				+ ".arg2 = .arg2 / 2;\r\n"
				+ "return ( .arg2 );\r\n"
				+ "}\r\n"
				+ ".real1 = .funcion(131.121, .entero9);\r\n"
				+ ".cadena1 = \"hola\";\r\n"
				+ ".cadena2 = \"mundo\";\r\n"
				+ ".cadena3 = \"lya\";\r\n"
				+ ".real1 = 121;\r\n"
				+ ".real2 = 131;\r\n"
				+ ".real3 = 141.1551;\r\n"
				+ ".entero3 = .entero2 % .entero1;";
		
		System.out.println("Codigo de entrada: \n" + codigo+"\n\n");
		System.out.println(trp.processCode(codigo));
		
	}
    
}


