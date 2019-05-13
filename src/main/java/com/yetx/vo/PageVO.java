package com.yetx.vo;

import java.util.List;

public class PageVO {
    private int curPage;			// 当前页数
    private int pageNum;			// 总页数
    private long records;		// 总记录数
    private List<?> curData;		// 每行显示的内容

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<?> getCurData() {
        return curData;
    }

    public void setCurData(List<?> curData) {
        this.curData = curData;
    }
}
