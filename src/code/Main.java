package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
    public static final File CODE_DIR = new File("src\\code");
    public static final File PARENT_DIR = CODE_DIR.getParentFile();
    public static final File INPUT_FILE = new File(PARENT_DIR, "input\\input.txt");
    public static final File OUTPUT_FILE = new File(PARENT_DIR, "output\\output.txt");

    public static void main(String[] args) {
        StringBuilder readSb = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(INPUT_FILE.getAbsolutePath()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> readSb.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String srcCode = readSb.toString();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenList = lexicalAnalyzer.process(srcCode);
        int[] lineIndexes = lexicalAnalyzer.getLineIndexes();
        String[] sourceLines = lexicalAnalyzer.getSourceLines();
        LL1Parser ll1Parser = new LL1Parser(tokenList, sourceLines, lineIndexes);
        ll1Parser.parse();
        ll1Parser.printOutputLines();
        ArrayList<String> outputLines = ll1Parser.getOutputLines();
        readSb.setLength(0);

        StringBuilder outputSb = new StringBuilder();

        for (String s: outputLines) {
            outputSb.append(s);
            outputSb.append('\n');
        }

        Path outputPath = OUTPUT_FILE.toPath();
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write(outputSb.toString());
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
