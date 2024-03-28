package dev.paracausal.testplugin.listener;

import dev.paracausal.testplugin.TestPlugin;
import dev.paracausal.testplugin.api.currency.TestCurrencyImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener implements Listener {

    private final TestPlugin plugin;

    public ChatListener(TestPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        ((TestCurrencyImpl) plugin.currency()).add(event.getPlayer(), 1L);
    }

}
