<%@ page import="org.spa.utils.PDFUtil" %>
<%@ page import="org.spa.utils.RandomUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URISyntaxException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.apache.commons.lang3.RandomStringUtils" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

Test Pdf

<%!


    // wkhtmltopdf 在系统中的路径
    private static final String EXECUTER = "wkhtmltopdf";
    private static final String JSESSIONID_KEY = "JSESSIONID";

    private static String PDF_PATH = "";

    static {
        try {
            PDF_PATH = new File(PDFUtil.class.getClassLoader().getResource("").toURI()).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + "/temp/pdf/";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        new File(PDF_PATH).mkdirs();
    }

    public static String getUrlRoot(HttpServletRequest request) {
        String urlRoot;
        if (request.getServerPort() == 80) {
            urlRoot = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
        } else {
            urlRoot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        }
        return urlRoot;
    }

    /**
     *
     * @param url
     *            相對路徑
     * @param request
     * @param parameter
     *            請求參數
     * @return
     * @throws IOException
     */
    public static File convert(String url, HttpServletRequest request, Map<String, Object> parameter) throws IOException {
        String destPath = PDF_PATH + RandomStringUtils.randomAlphanumeric(40) + ".pdf";

        StringBuilder cmd = new StringBuilder();
        cmd.append(EXECUTER);

        // content type
        // :
        //cmd.append(" --custom-header Host \"uat.ssl2.senseoftouch.com.hk\" ");
        //cmd.append(" --custom-header Content-Type \"application/x-www-form-urlencoded; charset=UTF-8\" ");
        //cmd.append(" --custom-header Accept \"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\" ");
        //cmd.append(" --custom-header Cookie \"SnapABugHistory=1#; _ga=GA1.3.1965997975.1478852309; JSESSIONID=0CAA58BE2735ADE1AB195B84B64A591D\" ");
        // JSESSIONID 处理
        cmd.append(" --load-error-handling ignore ");
        cmd.append(" --no-images ");
        cmd.append(" --cookie ");
        cmd.append(JSESSIONID_KEY).append(" \"").append(request.getSession().getId()).append("\" ");
        // 其他cookie处理
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (JSESSIONID_KEY.equals(cookie.getName())) {
                continue;
            }
            cmd.append(" --cookie ");
            cmd.append(cookie.getName()).append(" \"").append(cookie.getValue()).append("\" ");
        }

        // 处理参数
        if (parameter != null) {
            for (Map.Entry<String, Object> entry : parameter.entrySet()) {
                cmd.append(" --post ").append(entry.getKey()).append(" '").append(entry.getValue()).append("' ");
            }
        }

        cmd.append(" ");
        cmd.append(getUrlRoot(request));
        cmd.append(url); // 相对url路径
        cmd.append(" ");
        cmd.append(destPath); // 輸出的pdf文件路徑

        System.out.println("convert command:" + cmd.toString());
        InputStream inputStream = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd.toString());
            //
            inputStream = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String tmp;
            while ((tmp = br.readLine()) != null) {
                System.out.println(tmp);
            }

            InputStreamReader isr = new InputStreamReader(p.getErrorStream(), "utf-8");
            BufferedReader errors = new BufferedReader(isr);
            String line;
            System.out.println("ERROR:");
            while ((line = errors.readLine()) != null) {
                System.out.println(line); //输出内容
            }
            p.getOutputStream().close();
            int exitValue = p.waitFor();

            System.out.println("exitValue:" + exitValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return new File(destPath);
    }

    public static File convert(String url, HttpServletRequest request) throws IOException {
        return convert(url, request, null);
    }
%>
<%

    // String downloadFileName = RandomUtil.generateRandomNumberWithDate("IOTest1-") + ".pdf";
    //File downloadFile = PDFUtil.convert("/test/testHelloWorld1", request);
    //ServletUtil.download(downloadFile, downloadFileName, response);

    /*System.out.println("start11:");
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1L);
    File downloadFile = PDFUtil.convert("/test/helloWorld2", request, map);
    System.out.println(downloadFile);*/


   System.out.println("start21:");

    Map<String, Object> filter = new HashMap<>();
    filter.put("id", 1L);
    //convert("/test/helloWorld1", request, filter);
    convert("/inventory/toPurchaseOrderTemplate", request, filter);

/*
    System.out.println("start3:");
    PDFUtil.convert("/test/purchaseOrderTemplate", request, filter);

    System.out.println("start4:");
    PDFUtil.convert("/inventory/helloWorld2", request, filter);*/
%>

</body>
</html>