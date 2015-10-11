package com.example.modelgen.dmdl.model;
import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.DataModelKind;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateOption;
import com.asakusafw.runtime.value.IntOption;
import com.asakusafw.runtime.value.StringOption;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * 商品マスタを表すデータモデルクラス。
 */
@DataModelKind("DMDL")@PropertyOrder({"item_code", "item_name", "department_code", "department_name", "category_code", 
            "category_name", "unit_selling_price", "registered_date", "begin_date", "end_date"}) public class ItemInfo 
        implements DataModel<ItemInfo>, Writable {
    private final StringOption itemCode = new StringOption();
    private final StringOption itemName = new StringOption();
    private final StringOption departmentCode = new StringOption();
    private final StringOption departmentName = new StringOption();
    private final StringOption categoryCode = new StringOption();
    private final StringOption categoryName = new StringOption();
    private final IntOption unitSellingPrice = new IntOption();
    private final DateOption registeredDate = new DateOption();
    private final DateOption beginDate = new DateOption();
    private final DateOption endDate = new DateOption();
    @Override@SuppressWarnings("deprecation") public void reset() {
        this.itemCode.setNull();
        this.itemName.setNull();
        this.departmentCode.setNull();
        this.departmentName.setNull();
        this.categoryCode.setNull();
        this.categoryName.setNull();
        this.unitSellingPrice.setNull();
        this.registeredDate.setNull();
        this.beginDate.setNull();
        this.endDate.setNull();
    }
    @Override@SuppressWarnings("deprecation") public void copyFrom(ItemInfo other) {
        this.itemCode.copyFrom(other.itemCode);
        this.itemName.copyFrom(other.itemName);
        this.departmentCode.copyFrom(other.departmentCode);
        this.departmentName.copyFrom(other.departmentName);
        this.categoryCode.copyFrom(other.categoryCode);
        this.categoryName.copyFrom(other.categoryName);
        this.unitSellingPrice.copyFrom(other.unitSellingPrice);
        this.registeredDate.copyFrom(other.registeredDate);
        this.beginDate.copyFrom(other.beginDate);
        this.endDate.copyFrom(other.endDate);
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
     * 商品名を返す。
     * @return 商品名
     * @throws NullPointerException 商品名の値が<code>null</code>である場合
     */
    public Text getItemName() {
        return this.itemName.get();
    }
    /**
     * 商品名を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemName(Text value) {
        this.itemName.modify(value);
    }
    /**
     * <code>null</code>を許す商品名を返す。
     * @return 商品名
     */
    public StringOption getItemNameOption() {
        return this.itemName;
    }
    /**
     * 商品名を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setItemNameOption(StringOption option) {
        this.itemName.copyFrom(option);
    }
    /**
     * 商品部門コードを返す。
     * @return 商品部門コード
     * @throws NullPointerException 商品部門コードの値が<code>null</code>である場合
     */
    public Text getDepartmentCode() {
        return this.departmentCode.get();
    }
    /**
     * 商品部門コードを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setDepartmentCode(Text value) {
        this.departmentCode.modify(value);
    }
    /**
     * <code>null</code>を許す商品部門コードを返す。
     * @return 商品部門コード
     */
    public StringOption getDepartmentCodeOption() {
        return this.departmentCode;
    }
    /**
     * 商品部門コードを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setDepartmentCodeOption(StringOption option) {
        this.departmentCode.copyFrom(option);
    }
    /**
     * 商品部門名を返す。
     * @return 商品部門名
     * @throws NullPointerException 商品部門名の値が<code>null</code>である場合
     */
    public Text getDepartmentName() {
        return this.departmentName.get();
    }
    /**
     * 商品部門名を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setDepartmentName(Text value) {
        this.departmentName.modify(value);
    }
    /**
     * <code>null</code>を許す商品部門名を返す。
     * @return 商品部門名
     */
    public StringOption getDepartmentNameOption() {
        return this.departmentName;
    }
    /**
     * 商品部門名を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setDepartmentNameOption(StringOption option) {
        this.departmentName.copyFrom(option);
    }
    /**
     * 商品カテゴリコードを返す。
     * @return 商品カテゴリコード
     * @throws NullPointerException 商品カテゴリコードの値が<code>null</code>である場合
     */
    public Text getCategoryCode() {
        return this.categoryCode.get();
    }
    /**
     * 商品カテゴリコードを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryCode(Text value) {
        this.categoryCode.modify(value);
    }
    /**
     * <code>null</code>を許す商品カテゴリコードを返す。
     * @return 商品カテゴリコード
     */
    public StringOption getCategoryCodeOption() {
        return this.categoryCode;
    }
    /**
     * 商品カテゴリコードを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setCategoryCodeOption(StringOption option) {
        this.categoryCode.copyFrom(option);
    }
    /**
     * 商品カテゴリ名を返す。
     * @return 商品カテゴリ名
     * @throws NullPointerException 商品カテゴリ名の値が<code>null</code>である場合
     */
    public Text getCategoryName() {
        return this.categoryName.get();
    }
    /**
     * 商品カテゴリ名を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryName(Text value) {
        this.categoryName.modify(value);
    }
    /**
     * <code>null</code>を許す商品カテゴリ名を返す。
     * @return 商品カテゴリ名
     */
    public StringOption getCategoryNameOption() {
        return this.categoryName;
    }
    /**
     * 商品カテゴリ名を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setCategoryNameOption(StringOption option) {
        this.categoryName.copyFrom(option);
    }
    /**
     * 商品単価を返す。
     * @return 商品単価
     * @throws NullPointerException 商品単価の値が<code>null</code>である場合
     */
    public int getUnitSellingPrice() {
        return this.unitSellingPrice.get();
    }
    /**
     * 商品単価を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setUnitSellingPrice(int value) {
        this.unitSellingPrice.modify(value);
    }
    /**
     * <code>null</code>を許す商品単価を返す。
     * @return 商品単価
     */
    public IntOption getUnitSellingPriceOption() {
        return this.unitSellingPrice;
    }
    /**
     * 商品単価を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setUnitSellingPriceOption(IntOption option) {
        this.unitSellingPrice.copyFrom(option);
    }
    /**
     * マスタ登録日を返す。
     * @return マスタ登録日
     * @throws NullPointerException マスタ登録日の値が<code>null</code>である場合
     */
    public Date getRegisteredDate() {
        return this.registeredDate.get();
    }
    /**
     * マスタ登録日を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setRegisteredDate(Date value) {
        this.registeredDate.modify(value);
    }
    /**
     * <code>null</code>を許すマスタ登録日を返す。
     * @return マスタ登録日
     */
    public DateOption getRegisteredDateOption() {
        return this.registeredDate;
    }
    /**
     * マスタ登録日を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setRegisteredDateOption(DateOption option) {
        this.registeredDate.copyFrom(option);
    }
    /**
     * マスタ適用開始日を返す。
     * @return マスタ適用開始日
     * @throws NullPointerException マスタ適用開始日の値が<code>null</code>である場合
     */
    public Date getBeginDate() {
        return this.beginDate.get();
    }
    /**
     * マスタ適用開始日を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setBeginDate(Date value) {
        this.beginDate.modify(value);
    }
    /**
     * <code>null</code>を許すマスタ適用開始日を返す。
     * @return マスタ適用開始日
     */
    public DateOption getBeginDateOption() {
        return this.beginDate;
    }
    /**
     * マスタ適用開始日を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setBeginDateOption(DateOption option) {
        this.beginDate.copyFrom(option);
    }
    /**
     * マスタ適用終了日を返す。
     * @return マスタ適用終了日
     * @throws NullPointerException マスタ適用終了日の値が<code>null</code>である場合
     */
    public Date getEndDate() {
        return this.endDate.get();
    }
    /**
     * マスタ適用終了日を設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setEndDate(Date value) {
        this.endDate.modify(value);
    }
    /**
     * <code>null</code>を許すマスタ適用終了日を返す。
     * @return マスタ適用終了日
     */
    public DateOption getEndDateOption() {
        return this.endDate;
    }
    /**
     * マスタ適用終了日を設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setEndDateOption(DateOption option) {
        this.endDate.copyFrom(option);
    }
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("class=item_info");
        result.append(", itemCode=");
        result.append(this.itemCode);
        result.append(", itemName=");
        result.append(this.itemName);
        result.append(", departmentCode=");
        result.append(this.departmentCode);
        result.append(", departmentName=");
        result.append(this.departmentName);
        result.append(", categoryCode=");
        result.append(this.categoryCode);
        result.append(", categoryName=");
        result.append(this.categoryName);
        result.append(", unitSellingPrice=");
        result.append(this.unitSellingPrice);
        result.append(", registeredDate=");
        result.append(this.registeredDate);
        result.append(", beginDate=");
        result.append(this.beginDate);
        result.append(", endDate=");
        result.append(this.endDate);
        result.append("}");
        return result.toString();
    }
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + itemCode.hashCode();
        result = prime * result + itemName.hashCode();
        result = prime * result + departmentCode.hashCode();
        result = prime * result + departmentName.hashCode();
        result = prime * result + categoryCode.hashCode();
        result = prime * result + categoryName.hashCode();
        result = prime * result + unitSellingPrice.hashCode();
        result = prime * result + registeredDate.hashCode();
        result = prime * result + beginDate.hashCode();
        result = prime * result + endDate.hashCode();
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
        ItemInfo other = (ItemInfo) obj;
        if(this.itemCode.equals(other.itemCode) == false) {
            return false;
        }
        if(this.itemName.equals(other.itemName) == false) {
            return false;
        }
        if(this.departmentCode.equals(other.departmentCode) == false) {
            return false;
        }
        if(this.departmentName.equals(other.departmentName) == false) {
            return false;
        }
        if(this.categoryCode.equals(other.categoryCode) == false) {
            return false;
        }
        if(this.categoryName.equals(other.categoryName) == false) {
            return false;
        }
        if(this.unitSellingPrice.equals(other.unitSellingPrice) == false) {
            return false;
        }
        if(this.registeredDate.equals(other.registeredDate) == false) {
            return false;
        }
        if(this.beginDate.equals(other.beginDate) == false) {
            return false;
        }
        if(this.endDate.equals(other.endDate) == false) {
            return false;
        }
        return true;
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
     * 商品名を返す。
     * @return 商品名
     * @throws NullPointerException 商品名の値が<code>null</code>である場合
     */
    public String getItemNameAsString() {
        return this.itemName.getAsString();
    }
    /**
     * 商品名を設定する。
     * @param itemName0 設定する値
     */
    @SuppressWarnings("deprecation") public void setItemNameAsString(String itemName0) {
        this.itemName.modify(itemName0);
    }
    /**
     * 商品部門コードを返す。
     * @return 商品部門コード
     * @throws NullPointerException 商品部門コードの値が<code>null</code>である場合
     */
    public String getDepartmentCodeAsString() {
        return this.departmentCode.getAsString();
    }
    /**
     * 商品部門コードを設定する。
     * @param departmentCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setDepartmentCodeAsString(String departmentCode0) {
        this.departmentCode.modify(departmentCode0);
    }
    /**
     * 商品部門名を返す。
     * @return 商品部門名
     * @throws NullPointerException 商品部門名の値が<code>null</code>である場合
     */
    public String getDepartmentNameAsString() {
        return this.departmentName.getAsString();
    }
    /**
     * 商品部門名を設定する。
     * @param departmentName0 設定する値
     */
    @SuppressWarnings("deprecation") public void setDepartmentNameAsString(String departmentName0) {
        this.departmentName.modify(departmentName0);
    }
    /**
     * 商品カテゴリコードを返す。
     * @return 商品カテゴリコード
     * @throws NullPointerException 商品カテゴリコードの値が<code>null</code>である場合
     */
    public String getCategoryCodeAsString() {
        return this.categoryCode.getAsString();
    }
    /**
     * 商品カテゴリコードを設定する。
     * @param categoryCode0 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryCodeAsString(String categoryCode0) {
        this.categoryCode.modify(categoryCode0);
    }
    /**
     * 商品カテゴリ名を返す。
     * @return 商品カテゴリ名
     * @throws NullPointerException 商品カテゴリ名の値が<code>null</code>である場合
     */
    public String getCategoryNameAsString() {
        return this.categoryName.getAsString();
    }
    /**
     * 商品カテゴリ名を設定する。
     * @param categoryName0 設定する値
     */
    @SuppressWarnings("deprecation") public void setCategoryNameAsString(String categoryName0) {
        this.categoryName.modify(categoryName0);
    }
    @Override public void write(DataOutput out) throws IOException {
        itemCode.write(out);
        itemName.write(out);
        departmentCode.write(out);
        departmentName.write(out);
        categoryCode.write(out);
        categoryName.write(out);
        unitSellingPrice.write(out);
        registeredDate.write(out);
        beginDate.write(out);
        endDate.write(out);
    }
    @Override public void readFields(DataInput in) throws IOException {
        itemCode.readFields(in);
        itemName.readFields(in);
        departmentCode.readFields(in);
        departmentName.readFields(in);
        categoryCode.readFields(in);
        categoryName.readFields(in);
        unitSellingPrice.readFields(in);
        registeredDate.readFields(in);
        beginDate.readFields(in);
        endDate.readFields(in);
    }
}