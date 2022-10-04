package com.sbs.exam.service;

import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private Connection con;
  public ArticleService(Connection con) {
    this.con = con;
  }

  public int getItemsInAPage() {
    return 20;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(con, sql);
    int totalPage = (int) Math.ceil((double)totalCount / itemInAPage);
    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(con, sql);

    return articleRows;
  }
}
