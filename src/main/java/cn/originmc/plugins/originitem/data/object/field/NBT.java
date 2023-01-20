package cn.originmc.plugins.originitem.data.object.field;

import cn.originmc.plugins.origincore.util.item.DataType;
import cn.originmc.plugins.origincore.util.item.Item;
import org.bukkit.inventory.ItemStack;

public class NBT {
    private String key;
    private DataType dataType;
    private String spaceName;
    private int sign=2;

    public ItemStack give(ItemStack inItem,String value){
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
    public ItemStack add(ItemStack inItem,String value){
        Item item=new Item(inItem);
        Object object=DataType.stringToObject(value,getDataType());
        double v= (double) item.get(getKey(),getDataType());
        double addV=(double) object;
        if (spaceName.equalsIgnoreCase("*")){
            item.set(getKey(),v+addV);
        }else {
            if (!item.hasTag(getSpaceName())){
                item.addSpace(getSpaceName());
            }
            item.set(getKey(),v+addV,getSpaceName());
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
