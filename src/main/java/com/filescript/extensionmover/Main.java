package com.filescript.extensionmover;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        File currentDir = new File( System.getProperty("user.dir"));
        ArrayList<File> allFiles =  new ArrayList<>(Arrays.asList(Objects.requireNonNull(currentDir.listFiles())));
        allFiles.removeIf(new Predicate<File>() {
            @Override
            public boolean test(File file) {
                return file.isDirectory();
            }
        });
        allFiles.forEach(new Consumer<File>() {
            @Override
            public void accept(File file) {
                String extension = FilenameUtils.getExtension(file.getPath());
                System.out.println(extension);
                File extensionFolder =  new File(currentDir+"\\"+extension);
                File noExtensionFolder =  new File(currentDir+"\\noExt");
                if(!extension.equals("")){
                    if(!extensionFolder.exists()){
                        try {
                            FileUtils.forceMkdir(extensionFolder);
                            FileUtils.moveFileToDirectory(file,extensionFolder,false);
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }else{
                        try {
                            FileUtils.moveFileToDirectory(file,extensionFolder,false);
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }else{try {


                    if(!noExtensionFolder.exists()){
                        FileUtils.forceMkdir(noExtensionFolder);
                        FileUtils.moveFileToDirectory(file,noExtensionFolder,false);
                    }else {
                        FileUtils.moveFileToDirectory(file,noExtensionFolder,false);
                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                }
                }
            }
        });
    }
}