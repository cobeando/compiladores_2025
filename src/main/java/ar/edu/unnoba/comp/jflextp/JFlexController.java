package ar.edu.unnoba.comp.jflextp;

import ar.edu.unnoba.comp.jflextp.exceptions.CloseCommentException;
import ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException;
import ar.edu.unnoba.comp.jflextp.exceptions.LexerException;
import java_cup.runtime.Symbol;
import java_cup.sym;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JFlexController {

    @FXML
    private TextArea resultJFlex;
    @FXML
    private Button parseText;
    @FXML
    private Button parseCupText;
    @FXML
    private Button selectFileButton;
    @FXML
    private Button symbolTableDownload;
    @FXML
    private TextArea inputJFlex;

    private final Symbol eofSymbol = new Symbol(sym.EOF);
    private String symbolTable;

    @FXML
    private void initialize() {
        // Agregar un controlador de eventos al botón
        selectFileButton.setOnAction(this::handleSelectFile);   
        parseText.setOnAction(this::handleParseText);
        parseCupText.setOnAction(this::handleParseCupText);
        symbolTableDownload.setOnAction(this::descargarArchivo);
    }

    private void handleSelectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        ArrayList<String> extensionsAllowed = new ArrayList<String>(Arrays.asList("*.txt","*.java","*.py","*.xml","*.doc"));
        fileChooser.setTitle("Seleccionar archivo");
        FileChooser.ExtensionFilter customFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt,*.java,*.py,*.xml,*.doc)",extensionsAllowed);
        FileChooser.ExtensionFilter noFilter = new FileChooser.ExtensionFilter("Todos los archivos", "*");
        fileChooser.getExtensionFilters().addAll(customFilter, noFilter);


        // Mostrar el diálogo de selección de archivos y obtener el archivo seleccionado
        File selectedFile = fileChooser.showOpenDialog(selectFileButton.getScene().getWindow());

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                inputJFlex.setText(content.toString());
                //TODO ParsearTexto de paso
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

   private void handleParseText(ActionEvent event) {
       StringReader textToParse = new StringReader(inputJFlex.getText());
        MiLexico lexico = new MiLexico(textToParse);

        try {
            StringBuilder content = new StringBuilder();
            while (true) {
                try {
                    Symbol token = lexico.next_token();
                    if (token == null || token.sym == 0) {
                        break;
                    }
                    content.append("Token: " + token).append("\n");
                } catch (Exception e) {
                    content.append(e.getMessage()).append("\n");
                    break;
                }
            }
            content.append("Tabla de Simbolos").append("\n");
            lexico.tablaDeSimbolos.stream().map((simbolo) -> {
                System.out.println(simbolo.nombre);
                return simbolo;
            }).forEachOrdered((simbolo) -> {
                content.append(simbolo.nombre).append(" : ");
                content.append(simbolo.valor).append("\n");
            });
            content.append("Análisis léxico terminado.");

            resultJFlex.setText(content.toString());
        } catch (Exception e){
            resultJFlex.setText("Error no esperado: " + e.getMessage());
            System.out.println(e.getMessage() + e);
        }
  }

    private void handleParseCupText(ActionEvent event){
        StringReader textToParse = new StringReader(inputJFlex.getText());
        MiLexico lexico = new MiLexico(textToParse);
        MiParser parser = new MiParser(lexico);
        try{
            parser.parse();
            String result = parser.action_obj.getParseInfo();
            symbolTable = parser.action_obj.symbolTable.toString();
            resultJFlex.setText(result+ "\n\n" + symbolTable);
        }catch (Exception e){
            resultJFlex.setText("Error Inesperado: "+ e.getMessage());
        }
    }

    private void descargarArchivo(ActionEvent event) {
        // Ruta donde se guardará el archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo");

        // Establecer la extensión por defecto y el nombre del archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("symbolTable.txt");

        // Mostrar el diálogo y obtener la ruta seleccionada por el usuario
        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            // Escribir en el archivo seleccionado
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSeleccionado))) {
                writer.write(symbolTable);
                System.out.println("Archivo descargado correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Archivo descargado correctamente.");
    }
}
