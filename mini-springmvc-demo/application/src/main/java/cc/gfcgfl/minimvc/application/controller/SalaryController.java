package cc.gfcgfl.minimvc.application.controller;

import cc.gfcgfl.minimvc.annotation.MyAutoWired;
import cc.gfcgfl.minimvc.application.pojo.User;
import cc.gfcgfl.minimvc.application.service.SalaryService;
import cc.gfcgfl.minimvc.mvc_annotation.MyController;
import cc.gfcgfl.minimvc.mvc_annotation.MyRequestMapping;
import cc.gfcgfl.minimvc.mvc_annotation.MyRequestParam;

/**
 * @ClassName: SalaryController
 * @Description:
 * @CreatedBy: fcguo
 * @CreatedAt: 6/19/19 7:32 PM
 **/
@MyController
public class SalaryController {
    @MyAutoWired
    private SalaryService salaryService;

    @MyRequestMapping("/salary")
    public Integer getSalaryByNameAndDegree(@MyRequestParam("name") String name, @MyRequestParam("degree") String degree) {
        if ("xxx".equals(name) && "master".equals(degree))
            return 1;
        else
            return 0;
    }

    @MyRequestMapping("/salary/modify")
    public User modifySalary(String name, String salary) {
        return new User("xxx", "1000");
    }
    @MyRequestMapping("/salary/x")
    public User getUserByName(@MyRequestParam("username") String name, String salary){
        return new User(name, salaryService.getSalaryById(1).toString());
    }
}
