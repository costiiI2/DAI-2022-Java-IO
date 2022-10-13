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


    public void transform(File inputFile) {
        String outName = inputFile + ".out";
        StringBuilder StringToBeWritten = new StringBuilder();
        //we use stringBuilder because we do lots of concatenations
        UpperCaseCharTransformer upperTransform = new UpperCaseCharTransformer();
        LineNumberingCharTransformer lineTransform = new LineNumberingCharTransformer();
        Reader reader = null;
        try {
            //we verify that the file exists and that we can read from it
            reader = new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8);
            int tmp;
            while ((tmp = reader.read()) != -1) {
                String charToBeTransformed = "";
                charToBeTransformed += (char) tmp;
                charToBeTransformed = upperTransform.transform(charToBeTransformed);
                StringToBeWritten.append(lineTransform.transform(charToBeTransformed));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Error file not found.", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error :", e);
            throw new RuntimeException(e);
        }

        File outFile = new File(outName);
        try (FileOutputStream out = new FileOutputStream(outFile)) {
            //no need of out.close() because it is done automatically in this case
            byte[] bytes = StringToBeWritten.toString().getBytes(StandardCharsets.UTF_8);
            out.write(bytes);
            out.flush();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", ex);
        }
    }
}