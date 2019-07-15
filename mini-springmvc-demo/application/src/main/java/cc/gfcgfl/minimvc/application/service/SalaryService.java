package cc.gfcgfl.minimvc.application.service;


import cc.gfcgfl.minimvc.annotation.MyBean;

/**
 * @ClassName: SalaryService
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/21/19 11:34 AM
 **/
@MyBean
public class SalaryService {
    public Integer getSalaryById(Integer id){
        return id * 100;
    }
}
