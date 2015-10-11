package com.example.modelgen.dmdl.model;
import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.DataModelKind;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.runtime.value.IntOption;
import com.asakusafw.runtime.value.StringOption;
import com.asakusafw.vocabulary.model.Joined;
import com.asakusafw.vocabulary.model.Key;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * 売上明細+商品マスタを表すデータモデルクラス。
 */
@DataModelKind("DMDL")@Joined(terms = {@Joined.Term(source = SalesDetail.class, mappings = {@Joined.Mapping(source = 
                "itemCode", destination = "itemCode"),@Joined.Mapping(source = "amount", destination = "amount"),@Joined
                .Mapping(source = "sellingPrice", destination = "sellingPrice")}, shuffle = @Key(group = {"itemCode"})),
            @Joined.Term(source = ItemInfo.class, mappings = {@Joined.Mapping(source = "itemCode", destination = 
                "itemCode"),@Joined.Mapping(source = "categoryCode", destination = "categoryCode")}, shuffle = @Key(
            group = {"itemCode"}))})@PropertyOrder({"item_code", "amount", "selling_price", "category_code"}) public 
        class JoinedSalesInfo implements DataModel<JoinedSalesInfo>, Writable {
    private final StringOption itemCode = new StringOption();
    private final IntOption amount = new IntOption();
    private final IntOption sellingPrice = new IntOption();
    private final StringOption categoryCode = new StringOption();
    @Override@SuppressWarnings("deprecation") public void reset() {
        this.itemCode.setNull();
        this.amount.setNull();
        this.sellingPrice.setNull();
        this.categoryCode.setNull();
    }
    @Override@SuppressWarnings("deprecation") public void copyFrom(JoinedSalesInfo other) {
        this.itemCode.copyFrom(other.itemCode);
        this.amount.copyFrom(other.amount);
        this.sellingPrice.copyFrom(other.sellingPrice);
        this.categoryCode.copyFrom(other.categoryCode);
    }
    /**
     * item_codeを返す。
     * @return item_code
     * @throws NullPointerException item_codeの値が<code>null</code>である場合
     */
    public Text getItemCode() {
        return this.itemCode.get();
    }
    /**
     * item_codeを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemCode(Text value) {
        this.itemCode.modify(value);
    }
    /**
     * <code>null</code>を許すitem_codeを返す。
     * @return item_code
     */
    public StringOption getItemCodeOption() {
        return this.itemCode;
    }
    /**
     * item_codeを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setItemCodeOption(StringOption option) {
        this.itemCode.copyFrom(option);
    }
    /**
     * amountを返す。
     * @return amount
     * @throws NullPointerException amountの値が<code>null</code>である場合
     */
    public int getAmount() {
        return this.amount.get();
    }
    /**
     * amountを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setAmount(int value) {
        this.amount.modify(value);
    }
    /**
     * <code>null</code>を許すamountを返す。
     * @return amount
     */
    public IntOption getAmountOption() {
        return this.amount;
    }
    /**
     * amountを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setAmountOption(IntOption option) {
        this.amount.copyFrom(option);
    }
    /**
     * selling_priceを返す。
     * @return selling_price
     * @throws NullPointerException selling_priceの値が<code>null</code>である場合
     */
    public int getSellingPrice() {
        return this.sellingPrice.get();
    }
    /**
     * selling_priceを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setSellingPrice(int value) {
        this.sellingPrice.modify(value);
    }
    /**
     * <code>null</code>を許すselling_priceを返す。
     * @return selling_price
     */
    public IntOption getSellingPriceOption() {
        return this.sellingPrice;
    }
    /**
     * selling_priceを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setSellingPriceOption(IntOption option) {
        this.sellingPrice.copyFrom(option);
    }
    /**
     * category_codeを返す。
     * @return category_code
     * @throws NullPointerException category_codeの値が<code>null</code>である場合
     */
    public Text getCategoryCode() {
        return this.categoryCode.get();
    }
    /**
     * category_codeを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryCode(Text value) {
        this.categoryCode.modify(value);
    }
    /**
     * <code>null</code>を許すcategory_codeを返す。
     * @return category_code
     */
    public StringOption getCategoryCodeOption() {
        return this.categoryCode;
    }
    /**
     * category_codeを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setCategoryCodeOption(StringOption option) {
        this.categoryCode.copyFrom(option);
    }
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("class=joined_sales_info");
        result.append(", itemCode=");
        result.append(this.itemCode);
        result.append(", amount=");
        result.append(this.amount);
        result.append(", sellingPrice=");
        result.append(this.sellingPrice);
        result.append(", categoryCode=");
        result.append(this.categoryCode);
        result.append("}");
        return result.toString();
    }
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + itemCode.hashCode();
        result = prime * result + amount.hashCode();
        result = prime * result + sellingPrice.hashCode();
        result = prime * result + categoryCode.hashCode();
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
        JoinedSalesInfo other = (JoinedSalesInfo) obj;
        if(this.itemCode.equals(other.itemCode) == false) {
            return false;
        }
        if(this.amount.equals(other.amount) == false) {
            return false;
        }
        if(this.sellingPrice.equals(other.sellingPrice) == false) {
            return false;
        }
        if(this.categoryCode.equals(other.categoryCode) == false) {
            return false;
        }
        return true;
    }
    /**
     * item_codeを返す。
     * @return item_code
     * @throws NullPointerException item_codeの値が<code>null</code>である場合
     */
    public String getItemCodeAsString() {
        return this.itemCode.getAsString();
    }
    /**
     * item_codeを設定する。
     * @param itemCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemCodeAsString(String itemCode0) {
        this.itemCode.modify(itemCode0);
    }
    /**
     * category_codeを返す。
     * @return category_code
     * @throws NullPointerException category_codeの値が<code>null</code>である場合
     */
    public String getCategoryCodeAsString() {
        return this.categoryCode.getAsString();
    }
    /**
     * category_codeを設定する。
     * @param categoryCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryCodeAsString(String categoryCode0) {
        this.categoryCode.modify(categoryCode0);
    }
    @Override public void write(DataOutput out) throws IOException {
        itemCode.write(out);
        amount.write(out);
        sellingPrice.write(out);
        categoryCode.write(out);
    }
    @Override public void readFields(DataInput in) throws IOException {
        itemCode.readFields(in);
        amount.readFields(in);
        sellingPrice.readFields(in);
        categoryCode.readFields(in);
    }
}