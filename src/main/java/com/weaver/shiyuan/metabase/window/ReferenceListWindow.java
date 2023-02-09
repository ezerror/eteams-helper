package com.weaver.shiyuan.metabase.window;

import com.weaver.shiyuan.metabase.Global;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class ReferenceListWindow {
  private JTable noteContentList;
  private JPanel contentPanel;

  private void init() {
    noteContentList.setModel(Global.DEFAULT_TABLE);
    noteContentList.setEnabled(true);
  }


  public ReferenceListWindow(Project project, ToolWindow toolWindow) {
    init();
  }

  public JPanel getContentPanel() {
    return contentPanel;
  }
}
