package org.spa.vo.callback;

import org.spa.model.product.ProductOption;

import java.io.Serializable;

/**
 * Created by Hary on 2016-5-13
 */
public class ProductOptionCallBackVO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
    // product option reference
    private String reference;

    public ProductOptionCallBackVO(ProductOption productOption) {
        this.id = productOption.getId();
        this.reference = productOption.getReference();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
