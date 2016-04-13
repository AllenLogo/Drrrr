package tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringTool
{
  public static String getMyURLEncoder(String str)
  {
    try
    {
      return URLDecoder.decode(str, "utf-8"); } catch (UnsupportedEncodingException e) {
    }
    return "";
  }

  public static String setMyURLEncoder(String str, String type)
  {
    try {
      return URLEncoder.encode(str, type); } catch (UnsupportedEncodingException e) {
    }
    return "";
  }
}