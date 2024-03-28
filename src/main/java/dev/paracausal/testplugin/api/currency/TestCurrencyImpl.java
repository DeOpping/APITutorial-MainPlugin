package dev.paracausal.testplugin.api.currency;

import dev.paracausal.testplugin.TestPlugin;
import dev.paracausal.testplugin.api.events.AddCurrencyEvent;
import dev.paracausal.testplugin.api.events.RemoveCurrencyEvent;
import dev.paracausal.testplugin.api.events.SetCurrencyEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TestCurrencyImpl implements TestCurrency {

    private final TestPlugin plugin;
    private final HashMap<UUID, Long> storage;

    public TestCurrencyImpl(TestPlugin plugin) {
        this.plugin = plugin;
        this.storage = new HashMap<>();
    }

    @Override
    public long get(UUID uuid) {
        return storage.getOrDefault(uuid, 0L);
    }

    @Override
    public void set(UUID uuid, long amount) {
        storage.put(uuid, amount);
    }

    public boolean set(Player player, long amount) {
        final SetCurrencyEvent event = new SetCurrencyEvent(player, amount);
        plugin.getServer().getPluginManager().callEvent(event);

        final boolean isCancelled = event.isCancelled();
        if (!isCancelled) {
            amount = event.getAmount();
            set(player.getUniqueId(), amount);
            player.sendMessage("Your balance was set to " + amount);
        }

        return !isCancelled;
    }

    @Override
    public void add(UUID uuid, long amount) {
        set(uuid, get(uuid)+amount);
    }

    public boolean add(Player player, long amount) {
        final AddCurrencyEvent event = new AddCurrencyEvent(player, amount);
        plugin.getServer().getPluginManager().callEvent(event);

        final boolean isCancelled = event.isCancelled();
        if (!isCancelled) {
            amount = event.getAmount();
            add(player.getUniqueId(), amount);
            player.sendMessage("+ " + amount);
        }

        return !isCancelled;
    }

    @Override
    public void remove(UUID uuid, long amount) {
        set(uuid, get(uuid)-amount);
    }

    public boolean remove(Player player, long amount) {
        final RemoveCurrencyEvent event = new RemoveCurrencyEvent(player, amount);
        plugin.getServer().getPluginManager().callEvent(event);

        final boolean isCancelled = event.isCancelled();
        if (!isCancelled) {
            amount = event.getAmount();
            remove(player.getUniqueId(), amount);
            player.sendMessage("- " + amount);
        }

        return !isCancelled;
    }

}
