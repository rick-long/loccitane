package org.spa.model.bundle;

import javax.persistence.*;

import org.spa.model.product.ProductOption;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product_bundle_product_option", catalog = "loccitane")
public class ProductBundleProductOption implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Integer groups;

    private ProductBundle productBundle;
    private ProductOption productOption;
    
    
    @Id
    @Column(name = "id",unique = true, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_bundle_id")
    public ProductBundle getProductBundle() {
		return productBundle;
	}
    public void setProductBundle(ProductBundle productBundle) {
		this.productBundle = productBundle;
	}
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_option_id")
    public ProductOption getProductOption() {
		return productOption;
	}
    public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}
    
    @Basic
    @Column(name = "groups", nullable = true)
    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }
}
