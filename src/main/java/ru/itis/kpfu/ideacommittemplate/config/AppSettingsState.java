package ru.itis.kpfu.ideacommittemplate.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.itis.kpfu.ideacommittemplate.models.Template;

@State(
        name = "AppSettingsState",
        storages = @Storage("AppSettingsState.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public LinkedHashMap<String, Template> templates = new LinkedHashMap<>();
    public LinkedHashMap<String, String> arguments = new LinkedHashMap<>();

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Override
    public @Nullable AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
