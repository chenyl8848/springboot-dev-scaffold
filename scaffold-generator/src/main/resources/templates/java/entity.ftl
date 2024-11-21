package ${packageName}.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<#if baseEntityPackage??>
import ${baseEntityPackage}
</#if>
<#if enableSwagger == true>
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if filedInfoList??>
    <#list filedInfoList as field>
        <#if '${field.isPk}' == '1'>
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
        </#if>
    </#list>
</#if>

<#if importList??>
    <#list importList as import>
${import}
    </#list>
</#if>

/**
* @author：${author}
* @date：${date}
* @description：${tableComment} ${entitySuffix}
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
<#if baseEntity??>
public class ${tableName}${entitySuffix} extends ${baseEntity} {
<#else>
public class ${tableName}${entitySuffix} {
</#if>

<#if filedInfoList??>
    <#list filedInfoList as field>
<#--    /** ${field.filedComment?? } */-->
    <#if '${field.filedComment}' ?? && '${field.filedComment}' != "">
    /** ${field.filedComment} */
    </#if>
    <#if '${field.isPk}' == '1'>
    @TableId(type = IdType.AUTO)
    </#if>
    <#if enableSwagger == true>
    @ApiModelProperty(value = "${field.filedComment}")
    </#if>
    private ${field.fieldType} ${field.fileName};

    </#list>
</#if>
}
