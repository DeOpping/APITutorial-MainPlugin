package dev.paracausal.testplugin.commands;

import dev.paracausal.testplugin.TestPlugin;
import dev.paracausal.testplugin.api.currency.TestCurrencyImpl;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class CurrencyCommand implements CommandExecutor, TabCompleter {

    private final TestPlugin plugin;

    public CurrencyCommand(TestPlugin plugin) {
        this.plugin = plugin;
        final PluginCommand command = plugin.getCommand("currency");
        if (command == null) return;
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        final TestCurrencyImpl currency = (TestCurrencyImpl) plugin.currency();
        final UUID uuid = player.getUniqueId();

        if (args.length < 1) {
            player.sendMessage("Balance: " + currency.get(uuid));
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 2) {
                player.sendMessage("Select an amount to add!");
                return true;
            }

            final long amount;
            try { amount = Long.parseLong(args[1]); }
            catch (NumberFormatException e) {
                player.sendMessage("Invalid amount: '" + args[1] + "'");
                return true;
            }

            currency.add(uuid, amount);
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length < 2) {
                player.sendMessage("Select an amount to remove!");
                return true;
            }

            final long amount;
            try { amount = Long.parseLong(args[1]); }
            catch (NumberFormatException e) {
                player.sendMessage("Invalid amount: '" + args[1] + "'");
                return true;
            }

            currency.remove(uuid, amount);
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 2) {
                player.sendMessage("Select an amount to set!");
                return true;
            }

            final long amount;
            try { amount = Long.parseLong(args[1]); }
            catch (NumberFormatException e) {
                player.sendMessage("Invalid amount: '" + args[1] + "'");
                return true;
            }

            currency.set(uuid, amount);
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return null;
        if (args.length == 1) return List.of("add", "remove", "set");
        if (args.length == 2) return List.of("1", "2", "3");
        return null;
    }

}
