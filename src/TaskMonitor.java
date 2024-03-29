//https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html#runnable-interface-implementation
//https://stackoverflow.com/questions/541487/implements-runnable-vs-extends-thread-in-java
//https://stackoverflow.com/questions/10484390/java-main-code-stops-when-the-thread-is-started
//https://stackoverflow.com/questions/5562720/how-to-access-the-running-thread-runnable
//https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
//https://stackoverflow.com/questions/23068676/how-to-get-current-timestamp-in-string-format-in-java-yyyy-mm-dd-hh-mm-ss
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskMonitor implements Runnable{
    private int skoor;
    private volatile boolean running = false;
    private List<String[]> logi;
    private Map<String, String[]> programmidMap;


    public boolean isRunning() {
        return running;
    }

    public TaskMonitor() throws IOException {
        this.skoor = 0;
        programmidMap = new HashMap<>();
        File f = new File("log.txt"); //kontrolli log.txt olemasolu ja vajadusel loo see
        if (!f.isFile()) { // kui log.txt on puudu, siis tee loo see
            f.createNewFile();
        }else {
            String tänaneKuupäev = new SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date());
            logi = Abi.loeFailListi("log.txt");
            int viimane = logi.size()-1;
            if (logi.get(viimane)[0].equals(tänaneKuupäev))
                skoor = Integer.parseInt(logi.get(viimane)[2]);

        }
        List<String[]> programmid = Abi.loeFailListi("rakendused.txt");
        for (String[] exe : programmid) {
            programmidMap.put(exe[0], new String[]{exe[1], exe[2]});
        }
        //lae rakendused.txt dictionarisse
    }

    public int getSkoor() {
        return skoor;
    }

    @Override
    public void run() {
        //loo aktiivsete protsesside nimekiri
        running = true;
        while (running) {
            List<String> protsessid = VBSUtils.listRunningProcesses();
            int skooriDelta = 0;

            for (String protsess : protsessid) {
                String[] andmed = programmidMap.get(protsess);
                if (andmed != null){
//                    System.out.println(Arrays.toString(andmed));
                    if (andmed[1].equals("0")){
                        skooriDelta = -1;
                        break;
                    }
                    if (andmed[1].equals("1")){
                        skooriDelta = 1;
                    }
                }
            }
            skoor += skooriDelta;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //lisa viimane skoor logifaili
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd;HH:mm:ss").format(new java.util.Date());
        try {
            FileWriter fileWriter = new FileWriter("log.txt",true);
            fileWriter.write(timeStamp + ";" + skoor + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void stop(){running = false;}


}
