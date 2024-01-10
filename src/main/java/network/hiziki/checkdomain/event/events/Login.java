package network.hiziki.checkdomain.event.events;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import network.hiziki.checkdomain.Main;

import java.net.InetSocketAddress;

public class Login {
    public Login(LoginEvent e) {
        PendingConnection connection = e.getConnection();
        // プレイヤーのmcidを取得
        String name = connection.getName();
        // hostに関する情報を取得
        InetSocketAddress virtualHost = connection.getVirtualHost();
        String hostName = virtualHost.getHostName();

        // 許可されるドメインか確認
        if (!Main.getDomainList().contains(hostName)) {
            // ドメインが一致しない場合の処理を取得
            String action = Main.getUnmatchedDomainAction();

            // 「kick-logging」の場合はログに記録
            if (action.equals("kick-logging") || action.equalsIgnoreCase("allow-logging")) {
                Main.getPlugin().getLogger().info(name + "が許可されていないドメイン「" + hostName + "」で接続を行いました。");
            }

            // 許可する場合はreturn
            if (action.equals("allow") || action.equals("allow-logging")) return;

            // 接続を禁止
            e.setCancelled(true);
            String kickMessage = String.join("\n", Main.getKickMessage());
            e.setCancelReason(new TextComponent(kickMessage));
        }
    }
}
