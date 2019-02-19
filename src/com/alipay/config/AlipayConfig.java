package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092600601928";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC8ZuM1HtJLm8mOo9Ays+/UYjaCPios7lJ+1a7I3fWRSr3gcOZmaZFGBTWqEcLbEQHvEO0MWym/WP2PgL6KaOXHlLkhgOyXw2d57uYRZ25drFbIHb39aZDKTqLHldHV/oNdK0UYe1FbawMCjzKj+xOlnr0XviqIOznUbVptS4IC73IOg0d/eFHA8ZR107YWaUqB+FFSALUM63AK09JMZ18sDu6p0a94P7Z20lbd2pVikAInPloBmKGYGDtxfHVh5mxuUx3JqVw6iTRn105avSv6WumFAMfO199mYzqCmtHnRPf6mf8pr6+UvGyUIrhVXpYbU7zwU4RIpHAgLiE6WAazAgMBAAECggEBALe11/23ZTZm3Z6ia+TVApplfmN3/KY9aeHVuY6SqN8ZM5eEX5YjgI33jIGeuhPlPK6CIbP3vPih7PYegy3x5YW8pt1dmtbNqrwCUQNNiRf8cc7G+bj+VDqBfUcC908k07Fo/QJHVWjAK3o2ahnuPRU1eYuKWmzm25ghXZjPlSIH8C9/1w3BFYIYUi0pH6lEdRoyS1F03sSyXIc36E5fQMh6AEQ7tMBKS/+U52V94v9N/PQj1l6T2AFNd2uVMBLHaA85rSw0tJm0UcCRI6jd1ki3JTbihzbAdmlrvxSIJUbunRJ1qdckNPLDkwRWgX1WjN44bUiTV7XZEb9qGVq7RmkCgYEA3SzealzV9zJswqFtEDRT4e1GHez8geNZfByBcbk1hnftCvYGFlKQJ46bpkvU38VR/xEBD0gWBXty6SM/sis/U+7LdMZKauTq8LuEjSYf6h4xtepm7uEvEpq/8mv9eZ1QH/4J0fLsawXTJ6y2dOqIoBCqTktDlAnrlWxUhzYdl/8CgYEA2hD9fDJv5dWxcuUUnWvHkEDMSArQUmsjkW82G/O2NNNRzTZF6W8060XOEQcqxDgZpBr5rGl4dNdNDC5rcPOWVeD1qGu22JFrV8qc1vtY6D6pU/rXQhVU4PY8hecpzVippedoqi4hOAovITYcD5zxJxNKGszg/NS3tdFe1RKmsU0CgYBGCL8kzuCxtbXaq2LtjqRBFfDt6OzL9EGRmzUh3ZOlULQulFi5GMusuutubPSrJsAgFDJRtHHEqqJoUFELCcazvRmPkHpf/rymHbqLN1dDuuvivqZ5XbSaH4ZILQnGSJnmh3p0kIdsHBwQpA/iVaGNjblfsVVkuv9uAS+C4Q/d4QKBgQCpegbnnXNl1rzApNVuuzMwI4wgeXcKdwX/o8UT2EdFqu6qZIHVOH6SU4Ahr8ZHFaqn5echXh8l/6Z7zx/lewrd16ZUrNJAYlRguLFIS7P/W6PSBuOBEn8TD4xeLRhJ5ZyDueOYT521pgSW9Sy93aV2TwGK7qv1E4VrozrmskYnCQKBgQDAuIhD/Gl+x5mpnv7HaYUxiwVlDcyRWp7fXeuj0sYK47mlCOXZ2m1qgSson1huYm/T3/Vb2HV3GBeeAG8rqm8KnEUNZPGXGfhsI8hFJuUEad17FzM0PGifYfVYMNcc7+ONxurCrrASKMZPpmUlyk3cPUqfK+30zSL8+dd6vofyuA==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzmFn/7ynKd+sPb5q8QgE7oj1iXPwDoVP3Hl44XWlnayYn4Tz5of0JQ7Z5Amua11kN22xggUs1svVEiFu2TpAjuIozlw0cg0/FYz+ZDx9aokjLA8as2vajHc25HWN5n2nZaFtcME5xuqI1a3jf8ymHxkp64CVFx1ZYnxevBzW3ZVqLRilgWG5DrvAr5stz22cJ8FiuGBf44kWglKf0yQ1brV54zsYcgJaY8Son64mDitr6hAw7gBe9/Lu94k8sVMKubgPeuWtveySz3iAv6f93EKQ3BcnSXebfqdCFjpNN120zS66pKezfJovEDwNrlFXqnMLqOoErGUH/u3mQXlYZwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://127.0.0.1:8080/OnlineShop/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://127.0.0.1:8080/OnlineShop/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

