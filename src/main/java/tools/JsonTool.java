package tools;

import java.util.Map;
import net.sf.json.JSONObject;

public class JsonTool
{
  public static String buildStrig(Map<String, String> map)
  {
    JSONObject json = JSONObject.fromObject("{}");
    for (String key : map.keySet()) {
      json.accumulate(key, map.get(key));
    }
    return json.toString();
  }

  public static String buildStrig(String key, String value) {
    JSONObject json = JSONObject.fromObject("{}");
    json.accumulate(key, value);
    return json.toString();
  }

  public static String buildMessage_hall_02(String name, String host, int number, int count, boolean pwd)
  {
    StringBuilder msg = new StringBuilder();
    msg.append("{");
    msg.append("\"roomname\":\"" + name + "\",");
    msg.append("\"roomhost\":\"" + host + "\",");
    msg.append("\"roomnumber\":\"" + number + "\",");
    msg.append("\"roomcount\":\"" + count + "\",");
    msg.append("\"roompwd\":\"" + pwd + "\"");
    msg.append("}");
    return msg.toString();
  }

  public static String buildMessage_login(String type, String name, String style)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("{\"type\":\"" + type + "\",\"content\":{\"name\":\"" + name + "\",\"style\":\"" + style + "\"}}");
    return sb.toString();
  }

  public static void main(String[] args) {
  }

  public static String getMessage(String[] args) {
    JSONObject json = JSONObject.fromObject("{}");
    if (args.length % 2 == 0) {
      for (int i = 0; i < args.length - 1; i += 2) {
        json.accumulate(args[i], args[(i + 1)]);
      }
    }
    return json.toString();
  }
}