package data_constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by giiso on 2019/1/15.
 */
public class DataConstructor {
    public Map<String, List<String>> key_map;

    public DataConstructor() {
        List<String> keywords_motivation = new ArrayList<String>() {{
            add("发动机");
            add("功率");
            add("峰值扭矩");
        }};
        List<String> keywords_look = new ArrayList<String>() {{
            add("车身尺寸");
            add("轴距");
        }};
        key_map = new HashMap<>();
        key_map.put("外观", keywords_look);
        key_map.put("动力", keywords_motivation);
    }

    public void getData(int size, String[] tags, String type) {
        ESUtil.getInstance();
        List<String> keywords = key_map.get(type);
        List<Map<String, Object>> docs = ESUtil.searchDocs(tags, size, keywords);
        List<String> results = TextUtil.parseResult(docs, keywords, type);
        FileUtil.writeFile(type + ".txt", results);
    }

    public static void main(String[] args) {
        DataConstructor d = new DataConstructor();
        String[] tags = {"汽车"};
        String type = "外观";
        d.getData(2000, tags, type);
    }
}
