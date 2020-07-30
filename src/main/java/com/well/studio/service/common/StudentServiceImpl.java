package com.well.studio.service.common;

import com.well.studio.dao.StudentDao;
import com.well.studio.model.Student;
import com.well.studio.so.StudentSo;
import com.well.studio.vo.StudentVo;
import com.well.studio.util.PageList;
import com.well.studio.service.AbstractTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * 系统生成Service实现类
 * @author admin
 * @date 2020/07/30
 */
@Service
public class StudentServiceImpl extends AbstractTransactionalService implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**
     * 新增
     *
     * @param student 新增对象
     * @return 新增对象的id
     */
    @Override
    public Long create(StudentVo student) {
        checkBeforeCreate(student);
        Student studentBo = dozer.convert(student, Student.class);
        return studentDao.insert(studentBo);
    }

    private void checkBeforeCreate(StudentVo student) {
        checkRequired(student);
        checkDuplicate4Create(student);
    }


    /**
     * 删除
     *
     * @param id 删除对象的id
     */
    @Override
    public void delete(Long id) {
        studentDao.delete(id);
    }

    /**
     * 修改
     *
     * @param student 修改对象
     */
    @Override
    public void update(StudentVo student) {
        checkBeforeUpdate(student);
        Student studentBo = dozer.convert(student, Student.class);
        studentDao.update(studentBo);
    }

    private void checkBeforeUpdate(StudentVo student) {
        checkRequired(student);
        checkDuplicate4Update(student);
    }

    /**
     * 单个查询
     *
     * @param id 查询对象id
     * @return 查询结果
     */
    @Override
    public StudentVo find(Long id) {
        return studentDao.findVo(id);
    }

    /**
     * 按条件查询
     *
     * @param studentSo 查询条件
     * @return 查询结果
     */
    @Override
    public PageList<StudentVo> listPagination(StudentSo studentSo) {
        List<StudentVo> list = studentDao.listPaginationVoBySo(studentSo);
        int count = studentDao.countBySo(studentSo);
        return new PageList<>(list, count);
    }

    /**
     * 检查必填字段
     */
    private void checkRequired(StudentVo student) {

    }

    /**
     * 检查字段唯一性（创建）
     */
    private void checkDuplicate4Create(StudentVo student) {

    }



    /**
     * 检查字段唯一性（更新）
     */
    private void checkDuplicate4Update(StudentVo student) {

    }





}
