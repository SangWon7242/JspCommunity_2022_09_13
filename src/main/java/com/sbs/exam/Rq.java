package com.sbs.exam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rq {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private boolean isInvalid = false;
  private String controllerName;
  private String actionMethodName;

  public Rq(HttpServletRequest req, HttpServletResponse resp)  {
    // 들어오는 파라미터를 UTF-8로 해석
    try {
      req.setCharacterEncoding("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }

    // 서블릿이 HTML 파일을 만들 대 UTF-8 로 쓰기
    resp.setCharacterEncoding("UTF-8");

    // HTML이 UTF-8 형식이라는 것을 브라우저에게 알린다.
    resp.setContentType("text/html; charset-utf-8");

    this.req = req;
    this.resp = resp;

    String requestUri = req.getRequestURI();
    String[] requestUriBIts = requestUri.split("/");

    int minBitsCount = 4;

    if ( requestUriBIts.length < minBitsCount ) {
      isInvalid = true;
      return;
    }

    this.controllerName = requestUriBIts[2];
    this.actionMethodName = requestUriBIts[3];
  }

  public HttpServletRequest getReq() {
    return req;
  }

  public boolean getIsInvalid() {
    return isInvalid;
  }

  public String getControllerName() {
    return controllerName;
  }

  public String getActionMethodName() {
    return actionMethodName;
  }

  public int getIntParam(String paramName, int defaultValue) {
    String value = req.getParameter(paramName);

    if ( value == null ) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(value);
    }
    catch ( NumberFormatException e) {
      return defaultValue;
    }

  }

  public void appendBody(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
