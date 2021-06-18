package com.bruce.fileChangeListener.listener;

import com.bruce.fileChangeListener.service.FileChangeService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Objects;

/**
 * 文件变化监听器
 * <p>
 * 在Apache的Commons-IO中有关于文件的监控功能的代码. 文件监控的原理如下：
 * 由文件监控类FileAlterationMonitor中的线程不停的扫描文件观察器FileAlterationObserver，
 * 如果有文件的变化，则根据相关的文件比较器，判断文件时新增，还是删除，还是更改。（默认为1000毫秒执行一次扫描）
 *
 * @author liqilan
 */
@Slf4j
@NoArgsConstructor
public class FileListener extends FileAlterationListenerAdaptor {

    @Value("${root.dir.a}")
    private String DIR_A = "/Users/liqilan/Downloads/";

    @Value("${root.dir.b}")
    private String DIR_B= "/Users/liqilan/home/test/";
    /**
     * 文件创建执行
     */
    @Override
    public void onFileCreate(File file) {
        log.info("[新建]:" + file.getAbsolutePath());
        // 同步到B文件夹
        synchronizedFile(file);

    }

    /**
     * 文件创建修改
     */
    @Override
    public void onFileChange(File file) {
        log.info("[修改]:" + file.getAbsolutePath());
        // 删除B文件夹到文件
        delFile(file);
        // 再将修改后的文件后同步到B文件夹
        synchronizedFile(file);
    }

    /**
     * 文件删除
     */
    @Override
    public void onFileDelete(File file) {
        log.info("[删除]:" + file.getAbsolutePath());
    }

    /**
     * 目录创建
     */
    @Override
    public void onDirectoryCreate(File directory) {
        log.info("[新建]:" + directory.getAbsolutePath());
    }

    /**
     * 目录修改
     */
    @Override
    public void onDirectoryChange(File directory) {
        log.info("[修改]:" + directory.getAbsolutePath());

    }

    /**
     * 目录删除
     */
    @Override
    public void onDirectoryDelete(File directory) {
        log.info("[删除]:" + directory.getAbsolutePath());
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStart(observer);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStop(observer);
    }

    /**
     * 同步图片
     * @param file
     */
    private void synchronizedFile(File file){
        String path = file.getAbsolutePath();
        String pathB = path.replace(DIR_A,DIR_B);
        log.info("================pathB: {}",pathB);
        FileChangeService.copyFileToSomeWhere(file.getAbsolutePath(),pathB);
    }

    /**
     * 同步前删除图片
     * @param file
     */
    private void delFile(File file){
        String path = file.getAbsolutePath();
        String pathB = path.replace(DIR_A,DIR_B);
        log.info("================pathB: {}",pathB);
        File fileNeedDel = FileUtils.getFile(pathB);
        if(Objects.nonNull(fileNeedDel)){
            fileNeedDel.delete();
        }
    }

}

