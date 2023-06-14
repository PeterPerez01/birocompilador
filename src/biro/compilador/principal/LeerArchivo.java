package biro.compilador.principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class LeerArchivo {


	public String Leer(String txt) {
		int numid=0, numtd=0,numsep=0, numop=0;
		// TODO Auto-generated method stub
		String s1 = null;
        String s2, s3, impresora="Lexema,Tipo\r\n";
        // Cargamos el buffer con el contenido del archivo
        BufferedReader br = null;
		try {
			br = new BufferedReader (new FileReader (txt));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
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
            if(s2==",") {
            	s2="\r\n";
            }
            if(s2!=" ") {
            impresora+=s2+",";
            }
            char[] carac;
            carac=s2.toCharArray();
            if(carac[0]=='.') {
            	numid++;
            	s3="ID"+numid;
            	impresora+=s3+",";
            }
            
            if(s2.equals("*ent") || s2.equals("*cad") || s2.equals("*dec") || s2.equals("*ent[]") || s2.equals("*dec[]") || s2.equals("*cad[]")) {
            	numtd++;
            	s3="TD"+numtd;
            	impresora+=s3+",\r\n";
            } else {
            	
            
            
            if(s2.equals(";") || s2.equals("{") || s2.equals("}") || s2.equals("(") || s2.equals(")") || s2.equals("\"")) {
            	numsep++;
            	s3="SEP_"+numsep;
            	impresora+=s3+",\r\n";
            } else {
            
            if(s2.equals("+") || s2.equals("-") || s2.equals("/") || s2.equals("*") || s2.equals("=") || s2.equals("%")) {
            	numop++;
            	s3="OP"+numop;
            	impresora+=s3+",\r\n";
            } else {
            	s3="";
            	impresora+=s3+",\r\n";
            			}	
            
            
            		}
	            			}
	            
            //System.out.println ("    Palabra: " + s2);
        }
       
			
		}
	     return(impresora);
	    
	}

}
