import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.*;
import java.util.*;
import java.nio.charset.*;
public class HttpInterface {
  public static String excuteGet(String targetURL, Map<String, String> parameters)
    {
      URL url;
      HttpURLConnection connection = null;
      try {
        //Create connection
        url = new URL(targetURL);
        connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);//allows us to add parameters
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
public static void excutePost(String targetURL, Map<String, String> parameters)
      {
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
    throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();

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
}
