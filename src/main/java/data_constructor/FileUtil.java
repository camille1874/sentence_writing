package data_constructor;

import java.io.*;
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
        try {
            File file = new File(fileName);
            bw = new BufferedWriter(new FileWriter(file, append)); //追加
            StringBuffer sb = new StringBuffer();
            for (String r : results) {
                sb.append(r + "\n");
            }
            bw.write(sb.toString());
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
