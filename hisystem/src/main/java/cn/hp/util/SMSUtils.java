package cn.hp.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;

import java.util.HashMap;
import java.util.Map;

public class SMSUtils {
    private static String [] SMS_TEMPLATE ={"SMS_224345874","SMS_224347900"};

    /**
     * 使用AK&SK初始化账号Client
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("AK")
                // 您的AccessKey Secret
                .setAccessKeySecret("AKS");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    /**
     * @param map   手机号和需要填写内容map
     * @param smsIndex  短信模板的下标 0验证码 1 预约成功通知
     * @throws Exception
     */
    public static SendSmsResponseBody smsCode(Map<String,Object> map,int smsIndex) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = SMSUtils.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(map.get("phone").toString())//接收人手机号
                .setSignName("点鲜")//签名
                .setTemplateCode(SMS_TEMPLATE[smsIndex])//短信模板id
                .setTemplateParam(JSONObject.toJSONString(map));//短信模板中的参数以json字符串的格式传递
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse response = client.sendSms(sendSmsRequest);
        SendSmsResponseBody body = response.getBody();
        System.out.println(body.getBizId());//BizId
        System.out.println(body.getCode());//状态码
        System.out.println(body.getMessage());//说明
        System.out.println(body.getRequestId());//
        return body;
    }
    
    //调用的案例
    public static void main(String[] args_) throws Exception {
        Map<String,Object> map = new HashMap<>();
//        map.put("code",ValidateCodeUtils.generateValidateCode4String(4));
        com.aliyun.dysmsapi20170525.Client client = SMSUtils.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("15039939677")
                .setSignName("点鲜")
                .setTemplateCode("SMS_102660011")
                .setTemplateParam(JSONObject.toJSONString(map));
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse response = client.sendSms(sendSmsRequest);
        SendSmsResponseBody body = response.getBody();
        System.out.println(body.getBizId());
        System.out.println(body.getCode());
        System.out.println(body.getMessage());
        System.out.println(body.getRequestId());
        System.out.println(body.toString());
    }
}