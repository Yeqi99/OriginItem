package cn.originmc.plugins.originitem.data.object.item;

import cn.originmc.plugins.originitem.data.object.external.External;
import cn.originmc.plugins.originitem.data.object.field.Field;
import cn.originmc.plugins.originitem.data.object.inherent.Inherent;
import cn.originmc.plugins.originitem.function.FieldManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class OItem {
    private String id;
    private External external;
    private Inherent inherent;
    private String type;

    public ItemStack randomItem(int level){
        InstanceItem instanceItem=new InstanceItem(getExternal().getItem());
        instanceItem.set("type",getType(),"ITEM_FORMAT");
        instanceItem.set("id",getId(),"ITEM_FORMAT");
        instanceItem.set("UUID", UUID.randomUUID().toString(),"ITEM_FORMAT");
        instanceItem.setItemStack(getInherent().randomGive(level,instanceItem.getItemStack()));
        int tierIndex=instanceItem.getTier().getIndex();
        if (getExternal().hasTierFieldSet(tierIndex)){
            instanceItem.setItemStack(getExternal().getTierFieldSet(tierIndex).give(instanceItem.getItemStack()));
        }
        if (getExternal().hasTierMaterial(tierIndex)){
            ItemStack itemStack= instanceItem.getItemStack();
            itemStack.setType(instanceItem.toOItem().getExternal().getTierMaterial(tierIndex));
            instanceItem.setItemStack(itemStack);
        }
        Field field= FieldManager.getField("mi-item-type");
        if (field!=null){
            if (instanceItem.hasField(field)){
                ItemStack itemStack=instanceItem.getItemStack();
                ItemMeta im=itemStack.getItemMeta();
                im.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED
                        ,new AttributeModifier("mmoitemsDecoy",0,AttributeModifier.Operation.ADD_NUMBER));
                itemStack.setItemMeta(im);
                instanceItem.setItemStack(itemStack);
            }
        }
        return instanceItem.getItemStack();
    }
    public ItemStack getItem(int level,int hopeTierIndex){
        InstanceItem instanceItem=new InstanceItem(getExternal().getItem());
        instanceItem.set("type",getType(),"ITEM_FORMAT");
        instanceItem.set("id",getId(),"ITEM_FORMAT");
        instanceItem.set("UUID", UUID.randomUUID().toString(),"ITEM_FORMAT");
        instanceItem.setItemStack(getInherent().give(level,instanceItem.getItemStack(),hopeTierIndex));
        int tierIndex=instanceItem.getTier().getIndex();
        if (getExternal().hasTierFieldSet(tierIndex)){
            instanceItem.setItemStack(getExternal().getTierFieldSet(tierIndex).give(instanceItem.getItemStack()));
        }
        if (getExternal().hasTierMaterial(tierIndex)){
            ItemStack itemStack= instanceItem.getItemStack();
            itemStack.setType(instanceItem.toOItem().getExternal().getTierMaterial(tierIndex));
            instanceItem.setItemStack(itemStack);
        }
        Field field= FieldManager.getField("mi-item-type");
        if (field!=null){
            if (instanceItem.hasField(field)){
                ItemStack itemStack=instanceItem.getItemStack();
                ItemMeta im=itemStack.getItemMeta();
                im.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED
                        ,new AttributeModifier("mmoitemsDecoy",0,AttributeModifier.Operation.ADD_NUMBER));
                itemStack.setItemMeta(im);
                instanceItem.setItemStack(itemStack);
            }
        }
        return instanceItem.getItemStack();
    }
    public ItemStack getItem(int level,int minTierIndex,int maxTierIndex){
        InstanceItem instanceItem=new InstanceItem(getExternal().getItem());
        instanceItem.set("type",getType(),"ITEM_FORMAT");
        instanceItem.set("id",getId(),"ITEM_FORMAT");
        instanceItem.set("UUID", UUID.randomUUID().toString(),"ITEM_FORMAT");
        instanceItem.setItemStack(getInherent().give(level,instanceItem.getItemStack(),minTierIndex,maxTierIndex));
        int tierIndex=instanceItem.getTier().getIndex();
        if (getExternal().hasTierFieldSet(tierIndex)){
            instanceItem.setItemStack(getExternal().getTierFieldSet(tierIndex).give(instanceItem.getItemStack()));
        }
        if (getExternal().hasTierMaterial(tierIndex)){
            ItemStack itemStack= instanceItem.getItemStack();
            itemStack.setType(instanceItem.toOItem().getExternal().getTierMaterial(tierIndex));
            instanceItem.setItemStack(itemStack);
        }
        Field field= FieldManager.getField("mi-item-type");
        if (field!=null){
            if (instanceItem.hasField(field)){
                ItemStack itemStack=instanceItem.getItemStack();
                ItemMeta im=itemStack.getItemMeta();
                im.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED
                        ,new AttributeModifier("mmoitemsDecoy",0,AttributeModifier.Operation.ADD_NUMBER));
                itemStack.setItemMeta(im);
                instanceItem.setItemStack(itemStack);
            }
        }
        return instanceItem.getItemStack();
    }
    public External getExternal() {
        return external;
    }

    public void setExternal(External external) {
        this.external = external;
    }

    public Inherent getInherent() {
        return inherent;
    }

    public void setInherent(Inherent inherent) {
        this.inherent = inherent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
