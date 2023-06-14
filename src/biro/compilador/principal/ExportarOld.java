package biro.compilador.principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportarOld {
	public void Imprimir(String ruta, String texto) {
		File archivo = new File(ruta);
		try (FileWriter impresora = new FileWriter(archivo)) {
			impresora.write(texto);
		} catch (IOException exc) {
			
		}
		
	}
}