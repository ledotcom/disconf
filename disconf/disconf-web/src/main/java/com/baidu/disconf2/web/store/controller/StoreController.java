package com.baidu.disconf2.web.store.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf2.core.common.constants.Constants;
import com.baidu.disconf2.core.common.json.ValueVo;
import com.baidu.disconf2.web.store.form.ConfForm;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.exception.DocumentNotFoundException;
import com.baidu.ub.common.utils.FileUtils;

/**
 * 
 * STORE API需要特殊处理一下, 返回要是完整的可读性强的JSON
 * 
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/store")
public class StoreController extends BaseController {

    protected static final Logger LOG = LoggerFactory
            .getLogger(StoreController.class);

    public static String mfsPath = "D:\\";

    /**
     * 
     * 获取配置文件
     * 
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> getFile(ConfForm confForm) {

        return downloadDspBill(confForm.getKey());

    }

    /**
     * 获取配置项 Item
     * 
     * @param demoUserId
     * @return
     */
    @RequestMapping(value = "/item", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getItem() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue("88888");

        return confItemVo;
    }

    /**
     * 下载
     * 
     * @param fileName
     * @return
     */
    public HttpEntity<byte[]> downloadDspBill(String fileName) {

        HttpHeaders header = new HttpHeaders();
        byte[] res = readFileContent(fileName);
        if (res == null) {
            throw new DocumentNotFoundException(fileName);
        }

        if (fileName.toLowerCase().endsWith(".pdf")) {
            header.setContentType(new MediaType("application", "pdf"));

        } else if (fileName.toLowerCase().endsWith(".zip")) {
            header.setContentType(new MediaType("application",
                    "x-zip-compressed"));
        }

        String name = null;

        try {
            name = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        header.set("Content-Disposition", "attachment; filename=" + name);
        header.setContentLength(res.length);
        return new HttpEntity<byte[]>(res, header);
    }

    /**
     * 读取文件的内容到byte数组中
     * 
     * @param fileName
     * @return
     */
    private byte[] readFileContent(String fileName) {

        InputStream is = null;
        try {

            // 按GBK编码与UTF-8编码分别查找文件
            File f = new File(mfsPath, new String(fileName.getBytes("GBK")));
            f = f.isFile() ? f : new File(mfsPath, new String(
                    fileName.getBytes("UTF-8")));
            if (!f.isFile()) {
                LOG.error(fileName + " 文件不存在!");
                return null;
            }

            int length = (int) f.length();
            byte[] bytes = new byte[length];

            is = new FileInputStream(f);
            is.read(bytes);
            return bytes;

        } catch (Exception e) {

            LOG.error("error when download " + fileName, e);

        } finally {
            FileUtils.closeInputStream(is);
        }
        return null;
    }

}