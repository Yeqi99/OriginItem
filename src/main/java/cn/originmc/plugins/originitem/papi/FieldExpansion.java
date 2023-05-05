package cn.originmc.plugins.originitem.papi;

import cn.originmc.plugins.origincore.hook.PlaceholderAPIHook;
import cn.originmc.plugins.origincore.util.text.FormatText;
import cn.originmc.plugins.originitem.data.object.item.InstanceItem;
import cn.originmc.plugins.originitem.function.FieldManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FieldExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "ItemField";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Yeqi";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        FormatText formatText=new FormatText(params);
        String defaultValue=formatText.getValue("default");
        if (defaultValue==null){
            defaultValue="0";
        }
        String fieldName=formatText.getValue("field");
        String mode=formatText.getValue("mode");
        if (!player.isOnline()){
            return defaultValue;
        }else {
            Player p=player.getPlayer();
            if (mode.equalsIgnoreCase("main_hand")){
                ItemStack item=p.getInventory().getItemInMainHand();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("off_hand")){
                ItemStack item=p.getInventory().getItemInOffHand();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("helmet")){
                ItemStack item=p.getInventory().getHelmet();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("chestplate")){
                ItemStack item=p.getInventory().getChestplate();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("leggings")){
                ItemStack item=p.getInventory().getLeggings();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("boots")){
                ItemStack item=p.getInventory().getBoots();
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else if (mode.equalsIgnoreCase("slot")){
                int slot= Integer.parseInt(formatText.getValue("index"));
                ItemStack item=p.getInventory().getItem(slot);
                if (item.getType()== Material.AIR){
                    return defaultValue;
                }else {
                    InstanceItem instanceItem=new InstanceItem(item);
                    return instanceItem.getFieldValue( FieldManager.getField(fieldName),defaultValue);
                }
            }else {
                return defaultValue;
            }
        }
    }
}
