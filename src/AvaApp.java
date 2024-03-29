import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

// Anda 'path' muutujale mingi appi absoluutne path.
// meetod 'käivita' paneb selle appi tööle
public class AvaApp {
    public static void main(String[] args) throws IOException {
        String path = "C:/Program Files (x86)/Steam/steam.exe"; // Tee muudatus vastavalt oma rakenduse asukohale

        // Ava rakendus
        käivita(path);

    }

    public static void käivita(String path) {
        try {
            File fail = new File(path); // 'File' objekt, esindab faili (praegu steam.app).

            // Käitub nagu desktop (nt kui double click et avada app)
            if (Desktop.isDesktopSupported()) { // Käsurealt tulevad 'permission' errorid niiet see ainukesena töötab
                Desktop desktop = Desktop.getDesktop(); // loob desktop objekti
                desktop.open(fail); // avab appi, muutuja file salvestatud andmete abil
            }
        } catch (IOException e) { // püüab vead kinni. Ilma ei tööta miskipärast.
            e.printStackTrace();  // lihtsalt prindib vea vist. Täpsemalt ei uurinud.
        }
    }

    public static void avaSuvaline(List<String[]> programmid){
        int suvaline = (int) (Math.random()*programmid.size());
        käivita(programmid.get(suvaline)[1]);
    }
}