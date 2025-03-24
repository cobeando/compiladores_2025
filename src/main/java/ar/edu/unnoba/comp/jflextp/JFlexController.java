package ar.edu.unnoba.comp.jflextp;

import ar.edu.unnoba.comp.jflextp.exceptions.CloseCommentException;
import ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException;
import ar.edu.unnoba.comp.jflextp.exceptions.LexerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class JFlexController {

    @FXML
    private TextArea resultJFlex;
    @FXML
    private Button parseText;
    @FXML
    private Button selectFileButton;
    @FXML
    private TextArea inputJFlex;

    @FXML
    private void initialize() {
        // Agregar un controlador de eventos al botón
        selectFileButton.setOnAction(this::handleSelectFile);
        parseText.setOnAction(this::handleParseText);
    }

    private void handleSelectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        ArrayList<String> extensionsAllowed = new ArrayList<String>(Arrays.asList("*.txt","*.java","*.py","*.xml","*.doc"));
        fileChooser.setTitle("Seleccionar archivo");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt,*.java,*.py,*.xml,*.doc)",extensionsAllowed);
        FileChooser.ExtensionFilter javaFilter = new FileChooser.ExtensionFilter("Todos los archivos", "*");
        fileChooser.getExtensionFilters().addAll(txtFilter, javaFilter);


        // Mostrar el diálogo de selección de archivos y obtener el archivo seleccionado
        File selectedFile = fileChooser.showOpenDialog(selectFileButton.getScene().getWindow());

        // Aquí puedes hacer algo con el archivo seleccionado, por ejemplo, imprimir su ruta
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
                    MiToken token = lexico.yylex();
                    if (token == null)
                        break;
                    content.append(token).append("\n");
                } catch (EOFLexerException e){
                    content.append(e.getMessage()).append("\n");
                    break;
                } catch (LexerException e){
                    content.append(e.getMessage()).append("\n");
                }
            }
            resultJFlex.setText(content.toString());
        } catch (Exception e){
            resultJFlex.setText("Error no esperado: " + e.getMessage());
            System.out.println(e.getMessage() + e);
        }
    }
}
