package org.spa.vo.product;

import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-5-13
 */
public class ProductOptionVO extends Page<ProductOption> implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

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
}
