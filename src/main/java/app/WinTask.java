package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.*;

public class WinTask {

    public static ArrayList<Integer> pidList(String taskListExe, String imageName) {

        ArrayList<Integer> pidList = new ArrayList();

        try {
            Runtime run = Runtime.getRuntime();
            String[] commands = {"\""+ taskListExe +"\"", "/fi", "\"IMAGENAME eq "+ imageName +"\""};
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
                        System.out.println(statusList.get(i).toString());
                        return pidList;
                    }
                }
            }

            if (!errorList.isEmpty()) {
                for(int i = 0; i < errorList.size(); i += 1) {
                    System.out.println(errorList.get(i).toString());
                    if (errorList.get(i).toString().toLowerCase().contains("no tasks are running which match the specified criteria")){
                        return pidList;
                    }
                }
            }

            if (!statusList.isEmpty()) {
                Pattern pattern = Pattern.compile("([^\\s]+)\\s+(\\d+)\\s+.+", Pattern.CASE_INSENSITIVE);
                Matcher matcher;
                String imageNameListed;
                int pidListed;
                for(int i = 0; i < statusList.size(); i += 1) {
                    if (statusList.get(i).toString().toLowerCase().contains(imageName.toLowerCase())){
                        matcher = pattern.matcher(statusList.get(i).toString());
                        if (matcher.matches() && matcher.groupCount() == 2) {
                            imageNameListed = matcher.group(1);
                            pidListed = Integer.parseInt(matcher.group(2));
//                            System.out.println("    "+ imageNameListed +" "+ Integer.toString(pidListed));
                            pidList.add(pidListed);
                        }
                    }
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return pidList;
    }

    public static void kill(String taskListExe, String taskKillExe, String imageName, ArrayList<Integer> exceptPidList) {

        ArrayList<Integer> pidList = WinTask.pidList(taskListExe, imageName);

        for (int i = 0; i < pidList.size(); i += 1) {
            for (int e = 0; e < exceptPidList.size(); e += 1) {
                if (pidList.get(i).equals(exceptPidList.get(e))){
                    pidList.remove(i);
                }
            }

        }

        System.out.println("Kill "+ pidList.size() +" process");
        for (int i = 0; i < pidList.size(); i += 1) {
            WinTask.killPid(taskKillExe, imageName, pidList.get(i), true);
        }
        System.out.println("Ignore "+ exceptPidList.size() +" preexistent process");
    }

    static void killPid(String taskKillExe, String imageName, int pid, boolean force) {

        try {
            Runtime run = Runtime.getRuntime();
            String[] commandsForce = {"\""+ taskKillExe +"\"", "/f", "/pid "+ pid,"/im", "\"IMAGENAME eq "+ imageName +"\"", "/t"};
            String[] commands = {"\""+ taskKillExe +"\"", "/pid "+ pid,"/im", "\"IMAGENAME eq "+ imageName +"\"", "/t"};

            Process proc;
            if (force == true) {
                proc = run.exec(commandsForce);
            } else {
                proc = run.exec(commands);
            }
            System.out.println("    "+ imageName +" "+ pid);

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
