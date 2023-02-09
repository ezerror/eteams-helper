package com.weaver.shiyuan.metabase;

import cn.hutool.json.JSONArray;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

public class Global {
    public static List<JSONArray> NOTE_LIST = new LinkedList<cn.hutool.json.JSONArray>();

    public static String[] HEADERS = {"调用方项目", "调用方类",  "调用方方法名"};

    public static DefaultTableModel DEFAULT_TABLE = new DefaultTableModel(null, HEADERS);

    public static void reset() {
        NOTE_LIST.clear();
        DEFAULT_TABLE.setDataVector(null, HEADERS);
    }
}
