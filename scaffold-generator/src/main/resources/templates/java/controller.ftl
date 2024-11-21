package ${packageName}.controller;

import ${packageName}.entity.${tableName}${entitySuffix};
import ${packageName}.service.I${tableName}Service;
<#if commonResultPackage??>
import ${commonResultPackage};
<#else>
import org.springframework.http.ResponseEntity;
</#if>
<#if enableSwagger == true>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author：${author}
* @date：${date}
* @description：${tableComment} Controller
*/
@RestController
@RequestMapping("/${tableName}")
<#if enableSwagger == true>
@Api(value = "${tableComment}", tags = "${tableComment}接口")
</#if>
public class ${tableName}Controller {

    @Autowired
    private I${tableName}Service ${tableName?uncap_first}Service;

    @GetMapping("/{id}")
    <#if enableSwagger == true>
    @ApiOperation(value = "根据ID获取${tableComment}")
    </#if>
    <#if commonResult??>
    public ${commonResult}<${tableName}${entitySuffix}> getById(@PathVariable Long id) {
    <#else>
    public ResponseEntity<${tableName}${entitySuffix}> getById(@PathVariable Long id) {
    </#if>
        ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix} = ${tableName?uncap_first}Service.getById(id);
        <#if commonResult??>
        return ${commonResult}.success(${tableName?uncap_first}${entitySuffix});
        <#else>
        return ResponseEntity.ok(${tableName?uncap_first}${entitySuffix});
        </#if>
    }

    @GetMapping("/")
    <#if enableSwagger == true>
    @ApiOperation(value = "获取全部${tableComment}")
    </#if>
    <#if commonResult??>
    public ${commonResult}<List<${tableName}${entitySuffix}>> getList() {
    <#else>
    public ResponseEntity<List<${tableName}${entitySuffix}>> getList() {
    </#if>
        List<${tableName}${entitySuffix}> list = ${tableName?uncap_first}Service.list();
        <#if commonResult??>
        return ${commonResult}.success(list);
        <#else>
        return ResponseEntity.ok(list);
        </#if>
    }

    @PostMapping("/")
    <#if enableSwagger == true>
    @ApiOperation(value = "新增${tableComment}")
    </#if>
    <#if commonResult??>
    public ${commonResult}<Boolean> add(@RequestBody ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix}) {
    <#else>
    public ResponseEntity<Boolean> add(@RequestBody ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix}) {
    </#if>
        boolean save = ${tableName?uncap_first}Service.save(${tableName?uncap_first}${entitySuffix});
        <#if commonResult??>
        return ${commonResult}.success(save);
        <#else>
        return ResponseEntity.ok(save);
        </#if>
    }

    @PutMapping("/")
    <#if enableSwagger == true>
    @ApiOperation(value = "修改${tableComment}")
    </#if>
    <#if commonResult??>
    public ${commonResult}<Boolean> update(@RequestBody ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix}) {
    <#else>
    public ResponseEntity<Boolean> update(@RequestBody ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix}) {
    </#if>
        ${tableName}${entitySuffix} ${tableName?uncap_first}${entitySuffix}ById = ${tableName?uncap_first}Service.getById(${tableName?uncap_first}${entitySuffix}.getId());
        if (${tableName?uncap_first}${entitySuffix}ById == null) {
            return Result.fail("数据不存在！");
        }

        boolean update = ${tableName?uncap_first}Service.updateById(${tableName?uncap_first}${entitySuffix});
        <#if commonResult??>
        return ${commonResult}.success(update);
        <#else>
        return ResponseEntity.ok(update);
        </#if>
    }

    @DeleteMapping("/{id}")
    <#if enableSwagger == true>
    @ApiOperation(value = "删除${tableComment}")
    </#if>
    <#if commonResult??>
    public ${commonResult}<Boolean> delete(@PathVariable Long id) {
    <#else>
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    </#if>
        boolean remove = ${tableName?uncap_first}Service.removeById(id);
        <#if commonResult??>
        return ${commonResult}.success(remove);
        <#else>
        return ResponseEntity.ok(remove);
        </#if>
    }
}