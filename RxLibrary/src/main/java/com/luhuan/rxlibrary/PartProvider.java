package com.luhuan.rxlibrary;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PartProvider {

    /**
     * 将要上传的file转换成part
     * @param key 跟服务器约定好的上传参数
     * @param file  需要上传的文件
     */
    public static MultipartBody.Part getPart(String key,File file){
        RequestBody requestBody=RequestBody.create(MultipartBody.FORM,file);
        return MultipartBody.Part.createFormData(key,file.getName(),requestBody);
    }
}
