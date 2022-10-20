package com.ideayp.leaf.onlyoffice.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.ideayp.leaf.onlyoffice.dto.FileConfigDTO;
import com.ideayp.leaf.onlyoffice.properties.OnlyProperties;
import com.ideayp.leaf.onlyoffice.service.OnlyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author 95765
 */
@RestController
@Slf4j
public class EditorController {

    private final OnlyProperties onlyProperties;

    private final OnlyServiceImpl onlyServiceImpl;

    @Autowired
    public EditorController(OnlyServiceImpl onlyServiceImpl, OnlyProperties onlyProperties) {
        this.onlyServiceImpl = onlyServiceImpl;
        this.onlyProperties = onlyProperties;
    }

    /**
     * 打开编辑
     */
    @RequestMapping("/onlyOffice/edit")
    public ModelAndView edit(@RequestParam("url") String url, Model model) {
        log.info("only office url:" + url);
        FileConfigDTO fileDTO = onlyServiceImpl.openEditConfig(url);
        if (StrUtil.isNotBlank(onlyProperties.getKey())) {
            log.info("only office set key: {}", onlyProperties.getKey());

            fileDTO.getDocument().setKey(onlyProperties.getKey());

            Map<String, Object> raw = JSONUtil.parseObj(fileDTO).getRaw();
            String token = JWTUtil.createToken(raw, onlyProperties.getKey().getBytes());
            log.info("only office token:" + token);

            fileDTO.setToken(token);
        }

        String s = JSONUtil.toJsonStr(fileDTO);
        log.info("only office config:" + s);

        model.addAllAttributes(JSONUtil.parseObj(fileDTO));
        return new ModelAndView("onlyOffice");
    }

    /**
     * 获取 only office 需要的配置信息
     *
     * @param url 文件地址
     * @return 结果
     */
    @RequestMapping("/onlyOffice/editConfig")
    public String editConfig(@RequestParam("url") String url) {
        FileConfigDTO fileDTO = onlyServiceImpl.openEditConfig(url);
        return JSONObject.toJSONString(fileDTO);
    }

    /**
     * 文档编辑服务使用JavaScript API通知callbackUrl，向文档存储服务通知文档编辑的状态。
     * 文档编辑服务使用具有正文中的信息的POST请求。
     * https://api.onlyoffice.com/editors/callback
     * <p>
     * 当我们关闭编辑窗口后，十秒钟左右only office会将它存储的我们的编辑后的文件
     */
    @RequestMapping("/onlyOffice/save")
    public void saveFile(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            // 获取传输的json数据
            Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
            String body = scanner.hasNext() ? scanner.next() : "";
            JSONObject jsonObject = JSONObject.parseObject(body);
            onlyServiceImpl.handlerStatus(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * status = 1，我们给onlyOffice的服务返回{"error":"0"}的信息。
         * 这样onlyOffice会认为回调接口是没问题的，这样就可以在线编辑文档了，否则的话会弹出窗口说明
         */
        if (Objects.nonNull(writer)) {
            writer.write("{\"error\":0}");
        }
    }
}