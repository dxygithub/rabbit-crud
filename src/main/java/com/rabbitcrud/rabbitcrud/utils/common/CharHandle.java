package com.rabbitcrud.rabbitcrud.utils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理工具
 * @author dxy 2019-5-12 12:30
 */
public class CharHandle {

    /**
     * 去除字符串里面的特殊字符
     */
    public static String delBrackets(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 往每个字符之间添加其它字符或者换行符
     * @param str 字符
     * @param mark 要插入的字符
     */
    public static String insertStr(String str,String mark){
        String reg = "(?!^[\u4e00-\u9fff])([\u4e00-\u9fff])";
        str = str.replaceAll (reg, mark+"$1");
        return str;
    }

    /**
     * 往字符之间添加其它字符或者换行符
     * @param str 字符
     * @param mark 要插入的字符
     * @param num 间隔几个字符
     */
    public static String insertStr(String str,String mark,int num){
        char[] ch =  str.toCharArray();
        str = "";
        for (int i = 0; i < ch.length; i++) {
            if(i +1 < ch.length){
                if((i+1)%num  == 0){
                    str += ch[i];
                    str += mark;
                }else{
                    str += ch[i];
                }
            }else{
                str += ch[i];
            }
        }
        return str;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是长整型
     */
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是日期
     * @param value
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isDate(String value){
        boolean isD = false;
        try {
            new Date(value);
            isD = true;
        } catch (IllegalArgumentException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(value);
                isD = true;
            } catch (ParseException e1) {}
        }
        return isD;
    }

    /**
     * 判断是否为数字形式
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }


    /**
     * 去掉--注释
     */
    public static String removeComment(String cont){
        Pattern pa = Pattern.compile("(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/");
        String presult = pa.matcher(cont).replaceAll("$1");
        return presult;
    }

    /**
     * 简单的直接去掉星号斜杠注释段
     * @param code
     * @return
     */
    public static String removeComments(String code)
    {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < code.length(); i++)
        {
            if(cnt == 0)
            {
                if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                {
                    cnt++;
                    i++;
                    continue;
                }
            }
            else
            {
                if(i+1 < code.length() && code.charAt(i) == '*' && code.charAt(i+1) == '/')
                {
                    cnt--;
                    i++;
                    continue;
                }
                if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                {
                    cnt++;
                    i++;
                    continue;
                }
            }
            if(cnt == 0)
            {
                sb.append(code.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 处理带双引号的注释
     * @param code
     * @return
     */
    public static String removeCommentsWithQuote(String code)
    {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        boolean quoteFlag = false;
        for (int i = 0; i < code.length(); i++)
        {
            //如果没有开始双引号范围
            if(!quoteFlag)
            {
                //如果发现双引号开始
                if(code.charAt(i) == '\"')
                {
                    sb.append(code.charAt(i));
                    quoteFlag = true;
                    continue;
                }
                //不在双引号范围内
                else
                {
                    //处理/**/注释段
                    if(cnt == 0)
                    {
                        if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                        {
                            cnt++;
                            i++;
                            continue;
                        }
                    }
                    else
                    {
                        if(i+1 < code.length() && code.charAt(i) == '*' && code.charAt(i+1) == '/')
                        {
                            cnt--;
                            i++;
                            continue;
                        }
                        if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                        {
                            cnt++;
                            i++;
                            continue;
                        }
                    }
                    //如果没有发现/**/段或者已经处理完了嵌套的/**/
                    if(cnt == 0)
                    {
                        sb.append(code.charAt(i));
                        continue;
                    }
                }
            }
            //处理双引号段
            else
            {
                //如果发现双引号结束(非转移形式的双引号)
                if(code.charAt(i) == '\"' && code.charAt(i-1) != '\\')
                {
                    sb.append(code.charAt(i));
                    quoteFlag = false;
                }
                //双引号开始了但是还没有结束
                else
                {
                    sb.append(code.charAt(i));
                }
            }

        }
        return sb.toString();
    }

    /**
     * 处理双引号和双斜杠注释
     * @param code
     * @return
     */
    public static String removeCommentsWithQuoteAndDoubleEscape(String code)
    {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        boolean quoteFlag = false;
        for (int i = 0; i < code.length(); i++)
        {
            //如果没有开始双引号范围
            if(!quoteFlag)
            {
                //如果发现双引号开始
                if(code.charAt(i) == '\"')
                {
                    sb.append(code.charAt(i));
                    quoteFlag = true;
                    continue;
                }
                //处理双斜杠注释
                else if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '/')
                {
                    while(code.charAt(i) != '\n')
                    {
                        i++;
                    }
                    continue;
                }
                //不在双引号范围内
                else
                {
                    //处理/**/注释段
                    if(cnt == 0)
                    {
                        if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                        {
                            cnt++;
                            i++;
                            continue;
                        }
                    }
                    else
                    {
                        //发现"*/"结尾
                        if(i+1 < code.length() && code.charAt(i) == '*' && code.charAt(i+1) == '/')
                        {
                            cnt--;
                            i++;
                            continue;
                        }
                        //发现"/*"嵌套
                        if(i+1 < code.length() && code.charAt(i) == '/' && code.charAt(i+1) == '*')
                        {
                            cnt++;
                            i++;
                            continue;
                        }
                    }
                    //如果没有发现/**/注释段或者已经处理完了嵌套的/**/注释段
                    if(cnt == 0)
                    {
                        sb.append(code.charAt(i));
                        continue;
                    }
                }
            }
            //处理双引号注释段
            else
            {
                //如果发现双引号结束(非转义形式的双引号)
                if(code.charAt(i) == '\"' && code.charAt(i-1) != '\\')
                {
                    sb.append(code.charAt(i));
                    quoteFlag = false;
                }
                //双引号开始了但是还没有结束
                else
                {
                    sb.append(code.charAt(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 把clob转换成字符
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws SQLException, IOException {
        String reString = "";
        java.io.Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

}
