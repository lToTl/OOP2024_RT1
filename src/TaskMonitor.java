//WORK IN PROGRESS
//https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html#runnable-interface-implementation
//https://stackoverflow.com/questions/541487/implements-runnable-vs-extends-thread-in-java
//https://stackoverflow.com/questions/10484390/java-main-code-stops-when-the-thread-is-started
//https://stackoverflow.com/questions/5562720/how-to-access-the-running-thread-runnable
//https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
import java.util.List;

public class TaskMonitor implements Runnable{
    private int skoor;
    private volatile boolean running = false;

    public boolean isRunning() {
        return running;
    }

    public TaskMonitor() {
        this.skoor = 0;
        //kontrolli log.txt olemasolu ja vajadusel loo see
        //lae mällu rakendused.txt
    }
    public int getSkoor() {
        return skoor;
    }

    @Override
    public void run() {
        //loo aktiivsete protsesside nimekiri
        running = true;
        while (running) {
//        List<String> protsessid = CMD.tasklist();
            List<String> protsessid = VBSUtils.listRunningProcesses();
            int skooriDelta = 0;
            skoor++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
//            for (String protsess : protsessid) {
//                System.out.println(protsess);

//            }
            //skooriDelta = 0
            //muuda skoori--
            //lisa viimane skoor logifaili

        }
    }
    public void stop(){running = false;}


}
