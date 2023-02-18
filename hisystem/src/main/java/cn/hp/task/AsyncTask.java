package cn.hp.task;

import cn.hp.common.constant.Constants;
import cn.hp.entity.LoginInfor;
import cn.hp.entity.User;
import cn.hp.service.ILoginInforService;
import cn.hp.service.IUserService;
import cn.hp.util.DateUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 异步任务
 * 1.saveLoginInfor 保存登录信息 异步线程
 * 2.myTaskAsyncPool 邮件发送 异步线程
 */
@Component
public class AsyncTask {
    @Value("${mail.smtp.mailName}")
    String mailName;   //  发件人账号
    @Value("${mail.smtp.mailCode}")
    String mailCode;  //  发件人授权码
    @Value("${mail.smtp.host}")
    String mailhost;
    @Value("${mail.smtp.auth}")
    String mailauth;
    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginInforService loginInforService;

    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    /**
     * 保存登录信息 异步线程
     *
     * @param ip
     * @param broswer
     * @return
     */
    @Async("myTaskAsyncPool")
    public void saveLoginInfor(String ip, String broswer, String email) {
        User user = userService.login(email);
        if (!StringUtils.isEmpty(user)) {
            LoginInfor loginInfor = new LoginInfor();
            loginInfor.setUserId(user.getId() + "").setLoginIp(ip).setLoginBroswer(broswer);
            LoginInfor userInfo = loginInforService.findByLoginIpAndLoginBroswerAndUserId(loginInfor);
            try {
                if (userInfo == null) {
                    userInfo = new LoginInfor();
                    userInfo.setLoginIp(ip);
                    userInfo.setLoginBroswer(broswer);
                    userInfo.setUserId(user.getId() + "");
                    userInfo.setDescription(email);
                    RestTemplate restTemplate = new RestTemplate();
                    //获取地理位置
                    String url = Constants.URL.ADDRESS_URL;
                    String resultAddress = restTemplate.getForObject(url, String.class);
                    logger.info("登录获取地址，url={},返回={}", url, resultAddress);
                    if (!StringUtils.isEmpty(resultAddress)) {
                        resultAddress = resultAddress.replaceAll("[\r\n]", "");
                        userInfo.setLoginAddress(resultAddress);
                    }
                    loginInforService.insertLoginInfor(userInfo);
                } else {
                    userInfo.setUserId(user.getId() + "");
                    RestTemplate restTemplate = new RestTemplate();
                    //获取地理位置
                    String url = Constants.URL.ADDRESS_URL;
                    String resultAddress = restTemplate.getForObject(url, String.class);
                    logger.info("登录获取地址，url={},返回={}", url, resultAddress);
                    if (!StringUtils.isEmpty(resultAddress)) {
                        resultAddress = resultAddress.replaceAll("[\r\n]", "");
                        userInfo.setLoginAddress(resultAddress);
                    }
                    userInfo.setCreateDatetime(DateUtil.getCurrentDateToString());
                    loginInforService.updateLoginInfor(userInfo);
                }
            } catch (Exception e) {
                logger.error("userId={},保存登录记录失败！msg={}", user.getId(), e.getStackTrace());
            }
        }
    }


    /**
     * 邮件发送 异步线程
     *
     * @return
     */
    @Async("myTaskAsyncPool")
    public void sendMail(String username, String email, String uuid) {
        Properties properties = System.getProperties();      // 获取系统属性
        properties.setProperty("mail.smtp.host", mailhost);     // 设置邮件服务器
        properties.setProperty("mail.smtp.auth", mailauth);  // 打开认证
        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailName, mailCode); // 发件人邮箱账号、授权码
                }
            });
            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 3.设置发件人
            message.setFrom(new InternetAddress(mailName));
            // 4.设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 5.设置邮件主题
            message.setSubject("账号激活");
            // 6.设置邮件内容
            String content = "<html><head></head><body><h1>这是一封账号激活邮件" +
                    ",半个小时内有效！激活请点击以下按钮，如果不是本人操作，请勿点击</h1><h3>" +
                    "<a href='http://127.0.0.1:8080/activationUserName?code=" + uuid + "'>" +
                    "激活"
                    + "</a></h3></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 7.发送邮件
            Transport.send(message);   // 阻塞方法
            System.out.println("邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
