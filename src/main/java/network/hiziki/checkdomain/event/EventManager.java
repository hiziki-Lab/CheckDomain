package network.hiziki.checkdomain.event;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import network.hiziki.checkdomain.event.events.Login;

public class EventManager implements Listener {
    public EventManager(Plugin plugin) {
        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onLoginEvent(LoginEvent e) {
        new Login(e);
    }
}
