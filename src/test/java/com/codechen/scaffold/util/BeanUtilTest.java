package com.codechen.scaffold.util;

import com.codechen.scaffold.core.util.BeanUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author：Java陈序员
 * @date 2023-07-24 17:22
 * @description 对象拷贝工具类
 */
public class BeanUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilTest.class);

    @Test
    public void testCopyObject() {
        StudentVO studentVO = new StudentVO();
        studentVO.setId(1L);
        studentVO.setName("张三");
        studentVO.setAge(12);
        studentVO.setEmail("zhangsan@163.com");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("李四");
        BeanUtil.copy(studentVO, studentDTO);

        LOGGER.info("studentDTO:{}", studentDTO);
    }

    @Test
    public void testCopyClass() {
        StudentVO studentVO = new StudentVO();
        studentVO.setId(1L);
        studentVO.setName("张三");
        studentVO.setAge(12);
        studentVO.setEmail("zhangsan@163.com");

        StudentDTO studentDTO = BeanUtil.copy(studentVO, StudentDTO.class);

        LOGGER.info("studentDTO:{}", studentDTO);
    }

    @Test
    public void testCopyList() {
        StudentVO studentVO1 = new StudentVO();
        studentVO1.setId(1L);
        studentVO1.setName("张三");
        studentVO1.setAge(12);
        studentVO1.setEmail("zhangsan@163.com");

        StudentVO studentVO2 = new StudentVO();
        studentVO2.setId(2L);
        studentVO2.setName("李四");
        studentVO2.setAge(18);
        studentVO2.setEmail("lisi@163.com");

        ArrayList<StudentVO> sourceList = new ArrayList<>();
        sourceList.add(studentVO1);
        sourceList.add(studentVO2);

        List<StudentDTO> targetList = BeanUtil.copyList(sourceList, StudentDTO.class);

        LOGGER.info("targetList:{}", targetList);
    }

    @Test
    public void testCopySet() {
        StudentVO studentVO1 = new StudentVO();
        studentVO1.setId(1L);
        studentVO1.setName("张三");
        studentVO1.setAge(12);
        studentVO1.setEmail("zhangsan@163.com");

        StudentVO studentVO2 = new StudentVO();
        studentVO2.setId(2L);
        studentVO2.setName("李四");
        studentVO2.setAge(18);
        studentVO2.setEmail("lisi@163.com");

        HashSet<StudentVO> sourceSet = new HashSet<>();
        sourceSet.add(studentVO1);
        sourceSet.add(studentVO2);

        Set<StudentDTO> targetSet = BeanUtil.copySet(sourceSet, StudentDTO.class);

        LOGGER.info("targetSet:{}", targetSet);
    }

    @Test
    public void testCopyArray() {
        StudentVO studentVO1 = new StudentVO();
        studentVO1.setId(1L);
        studentVO1.setName("张三");
        studentVO1.setAge(12);
        studentVO1.setEmail("zhangsan@163.com");

        StudentVO studentVO2 = new StudentVO();
        studentVO2.setId(2L);
        studentVO2.setName("李四");
        studentVO2.setAge(18);
        studentVO2.setEmail("lisi@163.com");

        StudentVO[] studentVOS = new StudentVO[2];
        studentVOS[0] = studentVO1;
        studentVOS[1] = studentVO2;

        StudentDTO[] targetArray = BeanUtil.copyArray(studentVOS, StudentDTO.class);

        LOGGER.info("targetArray:{}", Arrays.asList(targetArray));
    }

    @Test
    public void testCopyIgnoreNullAndBlank() {
        StudentVO studentVO = new StudentVO();
        studentVO.setId(1L);
        studentVO.setName("张三");
        studentVO.setAge(null);
        studentVO.setEmail("");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("李四");
        studentDTO.setAge(18);
        studentDTO.setEmail("lisi@qq.com");

        BeanUtil.copyIgnoreNullAndBlank(studentVO, studentDTO);

        LOGGER.info("studentDTO:{}", studentDTO);
    }

    @Test
    public void testCopyIgnoreNull() {
        StudentVO studentVO = new StudentVO();
        studentVO.setId(1L);
        studentVO.setName("张三");
        studentVO.setAge(null);
        studentVO.setEmail("");

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("李四");
        studentDTO.setAge(18);
        studentDTO.setEmail("lisi@qq.com");

        BeanUtil.copyIgnoreNull(studentVO, studentDTO);

        LOGGER.info("studentDTO:{}", studentDTO);
    }
}

@Data
class StudentVO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}

@Data
class StudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String address;
    private String className;
}

