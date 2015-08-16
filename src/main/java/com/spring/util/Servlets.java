package com.spring.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;

import com.wondersgroup.showcase.core.utils.Encodes;

public class Servlets
{
  public static final long ONE_YEAR_SECONDS = 31536000L;
  
  public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
  {
    response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
    
    response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
  }
  
  public static void setNoCacheHeader(HttpServletResponse response)
  {
    response.setDateHeader("Expires", 1L);
    response.addHeader("Pragma", "no-cache");
    
    response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
  }
  
  public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
  {
    response.setDateHeader("Last-Modified", lastModifiedDate);
  }
  
  public static void setEtag(HttpServletResponse response, String etag)
  {
    response.setHeader("ETag", etag);
  }
  
  public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
  {
    long ifModifiedSince = request.getDateHeader("If-Modified-Since");
    if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L))
    {
      response.setStatus(304);
      return false;
    }
    return true;
  }
  
  public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag)
  {
    String headerValue = request.getHeader("If-None-Match");
    if (headerValue != null)
    {
      boolean conditionSatisfied = false;
      if (!"*".equals(headerValue))
      {
        StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
        while ((!conditionSatisfied) && (commaTokenizer.hasMoreTokens()))
        {
          String currentToken = commaTokenizer.nextToken();
          if (currentToken.trim().equals(etag)) {
            conditionSatisfied = true;
          }
        }
      }
      else
      {
        conditionSatisfied = true;
      }
      if (conditionSatisfied)
      {
        response.setStatus(304);
        response.setHeader("ETag", etag);
        return false;
      }
    }
    return true;
  }
  
  public static void setFileDownloadHeader(HttpServletResponse response, String fileName)
  {
    try
    {
      String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
    }
    catch (UnsupportedEncodingException e) {}
  }
  
  public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix)
  {
    Validate.notNull(request, "Request must not be null", new Object[0]);
    Enumeration paramNames = request.getParameterNames();
    Map<String, Object> params = new TreeMap();
    if (prefix == null) {
      prefix = "";
    }
    while ((paramNames != null) && (paramNames.hasMoreElements()))
    {
      String paramName = (String)paramNames.nextElement();
      if (("".equals(prefix)) || (paramName.startsWith(prefix)))
      {
        String unprefixed = paramName.substring(prefix.length());
        String[] values = request.getParameterValues(paramName);
        if ((values != null) && (values.length != 0)) {
          if (values.length > 1) {
            params.put(unprefixed, values);
          } else {
            params.put(unprefixed, values[0]);
          }
        }
      }
    }
    return params;
  }
  
  public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix)
  {
    if ((params == null) || (params.size() == 0)) {
      return "";
    }
    if (prefix == null) {
      prefix = "";
    }
    StringBuilder queryStringBuilder = new StringBuilder();
    Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, Object> entry = (Map.Entry)it.next();
      queryStringBuilder.append(prefix).append((String)entry.getKey()).append('=').append(entry.getValue());
      if (it.hasNext()) {
        queryStringBuilder.append('&');
      }
    }
    return queryStringBuilder.toString();
  }
  
  public static String encodeHttpBasic(String userName, String password)
  {
    String encode = userName + ":" + password;
    return "Basic " + Encodes.encodeBase64(encode.getBytes());
  }
}
