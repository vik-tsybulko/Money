package mainPack;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by viktor on 06.02.17.
 */
public class OpenStartPage {
    private String dir = "src/main/resources/";
    private String ext = ".sqlite";
    File[] listFile;

    OpenStartPage(){

    }


    public boolean findFiles(){
        boolean result = false;
        File file = new File(dir);
        if (!file.exists()) {
            System.out.println(dir + "папка не существует");
        }
        listFile = file.listFiles(new MyFileNameFilter(ext));
        if (listFile.length == 0){
            System.out.println(dir + " не содержит файлов с расширением " + ext);
        }
        else {
            for (File f : listFile){
                System.out.println("Файл: " + dir + f.getName());
            }
            result = true;
        }
        return result;
    }
    public class MyFileNameFilter implements FilenameFilter{
        private String ext;
        public MyFileNameFilter(String ext){
            this.ext = ext.toLowerCase();
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }

}
