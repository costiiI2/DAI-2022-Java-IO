package ch.heigvd.api.labio.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

/**
 * The FileExplorer performs an exploration of the file system. It
 * visits the files and directory in alphabetic order.
 * When the explorer sees a directory, it recursively explores the directory.
 * When the explorer sees a file, it invokes the transformation on it.
 *
 * @author Olivier Liechti, Juergen Ehrensberger
 */
public class FileExplorer {

    public void explore(File rootDirectory) {// root = /workspace/quotes
        FileTransformer transformer = new FileTransformer();
        if(rootDirectory.isFile()){
            transformer.transform(rootDirectory);
        }else {
            File quotes[] =rootDirectory.listFiles();
            if(quotes != null) {
                Arrays.sort(quotes);
                for (File quote : quotes) {
                    explore(quote);
                }
            }
        }
    }
}