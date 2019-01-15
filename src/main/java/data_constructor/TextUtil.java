package data_constructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuzh on 2019/1/15.
 */
public class TextUtil {
    public static List<String> parseResult(List<Map<String, Object>> results, List<String> keywords) {
        List<String> result = new ArrayList<>();
        for (Map<String, Object> m : results) {
            String tmp = getSens((String) m.get("textcontent"), keywords);
            if (!tmp.equals("") && !result.contains(tmp)) {
                result.add(tmp);
            }
        }
        return result;
    }

    public static String getSens(String doc, List<String> keywords) {
        doc = cleanStr(doc);
        String[] sens = doc.split("[，|,|。|！|!|?|；|;|？|?|“|”|\"]");
//        StringBuffer sb = new StringBuffer();
        List<String> str = new ArrayList<>();
        List<String> attr = new ArrayList<>();
        for (String k : keywords) {
            for (String s : sens) {
                if (s.contains(k)) {
//                    System.out.println(s);
//                    System.out.println(k);
                    String val = getAttr(s);
                    if (val.equals("")) {
                        return "";
                    }
                    str.add(s + "，");
//                    System.out.println(val);
                    if (k.equals("发动机")) {
                        attr.add(val + k);
                    } else {
                        attr.add(val);
                    }
                    break;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(String.join("", str));
        sb.replace(sb.length() - 1, sb.length(), "。");
        sb.append("\t" + String.join(" ", attr));
        if (attr.size() == keywords.size()) {
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String getAttr(String str) {
        String regEx = "(\\d+\\.\\d+|\\d+)(牛·米|(?i)N·m|(?i)Nm|T|(?i)kw|L|马力|千瓦)\\W";
        //数字+单位，且单位后面不接字母，防止是型号名称
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            String tmp = m.group();
            return tmp.substring(0, tmp.length() - 1);
        }
        regEx = "(\\d+\\.\\d+|\\d+)(牛·米|(?i)N·m|(?i)Nm|T|(?i)kw|L|马力|千瓦)$";
        p = Pattern.compile(regEx);
        m = p.matcher(str);
        if (m.find()) return m.group();
        return "";
    }

    public static String cleanStr(String str) {
        String regEx = "[`~@#$%^&*()+=|{}':;'//[//]<>/~@#￥%&*（）——+|{}【】\"\'\\s*|\t|\r|\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").replaceAll("[ 　  ]", ""); //去掉全角空格等
    }
}
