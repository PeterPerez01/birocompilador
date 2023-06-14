# BiroCompilador
Compilador de Lenguaje Ficticio Biro hecho en Java.

## GUI
![BiroCompilador GUI](https://i.postimg.cc/Z5F4MWBr/birocompilador.png)
La interfaz cuenta con un contador de número de linea, espacio para escritura de texto, título del archivo, botones, barra de herramientas, consola para feedback y un botón para limpiar la consola.

## Uso
Los tipos de datos son:
- *ent para Enteros
- *dec para Decimales
- *cad para Cadenas

Restricciones:
- Los números deben empezar y terminar con 1, es decir, para el número 44 se debe escribir 1441
- Los identificadores de variables deben comenzar con un punto '.' y tener al menos un caractér
- Las funciones deben comenzar con la palabra reservada 'function', seguido del tipo de dato de la función, el nombre de la función con punto al inicio, entre parentesis los argumentos de la función con su tipo de dato respectivo, llave de apertura, cuerpo de función debajo de la declaración de la función, un retorno de tipo correspondiente al tipo de la función y por último cierre de función
- Todas las sentencias deben terminar con punto y coma ';'
- Las cadenas deben ir entre comillas '"'
- Para llamar a una función debes asignarla a una variable para almacenar su valor de retorno

![image](https://github.com/PeterPerez01/birocompilador/assets/56180954/3c5c7f02-d6b9-4c82-ba00-e7556a222d54)

## Características
- Cuenta con modo oscuro y modo brillante
- Exportar e importar archivos desde la barra de herramientas 'Archivo'
- Formato de exportación propio '.biro'
- Genera tabla de tokens (símbolos)
- Genera tabla de triplo
- Genera tabla de errores
- Genera conversión de código a lenguaje Ensamblador (Assembly)
- Permite el uso de funciones y retornos
- Colorización de las palabras reservadas
- Número de linea
- Retroalimentación
- Es multiplataforma, disponible para uso en Windows, MacOS, Linux, Solaris, Unix [...]

## Requisitos previos

Antes de instalar y ejecutar este proyecto, asegúrate de tener instaladas las siguientes herramientas:

- Java [La última versión disponible] <pre><code> https://www.java.com/es/download/ </code></pre>
- Descarga la versión lista para correr del compilador (clic derecho > guardar enlace como...) <pre><code> https://github.com/PeterPerez01/birocompilador/blob/main/Compilador.jar </code></pre>


## Apoyar
BuyMeACoffee
<pre><code> https://www.buymeacoffee.com/birobytes </code></pre>

LinkedIn
<pre><code> https://www.linkedin.com/in/peter-perez01/ </code></pre>

Web:
<pre><code> https://birosoft.000webhostapp.com/ </code></pre>


## Contribución

Si deseas contribuir a este proyecto, sigue estos pasos:

1. Haz un fork de este repositorio.
2. Crea una rama para tu nueva característica o corrección de errores: `git checkout -b nueva-caracteristica`.
3. Realiza los cambios necesarios y commitea tus modificaciones: `git commit -am 'Agrega nueva característica'`.
4. Envía tus cambios a tu repositorio remoto: `git push origin nueva-caracteristica`.
5. Abre una pull request en este repositorio.

¡Agradecemos todas las contribuciones!

## Licencia

Este proyecto se encuentra bajo la [Licencia MIT](LICENSE).



