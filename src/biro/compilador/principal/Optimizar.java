package biro.compilador.principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Optimizar {
    public Optimizar() {
        new HashMap<>();
    }

    public String optimizar(String codigo) {
        // Dividir el código en líneas
        String[] lineas = codigo.split("\n");
        List<String> code = new ArrayList<>();
        List<String> funciones = new ArrayList<>();
        List<String> functions= new ArrayList<>();;
        String replace="";
        String[] funcionactual= {};
        boolean infunc = false;
        String aux="";
        int birote=0;
        
        
        for (String linea : lineas) {
        	
        	
        	
        	if (linea.contains("function")&&linea.contains("(")&&linea.contains(")")) {
        		code.add(linea);
        		//Obtener el nombre de la funcion
        		aux=(linea.replace("(", "").replace(")", "").replace("function", "").replace("*dec", "").replace("*cad", "").replace("*ent", "").replace("{", ""));
        		
        		funcionactual=aux.trim().split("\s");
        		
        		funciones.add(funcionactual[0].trim());
        		
        	}
        	
        	if(linea.contains("{")) {
        		infunc=true;
        	}
        	if(linea.contains("}")) {
        		infunc=false;
        	}
        	
        	
        	
        	if(infunc) {
        		if(linea.contains("function")&&linea.contains("(")) {
        			//Si es la declaracion de la funcion ignorar
        			birote++;
        		}
        		else {
        			
	        		functions.add(funcionactual[0] + " " + linea);
	        		System.out.println(birote+" - "+funcionactual[0]+": "+linea);
        		}
        	}
        	
        	if(linea.contains("function")||linea.contains("{")||infunc||linea.contains("}")) {
        		//NO GUARDAR
        	} else {
        		code.add(linea);
        		
        	}
        	
        	for (String string : funciones) {
        		
        		
    			if(linea.contains(string)&&linea.contains("=")) {
    				System.out.println("LLAMADA: "+linea.trim() + " A FUNC: "+string);
    				
    				if(infunc) {
    					//ENTONCES ESTÁ LLAMANDO UNA FUNCION DENTRO DE OTRA
    					System.out.println("COPIAR DE: "+string+" A: "+funcionactual[0]);
    					
    					for (String line : functions) {
    						if(line.contains(string)&&!line.startsWith(funcionactual[0])) {
    							line=line.replace(string, "").replace("-", "").trim();
    							System.out.println("COPIA: "+line);
    							code.add(line);
    							replace=string;
    							if(line.contains("return")) {
    								code.add("}");
    							}
    						}
    						
    					}
    					
    				}//fin infunc llamada
    			}
    		}
        	
        }

        for (String string : funciones) {
			System.out.println("FUNCION: "+ string);
			
		}
        
        for (String ln : code) {
			System.out.println("FINAL: "+ln);
		}
        
        codigo="";
        for (String lineacode : code) {
        	if(lineacode.contains("function")&&lineacode.contains(replace)) {
        		
        		codigo+=lineacode.replace(replace, funcionactual[0]);
        	} else {
        		if(lineacode.contains(funcionactual[0]) && lineacode.contains("function")) {
			
			} else codigo+=lineacode+"\n";
			}
		}

        return(codigo);
    }
    
    public static void main(String[] args) {
        String codigo =
                "*ent .entero1, .entero2, .entero3;\n" +
                "*dec .real1, .real2, .real3;\n" +
                "*cad .cadena1, .cadena2, .cadena3;\n" +
                "\n" +
                "function *dec .funcion ( *ent .arg1 , *ent .arg2 ) {\n" +
                "    .arg2 = .arg1 + .arg2;\n" +
                "    return ( .arg2 );\n" +
                "}\n" +
                "\n" +
                "function *dec .usar ( *ent .ent, *dec .deci) {\n" +
                "    .real2 = .funcion(.entero1, .real2);\n" +
                "    return (.real2);\n" +
                "}\n" +
                "\n" +
                ".real1 = .usar (.enterito, .enterado);";

        Optimizar optimizador = new Optimizar();
        String optimizado = optimizador.optimizar(codigo);

        System.out.println("Código de entrada:");
        System.out.println(codigo);
        System.out.println("\n===================\n");
        System.out.println("Código Optimizado:");
        System.out.println(optimizado);

        
        
    }
}
