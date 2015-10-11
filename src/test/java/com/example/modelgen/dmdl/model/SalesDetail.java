package com.example.modelgen.dmdl.model;
import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.DataModelKind;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateTimeOption;
import com.asakusafw.runtime.value.IntOption;
import com.asakusafw.runtime.value.StringOption;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * 売上明細を表すデータモデルクラス。
 */
@DataModelKind("DMDL")@PropertyOrder({"sales_date_time", "store_code", "item_code", "amount", "unit_selling_price", 
            "selling_price", "file_name"}) public class SalesDetail implements DataModel<SalesDetail>, Writable {
    private final DateTimeOption salesDateTime = new DateTimeOption();
    private final StringOption storeCode = new StringOption();
    private final StringOption itemCode = new StringOption();
    private final IntOption amount = new IntOption();
    private final IntOption unitSellingPrice = new IntOption();
    private final IntOption sellingPrice = new IntOption();
    private final StringOption fileName = new StringOption();
    @Override@SuppressWarnings("deprecation") public void reset() {
        this.salesDateTime.setNull();
        this.storeCode.setNull();
        this.itemCode.setNull();
        this.amount.setNull();
        this.unitSellingPrice.setNull();
        this.sellingPrice.setNull();
        this.fileName.setNull();
    }
    @Override@SuppressWarnings("deprecation") public void copyFrom(SalesDetail other) {
        this.salesDateTime.copyFrom(other.salesDateTime);
        this.storeCode.copyFrom(other.storeCode);
        this.itemCode.copyFrom(other.itemCode);
        this.amount.copyFrom(other.amount);
        this.unitSellingPrice.copyFrom(other.unitSellingPrice);
        this.sellingPrice.copyFrom(other.sellingPrice);
        this.fileName.copyFrom(other.fileName);
    }
    /**
     * 売上日時を返す。
     * @return 売上日時
     * @throws NullPointerException 売上日時の値が<code>null</code>である場合
     */
    public DateTime getSalesDateTime() {
        return this.salesDateTime.get();
    }
    /**
     * 売上日時を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setSalesDateTime(DateTime value) {
        this.salesDateTime.modify(value);
    }
    /**
     * <code>null</code>を許す売上日時を返す。
     * @return 売上日時
     */
    public DateTimeOption getSalesDateTimeOption() {
        return this.salesDateTime;
    }
    /**
     * 売上日時を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setSalesDateTimeOption(DateTimeOption option) {
        this.salesDateTime.copyFrom(option);
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
     * 商品コードを返す。
     * @return 商品コード
     * @throws NullPointerException 商品コードの値が<code>null</code>である場合
     */
    public Text getItemCode() {
        return this.itemCode.get();
    }
    /**
     * 商品コードを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemCode(Text value) {
        this.itemCode.modify(value);
    }
    /**
     * <code>null</code>を許す商品コードを返す。
     * @return 商品コード
     */
    public StringOption getItemCodeOption() {
        return this.itemCode;
    }
    /**
     * 商品コードを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setItemCodeOption(StringOption option) {
        this.itemCode.copyFrom(option);
    }
    /**
     * 数量を返す。
     * @return 数量
     * @throws NullPointerException 数量の値が<code>null</code>である場合
     */
    public int getAmount() {
        return this.amount.get();
    }
    /**
     * 数量を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setAmount(int value) {
        this.amount.modify(value);
    }
    /**
     * <code>null</code>を許す数量を返す。
     * @return 数量
     */
    public IntOption getAmountOption() {
        return this.amount;
    }
    /**
     * 数量を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setAmountOption(IntOption option) {
        this.amount.copyFrom(option);
    }
    /**
     * 販売単価を返す。
     * @return 販売単価
     * @throws NullPointerException 販売単価の値が<code>null</code>である場合
     */
    public int getUnitSellingPrice() {
        return this.unitSellingPrice.get();
    }
    /**
     * 販売単価を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setUnitSellingPrice(int value) {
        this.unitSellingPrice.modify(value);
    }
    /**
     * <code>null</code>を許す販売単価を返す。
     * @return 販売単価
     */
    public IntOption getUnitSellingPriceOption() {
        return this.unitSellingPrice;
    }
    /**
     * 販売単価を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setUnitSellingPriceOption(IntOption option) {
        this.unitSellingPrice.copyFrom(option);
    }
    /**
     * 販売金額を返す。
     * @return 販売金額
     * @throws NullPointerException 販売金額の値が<code>null</code>である場合
     */
    public int getSellingPrice() {
        return this.sellingPrice.get();
    }
    /**
     * 販売金額を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setSellingPrice(int value) {
        this.sellingPrice.modify(value);
    }
    /**
     * <code>null</code>を許す販売金額を返す。
     * @return 販売金額
     */
    public IntOption getSellingPriceOption() {
        return this.sellingPrice;
    }
    /**
     * 販売金額を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setSellingPriceOption(IntOption option) {
        this.sellingPrice.copyFrom(option);
    }
    /**
     * ファイル名を返す。
     * @return ファイル名
     * @throws NullPointerException ファイル名の値が<code>null</code>である場合
     */
    public Text getFileName() {
        return this.fileName.get();
    }
    /**
     * ファイル名を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setFileName(Text value) {
        this.fileName.modify(value);
    }
    /**
     * <code>null</code>を許すファイル名を返す。
     * @return ファイル名
     */
    public StringOption getFileNameOption() {
        return this.fileName;
    }
    /**
     * ファイル名を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setFileNameOption(StringOption option) {
        this.fileName.copyFrom(option);
    }
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("class=sales_detail");
        result.append(", salesDateTime=");
        result.append(this.salesDateTime);
        result.append(", storeCode=");
        result.append(this.storeCode);
        result.append(", itemCode=");
        result.append(this.itemCode);
        result.append(", amount=");
        result.append(this.amount);
        result.append(", unitSellingPrice=");
        result.append(this.unitSellingPrice);
        result.append(", sellingPrice=");
        result.append(this.sellingPrice);
        result.append(", fileName=");
        result.append(this.fileName);
        result.append("}");
        return result.toString();
    }
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + salesDateTime.hashCode();
        result = prime * result + storeCode.hashCode();
        result = prime * result + itemCode.hashCode();
        result = prime * result + amount.hashCode();
        result = prime * result + unitSellingPrice.hashCode();
        result = prime * result + sellingPrice.hashCode();
        result = prime * result + fileName.hashCode();
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
        SalesDetail other = (SalesDetail) obj;
        if(this.salesDateTime.equals(other.salesDateTime) == false) {
            return false;
        }
        if(this.storeCode.equals(other.storeCode) == false) {
            return false;
        }
        if(this.itemCode.equals(other.itemCode) == false) {
            return false;
        }
        if(this.amount.equals(other.amount) == false) {
            return false;
        }
        if(this.unitSellingPrice.equals(other.unitSellingPrice) == false) {
            return false;
        }
        if(this.sellingPrice.equals(other.sellingPrice) == false) {
            return false;
        }
        if(this.fileName.equals(other.fileName) == false) {
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
     * 商品コードを返す。
     * @return 商品コード
     * @throws NullPointerException 商品コードの値が<code>null</code>である場合
     */
    public String getItemCodeAsString() {
        return this.itemCode.getAsString();
    }
    /**
     * 商品コードを設定する。
     * @param itemCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemCodeAsString(String itemCode0) {
        this.itemCode.modify(itemCode0);
    }
    /**
     * ファイル名を返す。
     * @return ファイル名
     * @throws NullPointerException ファイル名の値が<code>null</code>である場合
     */
    public String getFileNameAsString() {
        return this.fileName.getAsString();
    }
    /**
     * ファイル名を設定する。
     * @param fileName0 設定する値
     */
    @SuppressWarnings("deprecation") public void setFileNameAsString(String fileName0) {
        this.fileName.modify(fileName0);
    }
    @Override public void write(DataOutput out) throws IOException {
        salesDateTime.write(out);
        storeCode.write(out);
        itemCode.write(out);
        amount.write(out);
        unitSellingPrice.write(out);
        sellingPrice.write(out);
        fileName.write(out);
    }
    @Override public void readFields(DataInput in) throws IOException {
        salesDateTime.readFields(in);
        storeCode.readFields(in);
        itemCode.readFields(in);
        amount.readFields(in);
        unitSellingPrice.readFields(in);
        sellingPrice.readFields(in);
        fileName.readFields(in);
    }
}