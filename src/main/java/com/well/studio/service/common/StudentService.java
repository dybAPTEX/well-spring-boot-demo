package com.well.studio.service.common;

import com.well.studio.util.PageList;
import com.well.studio.so.StudentSo;
import com.well.studio.vo.StudentVo;

/**
 * 系统生成Service类
 * @author admin
 * @date 2020/07/30
 */
public interface StudentService {

    /**
     * 新增
     * @param student 新增对象
     * @return 新增对象的id
     */
    Long create(StudentVo student);

    /**
     * 删除
     * @param id 删除对象的id
     */
    void delete(Long id);

    /**
     * 修改
     * @param student 修改对象
     */
    void update(StudentVo student);

    /**
     * 单个查询
     * @param id 查询对象id
     * @return 查询结果
     */
    StudentVo find(Long id);

    /**
     * 按条件查询
     * @param studentSo 查询条件
     * @return 查询结果
     */
    PageList<StudentVo> listPagination(StudentSo studentSo);



}

