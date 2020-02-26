package utils;

import java.io.*;

public class FileUtil {
    /**
     * 获取文件reader
     * @param path 文件路径
     * @return 文件reader
     */
    public static BufferedReader createFileReader(String path) {
        BufferedReader bufferedReader = null;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileReader reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader reader = new FileReader(file);
                bufferedReader = new BufferedReader(reader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bufferedReader;
    }

    /**
     * 获取文件writer
     * @param path 文件路径
     * @return 文件writer
     */
    public static BufferedWriter createFileWriter(String path) {
        BufferedWriter bufferedWriter = null;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                bufferedWriter = new BufferedWriter(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileWriter writer = new FileWriter(file);
                bufferedWriter = new BufferedWriter(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedWriter;
    }
}

