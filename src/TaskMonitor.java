//WORK IN PROGRESS
//https://stackoverflow.com/questions/6859681/better-way-to-signal-other-thread-to-stop
import java.io.IOException;
import java.util.List;

public class TaskMonitor implements Runnable{
    private int skoor;
    public static void start() throws IOException, InterruptedException {
        //kontrolli log.txt olemasolu ja vajadusel loo see
        //lae mällu rakendused.txt
        //loo aktiivsete protsesside nimekiri
//        List<String> protsessid = CMD.tasklist();
        List<String> protsessid = VBSUtils.listRunningProcesses();
        int skooriDelta = 0;
        for (String protsess : protsessid) {
            System.out.println(protsess);

        }
        //skooriDelta = 0
        //muuda skoori--
        //lisa viimane skoor logifaili
    }
    public static void stop(){

    }


    @Override
    public void run() {
        //kontrolli log.txt olemasolu ja vajadusel loo see
        //lae mällu rakendused.txt
        //loo aktiivsete protsesside nimekiri
        while (!Thread.currentThread().isInterrupted()) {
//        List<String> protsessid = CMD.tasklist();
            List<String> protsessid = VBSUtils.listRunningProcesses();
            int skooriDelta = 0;
            for (String protsess : protsessid) {
                System.out.println(protsess);

            }
            //skooriDelta = 0
            //muuda skoori--
            //lisa viimane skoor logifaili

        }
    }
}
