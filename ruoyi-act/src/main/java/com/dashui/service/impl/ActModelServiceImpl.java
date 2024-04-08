package com.dashui.service.impl;

import com.dashui.domain.vo.ActModelVo;
import com.dashui.service.ActModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.StringUtils;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 13276
 * @Description TODO
 * @Date 2024/3/31 15:20
 * @Version 1.0
 * @since 1.8
 */
@Service
public class ActModelServiceImpl implements ActModelService {

    @Resource
    private RepositoryService repositoryService;

    /**
     * 分页查询
     * @param actModel 参数
     * @return 数据表
     */
    @Override
    public TableDataInfo queryPage(ActModelVo actModel){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();

        if(StringUtils.isNotBlank(actModel.getName())){
            modelQuery.modelNameLike("%"+actModel.getName()+"%");
        }

        if(StringUtils.isNotBlank(actModel.getKey())){
            modelQuery.modelKey(actModel.getKey());
        }

        if(StringUtils.isNotBlank(actModel.getCategory())){
            modelQuery.modelCategory(actModel.getCategory());
        }

        List<Model> models = modelQuery.listPage(pageDomain.getFirstResult(), pageDomain.getMaxResult());
        long count = modelQuery.count();

        TableDataInfo tableDataInfo = new TableDataInfo();
        tableDataInfo.setRows(models);
        tableDataInfo.setTotal(count);
        return tableDataInfo;
    }

    /**
     * 根据Id查询模型
     * @param id 模型Id
     * @return 模型
     */
    @Override
    public ActModelVo getById(Serializable id) {
        Model model = repositoryService.getModel(id.toString());
        return new ActModelVo(model);
    }


    /**
     * 创建模型
     * @param actModel 参数
     * @return 模型id
     */
    @Override
    public String saveModel(ActModelVo actModel){
        try {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            RepositoryService repositoryService = processEngine.getRepositoryService();

            Model modelData = null;
            if(StringUtils.isNotBlank(actModel.getId())){
                modelData = repositoryService.getModel(actModel.getId());
            }else{
                modelData = repositoryService.newModel();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, actModel.getName());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, actModel.getDescription());
            // 设置模型信息
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(actModel.getName());
            modelData.setKey(actModel.getKey());
            modelData.setCategory(actModel.getCategory());
            //保存模型
            repositoryService.saveModel(modelData);



            if (modelData.getId() != null) {
                ObjectNode editorNode = objectMapper.createObjectNode();
                editorNode.put("id", "canvas");
                editorNode.put("resourceId", "canvas");
                ObjectNode stencilSetNode = objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
                editorNode.put("stencilset", stencilSetNode);
                repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

                return modelData.getId();
            }

            // response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 根据Ids删除模型
     * @param ids 模型Ids
     * @return 模型
     */
    @Override
    @Transactional
    public boolean deleteById(Serializable... ids) {
        if(ids.length > 0){
            for (int i = 0; i < ids.length; i++) {
                repositoryService.deleteModel(ids[i].toString());
            }
        }
        return true;
    }


}
