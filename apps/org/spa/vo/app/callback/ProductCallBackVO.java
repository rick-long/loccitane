package org.spa.vo.app.callback;

import org.spa.model.product.Product;

import java.io.Serializable;

public class ProductCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
/*    private String productRef;*/
    private Long productId;
    private String name;
    private String reference;
    public ProductCallBackVO() {
        super();
    }
    public  ProductCallBackVO(Product product){
        this.name=product.getName();
        this.productId=product.getId();
        this.reference=product.getReference();
/*        this.productRef=product.getReference();*/
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

/*    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }*/

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
