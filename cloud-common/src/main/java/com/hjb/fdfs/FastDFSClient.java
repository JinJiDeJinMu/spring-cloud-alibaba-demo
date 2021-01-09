package com.hjb.fdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FastDFSClient {

    private static StorageClient storageClient;

    @PostConstruct
    public void init(){
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static StorageClient client(){
        if(storageClient == null){
            TrackerClient client = new TrackerClient();
            try {
                TrackerServer connection = client.getConnection();
                storageClient = new StorageClient(connection, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storageClient;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String[] upload(FastDFSFile file){
        //获取文件作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair(file.getAuthor());

        String[] uploadResult = null;

        try {
            uploadResult = FastDFSClient.client().upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return uploadResult;
    }

    /**
     * 获取文件信息
     * @param groupName 组名
     * @param remoteFileName 文件存储完整名
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName){
        FileInfo file_info = null;
        try {
            file_info = FastDFSClient.client().get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return file_info;
    }

    /**
     * 文件下载
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static InputStream downFile(String groupName, String remoteFileName){
        byte[] bytes = new byte[0];
        try {
            bytes = FastDFSClient.client().download_file(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 文件删除
     * @param groupName
     * @param remoteFileName
     */
    public static void deleteFile(String groupName, String remoteFileName){
        try {
            FastDFSClient.client().delete_file(groupName,remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
