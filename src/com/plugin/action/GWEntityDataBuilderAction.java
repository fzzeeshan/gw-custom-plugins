package com.plugin.action;

import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.plugin.code.generator.EntityToGWDataBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Fiaz Zeeshan
 * Date: 08/1/22
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class GWEntityDataBuilderAction extends AnAction {
    public void actionPerformed(AnActionEvent event) {
        // TODO: insert action logic here
        Project p = event.getProject();

        PsiFile psiFile = event.getData(DataKeys.PSI_FILE);

        String _fileType = psiFile.getFileType().getName();
        String _language = psiFile.getLanguage().getDisplayName();
        VirtualFile vFile = event.getData(DataKeys.VIRTUAL_FILE);
        String _fileExtension = vFile.getExtension();
        String _fileName = vFile.getName();
        String _filePath = vFile.getPath();
        String _canPath = vFile.getCanonicalPath();
        String _url = vFile.getUrl();

        event.getPresentation().setEnabled(psiFile.getLanguage().isKindOf(JavaLanguage.INSTANCE));
        EntityToGWDataBuilder _instance = new EntityToGWDataBuilder();
        Editor editor = event.getData(DataKeys.EDITOR);
        System.out.println(editor.getDocument().getText());
        Messages.showMessageDialog(p,  "_fileExtension: " +_fileExtension,"GW-Entity Plugin", Messages.getInformationIcon());
        Messages.showMessageDialog(p,  "_fileName: " +_fileName,"GW-Entity Plugin", Messages.getInformationIcon());
        Messages.showMessageDialog(p,  "_filePath: " +_filePath,"GW-Entity Plugin", Messages.getInformationIcon());
        Messages.showMessageDialog(p,  "_canPath: " +_canPath,"GW-Entity Plugin", Messages.getInformationIcon());
        Messages.showMessageDialog(p,  "_url: " +_url,"GW-Entity Plugin", Messages.getInformationIcon());

        /*boolean _isDataBuilderGenerated = _instance.processEntityFile(_filePath);

        if (_isDataBuilderGenerated) {
            Messages.showMessageDialog(p, "Entity DataBuilder file generated successfully!!! Please check output.json file for details. ", "GW-Entity Plugin", Messages.getInformationIcon());
        } else {
            Messages.showMessageDialog(p, "Issues in generating data builder class. Please check output.json file on the same path.", "GW-Entity Plugin", Messages.getErrorIcon());
        } */

    }

    @Override
    public void update(AnActionEvent event) {

        VirtualFile vFile = event.getData(DataKeys.VIRTUAL_FILE);
        String _fileExtension = vFile.getExtension();
        String _fileName = vFile.getName();

        if ("eti".equalsIgnoreCase(_fileExtension) ||
                "etx".equalsIgnoreCase(_fileExtension)) {
            event.getPresentation().setEnabledAndVisible(true);
        } else {
            event.getPresentation().setEnabledAndVisible(false);
        }

    }

}
