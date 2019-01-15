package data_constructor;

import java.io.*;
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

    public static int writeFile(String fileName, List<String> results) {
        BufferedWriter bw = null;
        try {
            File file = new File(fileName);
            bw = new BufferedWriter(new FileWriter(file, true)); //追加
            StringBuffer sb = new StringBuffer();
            for (String r: results) {
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
}
