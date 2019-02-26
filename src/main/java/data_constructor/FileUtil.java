package data_constructor;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuzh on 2019/1/15.
 */
public class FileUtil {
    public static int writeFile(String fileName, String text) {
        BufferedWriter bw = null;
        try {
            File file = new File(fileName);
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 1;
                }
            }
        }
        return 0;
    }

    public static int writeFile(String fileName, List<String> results, boolean append) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File(fileName);
            fw = new FileWriter(file, append); //追加
            bw = new BufferedWriter(fw);
            for (String r : results) {
                bw.write(r + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public static List<String> readFile(String fileName) {
        List<String> result = new ArrayList<>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpString;
            while ((tmpString = reader.readLine()) != null) {
                result.add(tmpString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }

    // 一个文件按句子和标签读取返回，key计数(第一句有重复)
    public static List<Pair<String, String>> file2Map(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<Pair<String, String>> result = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpString;
            while ((tmpString = reader.readLine()) != null) {
                String[] tmp = tmpString.trim().split("\t");
                result.add(new Pair<>(tmp[0], tmp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }


    // 一个文件中的句子拆成子句和单个属性返回,key记录属性号
    public static Map<Integer, Map<String, String>> file2Map(String fileName, int attrNum) {
        Map<Integer, Map<String, String>> resultMap = new HashMap<>();
        for (int i = 0; i < attrNum; ++i) {
            resultMap.put(i, new HashMap<>());
        }
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmpString;
            while ((tmpString = reader.readLine()) != null) {
                String[] tmp = tmpString.split("\t");
                String[] sens = tmp[0].split("，");
                String[] labels = tmp[1].split(" ");
                for (int i = 0; i < sens.length; ++i) {
                    resultMap.get(i).put(sens[i], labels[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return resultMap;
    }
}
