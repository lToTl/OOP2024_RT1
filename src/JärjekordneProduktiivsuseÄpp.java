//WORK IN PROGRESS
import java.io.File;
import java.io.IOException;
import java.util.List;

//põhiklass
public class JärjekordneProduktiivsuseÄpp {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean TaskMontorOn = false;
        TaskMonitor taskmonitor = new TaskMonitor();
        File f = new File("rakendused.txt");
        if (!f.isFile()) // kui rakendused.txt on puudu, siis
            Setup.LooRakendusedTXT(); // tee setup
// kasutajaliides
// 1. alusta jälgimist /lõpeta jälgimine - taskMonitor.run() / taskmonitor.interrupt()
// 2. Im feeling lucky - ava suvaline programm nimekirjast rakendused.txt avaSuvaline()
//        List<String[]> programmid = LoeFailListi.loe("rakendused.txt");
//        avaApp.avaSuvaline(programmid);
// 3. tee setup uuesti - Setup()
//        Setup.LooRakendusedTXT();
// 4. lõpeta töö
//        taskmonitor.interrupt() + halt

        taskmonitor.run();



    }

}
