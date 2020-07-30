package com.well.studio.service.additional;

import com.well.studio.dao.StudentDao;
import com.well.studio.service.helper.StudentServiceHelper;
import com.well.studio.service.AbstractTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统生成AdditionalServiceImpl
 * @author admin
 * @date 2020/07/30
 */
@Service
public class StudentAdditionalServiceImpl extends AbstractTransactionalService implements StudentAdditionalService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentServiceHelper studentServiceHelper;



}
