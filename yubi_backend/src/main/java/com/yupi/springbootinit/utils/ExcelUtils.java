package com.yupi.springbootinit.utils;

import cn.hutool.extra.mail.MailUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.qcloud.cos.utils.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Excel 工具类
 */
public class ExcelUtils {

    public static String excelToCsv(MultipartFile multipartFile) throws FileNotFoundException {
        File file = null;
        try{
            file = ResourceUtils.getFile("classpath:网站数据.xlsx");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        //读取数据
         List<Map<Integer, String>> list = EasyExcel.read(file)
                 .excelType(ExcelTypeEnum.XLSX)
                 .sheet()
                 .headRowNumber(0)
                 .doReadSync();
        //如果数据为空
        if(CollectionUtils.isEmpty(list)){
            return "";
        }
        //转换为csv
        //读取表头（第一行）
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap<Integer, String>) list.get(0);
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty)
                .collect(Collectors.toList());
        System.out.println(StringUtils.join(String.valueOf(headerList),","));
        //读取数据
        for(int i=1;i<list.size();i++){
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap<Integer, String>) list.get(i);
            List<String> datalist = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            System.out.println(StringUtils.join(String.valueOf(datalist),","));
        }
        System.out.println(list);
        return "";
    }

    public static void main(String[] args) throws FileNotFoundException {
        excelToCsv(null);
    }
}
