package network.hiziki.checkdomain.tools;

import network.hiziki.checkdomain.Main;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class YamlConfiguration {
    private final File yamlFile;
    private final Yaml yaml;
    private LinkedHashMap<String, Object> data;

    public YamlConfiguration(File file) {
        yamlFile = file;
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
        load();
    }

    // YAMLファイルの読み込み
    private void load() {
        if (!yamlFile.exists()) {
            try {
                if (yamlFile.createNewFile()) {
                    Main.getPlugin().getLogger().info("ファイルを作成しました。");
                }
            } catch (IOException ex) {
                Main.getPlugin().getLogger().warning("ファイルの作成中にエラーが発生しました。\n" + ex);
            }
        }
        try (FileReader reader = new FileReader(yamlFile)) {
            data = yaml.load(reader);
            if (data == null) {
                data = new LinkedHashMap<>();
            }
        } catch (IOException ex) {
            Main.getPlugin().getLogger().warning("ファイルの読み込み中にエラーが発生しました。\n" + ex);
        }
    }

    // 指定したキーのデータを文字列として取得
    public String getString(String key) {
        Object value = data.get(key);
        return (value != null) ? value.toString() : null;
    }

    // 指定したキーのデータを文字列のListとして取得
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        return (List<String>) data.get(key);
    }
}