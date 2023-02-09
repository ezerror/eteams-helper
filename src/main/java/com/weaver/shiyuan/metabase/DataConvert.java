package com.weaver.shiyuan.metabase;

import cn.hutool.json.JSONArray;

public class DataConvert {
    public static String[] convert(JSONArray noteData) {
        String[] row = new String[3];
        row[0] = noteData.get(1,String.class);
        row[1] = noteData.get(2,String.class);
        row[2] = noteData.get(3,String.class);
        return row;
    }
}
