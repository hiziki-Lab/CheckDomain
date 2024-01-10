package network.hiziki.checkdomain;

import net.md_5.bungee.api.plugin.Plugin;
import network.hiziki.checkdomain.event.EventManager;
import network.hiziki.checkdomain.tools.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public final class Main extends Plugin {
    private static Plugin plugin;
    private static List<String> domainList;
    private static List<String> kickMessage;
    private static String unmatchedDomainAction;

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        // config読み込み
        initializeConfig();

        // 正常なconfigか確認
        if (isNormalConfig()) {

            new EventManager(this);
            getLogger().info("プラグインは正常に起動しました。");
        } else {
            getLogger().severe("configの読み込み中にエラーが発生しました。");
            getLogger().severe("configの「unmatchedDomainAction」が正しく設定されているか確認してください");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (isNormalConfig()) {
            getLogger().info("プラグインは正常に停止しました。");
        }
    }

    private void initializeConfig() {
        // プラグインデータフォルダが存在しない場合は作成
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                getLogger().info("プラグインフォルダを作成しました。");
            }
        }

        File file = new File(getDataFolder(), "config.yml");

        // configファイルが存在しない場合は新規作成
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
                getLogger().severe("configファイルを作成しました。");
            } catch (IOException ex) {
                getLogger().severe("configファイル読み込み中にエラーが発生しました。" + ex);
            }
        }

        YamlConfiguration config = new YamlConfiguration(file);

        domainList = config.getStringList("domainList");
        kickMessage = config.getStringList("kickMessage");
        unmatchedDomainAction = config.getString("unmatchedDomainAction");
    }

    private boolean isNormalConfig() {
        return unmatchedDomainAction.equalsIgnoreCase("allow") || unmatchedDomainAction.equalsIgnoreCase("allow-logging") ||
                unmatchedDomainAction.equalsIgnoreCase("kick") || unmatchedDomainAction.equalsIgnoreCase("kick-logging");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static List<String> getDomainList() {
        return domainList;
    }

    public static List<String> getKickMessage() {
        return kickMessage;
    }

    public static String getUnmatchedDomainAction() {
        return unmatchedDomainAction;
    }
}
