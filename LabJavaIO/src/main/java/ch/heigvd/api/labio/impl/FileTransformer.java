package ch.heigvd.api.labio.impl;

import ch.heigvd.api.labio.impl.transformers.LineNumberingCharTransformer;
import ch.heigvd.api.labio.impl.transformers.NoOpCharTransformer;
import ch.heigvd.api.labio.impl.transformers.UpperCaseCharTransformer;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class transforms files. The transform method receives an inputFile.
 * It writes a copy of the input file to an output file, but applies a
 * character transformer before writing each character.
 *
 * @author Juergen Ehrensberger
 */
public class FileTransformer {
    private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());

    @SneakyThrows
    public void transform(File inputFile) {
        String outName = inputFile + ".out";
        StringBuilder StringToBeWritten = new StringBuilder();
        //NoOpCharTransformer t = new NoOpCharTransformer();
        UpperCaseCharTransformer upT = new UpperCaseCharTransformer();
        LineNumberingCharTransformer liT = new LineNumberingCharTransformer();
        Reader reader = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8);
        int tmp;
        while ((tmp = reader.read()) != -1) {
            String charToBeTransformed = "";
            charToBeTransformed += (char) tmp;
            charToBeTransformed = upT.transform(charToBeTransformed);
            StringToBeWritten.append(liT.transform(charToBeTransformed));
        }
        reader.close();
        try {
            File outFile = new File(outName);
            FileOutputStream out = new FileOutputStream(outFile);
            byte[] bytes = StringToBeWritten.toString().getBytes(StandardCharsets.UTF_8);
            out.write(bytes);
            out.close();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", ex);
        }
    }
}