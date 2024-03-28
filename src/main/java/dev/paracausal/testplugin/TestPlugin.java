package dev.paracausal.testplugin;

import dev.paracausal.testplugin.api.TestAPI;
import dev.paracausal.testplugin.api.currency.TestCurrency;
import dev.paracausal.testplugin.api.currency.TestCurrencyImpl;
import dev.paracausal.testplugin.commands.CurrencyCommand;
import dev.paracausal.testplugin.listener.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin implements TestAPI {

    private TestCurrencyImpl currency;

    @Override
    public void onEnable() {
        this.currency = new TestCurrencyImpl(this);
        new CurrencyCommand(this);
        new ChatListener(this);
        getLogger().info("TestPlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TestPlugin disabled!");
    }

    @Override
    public TestCurrency currency() {
        return currency;
    }

}
