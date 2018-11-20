package org.spa.vo.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询类
 * <p/>
 * Created by Ivy on 2016/01/16.
 */
public class Page<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Page() {
	}

    public Page(List<T> list, Long totalRecords, Integer pageNumber, Integer pageSize) {
        this.list = list;
        this.totalRecords = totalRecords;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);
    }

    private List<T> list;

    /**
     * 总记录数
     */
    private Long totalRecords;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 当前页
     */
    private Integer pageNumber=1;

    /**
     * 每页大小
     */
    private Integer pageSize=10;
    private int maxSize=100;//一页显示的最大值

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
    	if (pageNumber < 1) {
    		pageNumber = 1;
		}
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
    	if(pageSize>maxSize){
			pageSize=maxSize;
		}
        this.pageSize = pageSize;
    }
}
