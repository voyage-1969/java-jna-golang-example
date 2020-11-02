package com.example.utils;

import com.sun.jna.Library;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public interface Lib extends Library {
    /**
     * CGO 字符串类型特殊处理
     */
    public class GoString extends Structure {
        public static class ByValue extends GoString implements Structure.ByValue {
        }

        public String p;
        public long n;

        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"p", "n"});
        }
    }

    /**
     * JNA 方法
     *
     * @param pub
     * @return
     */
    String FunctionTest(GoString.ByValue pub);
}
