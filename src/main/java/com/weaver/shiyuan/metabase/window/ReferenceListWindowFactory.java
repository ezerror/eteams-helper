package com.weaver.shiyuan.metabase.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class ReferenceListWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ReferenceListWindow window = new ReferenceListWindow(project, toolWindow );
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Content content = factory.createContent(window.getContentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
