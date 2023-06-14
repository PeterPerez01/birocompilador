package biro.compilador.principal;

public class Translador {
	
	public static void main(String[] args) {
		calcular(".var=12+6");
	}
	
	public static double calcular(String operacion) {
	    String[] partes = operacion.split("=");
	    String operacionAritmetica = partes[1].trim();
	    double resultado = 0;

	    if (operacionAritmetica.contains("+")) {
	        String[] numeros = operacionAritmetica.split("\\+");
	        double num1 = Double.parseDouble(numeros[0].trim());
	        double num2 = Double.parseDouble(numeros[1].trim());
	        resultado = num1 + num2;
	    } else if (operacionAritmetica.contains("-")) {
	        String[] numeros = operacionAritmetica.split("-");
	        double num1 = Double.parseDouble(numeros[0].trim());
	        double num2 = Double.parseDouble(numeros[1].trim());
	        resultado = num1 - num2;
	        
	    } /*else if (operacionAritmetica.contains("*")) {
	        String[] numeros = operacionAritmetica.split("\\*");
	        double num1 = Double.parseDouble(numeros[0].trim());
	        double num2 = Double.parseDouble(numeros[1].trim());
	        resultado = num1 * num2;
	    } */
	        else if (operacionAritmetica.contains("/")) {
	        String[] numeros = operacionAritmetica.split("/");
	        double num1 = Double.parseDouble(numeros[0].trim());
	        double num2 = Double.parseDouble(numeros[1].trim());
	        resultado = num1 / num2;
	    }

	    System.out.println("El resultado de la operación " + operacion + " es " + resultado);

	    return resultado;
	}

	
}
