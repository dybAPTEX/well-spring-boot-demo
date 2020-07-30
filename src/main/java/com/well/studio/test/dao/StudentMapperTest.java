package com.well.studio.test.dao;

import com.well.studio.mapper.StudentMapper;
import com.well.studio.mapper.base.Mapper;
import com.well.studio.model.Student;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统生成Mapper测试类
 * @author admin
 * @date 2020/07/30
 */
public class StudentMapperTest  {

    @Autowired
    private StudentMapper studentMapper;

    protected Mapper<Student> getDAO() {
        return studentMapper;
    }
}
