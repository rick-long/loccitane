package org.spa.vo.product;

import org.hibernate.validator.constraints.NotBlank;
import org.spa.vo.common.KeyAndValueVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public class ProductEditVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long brandId;

    private List<KeyAndValueVO> pdvalues;

    @NotNull
    private List<PoItemVO> poItemList;

    private Long id;

    private Boolean showOnline;

    private Boolean isActive;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getShowOnline() {
        return showOnline;
    }

    public void setShowOnline(Boolean showOnline) {
        this.showOnline = showOnline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<KeyAndValueVO> getPdvalues() {
        return pdvalues;
    }

    public void setPdvalues(List<KeyAndValueVO> pdvalues) {
        this.pdvalues = pdvalues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public List<PoItemVO> getPoItemList() {
        return poItemList;
    }

    public void setPoItemList(List<PoItemVO> poItemList) {
        this.poItemList = poItemList;
    }

}
