package com.example.modelgen.dmdl.model;
import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.DataModelKind;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.runtime.value.StringOption;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * 店舗マスタを表すデータモデルクラス。
 */
@DataModelKind("DMDL")@PropertyOrder({"store_code", "store_name"}) public class StoreInfo implements DataModel<StoreInfo
        >, Writable {
    private final StringOption storeCode = new StringOption();
    private final StringOption storeName = new StringOption();
    @Override@SuppressWarnings("deprecation") public void reset() {
        this.storeCode.setNull();
        this.storeName.setNull();
    }
    @Override@SuppressWarnings("deprecation") public void copyFrom(StoreInfo other) {
        this.storeCode.copyFrom(other.storeCode);
        this.storeName.copyFrom(other.storeName);
    }
    /**
     * 店舗コードを返す。
     * @return 店舗コード
     * @throws NullPointerException 店舗コードの値が<code>null</code>である場合
     */
    public Text getStoreCode() {
        return this.storeCode.get();
    }
    /**
     * 店舗コードを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setStoreCode(Text value) {
        this.storeCode.modify(value);
    }
    /**
     * <code>null</code>を許す店舗コードを返す。
     * @return 店舗コード
     */
    public StringOption getStoreCodeOption() {
        return this.storeCode;
    }
    /**
     * 店舗コードを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setStoreCodeOption(StringOption option) {
        this.storeCode.copyFrom(option);
    }
    /**
     * 店舗名称を返す。
     * @return 店舗名称
     * @throws NullPointerException 店舗名称の値が<code>null</code>である場合
     */
    public Text getStoreName() {
        return this.storeName.get();
    }
    /**
     * 店舗名称を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setStoreName(Text value) {
        this.storeName.modify(value);
    }
    /**
     * <code>null</code>を許す店舗名称を返す。
     * @return 店舗名称
     */
    public StringOption getStoreNameOption() {
        return this.storeName;
    }
    /**
     * 店舗名称を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setStoreNameOption(StringOption option) {
        this.storeName.copyFrom(option);
    }
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("class=store_info");
        result.append(", storeCode=");
        result.append(this.storeCode);
        result.append(", storeName=");
        result.append(this.storeName);
        result.append("}");
        return result.toString();
    }
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + storeCode.hashCode();
        result = prime * result + storeName.hashCode();
        return result;
    }
    @Override public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        StoreInfo other = (StoreInfo) obj;
        if(this.storeCode.equals(other.storeCode) == false) {
            return false;
        }
        if(this.storeName.equals(other.storeName) == false) {
            return false;
        }
        return true;
    }
    /**
     * 店舗コードを返す。
     * @return 店舗コード
     * @throws NullPointerException 店舗コードの値が<code>null</code>である場合
     */
    public String getStoreCodeAsString() {
        return this.storeCode.getAsString();
    }
    /**
     * 店舗コードを設定する。
     * @param storeCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setStoreCodeAsString(String storeCode0) {
        this.storeCode.modify(storeCode0);
    }
    /**
     * 店舗名称を返す。
     * @return 店舗名称
     * @throws NullPointerException 店舗名称の値が<code>null</code>である場合
     */
    public String getStoreNameAsString() {
        return this.storeName.getAsString();
    }
    /**
     * 店舗名称を設定する。
     * @param storeName0 設定する値
     */
    @SuppressWarnings("deprecation") public void setStoreNameAsString(String storeName0) {
        this.storeName.modify(storeName0);
    }
    @Override public void write(DataOutput out) throws IOException {
        storeCode.write(out);
        storeName.write(out);
    }
    @Override public void readFields(DataInput in) throws IOException {
        storeCode.readFields(in);
        storeName.readFields(in);
    }
}