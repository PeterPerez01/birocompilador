package biro.compilador.principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class RunCode {

	public static void main(String[] args) {
		
		
	}
	
	public void Ejecutar(String txt) {
		String tipodedato="";
		// TODO Auto-generated method stub
		String s1 = null;
        String s2, impresora="";
        // Cargamos el buffer con el contenido del archivo
        BufferedReader br = null;
		try {
			br = new BufferedReader (new FileReader (txt));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
 
		while(br!=null) {
        // Leemos
        try {
			s1 = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        if(s1==null) {break; }
        
        StringTokenizer st = new StringTokenizer (s1);
        while (st.hasMoreTokens())
        {
        	//s2 contiene la palabra
            s2 = st.nextToken();
            if(s2.equals(";")) {
            	s2=";\r\n";
            }
            if(s2!=" ") {
            //impresora+=s2+" ";
            } else {impresora+=" "; }
            char[] carac;
            carac=s2.toCharArray();
            if(carac[0]=='.') {
            	s2=s2.replace(carac[0], ' ');
            	tipodedato=s2; impresora+=tipodedato;
            	s2="";
            }
            if(s2.equals(" ")) {impresora+=" ";}
            if(s2.equals("*ent") || s2.equals("*cad") || s2.equals("*dec") || s2.equals("*ent[]") || s2.equals("*dec[]") || s2.equals("*cad[]")) {
            	if(s2.equals("*ent")){s2="";tipodedato="int "; impresora+=tipodedato;}
            	if(s2.equals("*cad")){s2="";tipodedato="String "; impresora+=tipodedato;}
            	if(s2.equals("*dec")){s2="";tipodedato="double "; impresora+=tipodedato;}
            	if(s2.equals("*ent[]")){s2="";tipodedato="int[] "; impresora+=tipodedato;}
            	if(s2.equals("*dec[]")){s2="";tipodedato="double[] "; impresora+=tipodedato;}
            	if(s2.equals("*cad[]")){s2="";tipodedato="String[] "; impresora+=tipodedato;}
            } else {
            	
            if(s2.equals("print")) {
            	tipodedato="System.out.println";
            	impresora+=tipodedato;
            	s2="";
            }
            
            
            if(s2.equals(";") || s2.equals("{") || s2.equals("}") || s2.equals("(") || s2.equals(")") || s2.equals("\"")) {
            	tipodedato=s2+"\n"; impresora+=tipodedato;
            	
            } else {
            
            if(s2.equals("+") || s2.equals("-") || s2.equals("/") || s2.equals("*") || s2.equals("=") || s2.equals("%")) {
            	tipodedato=s2; impresora+=tipodedato;
            } else {
            	if(tipodedato=="print") {}
            	tipodedato=s2; impresora+=tipodedato;
            			}		
            
            		}
	            			}
	            
            System.out.println (impresora);
        }
       
			
		}
	     //return(impresora);
	}

}
