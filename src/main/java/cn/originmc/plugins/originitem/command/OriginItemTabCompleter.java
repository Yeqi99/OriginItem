package cn.originmc.plugins.originitem.command;


import cn.originmc.plugins.originitem.function.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OriginItemTabCompleter implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("OriginItem")) {
            if (args.length == 1) {
                return filterStartingWith(args[0], Arrays.asList("item", "up", "down", "*", "/", "+", "fields", "reload"));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("item")) {
                return filterStartingWith(args[1], Arrays.asList("get", "give", "list"));
            } else if (args.length == 3 && args[0].equalsIgnoreCase("item") && (args[1].equalsIgnoreCase("get") || args[1].equalsIgnoreCase("give"))) {
                List<String> itemIds = new ArrayList<>();
                for (String itemId : ItemManager.getOItemIDList()) {
                    if (itemId.toLowerCase().startsWith(args[2].toLowerCase())) {
                        itemIds.add(itemId);
                    }
                }
                return itemIds;
            }
        }
        return null;
    }

    private List<String> filterStartingWith(String prefix, List<String> strings) {
        List<String> filteredList = new ArrayList<>();
        for (String str : strings) {
            if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
                filteredList.add(str);
            }
        }
        return filteredList;
    }
}
