package com.well.studio.test.dao;

import com.well.studio.dao.base.BaseDao;
import com.well.studio.dao.StudentDao;
import com.well.studio.model.Student;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统生成dao测试类
 * @author admin
 * @date 2020/07/30
 */
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    protected BaseDao<Student> getDAO() {
        return studentDao;
    }
}
