package com.weaver.shiyuan.metabase.linkMarker;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.weaver.shiyuan.metabase.DataConvert;
import com.weaver.shiyuan.metabase.Global;
import com.weaver.shiyuan.metabase.data.DataQuery;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReferenceLineMarker implements LineMarkerProvider {


  @Override
  public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
    try {
      // 仅作用于函数
      if (!(element instanceof PsiMethod)) return null;
      PsiMethod psiMethod = (PsiMethod) element;
      if (psiMethod.getNameIdentifier() == null) {
        return null;
      }
      return new LineMarkerInfo<>(psiMethod.getNameIdentifier(), element.getTextRange(), IconLoader.getIcon("/images/icon.svg"), psiElement -> "查看当前方法调用链",
        new GutterIconNavigationHandler<PsiElement>() {
          @Override
          public void navigate(MouseEvent e, PsiElement elt) {
            Global.reset();
            JSONArray rows = DataQuery.query(psiMethod, psiMethod.getName());
            for (Object row : rows) {
              JSONArray object = (JSONArray) row;
              Global.NOTE_LIST.add(object);
              Global.DEFAULT_TABLE.addRow(DataConvert.convert(object));
            }
            if (elt != null) {
              // 将项目对象，ToolWindow的id传入，获取控件对象
              ToolWindow toolWindow = ToolWindowManager.getInstance(elt.getProject()).getToolWindow("调用链关系");
              if (toolWindow != null) {
                // 无论当前状态为关闭/打开，进行强制打开ToolWindow
                toolWindow.show(() -> {});
              }
            }
          }
        }, GutterIconRenderer.Alignment.RIGHT, () -> null);
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

}
