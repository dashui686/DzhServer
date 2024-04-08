package com.dashui.controller;

import com.dashui.domain.vo.ActModelVo;
import com.dashui.service.ActModelService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zr
 * @date 2022/6/13 16:36
 */
@RestController
@RequestMapping("/actModel")
public class ActModelController {


    @Autowired
    private ActModelService actModelService;


    /**
     * 分页查询
     * @param actModel 参数
     * @return 数据表
     */
    @GetMapping("/list")
    public TableDataInfo listModel(ActModelVo actModel){
        return actModelService.queryPage(actModel);
    }

    /**
     * 根据Id查询
     * @param id 参数
     * @return 数据表
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") String id){
        return AjaxResult.success(actModelService.getById(id));
    }

    /**
     * 创建模型
     * @param actModel 参数
     * @return 模型id
     */
    @PostMapping
    public AjaxResult createModel(@RequestBody ActModelVo actModel) {
       return AjaxResult.success(actModelService.saveModel(actModel));
    }

    /**
     * 创建模型
     * @param actModel 参数
     * @return 模型id
     */
    @PutMapping
    public AjaxResult updateModel(@RequestBody ActModelVo actModel) {
       return AjaxResult.success(actModelService.saveModel(actModel));
    }

    /**
     * 删除模型
     * @param ids 模型id
     * @return ajax
     */
    @DeleteMapping("/{ids}")
    public AjaxResult updateModel(@PathVariable String[] ids) {
       return AjaxResult.success(actModelService.deleteById(ids));
    }

}
