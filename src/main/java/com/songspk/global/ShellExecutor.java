package com.songspk.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutor {
    

    static {
        File file = new File(SysProperties.getInstance().getProperty("BASE_DIR") + "/data");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static Process executeProcess(String shellScript) throws IOException {

        String tempFileName = SysProperties.getInstance().getProperty("BASE_DIR") + "/tempshell/" + Randomizer.getRandomString(10) + ".sh";
        FileUtility.writeFile(tempFileName, shellScript);

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(new String[]{"sh", tempFileName});
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        String s = null;
        String response = "";
        while ((s = stdInput.readLine()) != null) {
            response += s + "\n";
        }

        while ((s = stdError.readLine()) != null) {
            response += s + "\n";
        }

        //FileUtility.deleteFile(tempFileName);

        return proc;
    }

    public static String execute(String shellScript) throws IOException {

        String tempFileName = SysProperties.getInstance().getProperty("BASE_DIR") + "/tempshell/" + Randomizer.getRandomString(10) + ".sh";
        FileUtility.writeFile(tempFileName, shellScript);

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(new String[]{"sh", tempFileName});
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        String s = null;
        String response = "";
        while ((s = stdInput.readLine()) != null) {
            response += s + "\n";
        }

        while ((s = stdError.readLine()) != null) {
            response += s + "\n";
        }

        FileUtility.deleteFile(tempFileName);

        return response;
    }

    public static String executeAndShowStatus(String shellScript) throws IOException {

        String tempFileName = SysProperties.getInstance().getProperty("BASE_DIR") + "/" + Randomizer.getRandomString(10) + ".sh";
        FileUtility.writeFile(tempFileName, shellScript);

        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(new String[]{"sh", tempFileName});
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        String s = null;
        String response = "";
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        FileUtility.deleteFile(tempFileName);
        return response;
    }

    public static void main(String[] args) {
    }
}
