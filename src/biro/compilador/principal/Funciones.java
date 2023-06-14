package biro.compilador.principal;

import java.util.regex.*;

public class Funciones {

  private static final String ERROR_SEMANTICO = "ERRSEM";

  public void funcion(String code) {
    String[] lineas = code.split("\n");

    Pattern pattern = Pattern.compile("(\\*cad|\\*ent|\\*dec)\\s+([a-zA-Z]\\w*)\\s*\\(\\s*(\\*cad|\\*ent|\\*dec)\\s+\\.(\\w*[A-Za-z]\\w*)\\s*(,\\s*(\\*cad|\\*ent|\\*dec)\\s+\\.(\\w*[A-Za-z]\\w*)\\s*)*\\)\\s*\\{");
    
    for (int i = 0; i < lineas.length; i++) {
      if (!lineas[i].equals("")) {
        Matcher matcher = pattern.matcher(lineas[i].trim());
        if (matcher.find()) {
          int numeroLinea = i + 1;
          System.out.println("Se encontro una funcion en " + numeroLinea + ": " + lineas[i]);

          // Interpretar el contenido de la funcion por llaves
          int llaveabierta = 1;
          i++;
          do {
            i--;
            if (lineas[i].contains("{")) {
              llaveabierta--;
            } else if (lineas[i].contains("}")) {
              llaveabierta++;
            }
          } while (llaveabierta > 0 && i > 0);
        } else {
          System.out.println(ERROR_SEMANTICO + (i + 1) + ": Funcion no declarada en la linea: " + lineas[i]);
        }
      }
    }
  }
}
