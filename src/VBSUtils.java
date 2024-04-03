//CC BY-NC-SA 2.5 CA
//https://www.rgagnon.com/javadetails/java-0593.html
//tagastab nimekirja hetkel töötavatest protsessidest kasutades VBScript-i
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.*;

public class VBSUtils {
    private VBSUtils() {  }

    public static List<String> listRunningProcesses() {
        List<String> processList = new ArrayList<String>();
        try {

            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                    + "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
                    + "Set service = locator.ConnectServer()\n"
                    + "Set processes = service.ExecQuery _\n"
                    + " (\"select name from Win32_Process\")\n"
                    + "For Each process in processes\n"
                    + "wscript.echo process.Name \n"
                    + "Next\n"
                    + "Set WSHShell = Nothing\n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return processList;
    }

    public static void main(String[] args){
        List<String> processes = VBSUtils.listRunningProcesses();
        String result = "";

        Iterator<String> it = processes.iterator();
        int i = 0;
        while (it.hasNext()) {
            result += it.next() +",";
            i++;
            if (i==10) {
                result += "\n";
                i = 0;
            }
        }
        for (String process : processes) {
            System.out.println(process);
        }
        msgBox("Running processes : " + result);
    }

    public static void msgBox(String msg) {
        javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
                null, msg, "VBSUtils", javax.swing.JOptionPane.DEFAULT_OPTION);
    }
}