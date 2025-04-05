package ar.edu.unnoba.comp.compilertp;

import ar.edu.unnoba.comp.compilertp.ast.Program;
import java_cup.runtime.Symbol;
import java_cup.sym;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class MainController {

    @FXML
    private Button generateGraphButton;
    @FXML
    private TextArea resultMain;
    @FXML
    private Button parseText;
    @FXML
    private Button parseCupText;
    @FXML
    private Button selectFileButton;
    @FXML
    private Button symbolTableDownload;
    @FXML
    private TextArea inputMain;

    private final Symbol eofSymbol = new Symbol(sym.EOF);
    private String symbolTable;

    @FXML
    private void initialize() {
        File file= new File(Generador.class.getClassLoader().getResource("code_compile.txt").getFile());
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                content.append(line).append("\n");
            }
            inputMain.setText(content.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        selectFileButton.setOnAction(this::handleSelectFile);
        parseText.setOnAction(this::handleParseText);
        parseCupText.setOnAction(this::handleParseCupText);
        symbolTableDownload.setOnAction(this::descargarArchivo);
        generateGraphButton.setOnAction(this::generateGraph);
    }


    private void generateGraph(ActionEvent event) {
        StringReader textToParse = new StringReader(inputMain.getText());
        MiLexico lexico = new MiLexico(textToParse);
        MiParser parser = new MiParser(lexico);

        try {
            Program program = (Program) parser.parse().value;
            String graphic = program.graficar();
            resultMain.setText(graphic);
            PrintWriter grafico = new PrintWriter(new FileWriter("arbol.dot"));
            grafico.println(graphic);
            grafico.close();
            String cmdDot = "ls; dot -Tpng arbol.dot -o arbol.png";
            String outputPath = getClass().getResource("/").toExternalForm();
            outputPath = "src/main/resources/";
            outputPath = "target/classes/";
            String[] command = {
                    "dot", "-Tpng", "arbol.dot", "-o", outputPath + "graph.png"
            };

            Process process = new ProcessBuilder(command).start();
            process.waitFor();
            System.out.println("Imagen generada en " + outputPath + "graph.png");

            String[] command2 = {
                    "dot", "-Tsvg", "arbol.dot", "-o", outputPath + "graph.svg"
            };

            Process process2 = new ProcessBuilder(command2).start();
            process2.waitFor();
            System.out.println("Imagen generada en " + outputPath + "graph.svg");
            sleep(2000);

            showGraphInNewWindow();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    private void showGraphInNewWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/graphView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Graph Viewer");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                inputMain.setText(content.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

   private void handleParseText(ActionEvent event) {
       StringReader textToParse = new StringReader(inputMain.getText());
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

            resultMain.setText(content.toString());
        } catch (Exception e){
            resultMain.setText("Error no esperado: " + e.getMessage());
            System.out.println(e.getMessage() + e);
        }
  }

    private void handleParseCupText(ActionEvent event){
        StringReader textToParse = new StringReader(inputMain.getText());
        MiLexico lexico = new MiLexico(textToParse);
        MiParser parser = new MiParser(lexico);
        try{
            parser.parse();
            String result = parser.action_obj.getParseInfo();
            symbolTable = parser.action_obj.symbolTable.toString();
            resultMain.setText(result+ "\n\n" + symbolTable);
        }catch (Exception e){
            resultMain.setText("Error Inesperado: "+ e.getMessage());
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
