package biro.compilador.principal;
import java.io.IOException;

public class AnalizadorTexto {
	public static void main(String[] args) {
		try {
			analizarTexto("*cad .var = \"hola\";");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String analizarTexto(String texto) throws IOException {
	    String impresora="";
	    String[] lineas = texto.split("\n");
	    for (int i = 0; i < lineas.length; i++) {
	        String[] palabras = lineas[i].split(" ");
	        for (int j = 0; j < palabras.length; j++) {
	            if (palabras[j].startsWith("*")) {
	                String tipoDato = palabras[j];
	                String nombreVariable = "";
	                String datoAlmacenado = "";
	                String tokenError = "";
	                String reglaIncumplida = "";
	                j++;
	                if (j < palabras.length && palabras[j].startsWith(".")) {
	                    nombreVariable = palabras[j];
	                    j++;
	                    if (j < palabras.length && (palabras[j].startsWith("\"") || tipoDato.equals("*ent") || tipoDato.equals("*dec"))) {
	                        if (palabras[j].startsWith("\"")) {
	                            datoAlmacenado = palabras[j];
	                            if (datoAlmacenado.endsWith("\"")) {
	                                datoAlmacenado = datoAlmacenado.substring(1, datoAlmacenado.length() - 1);
	                            } else {
	                                for (int k = j + 1; k < palabras.length; k++) {
	                                    datoAlmacenado += " " + palabras[k];
	                                    j = k;
	                                    if (palabras[k].endsWith("\"")) {
	                                        datoAlmacenado = datoAlmacenado.substring(1, datoAlmacenado.length() - 1);
	                                        break;
	                                    }
	                                }
	                            }
	                        } else {
	                            datoAlmacenado = palabras[j];
	                            if (tipoDato.equals("*ent")) {
	                                try {
	                                    Integer.parseInt(datoAlmacenado);
	                                } catch (NumberFormatException e) {
	                                    tokenError = datoAlmacenado;
	                                    reglaIncumplida = "No es un entero válido";
	                                }
	                            } else if (tipoDato.equals("*dec")) {
	                                try {
	                                    Double.parseDouble(datoAlmacenado);
	                                } catch (NumberFormatException e) {
	                                    tokenError = datoAlmacenado;
	                                    reglaIncumplida = "No es un número decimal válido";
	                                }
	                            }
	                        }
	                        if (reglaIncumplida.isEmpty()) {
	                            if (tipoDato.equals("*cad") && (datoAlmacenado.startsWith("\"") || datoAlmacenado.endsWith("\""))) {
	                                impresora+=nombreVariable+","+datoAlmacenado+","+(i+1)+","+tipoDato+","+tokenError+","+reglaIncumplida;
	                            } else if (tipoDato.equals("*ent") && !datoAlmacenado.contains(".") && !datoAlmacenado.startsWith("\"") && !datoAlmacenado.endsWith("\"")) {
	                                impresora+=nombreVariable+","+datoAlmacenado+","+(i+1)+","+tipoDato+","+tokenError+","+reglaIncumplida;
	                            } else if (tipoDato.equals("*dec") && (datoAlmacenado.startsWith("\"") || datoAlmacenado.endsWith("\"") || datoAlmacenado.contains("\"") || datoAlmacenado.contains(","))) {
	                            	impresora+=nombreVariable+","+datoAlmacenado+","+(i+1)+","+tipoDato+","+tokenError+","+reglaIncumplida;
	                            }
	                        } else {
	                        	impresora+=nombreVariable+","+datoAlmacenado+","+(i+1)+","+tipoDato+","+tokenError+","+reglaIncumplida;
	                        }
	                    }
	                }
	            }
	        }
	    }
		return impresora;
	}
}
