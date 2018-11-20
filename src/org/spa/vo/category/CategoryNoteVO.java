package org.spa.vo.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spa.model.product.Category;
import org.spa.model.product.Product;

import com.spa.constant.CommonConstant;
import org.spa.service.product.CategoryService;
import org.spa.serviceImpl.product.CategoryServiceImpl;
import org.spa.utils.SpringUtil;

/**
 * @author Ivy 2016-3-30
 */
public class CategoryNoteVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private boolean checked = false;

    private boolean halfCheck;

	private String name;
	
	private String displayOrder;

	private String parentCategoryId;
	
	private String remarks;

    private boolean open;

    private boolean isParent;

    // 类别:category, product, productOption ....
    private String type;

    private boolean hasChildrenCheck;

    private List<CategoryNoteVO> children = new ArrayList<>();

    public static void getMappingData(List<Long> categoryIds, List<Product> productList) {
        Map<Object, CategoryNoteVO> dataMap = new HashMap<>();
        for(Product product : productList) {
            CategoryNoteVO categoryNoteVO = dataMap.get(product);
            if(categoryNoteVO == null) {
                categoryNoteVO = new CategoryNoteVO();
                dataMap.put(product, categoryNoteVO);
            }
            categoryNoteVO.setId(product.getId());
            categoryNoteVO.setName(product.getName());
            categoryNoteVO.setParentCategoryId(product.getCategory().getId().toString());
            categoryNoteVO.setChecked(true);
            categoryNoteVO.isParent = false; // 叶子节点
            categoryNoteVO.open = false;
            categoryNoteVO.type = CommonConstant.TYPE_PRODUCT;
        }


    }

   /* private static CategoryNoteVO getCagegory(Category category, Map<Object, CategoryNoteVO> dataMap,) {
        CategoryNoteVO categoryNoteVO = dataMap.get(category);
        if(categoryNoteVO == null) {
            categoryNoteVO = new CategoryNoteVO();
        }

        return categoryNoteVO;
    }*/


    // 获取 category tree node
    public static CategoryNoteVO getInstance(Category category, List<Long> categoryIds) {
        CategoryNoteVO categoryNoteVO = new CategoryNoteVO();
        categoryNoteVO.setId(category.getId());
        categoryNoteVO.setName(category.getName());
        if (category.getCategory() != null) {
            categoryNoteVO.setParentCategoryId(category.getCategory().getId().toString());
        }
        Long categoryId = category.getId();
        categoryNoteVO.setChecked(categoryIds.contains(categoryId));
        categoryNoteVO.isParent = category.getCategories().size() > 0; // 是否为叶子节点
        categoryNoteVO.open = false;
        categoryNoteVO.type = CommonConstant.TYPE_CATEGORY;
        return categoryNoteVO;
    }

    // 获取 category tree node
    public static CategoryNoteVO getInstance(Category category, List<Long> categoryIds, List<Product> productList) {
        CategoryNoteVO categoryNoteVO = new CategoryNoteVO();
        categoryNoteVO.setId(category.getId());
        categoryNoteVO.setName(category.getName());
        if (category.getCategory() != null) {
            categoryNoteVO.setParentCategoryId(category.getCategory().getId().toString());
        }
        Long categoryId = category.getId();
        //categoryNoteVO.setChecked(categoryIds.contains(categoryId));
        CategoryService categoryService = SpringUtil.getBean(CategoryServiceImpl.class);
        for (Long cid : categoryIds) {
            Category selectedCategory = categoryService.get(cid);
            setCheck(categoryNoteVO, categoryId, selectedCategory);
            if (categoryNoteVO.isChecked()) {
                break;
            }
        }
        if (!categoryNoteVO.isChecked()) {
            for (Product product : productList) {
                setCheck(categoryNoteVO, categoryId, product.getCategory());
                if (categoryNoteVO.isChecked()) {
                    break;
                }
            }
        }
        // 是否全选中
        if (categoryNoteVO.isChecked()) {
            int childrenSize = category.getSortedChilden().size();
            if (childrenSize > 0) {
                // 非叶子节点
                if(categoryIds.contains(categoryId)) {
                    categoryNoteVO.setHalfCheck(false);
                } else {
                    long count = category.getSortedChilden().stream().filter(child -> categoryIds.contains(child.getId())).count();
                    if(count > 0) {
                        categoryNoteVO.setHalfCheck(count < childrenSize && childrenSize != 1);  // 部分选择
                    } else {
                        categoryNoteVO.setHalfCheck(true);
                    }
                }

            } else {
                List<Product> products = category.getSortedProducts();
                if (products.size() > 0) {
                    // 叶子节点
                    int productSize = products.size();

                    if (productList.size() > 0) {
                        if(categoryIds.contains(categoryId)) {
                            categoryNoteVO.setHalfCheck(false);
                        } else {
                            long count = products.stream().filter(product -> productList.contains(product)).count();
                            if (count > 0) {
                                categoryNoteVO.setHalfCheck(count < productSize && productSize != 1);
                            } else {
                                categoryNoteVO.setHalfCheck(true);
                            }
                        }

                    } else {
                        categoryNoteVO.setHalfCheck(false); // category勾选，全选
                    }
                } else {
                    System.out.println("category:" + category.getName() + ",isHalfCheck:" + categoryNoteVO.isHalfCheck());
                    // 没有产品的category, 也是叶子节点的处理
                    if(categoryIds.contains(categoryId)) {
                        categoryNoteVO.setHalfCheck(false);
                    } else {
                        categoryNoteVO.setChecked(false);
                       /* Category parent = category.getCategory();
                        long count = parent.getCategories().stream().filter(child -> categoryIds.contains(child.getId())).count();
                        if(count > 0) {

                        }
                        categoryNoteVO.setHalfCheck(count > 0 && count < parent.getCategories().size() && parent.getCategories().size() != 1);  // 部分选择*/
                    }
                }
            }

        }
        categoryNoteVO.isParent = true;
        //categoryNoteVO.open = categoryNoteVO.isChecked() || categoryNoteVO.isHalfCheck();
        categoryNoteVO.type = CommonConstant.TYPE_CATEGORY;
        return categoryNoteVO;
    }


    private static void setCheck(CategoryNoteVO categoryNoteVO, Long categoryId, Category selectedCategory) {
        Category current = selectedCategory;
        while (current != null) {
            if (current.getId().equals(categoryId)) {
                categoryNoteVO.setChecked(true);
                categoryNoteVO.setHalfCheck(true);
                break;
                // 退出循环
            }
            current = current.getCategory(); // 返回上一节点
        }
    }

    // 获取 product tree node
    public static CategoryNoteVO getInstance(Product product, List<Long> treatmentIds) {
        CategoryNoteVO categoryNoteVO = new CategoryNoteVO();
        categoryNoteVO.setId(product.getId());
        categoryNoteVO.setName(product.getName());
        categoryNoteVO.setParentCategoryId(product.getCategory().getId().toString());
        if (treatmentIds != null && treatmentIds.size() > 0) {
            categoryNoteVO.setChecked(treatmentIds.contains(product.getId()));
        }
        categoryNoteVO.isParent = false; // 叶子节点
        categoryNoteVO.open = false;
        categoryNoteVO.type = CommonConstant.TYPE_PRODUCT;
        return categoryNoteVO;
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

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public List<CategoryNoteVO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryNoteVO> children) {
        this.children = children;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(boolean halfCheck) {
        this.halfCheck = halfCheck;
    }
}
