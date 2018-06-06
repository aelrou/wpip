package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WinTask {

    public static boolean find(String taskListExe, String imageName) {

        boolean processFound = false;

        try {
            Runtime run = Runtime.getRuntime();
            String[] commands = {"\""+ taskListExe +"\"", "/fi", "\""+ imageName +"\""};
            Process proc = run.exec(commands);

            InputStream iStream = proc.getInputStream();
            InputStreamReader iStreamReader = new InputStreamReader(iStream);
            BufferedReader iBuffer = new BufferedReader(iStreamReader);

            InputStream eStream = proc.getErrorStream();
            InputStreamReader eStreamReader = new InputStreamReader(eStream);
            BufferedReader eBuffer = new BufferedReader(eStreamReader);

            // read the output from the command
            ArrayList statusList = new ArrayList();
            String statusLine;
            while ((statusLine = iBuffer.readLine()) != null) {
                statusList.add(statusLine);
            }

            // read any errors from the attempted command
            ArrayList errorList = new ArrayList();
            String errorLine;
            while ((errorLine = eBuffer.readLine()) != null) {
                errorList.add(errorLine);
            }

            if (!statusList.isEmpty()) {
                for(int i = 0; i < statusList.size(); i += 1) {
                    if (statusList.get(i).toString().toLowerCase().contains("no tasks are running which match the specified criteria")){
                        break;
                    }
                    if (statusList.get(i).toString().toLowerCase().contains(imageName)){
                        processFound = true;
                        break;
                    }
//                    Log.save(logFile, statusList.get(i).toString());
                }
            }

            if (!errorList.isEmpty()) {
                for(int i = 0; i < errorList.size(); i += 1) {
                    if (statusList.get(i).toString().toLowerCase().contains("no tasks are running which match the specified criteria")){
                        break;
                    }
                }
//                Log.save(logFile, errorList.get(i).toString());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return processFound;
    }

    public static void kill(String taskKillExe, String imageName, boolean force) {

        try {
            Runtime run = Runtime.getRuntime();
            String[] commandsForce = {"\""+ taskKillExe +"\"", "/f", "/im", "\""+ imageName +"\"", "/t"};
            String[] commands = {"\""+ taskKillExe +"\"", "/im", "\""+ imageName +"\"", "/t"};

            Process proc;
            if (force == true) {
                proc = run.exec(commandsForce);
            } else {
                proc = run.exec(commands);
            }

            InputStream iStream = proc.getInputStream();
            InputStreamReader iStreamReader = new InputStreamReader(iStream);
            BufferedReader iBuffer = new BufferedReader(iStreamReader);

            InputStream eStream = proc.getErrorStream();
            InputStreamReader eStreamReader = new InputStreamReader(eStream);
            BufferedReader eBuffer = new BufferedReader(eStreamReader);

            // read the output from the command
            ArrayList statusList = new ArrayList();
            String statusLine;
            while ((statusLine = iBuffer.readLine()) != null) {
                statusList.add(statusLine);
            }

            // read any errors from the attempted command
            ArrayList errorList = new ArrayList();
            String errorLine;
            while ((errorLine = eBuffer.readLine()) != null) {
                errorList.add(errorLine);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
