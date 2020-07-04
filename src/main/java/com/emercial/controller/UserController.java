package com.emercial.controller;

import com.alibaba.druid.util.StringUtils;
import com.emercial.controller.viewobject.UserVO;
import com.emercial.error.BusinessException;
import com.emercial.error.EmBusinessError;
import com.emercial.response.CommonReturnType;
import com.emercial.service.UserService;
import com.emercial.service.dto.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders = "*", maxAge = 3600)
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone") String telephone,
                                  @RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(org.apache.commons.lang3.StringUtils.isEmpty(telephone)
                || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //login service, test if user login is valid
        UserModel userModel = userService.validateLogin(telephone, this.EncodeByMd5(password));

        //login to session
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        System.out.println("user login successfully");

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name ="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")String gender,
                                     @RequestParam(name="password")String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
    //validate telphone match the otpcode
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "otp validation error");
        }

        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(Integer.parseInt(gender));
        userModel.setTelnumber(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncryptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


    //user otp acquire
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //1.generate
        Random random = new Random();
        int randomInt =random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //2.match opt code and telphone, use http
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        System.out.println("telphone=" + telphone+"&otpCode =" + otpCode);

        return CommonReturnType.create(null);

    }
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException{
    //call service to get user object to UI according to id
        UserModel userModel = userService.getUserById(id);

        //uer id not exist exception
        if (userModel == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);

        }
        UserVO userVO = convertFromUserModel(userModel);
        return CommonReturnType.create(userVO);
    }

    @RequestMapping(value = "/account", method = {RequestMethod.POST}, consumes = CONTENT_TYPE_FORMED)
    @ResponseBody
    public CommonReturnType account() throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "please login");
        }

        //get user info
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        UserVO userVO = convertFromUserModel(userModel);
        return CommonReturnType.create(userVO);
    }


    private UserVO convertFromUserModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }



}
