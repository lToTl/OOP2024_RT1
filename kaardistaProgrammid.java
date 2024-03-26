////CC BY-SA 4.0
//POOLELI!
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class kaardistaProgrammid {
    public static void main(String[] args) throws IOException{
//https://stackoverflow.com/questions/19990038/how-to-get-windows-username-in-java#:~:text=NTSystem.getName%20%28%29%20returns%20the%20currently%20logged%20username%20at,System.getProperty%20%28%22user.name%22%29%20when%20running%20as%20a%20windows%20service.
        String kastajaNimi = System.getProperty("user.name");
//
        String kasutaja = "C:/Users/"+kastajaNimi+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs";
        String k6ikKasutajad = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs";
        String steam = "C:/Program Files (x86)/Steam/steamapps/common";

        List<String> programmid = new ArrayList<>();
        programmid.addAll(exedeNimekiri(kasutaja, true));
        programmid.addAll(exedeNimekiri(k6ikKasutajad, true));
        programmid.addAll(exedeNimekiri(steam, false));

        for (String exe : programmid) {
            System.out.println(exe);
        }
//
    }
//https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder-and-also-sub-folders
//    inspireeritud ülemisest postitusest, käib rekursiivselt läbi kõik alamkaustad ja lisab leitud failid nimekirja.
    public static List<File> k6ikFailid(String directoryName, int rekursioone){
        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory() && rekursioone != 0){
                resultList.addAll(k6ikFailid(file.getAbsolutePath(),rekursioone-1));
            }
        }
        return resultList;
    }

    private static ArrayList<String> exedeNimekiri(String aadress, boolean rekursiivne) throws IOException {
        List<File> listOfFiles = new ArrayList<>();
        if(rekursiivne) {
            listOfFiles = k6ikFailid(aadress, -1);
        }
        if(!rekursiivne){
            listOfFiles = k6ikFailid(aadress, 1);
        }

        ArrayList<String> programmid = new ArrayList<>();
        for (File file : listOfFiles) {
            String failiAadress = file.getAbsolutePath();
            String laiend = failiAadress.substring(failiAadress.length()-4);
            if(file.isFile() && laiend.equals(".exe")){
                programmid.add(failiAadress);
            }
            if (!file.isDirectory() && WindowsShortcut.isPotentialValidLink(file)){
                WindowsShortcut shortcut;
                try {
                    shortcut = new WindowsShortcut(file);
                } catch (ParseException e) {
                    continue;
                }
                String sihtfail = shortcut.getRealFilename();
                laiend = shortcut.getRealFilename().substring(sihtfail.length()-4);
                if(laiend.equals(".exe")){
                    String argumendid = shortcut.getCommandLineArguments();
                    if(Objects.equals(argumendid, null))
                        programmid.add(sihtfail);
                    else
                        programmid.add(sihtfail + " " + shortcut.getCommandLineArguments());
                }
            }
        }
        return programmid;
    }
}

