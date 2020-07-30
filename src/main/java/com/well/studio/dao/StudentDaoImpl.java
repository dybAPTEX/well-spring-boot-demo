package com.well.studio.dao;

import com.well.studio.dao.base.BaseNoHistoryDaoImpl;
import com.well.studio.mapper.base.Mapper;
import com.well.studio.model.Student;
import com.well.studio.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统生成Dao实现类
 * @author admin
 * @date 2017/11/17
 */
@Repository
public class StudentDaoImpl extends BaseNoHistoryDaoImpl<Student> implements StudentDao {

    @Autowired
    private StudentMapper mapper;

    @Override
    protected Mapper<Student> getMapper() {
        return mapper;
    }

}
