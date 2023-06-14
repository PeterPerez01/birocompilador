package biro.compilador.principal;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exportar {
	private JTextPane textPane;
	List<Data> dataList = new ArrayList<>();

	public Exportar(JTextPane textPane) {
		this.textPane = textPane;
		dataList.add(new Data("Lexema", "Tipo", "Token"));
	}

	public void analyzeText() {
		boolean coma=false, puntocoma=false;
		String content = textPane.getText();
		String[] lines = content.split("\n");
		StringBuilder sb = new StringBuilder();
		int i=0;
		for (String line : lines) {
			if (!line.startsWith("//") && !line.contains("/*")) {
				sb.append(line + "\n");
			}
			if (!line.startsWith(".") && !line.contains("=")) {
				
			}
			if (line.contains("/*")) {
				int startIndex = line.indexOf("/*");
				int endIndex = line.indexOf("*/");
				if (endIndex != -1) {
					sb.append(line.substring(0, startIndex) + line.substring(endIndex + 2) + "\n");
				} else {
					sb.append(line.substring(0, startIndex) + "\n");
					while (!line.contains("*/")) {
						line = lines[++i];
					}
					endIndex = line.indexOf("*/");
					sb.append(line.substring(endIndex + 2) + "\n");
				}
			}
		}
		
		String[] words = sb.toString().split("\\s+");
		int datatypecounter = 0, idcounter = 0, sepcounter = 0, opcounter = 0;
		int functioncounter=1, returncounter=0;
		String currentType = "", token = "";
		
		Pattern patternCad = Pattern.compile("^\\*cad(\\[\\])?");
		Pattern patternDec = Pattern.compile("^\\*dec(\\[\\])?");
		Pattern patternEnt = Pattern.compile("^\\*ent(\\[\\])?");
		Pattern patternId = Pattern.compile("^\\.");
		Pattern patternEqual = Pattern.compile("^=");
		Pattern patternColon = Pattern.compile("^,");
		Pattern patternSemi = Pattern.compile("^;");
		Pattern patternCorch = Pattern.compile("^\\{");
		Pattern patternCierreCor = Pattern.compile("^\\}");
		Pattern patternParentesis = Pattern.compile("^\\(");
		Pattern patternCierreParen = Pattern.compile("^\\)");
		Pattern patternRetorno = Pattern.compile("^return");
		Pattern patternString = Pattern.compile("^\".*\"");
		Pattern patternEntero = Pattern.compile("^[+-]?\\d+$");
		Pattern patternReal = Pattern.compile("^[+-]?\\d+\\.\\d+$");
		Pattern patternFunction = Pattern.compile("(\\*cad|\\*ent|\\*dec)\\s+([a-zA-Z]\\w*)\\s*\\(\\s*(\\*cad|\\*ent|\\*dec)\\s+\\.(\\w*[A-Za-z]\\w*)\\s*(,\\s*(\\*cad|\\*ent|\\*dec)\\s+\\.(\\w*[A-Za-z]\\w*)\\s*)*\\)\\s*\\{");



		for (String word : words) {
		    Matcher matcherCad = patternCad.matcher(word);
		    Matcher matcherDec = patternDec.matcher(word);
		    Matcher matcherEnt = patternEnt.matcher(word);
		    Matcher matcherId = patternId.matcher(word);
		    Matcher matcherRetorno = patternRetorno.matcher(word);
		    Matcher matcherColon = patternColon.matcher(word);
		    Matcher matcherEqual = patternEqual.matcher(word);
		    Matcher matcherSemi = patternSemi.matcher(word);
		    Matcher matcherString = patternString.matcher(word);
		    Matcher matcherEntero = patternEntero.matcher(word);
		    Matcher matcherReal = patternReal.matcher(word);
		    
		    Matcher matcherCier = patternCierreCor.matcher(word);
		    Matcher matcherCorch = patternCorch.matcher(word);
		    Matcher matcherCierre = patternCierreParen.matcher(word);
		    Matcher matcherAbreP = patternParentesis.matcher(word);
		    
		    
		    Matcher matcherFuncion = patternFunction.matcher(word);
		    

		    if (matcherCad.find()) {
		        currentType = "*cad";
		        token = "TD";
		        datatypecounter++;
		        if(word.contains("function")) {} else {
		        if(word.equals("*cad")) { dataList.add(new Data(word, "", token + datatypecounter));
		        }
		        else {
		        	dataList.add(new Data(word, currentType, token + datatypecounter));}
		        }
		    } else if (matcherDec.find()) {
		        currentType = "*dec";
		        token = "TD";
		        datatypecounter++;
		        if(word.equals("*dec")) {dataList.add(new Data(word, "", token + datatypecounter));}
		        else {
		        dataList.add(new Data(word, currentType, token + datatypecounter));
		        }
		        
		    } else if (matcherFuncion.find()) {
		    	if(word.contains("function")) {} else {
		        token = "FN";
		        functioncounter++;
		        dataList.add(new Data(word, "Funcion", token + functioncounter));
		        }
		    } else if (matcherRetorno.find()) {
		        token = "RT";
		        returncounter++;
		        dataList.add(new Data(word, "Return", token + returncounter));
		    }
		    
		    else if (matcherEntero.find()) {
		        currentType = "*ent";
		        token = "CONS";
		        dataList.add(new Data(word, currentType, token));
		    } else if (matcherEnt.find()) {
		        currentType = "*ent";
		        datatypecounter++;
		        token = "TD" + datatypecounter;
		        if(word.equals("*ent")) {dataList.add(new Data(word, "", token + datatypecounter));}
		        else {
		        dataList.add(new Data(word, currentType, token + datatypecounter));
		        }
		    } else if (matcherReal.find()) { 
		        currentType = "*dec";
		        token = "CONS";
		        dataList.add(new Data(word, currentType, token));
		    } else if (matcherId.find() && currentType != "") {
		        token = "ID";
		        idcounter++;
		        dataList.add(new Data(word, currentType, token + idcounter));
		    } else if (matcherId.find() && currentType == null) {
		        JOptionPane.showMessageDialog(null, "No definida "+word);
		    } else if (matcherEqual.find()) {
		        token = "OPA";
		        opcounter++;
		        dataList.add(new Data(word, "Asignacion", token + opcounter));
		        //SI
		    } else if (matcherSemi.find()) {
		    	if(puntocoma==false) {
	        	sepcounter++;
		        if(word.equals("\\}")) {functioncounter++;}
		        dataList.add(new Data("\""+word+"\"", "Separador", "'SEP"+sepcounter));
		        puntocoma=true;
		    	}
		        
		        
	    } else if (matcherCier.find()|| matcherCorch.find()||matcherCierre.find()||matcherAbreP.find()) {
		        	sepcounter++;
			        if(word.equals("\\}")) {functioncounter++;}
			        dataList.add(new Data("\""+word+"\"", "Separador", "'SEP"+sepcounter));
		    } else if (matcherColon.find()) {
		    	if(coma==false) {
		        sepcounter++;
		        dataList.add(new Data("\",\"", "Separador", "'SEP"+sepcounter));
		        coma=true;
		        }
		    }else if (matcherString.find()) {
		        currentType = "*cad";
		        token="ID";
		        idcounter++;
		        word=word.replace("\"", "\'\"");
		        dataList.add(new Data(word, currentType, token+idcounter));
		    } else {
		        token="FN";
		        word = String.format("\"%s\"", word);
		        dataList.add(new Data(word, "Function", token+functioncounter));
		    }
		}
	}
	
	

	void writeToCSV(List<Data> dataList, String ruta) {
	    Set<String> existingWords = new HashSet<>();
	    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta), "utf-8"))) {
	        for (Data data : dataList) {
	            if (existingWords.contains(data.word)) {
	                // Si la palabra ya existe
	                for (Data existingData : dataList) {
	                    if (existingData.word.equals(data.word)) {
	                        data.type = existingData.type;
	                        data.token = existingData.token;
	                        break;
	                    }
	                    
	                }
	            }
	            // Si la palabra no existe, se agrega al conjunto
	            existingWords.add(data.word);
	            // Se escribe la información en el archivo
	            writer.write(data.word + "," + data.type + "," + data.token + "\n");
	        }
	        //System.out.println("Guardado");
	    } catch (IOException e) {
	        //System.out.println("Error al escribir el archivo csv: " + e.getMessage());
	    	
	    	//quite los sysout porque en el ejecutable no se van a ver jaja - josue
	    }
	}
	
	

	public List<Data> getList(){
		return dataList;
	}

	private class Data {
		String word;
		String type;
		String token;
		public Data(String word, String type, String token) {
			this.word = word;
			this.type = type;
			this.token = token;
		}
	}
}