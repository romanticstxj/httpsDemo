package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.HttpClientUtil;
import com.example.demo.util.SignUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author : JCccc
 * @CreateTime : 2019/8/2
 * @Description :
 **/

@RestController
@RequestMapping("/wxAuth")
public class WxLoginController {
    private  static  String APPID="xxxxxx";//填你自己的
    private  static  String APPSECRET="xxxxx";//填你自己的


    /**
     * 用于给微信验证token
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/checkToken")
    public String checkToken(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("校验token成功");
            return echostr;
        }else{
            System.out.println("校验token不成功");
            return  null;
        }
    }

    /**
     * 用于获取出回调地址  （引导用户调用此接口，成功后自动调取回调地址然后取出用户信息）
     * @param response
     * @throws IOException
     */
    @RequestMapping("/login")
    public void wxLogin(HttpServletResponse response) throws IOException {
        //请求获取code的回调地址
        //用线上环境的域名或者用内网穿透，不能用ip
        String callBack = "http://3xXXXXXXXi.natappfree.cc/wxAuth/callBack";//域名填你自己的

        //请求地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
                "?appid=" + APPID +
                "&redirect_uri=" + URLEncoder.encode(callBack) +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";

        System.out.println(url);
        //重定向
        response.sendRedirect(url);
    }


    /**
     * 回调方法
     * @param request
     * @param response
     * @throws IOException
     */
    //	回调方法
    @RequestMapping("/callBack")
    public String wxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        //获取access_token
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + APPID +
                "&secret=" + APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";

        String result = HttpClientUtil.doGet(url);

        System.out.println("请求获取access_token:" + result);
        //返回结果的json对象
        JSONObject resultObject = JSON.parseObject(result);

        //请求获取userInfo
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=" + resultObject.getString("access_token") +
                "&openid=" + resultObject.getString("openid") +
                "&lang=zh_CN";

        String resultInfo = HttpClientUtil.doGet(infoUrl);

        //此时已获取到userInfo，再根据业务进行处理
        System.out.println("请求获取userInfo:" + resultInfo);

        return  "hello!";
    }
}

