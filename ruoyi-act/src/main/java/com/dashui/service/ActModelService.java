package com.dashui.service;

import com.dashui.domain.vo.ActModelVo;
import com.ruoyi.common.core.page.TableDataInfo;

import java.io.Serializable;

/**
 * @Author 13276
 * @Description TODO
 * @Date 2024/3/31 15:20
 * @Version 1.0
 * @since 1.8
 */
public interface ActModelService {


    /**
     * 分页查询
     * @param actModel 参数
     * @return
     */
    TableDataInfo queryPage(ActModelVo actModel);

    /**
     * 创建模型
     * @param actModel 参数
     * @return 模型id
     */
    String saveModel(ActModelVo actModel);

    /**
     * 根据Id查询模型
     * @param id 模型Id
     * @return 模型
     */
    ActModelVo getById(Serializable id);

    /**
     * 根据Ids删除模型
     * @param ids 模型Ids
     * @return 模型
     */
    boolean deleteById(Serializable... ids);
}
