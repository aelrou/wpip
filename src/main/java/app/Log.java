package app;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Log {
    public static String find(String logPath, String logName) {
        String logFile = null;
        File file = null;
        try {
            file = new File(logPath +"\\"+ logName).getCanonicalFile();
            logFile = logPath +"\\"+ logName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null || file.exists()) {
            if (file.length() > (1024 * 1024) ) {
                String achiveFile = logPath +"\\"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +"_"+ logName;
                File fileOld = new File(achiveFile);
                file.renameTo(fileOld);
                System.out.println("Archived LOG \""+ achiveFile +"\"");
                create(logFile);
            } else {
                System.out.println("Found LOG \""+ logFile +"\"");
            }
        } else {
            create(logFile);
        }

        return logFile;
    }

    public static void create(String logFile) {
        try {
            FileWriter writer = new FileWriter(logFile, false);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write("");
            buffer.flush();
            buffer.close();
            System.out.println("Created LOG \""+ logFile +"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(String logFile, String statement) {
        try {
            FileWriter writer = new FileWriter(logFile, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +" "+ statement);
            buffer.newLine();
            buffer.flush();
            buffer.close();
            System.out.println("Wrote LOG: "+ statement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveList(String logFile, ArrayList<String> statementList) {
        try {
            FileWriter writer = new FileWriter(logFile, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            buffer.newLine();
            for(int i = 0; i < statementList.size(); i += 1) {
                buffer.write(Go.indent + statementList.get(i).toString());
                buffer.newLine();
            }
            buffer.flush();
            buffer.close();
            System.out.println("Wrote LOG:");
            for(int i = 0; i < statementList.size(); i += 1) {
                System.out.println(statementList.get(i).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
