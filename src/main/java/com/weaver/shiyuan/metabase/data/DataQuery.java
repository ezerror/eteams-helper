package com.weaver.shiyuan.metabase.data;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * @author ：shiyuan
 * @date ：Created in 2023/2/10 0:06
 * @description：
 * @version:
 */
public class DataQuery {

  public static TimedCache<Object, Object> cache = CacheUtil.newTimedCache(10000);

  public static JSONArray query(PsiMethod psiMethod, String methodName) {
    try {
      PsiJavaFile javaFile = (PsiJavaFile) psiMethod.getContainingFile();
      String packageName = javaFile.getPackageName();
      String outClassName = FilenameUtils.getBaseName(javaFile.getName());
      String qualifiedClassName = packageName + "." + outClassName;
      HashMap<String, Object> paramMap = new HashMap<>();
      paramMap.put("username", "weaver2022@163.com");
      paramMap.put("password", "fdSCO6caJGTp0v");
      query("http://10.10.27.205:3000/api/session", JSONUtil.toJsonStr(paramMap));
      String methodParam = StringUtils.isBlank(methodName) ? "" :
        ",{\"type\":\"category\",\"value\":\"METHOD_NAME\",\"target\":[\"variable\",[\"template-tag\",\"invoked_name\"]],\"id\":\"25df07e3-9850-ae9e-a0f5-76fe56fcb6a2\"}".replace("METHOD_NAME", methodName);
      String param2 = "{\"ignore_cache\":true,\"collection_preview\":false,\"parameters\":[{\"type\":\"category\",\"value\":\"CLASS_NAME\",\"target\":[\"variable\",[\"template-tag\",\"invoked_class\"]],\"id\":\"5113bf91-1d19-ce9a-5050-b86e3d2e4b44\"}METHOD_SUP]}";
      param2 = param2.replace("CLASS_NAME", qualifiedClassName).replace("METHOD_SUP", methodParam);
      String data = query("http://10.10.27.205:3000/api/card/131/query", param2, qualifiedClassName + methodName);
      return  (JSONArray) JSONUtil.parse(data).getByPath("data.rows");
    }catch (Exception e){
      e.printStackTrace();
    }
    return new JSONArray();
  }


  public static String query(String url, String param,String cacheKey) {
    try {
      if (cache.containsKey(cacheKey)) {
        return (String) cache.get(cacheKey);
      }
      String result = query(url, param);
      cache.put(cacheKey, result);
      return result;
    }catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }

  public static String query(String url, String param) {
    try {
      return HttpUtil.post(url, param);
    }catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }
}
