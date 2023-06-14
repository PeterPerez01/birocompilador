package biro.compilador.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ensamblador {
	public String ruta="./Ensamblador.ASM";
	
	public void setRuta(String rut) {
		this.ruta=rut;
	}
    
	public void Ensamblar(String triplo) {
		String imprimir="";
		//Limpiamos el triplo entrada
		triplo=triplo.replace(",", "");
		triplo=triplo.replace("N,Dato Objeto,Dato Fuente,Operador", "");
		String[] lineas = triplo.split("\n");
		int index=0, num=0;
		List<String> as = new ArrayList<>();
		List<String> marcadores = new ArrayList<>();
		boolean multi = false, mod=false;
		for (String linea : lineas) {
			System.out.print(linea);
			linea=linea.replaceAll("\\s\\s", " ");
		}
		System.out.println("------------ENDEBUG------------");
		for (String linea : lineas) {
			linea=linea.replace("  ", " ");
			num++;
			as.add("Linea"+num+": ");
			if(linea.contains("JMP")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("0 "+renglon[0]); //A DONDE
				System.out.println("1"+renglon[1]); // INSTRUCCION JMP
				as.add("\t"+renglon[1]+" "+"Linea"+renglon[0]);
				marcadores.add("Linea"+renglon[0]);
			}
			
			if(linea.contains("+")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("SUM0: "+renglon[0]); //OPERANDO
				System.out.println("SUM1: "+renglon[1]); // PRIMER OP
				System.out.println("SUM2: "+renglon[2]); // SEGUNDO OP
				as.add("\t"+"ADD "+ "AX, "+ renglon[2]);
			}
			
			if(linea.contains("-")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("SUM0: "+renglon[0]); //OPERANDO
				System.out.println("SUM1: "+renglon[1]); // PRIMER OP
				System.out.println("SUM2: "+renglon[2]); // SEGUNDO OP
				as.add("\t"+"SUB "+ "AX, "+ renglon[2]);
			}
			
			if(linea.contains("*")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("MUL 0: "+renglon[0]); //OPERANDO
				System.out.println("MUL1: "+renglon[1]); // PRIMER OP
				System.out.println("MUL2: "+renglon[2]); // SEGUNDO OP
				as.add("\t"+"MOV "+ "BL, "+ renglon[2]);
				index=as.size();
				if(as.get(index-1).length()<1) {
					index--;
				}
				//String anterior=as.get(index-1);
				//String[] ant = anterior.trim().split("\\s");
				as.add("\t"+"MUL "+ "BL"+"");//aqui estaba ant[2]
				multi=true;
			}
			
			if(linea.contains("/")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("\t"+"DIV 0: "+renglon[0]); //OPERANDO
				System.out.println("\t"+"DIV1: "+renglon[1]); // PRIMER OP
				System.out.println("\t"+"DIV2: "+renglon[2]); // SEGUNDO OP
				index=as.size();
				
				if(as.get(index-2).length()<1) {
					index--;
				}
				
				String anterior=as.get(index-2);
				String[] ant = anterior.trim().split("\\s");
				as.add("\t"+"MOV "+ "AX, "+ant[2]);
				as.remove(index-2);
				as.add("\t"+"MOV "+ "BL, "+ renglon[2]);
				as.add("\t"+"DIV "+ "BL");
				as.add("\t"+"MOV AH, 0");
				multi=true;
			}
			
			if(linea.contains("%")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("MOD 0: "+renglon[0]); //OPERANDO
				System.out.println("MOD 1: "+renglon[1]); // PRIMER OP
				System.out.println("MOD 2: "+renglon[2]); // SEGUNDO OP
				index=as.size();
				if(as.get(index-1).length()<1) {
					index--;
				}
				String anterior=as.get(index-2);
				String[] ant = anterior.trim().split("\\s");
				as.add("\t"+"MOV "+ "AX, "+ant[2]);
				as.remove(index-2);
				as.add("\t"+"MOV "+ "BL, "+ renglon[2]);
				as.add("\t"+"DIV "+ "BL");
				mod=true;
			}
			
			if(linea.contains("=")) {
				String[] renglon = linea.trim().split("\\s");
				System.out.println("0x: "+renglon[0]); //OPERANDO
				System.out.println("1x: "+renglon[1]); // PRIMER OP
				System.out.println("2x: "+renglon[2]); // SEGUNDO OP
				if(mod) {
						as.add("\t"+"MOV "+renglon[1]+", "+"AH");
						mod=false;
						continue;
					} else if(renglon[1].contains("T1")) {
					as.add("\t"+"MOV "+"AL"+", "+renglon[2]);
					Pattern patternId = Pattern.compile("^\\.");
					Matcher matcherId = patternId.matcher(linea);
					matcherId.find();
					System.out.println("DEBUGGING 0: " +renglon[0] + " , 1: "+renglon[1]+ " - 2: " +renglon[2] + " LINE: "+linea);
					
				
					
				} if(renglon[2].contains("T1")) {
					
					if(multi) {
						as.add("\t"+"MOV "+renglon[1]+", "+"AX");
						multi=false;
					}else {
					
					as.add("\t"+"MOV "+renglon[1]+", "+"AL");
					}
					
					
					
				}
				
			}
			
			
		}
		
		System.out.println("\n\n### ASSEMBLER intel 8086 ###\n\n");
		imprimir+="##BIRO ASSEMBLY CODE GENERATOR##\n";
		for (String line : as) {
			for (String string : marcadores) {
				if(line.equals(string+": ")) {
					imprimir+=line+"\n";
					System.out.println(line+"");
				}
				
			}
			if(line.contains("JMP")) {
					imprimir+=line+";\n";
					System.out.println(line+";");
				}
				if(!line.contains("Linea")) {
					imprimir+=line+";\n";
					System.out.println(line+";");
				}
		}
		
		ExportarOld eo = new ExportarOld();
		eo.Imprimir(ruta, imprimir);
		
	}
	
	public static void main(String[] args) {
		String codigoprueba=" 9 JMP \r\n"
				+ "= T1 .arg1\r\n"
				+ "+ T1 .arg2\r\n"
				+ "= arg2 T1\r\n"
				+ "= T1 .arg2\r\n"
				+ "/ T1 2\r\n"
				+ "= arg2 T1\r\n"
				+ "  14 JMP\r\n"
				+ "= T1 131.121\r\n"
				+ "= .arg1 T1\r\n"
				+ "= T1 .entero9\r\n"
				+ "= .arg2 T1\r\n"
				+ " 2 JMP \r\n"
				+ "= T1 .arg2 \r\n"
				+ "= real1 T1\r\n"
				+ "= T1 \"hola\"\r\n"
				+ "= .cadena1 T1\r\n"
				+ "= T1 \"mundo\"\r\n"
				+ "= .cadena2 T1\r\n"
				+ "= T1 \"lya\"\r\n"
				+ "= .cadena3 T1\r\n"
				+ "= T1 121\r\n"
				+ "= real1 T1\r\n"
				+ "= T1 131\r\n"
				+ "= real2 T1\r\n"
				+ "= T1 141.1551\r\n"
				+ "= real3 T1\r\n"
				+ "= T1 .entero2\r\n"
				+ "* T1 .entero1\r\n"				
				+ "= entero3 T1";
		Ensamblador ens = new Ensamblador();
		ens.Ensamblar(codigoprueba);
	}

}