package com.blocksignature.utils;

import com.example.utils.Lib;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.ApplicationHome;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class TestUtils {
    public static Lib LIB;
    private static final boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
    private static final String WIN_DLL = "test.dll";
    private static final String LINUX_SO = "test.so";
    private static final String UTF8 = "utf-8";

    static {
        ApplicationHome applicationHome = new ApplicationHome(com.blocksignature.utils.TestUtils.class);
        String rootPath = applicationHome.getSource().getParentFile().toString();
        try {
            if (isWin) {
                String configFilePath = rootPath + File.separator + WIN_DLL;
                File configFile = new File(configFilePath);
                if (!configFile.exists()) {
                    InputStream in = com.blocksignature.utils.TestUtils.class.getClassLoader().getResourceAsStream(WIN_DLL);
                    FileUtils.copyInputStreamToFile(Objects.requireNonNull(in), configFile);
                }
                //WIN10 环境DLL加载
                NativeLibrary.addSearchPath(WIN_DLL, rootPath);
                LIB = Native.load(WIN_DLL, Lib.class);
            } else {
                //LINUX 环境SO加载
                String configFilePath = rootPath + File.separator + LINUX_SO;
                File configFile = new File(configFilePath);
                if (!configFile.exists()) {
                    InputStream in = com.blocksignature.utils.TestUtils.class.getClassLoader().getResourceAsStream(LINUX_SO);
                    FileUtils.copyInputStreamToFile(Objects.requireNonNull(in), configFile);
                }
                NativeLibrary.addSearchPath(LINUX_SO, rootPath);
                LIB = Native.load(LINUX_SO, Lib.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param publicKeyBase64
     * @return
     */
    public static String functionTest(String publicKeyBase64) {

        return LIB.FunctionTest(toGoString(publicKeyBase64));
    }


    private static Lib.GoString.ByValue toGoString(String str) {
        Lib.GoString.ByValue str3 = new Lib.GoString.ByValue();
        str3.p = str;
        str3.n = str3.p.length();
        return str3;
    }

    public static void main(String[] args) {
        System.out.println(functionTest("World!"));
    }
}
