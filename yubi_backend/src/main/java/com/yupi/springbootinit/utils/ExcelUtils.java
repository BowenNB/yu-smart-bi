package com.yupi.springbootinit.utils;

import cn.hutool.extra.mail.MailUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.qcloud.cos.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Excel 工具类
 */
@Slf4j
public class ExcelUtils {

    public static String excelToCsv(MultipartFile multipartFile) throws FileNotFoundException {
//        File file = null;
//        try{
//            file = ResourceUtils.getFile("classpath:网站数据.xlsx");
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }

        //读取数据
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("读取Excel失败", e);// 这里的e是什么意思？
        }
        //如果数据为空
        if(CollectionUtils.isEmpty(list)){
            return "文件数据为空";
        }
        //转换为csv
        StringBuilder csvbuilder = new StringBuilder();
//        //读取表头（第一行）
//        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap)list.get(0);
//        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty)
//                .collect(Collectors.toList());
//        stringbuilder.append(StringUtils.join(headerList)).append("\n");
//        //读取数据
//        for(int i=1;i<list.size();i++){
//            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
//            List<String> datalist = dataMap.values().stream().filter(ObjectUtils::isNotEmpty)
//                    .collect(Collectors.toList());
//            stringbuilder.append(StringUtils.join(datalist)).append("\n");
//        }
        // 正确拼接CSV
        for (Map<Integer, String> rowMap : list) {
            // 提取每行的 value 值，过滤空值，用逗号拼接
            String row = rowMap.values().stream()
                    .filter(ObjectUtils::isNotEmpty).collect(Collectors.joining(","));
            csvbuilder.append(row).append("\n");
        }
        return csvbuilder.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        excelToCsv(null);
    }

}
