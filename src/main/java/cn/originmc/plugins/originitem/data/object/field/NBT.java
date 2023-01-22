package cn.originmc.plugins.originitem.data.object.field;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import cn.originmc.plugins.origincore.util.list.ListUtil;
import cn.originmc.plugins.origincore.util.random.Randomizer;
import org.bukkit.inventory.ItemStack;

public class NBT {
    private String key;
    private DataType dataType;
    private String spaceName;
    private int sign=2;

    public ItemStack give(ItemStack inItem,String value){
        if (value.contains("|")){
            value = Randomizer.getRandomFromStr(value);
        }
        if (value.contains("-")){
            value = Randomizer.getRandomFromSection(value,sign);
        }
        Item item=new Item(inItem);
        Object object=DataType.stringToObject(value,getDataType());
        if (spaceName.equalsIgnoreCase("*")){
            item.set(getKey(),object);
        }else {
            if (!item.hasTag(getSpaceName())){
                item.addSpace(getSpaceName());
            }
            item.set(getKey(),object,getSpaceName());
        }
        return item.getItemStack();
    }
    public ItemStack add(ItemStack inItem,String value,int amount){
        if (value.contains("|")){
            value = Randomizer.getRandomFromStr(value);
        }
        if (value.contains("-")){
            value = Randomizer.getRandomFromSection(value,sign);
        }
        Item item=new Item(inItem);
        switch (getDataType()){
            case DOUBLE:{
                if (spaceName.equalsIgnoreCase("*")){
                    item.addDouble(getKey(), Double.parseDouble(value)*amount);
                }else {
                    item.addDouble(getKey(),Double.parseDouble(value)*amount,getSpaceName());
                }
                break;
            }
            case FLOAT:{
                if (spaceName.equalsIgnoreCase("*")){
                    item.addFloat(getKey(), Float.parseFloat(value)*amount);
                }else {
                    item.addFloat(getKey(),Float.parseFloat(value)*amount,getSpaceName());
                }
                break;
            }
            case INT:{
                if (spaceName.equalsIgnoreCase("*")){
                    item.addInt(getKey(), Integer.parseInt(value)*amount);
                }else {
                    item.addInt(getKey(),Integer.parseInt(value)*amount,getSpaceName());
                }
                break;
            }
        }
        return item.getItemStack();
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
