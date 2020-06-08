package com.example.one.cache;

import com.example.one.Dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOs = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("电脑数码");
        program.setTags(Arrays.asList("手机配件", "数码摄影", "存储设备", "电脑配件", "游戏设备", "智能设备", "软件游戏"));
        tagDTOs.add(program);

        TagDTO frameWork = new TagDTO();
        frameWork.setCategoryName("运动器材");
        frameWork.setTags(Arrays.asList("运动服饰", "户外服饰", "户外设备", "体育器材"));
//        "", "django", "express"
        tagDTOs.add(frameWork);

        TagDTO service = new TagDTO();
        service.setCategoryName("书籍");
        service.setTags(Arrays.asList("计算机类", "通信类", "文学类",  "科普类","机械类", "考研类", "考级类","技能培训类"));
        tagDTOs.add(service);

        TagDTO dataBase = new TagDTO();
        dataBase.setCategoryName("服装装饰");
        dataBase.setTags(Arrays.asList("衣服", "鞋袜", "裤子", "鞋子", "装饰品", "裙子", "mongodb"));
        tagDTOs.add(dataBase);

        TagDTO tools = new TagDTO();
        tools.setCategoryName("交通工具");
        tools.setTags(Arrays.asList("自行车", "摩托", "电动车", "代步车", "滑板"));
        tagDTOs.add(tools);
        return tagDTOs;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, " ");
        List<TagDTO> tagDTOs = get();
        List<String> tagList = tagDTOs.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        return Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(" "));
    }
}
