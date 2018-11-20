package org.spa.vo.common;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivy on 2016/9/6.
 */
public class TreeVO implements Serializable {

    private String id; // unique

    private String displayName;

    private String level; //层级

    private String type; // 类别

    private TreeVO parent;

    private List<TreeVO> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public TreeVO getParent() {
        return parent;
    }

    public void setParent(TreeVO parent) {
        this.parent = parent;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // 是否是叶子节点
    @Transient
    public boolean isLeaf() {
        return children.isEmpty();
    }

}
