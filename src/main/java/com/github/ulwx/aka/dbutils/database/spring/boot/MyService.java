package com.github.ulwx.aka.dbutils.database.spring.boot;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MyService {
    private AddressDao addressDao;

    public MyService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMdb(){

        List<Address> list2= addressDao.getListMd();
        //声明了Propagation.NESTED事务
        addressDao.updateMd();
       //MyService方法的内部调用会使用被调用方法上声明的事务失效，所以需要用下面方式调用
        ((MyService) AopContext.currentProxy()).updateMdbOther();
        //MyService方法的内部调用会使用被调用方法上声明的事务失效，下面的内部调用还是在updateMdb()方法的事务里。
        //updateMdbOther();

    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateMdbOther(){
        System.out.println("call updateMdbOther");
    }

}
