package app;

import java.io.File;

public class WorkDir {
    private String[] args;
    private String jarName;
    private String workDir;

    public WorkDir(String[] args, String jarName) throws WorkDirException {
        this.args = args;
        this.jarName = jarName;

        if (jarName == null || jarName.trim().isEmpty()) {
            throw new WorkDirException("Internal error. \"jarName\" is not set.");
        }
        if (args == null || args.length < 1) {
            System.out.println("Usage: \"java.exe\" -jar \"" + jarName + "\" \"C:\\Working\\Directory\"");
            throw new WorkDirException("Please specify the working directory where \"" + jarName + "\" is located.");
        }
        if (args.length != 1) {
            throw new WorkDirException("Only 1 parameter allowed. Found " + args.length);
        }
        this.workDir = args[0];
    }

    String dir() throws WorkDirException {
        new WorkDir(args, jarName).findJar();
        return workDir;
    }

    File findJar() throws WorkDirException {

        File jarFile = new File(workDir + "\\" + jarName);
        String jarPath = jarFile.getAbsolutePath();

        if (jarFile.exists()) {
            if (jarFile.isFile()) {
                System.out.println("Found JAR \"" + jarPath + "\"");
                return jarFile;
            } else {
                throw new WorkDirException("Not a file: \"" + jarPath + "\"");
            }
        } else {
            throw new WorkDirException("Cannot find: \"" + jarPath + "\"");
        }
    }
}
