package org.spa.vo.shop;

import java.io.Serializable;

import org.spa.model.shop.OutSourceTemplate;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-8-29
 */
public class OutSourceTemplateListVO extends Page<OutSourceTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


