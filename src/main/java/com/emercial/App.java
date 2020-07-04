package com.emercial;

import com.emercial.dao.UserDao;
import com.emercial.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.emercial"})
@RestController

public class App 
{
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/")
    public String home(){
        UserEntity userEntity = userDao.selectByPrimaryKey(1);

        if (userEntity==null){
            return "user not found";
        }else{
            return "user exists";
        }
    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
}
