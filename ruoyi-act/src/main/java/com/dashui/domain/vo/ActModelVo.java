package com.dashui.domain.vo;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.BaseEntity;
import org.activiti.engine.repository.Model;

/**
 * @Author 13276
 * @Description TODO
 * @Date 2024/3/31 15:25
 * @Version 1.0
 * @since 1.8
 */
public class ActModelVo extends BaseEntity {

    /**
     * 流程模型ID
     */
    private String id;

    /**
     * 流程模型名称
     */
    private String name;

    /**
     * 流程模型描述
     */
    private String description;

    /**
     * 流程key
     */
    private String key;

    /**
     * 流程分类
     */
    private String category;

    public ActModelVo(Model model) {
        this.category = model.getCategory();
        this.id = model.getId();
        this.name = model.getName();
        this.key = model.getKey();
        this.description = JSONObject.parseObject(model.getMetaInfo()).getString("description");
    }

    public ActModelVo() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
