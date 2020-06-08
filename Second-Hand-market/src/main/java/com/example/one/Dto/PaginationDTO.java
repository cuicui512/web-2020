package com.example.one.Dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrePage;
    private boolean showNextPage;
    private boolean showReturnFirstPage;
    private boolean showReturnEndPage;
    private Integer CurrentPage;
    private List<Integer> showPages = new ArrayList<>();
    private Integer TotalPage;

    public List<T> getData() {
        return data;
    }

    public boolean isShowPrePage() {
        return showPrePage;
    }

    public boolean isShowNextPage() {
        return showNextPage;
    }

    public boolean isShowReturnFirstPage() {
        return showReturnFirstPage;
    }

    public boolean isShowReturnEndPage() {
        return showReturnEndPage;
    }

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public List<Integer> getShowPages() {
        return showPages;
    }

    public Integer getTotalPage() {
        return TotalPage;
    }

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            this.TotalPage = totalCount / size;
        } else {
            this.TotalPage = totalCount / size + 1;
        }
//        容错处理

        if(page<1){
            page=1;
        }
        if(page>this.TotalPage){
            page=this.TotalPage;
        }
        this.CurrentPage = page;
        showPages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                showPages.add(0,page-i);
            }

            if(page+i<=this.TotalPage){
                showPages.add(page+i);
            }
        }

        //        是否展示上一页的分页功能
        this.showPrePage = page != 1;

    //        是否展示下一页
        this.showNextPage = page != this.TotalPage;

        //是否展示返回首页
        this.showReturnFirstPage= !showPages.contains(1);
        //是否展示返回最后一页
        this.showReturnEndPage= !showPages.contains(this.TotalPage);
    }
}
