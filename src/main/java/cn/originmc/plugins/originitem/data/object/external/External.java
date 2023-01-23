package cn.originmc.plugins.originitem.data.object.external;

import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.text.Color;
import cn.originmc.plugins.originitem.data.ExternalData;
import cn.originmc.plugins.originitem.data.object.inherent.Attributes;
import cn.originmc.plugins.originitem.data.object.inherent.TierLevelSetting;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class External {
    private String id;
    private String display;
    private Material material;
    private int customModelData;
    private List<String> lore=new ArrayList<>();
    private List<String> infoIdList;

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public ItemStack getItem(){
        Item item=new Item(new ItemStack(getMaterial()));
        item.setDisplay(Color.toColor(getDisplay()));
        item.setCustomModelData(getCustomModelData());
        item.setLore(getLore());
        item.addSpace("ITEM_FORMAT");
        item.set("external",getId(),"ITEM_FORMAT");
        item.set("nowPage",-1,"ITEM_FORMAT");
        return item.getItemStack();
    }
    public boolean hasTierFieldSet(int index){
        return ExternalData.getYamlManager().has(getId(), index+".field-set");
    }
    public boolean hasTierLevelSetting(int index){
        return ExternalData.getYamlManager().has(getId(), index+".level");
    }
    public Attributes getTierFieldSet(int index){
        List<String> formatList= (List<String>) ExternalData.getYamlManager().get(getId(),index+".field-set");
        return new Attributes(formatList);
    }
    public TierLevelSetting getTierLevelSetting(int index){
        TierLevelSetting tierLevelSetting=new TierLevelSetting();
        tierLevelSetting.setLvlPerAddAttributes(new Attributes((List<String>) ExternalData.getYamlManager().get(getId(),index+".level.per-field-add")));
        for (String s : ExternalData.getYamlManager().getKeyList(getId(), index + ".level.special",false)) {
            tierLevelSetting.getSpecialLvlPerAddAttributesMap().put(Integer.valueOf(s)
                    ,new Attributes((List<String>) ExternalData.getYamlManager().get(getId(),index+".level.special."+s)));
        }
        return tierLevelSetting;
    }
    public boolean hasTierLore(int index){
        return ExternalData.getYamlManager().has(getId(),index+".lore");
    }
    public boolean hasTierMaterial(int index){
        return ExternalData.getYamlManager().has(getId(),index+".material");
    }
    public Material getTierMaterial(int index){
        return Material.valueOf((String) ExternalData.getYamlManager().get(getId(),index+".material"));
    }
    public List<String> getTierLore(int index){
        return (List<String>) ExternalData.getYamlManager().get(getId(),index+".lore",ExternalData.getYamlManager().get(getId(),"info.lore"));
    }
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<String> getInfoIdList() {
        return infoIdList;
    }

    public void setInfoIdList(List<String> infoIdList) {
        this.infoIdList = infoIdList;
    }

}
