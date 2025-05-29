# Compiladores Trabajo Practico

Grupo 5:Facundo Benavidez, Agus Campagna, Alejo Fidalgo, Tiago Roldan

## Descripción

El objetivo de este proyecto es proporcionar un ejemplo básico de cómo utilizar JFlex para generar un analizador léxico en Java. El analizador léxico está diseñado para tokenizar código fuente escrito en un lenguaje específico, identificando diferentes tipos de tokens como palabras clave, identificadores, literales numéricos, operadores, etc.

## Dependencias

- Java 8
- Maven

## Instalación

1. Clona este repositorio en tu máquina local utilizando Git:

```bash
git clone <URL-del-repositorio>
```

2. Instala las depencencias de maven, esta instalacion va a generar un jar ejecutable con la version actual del proyecto:

```bash
mvn install 
```
3. El ejecutable esta en target. Ejecuta la interfaz grafica en tu IDE o a traves de la consola:

```bash
mvn javafx:run
```

## Desarrollo

- Para actualizar la clase `MiLexico` luego de modificar `lexico.flex`  ejecuta la clase `Generador` y reemplaza el archivo con el nuevo generado en Target.
- Para agregar dependencias definirlas en `pom.xml` y recompilar el proyecto.
- Para obtener un nuevo archivo jar ejecutar los siguientes comandos:

```bash
mvn clean
mvn install 
```

