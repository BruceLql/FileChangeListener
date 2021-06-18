package com.bruce.fileChangeListener.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static sun.jvm.hotspot.debugger.win32.coff.DebugVC50X86RegisterEnums.TAG;

/**
 * @author 李启岚(起冉)
 */
@Service
@Slf4j
public class FileChangeService {

    public static void copyFileToSomeWhere(String filePath, String filePath2) {
        // copy files using java.nio.FileChannel
        File source = new File(filePath);
        File dest = new File(filePath2);
        Long start = System.nanoTime();
        try {
            copyFileUsingFileChannels(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long end = System.nanoTime();
        System.out.println("Time taken by FileChannels Copy = " + (end - start)/1000000 + "ms");

    }

    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public static void copyNio(String source, String dest) throws IOException {
        FileChannel input = null;
        FileChannel output = null;

        try {
            input = new FileInputStream(new File(source)).getChannel();
            output = new FileOutputStream(new File(dest)).getChannel();
            output.transferFrom(input, 0, input.size());
        } catch (Exception e) {
            log.info(TAG + "copyNio", "error occur while copy", e);
        } finally {
            input.close();
            output.close();
        }
    }

}
