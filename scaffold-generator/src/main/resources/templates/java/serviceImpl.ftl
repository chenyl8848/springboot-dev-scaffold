package ${packageName}.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.entity.${tableName}${entitySuffix};
import ${packageName}.${mapperSuffix?lower_case}.${tableName}${mapperSuffix};
import ${packageName}.service.I${tableName}Service;
import org.springframework.stereotype.Service;

/**
* @author：${author}
* @date：${date}
* @description：${tableComment} ServiceImpl
*/
@Service
public class ${tableName}ServiceImpl extends ServiceImpl<${tableName}${mapperSuffix}, ${tableName}${entitySuffix}> implements I${tableName}Service {
}
