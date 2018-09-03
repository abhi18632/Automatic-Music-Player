package com.songspk.global;

import java.io.*;
import java.util.*;

public class FileUtility {




    public static List<String> readListFromFile(File file) {
        List<String> result = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
            scanner.close();
        } catch (
                IOException e) {
        }
        return result;
    }

    public static String readFile(String fname) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fname));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            if (br != null)
                br.close();
        } catch (Exception e) {
        }
        return stringBuffer.toString();
    }

    public static List<String> readListFromFile(String fname) {
        BufferedReader br;
        List<String> result = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(fname));
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            if (br != null)
                br.close();
        } catch (Exception e) {
        }
        return result;
    }

    public static void writeFile(String fname, String content) {
        try {
            File file = new File(fname);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
    }

    public static void writeSetToFileLineByLine(String fname, Collection<String> set) {
        try {
            File file = new File(fname);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String s : set)
                bw.write(s + "\n");
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
    }

    public static void deleteFile(String fname) {
        try {
            File file = new File(fname);
            file.delete();
        } catch (Exception e) {
        }
    }

    public static void appendToFile(String fname, String content) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fname, true)));
            out.println(content);
            out.close();
        } catch (IOException e) {
        }
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    public static boolean moveFile(File file, String destinationDir) {
        return file.renameTo(new File(destinationDir, file.getName()));
    }

    public static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int length;
        while ((length = in.read(buf)) > 0) {
            out.write(buf, 0, length);
        }
    }

    public static ArrayList<String> listFilesAndFilesSubDirectories(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        ArrayList<String> result = new ArrayList();
        if (fList != null && fList.length > 0) {
            for (File file : fList) {
                if (file.isFile()) {
                    result.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    ArrayList<String> resultFiles = listFilesAndFilesSubDirectories(file.getAbsolutePath());
                    if (resultFiles != null)
                        result.addAll(resultFiles);
                }
            }
        }
        return result;
    }

    public static ArrayList<String> getFilesInDirectoryStartingWithAndNotEndingWith(String directoryName, String prefix, String suffix) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        ArrayList<String> result = new ArrayList();
        if (fList != null && fList.length > 0) {
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().startsWith(prefix) && !file.getName().endsWith(suffix))
                        result.add(file.getAbsolutePath());
                }
            }
        }
        return result;
    }

    public static ArrayList<String> getFilesInDirectoryStartingWithAndEndingWith(String directoryName, String prefix, String suffix) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        ArrayList<String> result = new ArrayList();
        if (fList != null && fList.length > 0) {
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().startsWith(prefix) && file.getName().endsWith(suffix))
                        result.add(file.getAbsolutePath());
                }
            }
        }
        return result;
    }

    public static ArrayList<String> replaceFileContent(File log, HashMap<String, String> map) throws FileNotFoundException {
        ArrayList<String> changes = new ArrayList<String>();
        FileReader fr = new FileReader(log);
        String s;
        String content = "";
        int lineNo = 1;
        try {
            BufferedReader br = new BufferedReader(fr);
            while ((s = br.readLine()) != null) {
                for (Map.Entry x : map.entrySet()) {
                    if (s.contains((String) (x.getKey()))) {
                        changes.add((log.getAbsolutePath() + ": Line-" + lineNo + "" + s));
                        s = s.replace((String) (x.getKey()), (String) (x.getValue()));
                    }
                }
                content += s + "\r\n";
                lineNo++;
            }
        } catch (Exception e) {

        }
        writeFile(log.getAbsolutePath(), content);
        return changes;
    }
}
