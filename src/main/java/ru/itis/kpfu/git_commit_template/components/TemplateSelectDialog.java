package ru.itis.kpfu.git_commit_template.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import ru.itis.kpfu.git_commit_template.config.AppSettingsState;
import ru.itis.kpfu.git_commit_template.models.Template;
import ru.itis.kpfu.git_commit_template.util.GitHelper;

public class TemplateSelectDialog extends DialogWrapper {

    private JPanel centerPanel;
    private JBList<Template> namesList;
    private final CommitMessageI commitMessageI;
    private final AppSettingsState settingsState;
    private JCheckBox addBranchNameCheckBox;

    @Getter
    private final Project project;


    public TemplateSelectDialog(
            @Nullable Project project,
            @Nullable CommitMessageI commitMessageI) {
        super(project);
        this.project = project;
        this.commitMessageI = commitMessageI;
        this.settingsState = AppSettingsState.getInstance();
        this.settingsState.refreshSystemArgs();

        super.setTitle("Saved Templates");
        super.setOKButtonText("Select");
        super.init();
    }




    @Override
    public JComponent createCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        namesList = new JBList<>(new CollectionListModel<>(
                settingsState.templates.values().stream().toList()));

        namesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        namesList.setMinimumSize(new Dimension(200, 480));
        namesList.setSelectedIndex(0);
        JBScrollPane namesPane = new JBScrollPane(namesList);
        namesPane.setPreferredSize(new Dimension(200, 480));

        centerPanel.add(namesPane);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setLayout(new BorderLayout());
        addBranchNameCheckBox = new JCheckBox("Add branch name");
        optionsPanel.add(addBranchNameCheckBox);

        centerPanel.add(optionsPanel);

        return centerPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return namesList;
    }

    @Override
    protected void doOKAction() {
        if (commitMessageI != null && project != null) {
            String commitMessage = namesList.getModel().getElementAt(namesList.getSelectedIndex()).fillContent(settingsState);

            if (addBranchNameCheckBox.isSelected()) {
                String branchName = GitHelper.getBranchName(project);
                if (branchName != null && !branchName.isEmpty()) {
                    commitMessage = String.format("#%s %s", branchName, commitMessage);
                }
            }

            commitMessageI.setCommitMessage(commitMessage);
        }
        super.close(OK_EXIT_CODE);
    }

    @Override
    public void doCancelAction() {
        super.doCancelAction();
    }
}
