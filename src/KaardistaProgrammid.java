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
    private static final String kasutajaNimi = System.getProperty("user.name"); // leia Windowsis aktiivse kasutaja nimi
    private static final String kasutajaOtseteed = "C:/Users/"+ kasutajaNimi +"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs"; // aktiivse kasutaja otseteed
    private static final String AllUsersOtseteed = "C:/ProgramData/Microsoft/Windows/Start Menu/Programs";
    private static final String steamiKaust = "C:/Program Files (x86)/Steam/steamapps/common";

    public List<String> getProgrammid() {
        return programmid;
    }

    public KaardistaProgrammid() throws IOException { // konstruktor
        this.programmid = new ArrayList<>();
        this.programmid.addAll(exeNimekiri(kasutajaOtseteed, true));
        this.programmid.addAll(exeNimekiri(AllUsersOtseteed, true));
        this.programmid.addAll(exeNimekiri(steamiKaust, false));
    }

//https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder-and-also-sub-folders
//    inspireeritud ülemisest postitusest, käib rekursiivselt läbi kõik alamkaustad ja lisab leitud failid nimekirja.
    private static List<File> k6ikFailid(String directoryName, int rekursioone){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles(); // vali kõik failid kaustas
        assert fList != null; // IntelliJ tugevalt soovitas kinnitada, et kaust ei ole tühi.
        List<File> resultList = new ArrayList<>(Arrays.asList(fList));
        for (File file : fList) { // kordus iga faili kohta kaustas (siin kontekstis on kaustad ka failid)
            if (file.isDirectory() && rekursioone != 0){ // kui fail on kaust ja ei ole ühe kausta sügavuse piirangut
                resultList.addAll(k6ikFailid(file.getAbsolutePath(),rekursioone-1)); //siis avame kausta uues rekursiooni sammus
            }
        }
        return resultList;
    }
    // genereerib massiivi kõigist exe failide asukohtadest kasutades windows shortcutide target aadressi
    private static ArrayList<String> exeNimekiri(String aadress, boolean rekursiivne) throws IOException {
        List<File> listOfFiles = new ArrayList<>(); // lähesta tühi massiiv
        if(rekursiivne) {
            listOfFiles = k6ikFailid(aadress, -1); // genereeri failide nimekiri minnes failipuus lõpuni
        }
        if(!rekursiivne){
            listOfFiles = k6ikFailid(aadress, 1); // genereeri failide nimekiri minnes ainult ühe kausta võrra sügavamale
        }

        ArrayList<String> programmid = new ArrayList<>();  // loo tühi massiiv exe failide jaoks
        for (File file : listOfFiles) { // käi läbi kõik failid nimekirjas
            String failiAadress = file.getAbsolutePath();
            String laiend = failiAadress.substring(failiAadress.length()-4); // kontrolli faililaiendit
            if(file.isFile() && laiend.equals(".exe")){ // kui faililaiend on .exe
                programmid.add(failiAadress); // siis lisa prorammide nimekirja
            }
            if (!file.isDirectory() && WindowsShortcut.isPotentialValidLink(file)){
                WindowsShortcut shortcut;
                try {
                    shortcut = new WindowsShortcut(file);
                } catch (ParseException e) {
                    continue;
                }
                String sihtfail = shortcut.getRealFilename(); //leia lühitee poolt viidatud absoluutse aadressi
                laiend = shortcut.getRealFilename().substring(sihtfail.length()-4); //leia faililaiend
                if(laiend.equals(".exe")){ // kui tegemist on rakendusega
                    String argumendid = shortcut.getCommandLineArguments(); // leia rakendusele lühitees antavad parameetrid
                    if(Objects.equals(argumendid, null)) //kui lisaparameetreid ei ole
                        programmid.add(sihtfail); // siis lisa rakenduse absoluutne aadress programmide nimekirja
                    else // kui on lisaparameetreid
                        programmid.add(sihtfail + " " + shortcut.getCommandLineArguments());// siis lisa need koos rakenduse aadressiga programmide nimekirja
                }
            }
        }
        return programmid; // tagasta programmide nimekiri
    }

}