package org.spa.vo.product;

import org.spa.model.product.ProductOption;
import org.spa.vo.page.Page;

import java.io.Serializable;

public class TreatmentListVo extends Page<ProductOption> implements Serializable {

    private String treatmentCode;

    private String treatmentName;

}
