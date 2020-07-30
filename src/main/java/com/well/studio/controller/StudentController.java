package com.well.studio.controller;

import com.well.studio.vo.base.PackVo;
import com.well.studio.so.StudentSo;
import com.well.studio.vo.StudentVo;
import com.well.studio.service.additional.StudentAdditionalService;
import com.well.studio.service.common.StudentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统生成Controller
 * @author admin
 * @date 2020/07/30
 */
@RestController
@RequestMapping(StudentController.VIEW_PREFIX)
public class StudentController extends AjaxBaseController {

    public static final String VIEW_PREFIX = "/rest/student";

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentAdditionalService studentAdditionalService;


    @RequestMapping(value = CREATE, method = RequestMethod.POST)
    public PackVo create(@RequestBody StudentVo student) {
        studentService.create(student);
        return new PackVo();
    }

    @RequestMapping(value = DELETE, method = RequestMethod.GET)
    public PackVo delete(Long id) {
        studentService.delete(id);
        return new PackVo();
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.POST)
    public PackVo update(@RequestBody StudentVo student) {
        studentService.update(student);
        return new PackVo();
    }

    @RequestMapping(value = FIND, method = RequestMethod.GET)
    @HystrixCommand(commandKey = "Student")
    public PackVo<StudentVo> find(Long id) {
        PackVo<StudentVo> packVo = new PackVo<>();
        StudentVo studentVo = studentService.find(id);
        packVo.setVo(studentVo);
        return packVo;
    }

    @RequestMapping(value = LIST_PAGINATION, method = RequestMethod.POST)
    @HystrixCommand(commandKey = "Student")
    public PackVo<StudentVo> listPagination(@RequestBody StudentSo studentSo) {
        PackVo<StudentVo> packVo = new PackVo<>();
        packVo.setPageList(studentService.listPagination(studentSo));
        return packVo;
    }



}
