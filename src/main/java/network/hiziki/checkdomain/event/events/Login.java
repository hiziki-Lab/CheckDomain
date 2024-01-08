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

        if (!Main.getDomainList().contains(hostName)) { // 許可されるドメインか確認
            String action = Main.getUnmatchedDomainAction(); // ドメインが一致しない場合の処理を取得

            if (action.equals("allow")) return;

            String kickMessage = String.join("\n", Main.getKickMessage());

            e.setCancelled(true);
            e.setCancelReason(new TextComponent(kickMessage));

            if (action.equals("kickLogging")) {
                Main.getPlugin().getLogger().info(name + "が許可されていないドメイン「" + hostName + "」で接続を行いました。");
            }
        }
    }
}
