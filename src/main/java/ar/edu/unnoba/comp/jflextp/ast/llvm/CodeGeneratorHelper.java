package ar.edu.unnoba.comp.jflextp.ast.llvm;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.utils.SymbolTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Juan Pablo
 */
public class CodeGeneratorHelper {

    private static final String DUPLE_REGEX= "(?:\\d+\\.\\d+|\\d+)\\s*,\\s*(?:\\d+\\.\\d+|\\d+)";

    private static int nextID = 0;

    private static SymbolTable symbolTable;

    private static final Pattern pattern = Pattern.compile(DUPLE_REGEX);


    private CodeGeneratorHelper(){}

    public static void setSymbolTable(SymbolTable newsymbolTable){
        symbolTable = newsymbolTable;
    }

    public static String getPointerForId(String id){
        return symbolTable.getPointer(id);
    }

    public static String getSecondPointerForId(String id){
        return symbolTable.getSecondPointer(id);
    }

    public static DataType getSymbolForId(String id){
        return symbolTable.getType(id);
    }

    public static String getNewGlobalPointerForId(String id){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        String pointer=String.format("@gb.%s", nextID);
        ret.append(pointer);
        symbolTable.addPointer(id, pointer);
        return ret.toString();
    }
    public static String getNewPointer(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("%%ptro.%s", nextID));
        return ret.toString();
    }
    
    public static String getNewGlobalPointer(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("@gb.%s", nextID));
        return ret.toString();
    }
    
    public static String getNewTag(){
        StringBuilder ret = new StringBuilder();
        nextID+=1;
        ret.append(String.format("tag.%s", nextID));
        return ret.toString();
    }

    public static String getNewTemp() {
    return String.format("%%tmp.%d", ++nextID);
}

    public static String getNewLabel(String base) {
        return String.format("%s.%d", base, ++nextID);
    }

    
    public static String generateStringConstants() {
        return symbolTable.generateStringConstants();
    }

    public static String generateDuplePointers() {
        return symbolTable.generateDuplePointers();
    }

    public static DataType getTypeForId(String id) {
        return symbolTable.getType(id);
    }


    public static String convertToFloat(Expression expression){
        String pointerRef = expression.getIr_ref();
        expression.setIr_ref(getNewPointer());
        return String.format("%s = sitofp i32 %s to float \n", expression.getIr_ref(), pointerRef);
    }

    public static String convertToDuple(Expression expression) {
        if (expression.getType() == DataType.FLOAT) {
            return "";
        }
        return convertToFloat(expression);
    }

    public static String toLLVMFloatFormat(String value) {
        try {
            double doubleValue = Double.parseDouble(value);
            long doubleBits = Double.doubleToLongBits(doubleValue);
            String hexString = Long.toHexString(doubleBits);
            //String overflow = hexString.substring(8,9);
            return "0x" + hexString.substring(0,8) +"00000000";
        } catch (NumberFormatException e) {
            // Log the error and return a default value
            System.err.println("Invalid float value: " + value);
            return "0.000000e+00";
        }
    }


    public static void setVariableAsInitialized(String etiqueta) {
        symbolTable.setVariableAsInitialized(etiqueta);
    }

    public static boolean isVariableInitialized(String id) {
         return symbolTable.isVariableInitialized(id);
    }

    public static String getLLVMValueDuple(String dupleValue, int index){
        final Matcher matcher = pattern.matcher(dupleValue);
        if (matcher.find()) {
            return toLLVMFloatFormat(matcher.group().split(",")[index].trim());
        }
        throw new RuntimeException("Unexpected Error in CodeGeneratorHelper.getFirstValueDuple");
    }
}