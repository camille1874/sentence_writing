package data_constructor;

import java.util.*;
import java.util.Map.Entry;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;
import static data_constructor.FileUtil.file2Map;
import static data_constructor.FileUtil.writeFile;
import static data_constructor.TextUtil.cleanStr;

/**
 * Created by xuzh on 2019/2/18.
 */
public class DataEnhancer {
    public void getNewData(String inputFile, int attrSum, String outputFile) {
        Map<Integer, Map<String, String>> resultMap = file2Map(inputFile, attrSum);
        List<String> newData = new ArrayList<>();
        if (attrSum == 2) {
            Map<String, String> map1 = resultMap.get(0);
            Map<String, String> map2 = resultMap.get(1);
            Iterator<Map.Entry<String, String>> it1 = map1.entrySet().iterator();
            while (it1.hasNext()) {
                Entry<String, String> entry1 = it1.next();
                String s1 = entry1.getKey();
                String l1 = entry1.getValue();
                Iterator<Map.Entry<String, String>> it2 = map2.entrySet().iterator();
                while (it2.hasNext()) {
                    Entry<String, String> entry2 = it2.next();
                    String s2 = entry2.getKey();
                    String l2 = entry2.getValue();
                    if (!(s1.contains(l2) || s2.contains(l1)))
                        newData.add(cleanStr(s1, null) + "，" + cleanStr(s2, null) + "\t" + l1 + " " + l2);
                }
            }
        }
        Collections.shuffle(newData);
        writeFile(outputFile, newData, false);
    }

    public static void main(String[] args) {
        DataEnhancer de = new DataEnhancer();
        de.getNewData("描述.txt", 2, "描述1.txt");
    }
}
