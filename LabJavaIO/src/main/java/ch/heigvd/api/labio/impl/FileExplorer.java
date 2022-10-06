package ch.heigvd.api.labio.impl;

import java.io.File;
import java.io.FilenameFilter;

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
                for (File quote : quotes) {
                    explore(quote);
                }
        }
        /* TODO: implement the logic to explore the rootDirectory.
         *  Use the Java JDK documentation to see:
         *  - to sort the items (files and directories) alphabetically
         */
    }
}