//POOLELI!
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kaardistaProgrammid {
    public static void main(String[] args) throws IOException{
//https://stackoverflow.com/questions/19990038/how-to-get-windows-username-in-java#:~:text=NTSystem.getName%20%28%29%20returns%20the%20currently%20logged%20username%20at,System.getProperty%20%28%22user.name%22%29%20when%20running%20as%20a%20windows%20service.
        String kastajaNimi = System.getProperty("user.name");
//
        String aadress1 = "C:/Users/"+kastajaNimi+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs";
        String aadress2 = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs";
        List<String> programmid = new ArrayList<>(exedeNimekiri(aadress1));
        programmid.addAll(exedeNimekiri(aadress2));
        for (String exe : programmid) {
            System.out.println(exe);
        }
//
    }
//https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder-and-also-sub-folders
//    inspireeritud ülemisest postitusest, käib rekursiivselt läbi kõik alamkaustad ja lisab leitud failid nimekirja.
    public static List<File> k6ikFailid(String directoryName){
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
        return resultList;
    }

    private static ArrayList<String> exedeNimekiri(String aadress) throws IOException {
        List<File> listOfFiles = k6ikFailid(aadress);
        ArrayList<String> programmid = new ArrayList<>();
        for (File file : listOfFiles) {
            if (!file.isDirectory() && WindowsShortcut.isPotentialValidLink(file)){
                WindowsShortcut shortcut;
                try {
                    shortcut = new WindowsShortcut(file);
                } catch (ParseException e) {
                    continue;
                }
                String sihtfail = shortcut.getRealFilename();
                String laiend = shortcut.getRealFilename().substring(sihtfail.length()-4);
                if(laiend.equals(".exe"))
                    programmid.add(sihtfail);
            }
        }
        return programmid;
    }
}

