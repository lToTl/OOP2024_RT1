// Kasutatud on paar rida koodi StackOverflow-st ja seega kaasneb litsents CC BY-SA 4.0
// Tulevikus võiks leida steami installatsiooni aadressi windowsi registrist juhuks kui see on erinev default-ist
// Tulevikus saaks leida ka kõik käsitsi loodu steami library-id vaadates faili "*/Steam/config/libraryfolders.vdf"

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class KaardistaProgrammid {
    private List<String> programmid;
//https://stackoverflow.com/questions/19990038/how-to-get-windows-username-in-java#:~:text=NTSystem.getName%20%28%29%20returns%20the%20currently%20logged%20username%20at,System.getProperty%20%28%22user.name%22%29%20when%20running%20as%20a%20windows%20service.
    private static final String kasutajaNimi = System.getProperty("user.name");
    private static final String kasutajaOtseteed = "C:/Users/"+ kasutajaNimi +"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs";
    private static final String AllUsersOtseteed = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs";
    private static final String steamiKaust = "C:/Program Files (x86)/Steam/steamapps/common";

    public List<String> getProgrammid() {
        return programmid;
    }

    public KaardistaProgrammid() throws IOException {
        this.programmid = new ArrayList<>();
        this.programmid.addAll(exeNimekiri(kasutajaOtseteed, true));
        this.programmid.addAll(exeNimekiri(AllUsersOtseteed, true));
        this.programmid.addAll(exeNimekiri(steamiKaust, false));
    }

//https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder-and-also-sub-folders
//    inspireeritud ülemisest postitusest, käib rekursiivselt läbi kõik alamkaustad ja lisab leitud failid nimekirja.
    private static List<File> k6ikFailid(String directoryName, int rekursioone){
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

    private static ArrayList<String> exeNimekiri(String aadress, boolean rekursiivne) throws IOException {
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
class testProgrammid {
    public static void main(String[] args) throws IOException {
        List<String> programmid = new KaardistaProgrammid().getProgrammid();
        for (String exe : programmid) {
            System.out.println(exe);
        }
    }
}
