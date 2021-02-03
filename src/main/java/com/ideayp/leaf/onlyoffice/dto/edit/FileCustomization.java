package com.ideayp.leaf.onlyoffice.dto.edit;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义设置
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/11 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Data
public class FileCustomization implements Serializable {

    /**
     * 定义是否启用或禁用“自动保存”菜单选项。
     * 如果设置为false，则只能选择Strict协同编辑模式，因为Fast在没有自动保存的情况下无法工作。
     * 请注意，如果您在菜单中更改此选项，它将保存到您的浏览器localStorage。默认值为true
     */
    @JSONField(name = "autosave")
    private Boolean autoSave;

    /**
     * 定义是否显示或隐藏了“聊天”菜单按钮；请注意，如果隐藏了“聊天”按钮，相应的聊天功能也将被禁用。默认值为true
     */
    private Boolean chat;

    /**
     * 如果用户定义CAN编辑只读他的评论。默认值是FALSE。
     * 默认false
     */
    private Boolean commentAuthorOnly;

    /**
     * 定义是否显示或隐藏了Comments菜单按钮；请注意，如果隐藏了Comments按钮，则相应的注释功能将仅可用于查看，注释的添加和编辑将不可用
     * 默认true
     */
    private Boolean comments;

    /**
     * 顶部工具栏是否隐藏
     * 默认false
     */
    private Boolean compactToolbar;

    @JSONField(name = "customer")
    private FileCustomer customer;

    /**
     * boolean or object
     * <p>
     *     url - the absolute URL to the website address which will be opened when clicking the Feedback & Support menu button,
     *         type: string,
     *         example: "https://example.com";
     * <p>
     *     visible - show or hide the Feedback & Support menu button,
     *         type: boolean,
     *         example: true.
     */
    private Object feedback;


    /**
     * 是否强制保存
     * 在文档编辑服务中保存文档时（例如，单击Save按钮等），将强制保存文件的请求添加到回调处理程序。
     * 默认值为false。
     */
    @JSONField(name = "forcesave")
    private Boolean forceSave;


    /**
     * Defines settings for the Go to Documents menu button and upper right corner button. The object has the following parameters:
     *     blank - open the website in the new browser tab/window (if the value is set to true) or the current tab (if the value is set to false) when the Go to Documents button is clicked. The default value is true,
     *         type: boolean,
     *         example: true;
     *     text - the text which will be displayed for the Go to Documents menu button and upper right corner button (i.e. instead of Go to Documents),
     *         type: string,
     *         example: "Go to Documents";
     *     url - the absolute URL to the website address which will be opened when clicking the Go to Documents menu button,
     *         type: string,
     *         example: "https://example.com".
     */
    @JSONField(name = "goback")
    private Object goBack;

    /**
     * 定义帮助按钮是否展示
     * 默认 true
     */
    private Boolean help;

    /**
     * Changes the image file at the top left corner of the Editor header. The recommended image height is 20 pixels. The object has the following parameters:
     *     image - path to the image file used to show in common work mode (i.e. in view and edit modes for all editors). The image must have the following size: 172x40,
     *         type: string,
     *         example: "https://example.com/logo.png";
     *     imageEmbedded - path to the image file used to show in the embedded mode (see the config section to find out how to define the embedded document type). The image must have the following size: 248x40,
     *         type: string,
     *         example: "https://example.com/logo_em.png";
     *     url - the absolute URL which will be used when someone clicks the logo image (can be used to go to your web site, etc.),
     *         type: string,
     *         example: "https://example.com".
     */
    private Object logo;


    /**
     * 定义在编辑器加载时是否自动显示或隐藏评论更改面板。
     * 默认false
     */
    private Boolean showReviewChanges;

    /**
     * 扩大或缩放
     * 默认100
     */
    private Integer zoom;
}
