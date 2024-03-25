//POOLELI!
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kaardistaProgrammid {
    public static void main(String[] args) {
//https://stackoverflow.com/questions/19990038/how-to-get-windows-username-in-java#:~:text=NTSystem.getName%20%28%29%20returns%20the%20currently%20logged%20username%20at,System.getProperty%20%28%22user.name%22%29%20when%20running%20as%20a%20windows%20service.
        String kastajaNimi = System.getProperty("user.name");
//
        String aadress = "C:/Users/"+kastajaNimi+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs";

        List<File> listOfFiles = k6ikFailid(aadress);
        for (Object rida : listOfFiles) {
            int reaPikkus = rida.toString().length();
            if (rida.toString().substring(reaPikkus - 4).equals(".lnk"))
                System.out.println(rida);
        }
        System.out.println(listOfFiles.size());





    }
//https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder-and-also-sub-folders
//    modifitseeritud
    public static List<File> k6ikFailid(String directoryName) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory()) {
                resultList.addAll(k6ikFailid(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        return resultList;
    }
}

