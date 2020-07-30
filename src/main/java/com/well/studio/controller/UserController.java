package com.well.studio.controller;

import com.well.studio.so.UserSo;
import com.well.studio.vo.UserVo;
import com.well.studio.service.additional.UserAdditionalService;
import com.well.studio.service.common.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.well.studio.vo.base.PackVo;
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
@RequestMapping(UserController.VIEW_PREFIX)
public class UserController extends AjaxBaseController {

    public static final String VIEW_PREFIX = "/rest/user";

    @Autowired
    private UserService userService;

    @Autowired
    private UserAdditionalService userAdditionalService;


    @RequestMapping(value = CREATE, method = RequestMethod.POST)
    public PackVo create(@RequestBody UserVo user) {
        userService.create(user);
        return new PackVo();
    }

    @RequestMapping(value = DELETE, method = RequestMethod.GET)
    public PackVo delete(Long id) {
        userService.delete(id);
        return new PackVo();
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.POST)
    public PackVo update(@RequestBody UserVo user) {
        userService.update(user);
        return new PackVo();
    }

    @RequestMapping(value = FIND, method = RequestMethod.GET)
    @HystrixCommand(commandKey = "User")
    public PackVo<UserVo> find(Long id) {
        PackVo<UserVo> packVo = new PackVo<>();
        UserVo userVo = userService.find(id);
        packVo.setVo(userVo);
        return packVo;
    }

    @RequestMapping(value = LIST_PAGINATION, method = RequestMethod.POST)
    @HystrixCommand(commandKey = "User")
    public PackVo<UserVo> listPagination(@RequestBody UserSo userSo) {
        PackVo<UserVo> packVo = new PackVo<>();
        packVo.setPageList(userService.listPagination(userSo));
        return packVo;
    }



}
