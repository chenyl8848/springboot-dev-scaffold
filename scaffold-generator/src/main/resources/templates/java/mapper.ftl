package ${packageName}.${mapperSuffix?lower_case};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.entity.${tableName}${entitySuffix};
import org.apache.ibatis.annotations.Mapper;

/**
* @author：${author}
* @date：${date}
* @description：${tableComment} ${mapperSuffix}
*/
@Mapper
public interface ${tableName}${mapperSuffix} extends BaseMapper<${tableName}${entitySuffix}> {

}