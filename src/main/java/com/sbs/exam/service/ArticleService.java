package com.sbs.exam.service;

import com.sbs.exam.dao.ArticleDao;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleDao articleDao;
  public ArticleService(Connection con) {
    this.articleDao = new ArticleDao(con);
  }

  public int getItemsInAPage() {
    return 20;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    int totalCount = articleDao.getTotalCount();
    int totalPage = (int) Math.ceil((double)totalCount / itemInAPage);
    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Map<String, Object>> articleRows = articleDao.getArticleRows(itemInAPage, limitFrom);

    return articleRows;
  }
}
