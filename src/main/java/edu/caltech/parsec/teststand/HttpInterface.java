package edu.caltech.parsec.teststand;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.*;
import java.util.*;
import java.nio.charset.*;
public class HttpInterface {
  public static String executeGet(String targetURL, Map<String, String> parameters)
    {
      URL url;
      HttpURLConnection connection = null;
      try {
        //Create connection
        url = new URL(targetURL);
        connection = (HttpURLConnection)url.openConnection();
        //System.out.println("code:" + connection.getResponseCode());

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);//allows us to add parameters

        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(getParamsString(parameters));
        out.flush();
        out.close();

        int status = connection.getResponseCode();
        BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        return content.toString();

      } catch (Exception e) {
        e.printStackTrace();
        return null;
      } finally {
        if(connection != null) {
          connection.disconnect();
        }
      }
    }

  public static String executeGet2(String targetURL, Map<String, String> parameters) {
    try {
      URL url = new URL(targetURL);

      //URLConnection con = url.openConnection();
      HttpURLConnection http = (HttpURLConnection)url.openConnection();
      //System.out.println("code:"+http.getResponseCode());
      http.setRequestMethod("GET");
      http.setDoOutput(true);

      http.setConnectTimeout(5000);
      http.setReadTimeout(5000);

    //  HttpURLConnection conn = (HttpURLConnection) url.openConnection();

/*
      DataOutputStream out = new DataOutputStream(http.getOutputStream());
      out.writeBytes(getParamsString(parameters));
      out.flush();
      out.close();
*/

      int status = http.getResponseCode();
      BufferedReader in = new BufferedReader(
        new InputStreamReader(http.getInputStream()));
      String inputLine;
      StringBuffer content = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      return content.toString();
    }
    catch(Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void executePost(String targetURL, Map<String, String> parameters) {
    URL url;
    HttpURLConnection connection = null;
    try {
      String urlParameters  = getParamsString(parameters);
      byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
      url = new URL(targetURL);
      connection= (HttpURLConnection) url.openConnection();
      connection.setDoOutput( true );
      connection.setInstanceFollowRedirects( false );
      connection.setRequestMethod( "POST" );
      connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty( "charset", "utf-8");
      connection.setRequestProperty( "Content-Length", Integer.toString(postData.length));
      connection.setUseCaches( false );
      try( DataOutputStream out = new DataOutputStream( connection.getOutputStream())) {
       out.write( postData );
      }
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } finally {
      if(connection != null) {
        connection.disconnect();
      }
    }
  }
  private static String getParamsString(Map<String, String> params)
    throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(); //

        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }

        String resultString = result.toString();
        if(resultString.length()>0)
          return resultString.substring(0, resultString.length() - 1);
        return "";
  }

  private static String makeFullURL(String baseURL, Map<String, String> params) {
    String paramStr = "";

    try {
      paramStr = getParamsString(params);
    }
    catch (UnsupportedEncodingException e) {
      System.out.println("Error occurred creating the paramater string from the parameter map.");
      e.printStackTrace();

      return null;
    }

    if (paramStr.length() == 0) {
      // The baseURL should not have a '?' at the end
      if (baseURL.charAt(baseURL.length() -1) == '?') {
        return baseURL.substring(0, baseURL.length() -1);
      }

      return baseURL;
    }

    // Make sure there is exactly one '?' between the base url and paramaters
    if (baseURL.charAt(baseURL.length() -1) != '?') {
      baseURL += "?";
    }

    return baseURL + paramStr;
  }

  public static void main(String[] args) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("sensorName", "altimeter");

    String baseURL = "http://private-143c20-simulatorcontrol.apiary-mock.com/getSensor?";
    String targetURL = makeFullURL(baseURL, params);
    System.out.println("URL: " + targetURL);

    System.out.println(executeGet2(targetURL, params));
  }
}
