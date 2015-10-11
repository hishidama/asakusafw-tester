package com.example.modelgen.dmdl.model;
import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.DataModelKind;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.runtime.value.LongOption;
import com.asakusafw.runtime.value.StringOption;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.model.Summarized;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
/**
 * カテゴリ別売上集計を表すデータモデルクラス。
 */
@DataModelKind("DMDL")@PropertyOrder({"category_code", "amount_total", "selling_price_total"})@Summarized(term = @
        Summarized.Term(source = JoinedSalesInfo.class, foldings = {@Summarized.Folding(aggregator = Summarized.
            Aggregator.ANY, source = "categoryCode", destination = "categoryCode"),@Summarized.Folding(aggregator = 
            Summarized.Aggregator.SUM, source = "amount", destination = "amountTotal"),@Summarized.Folding(aggregator = 
            Summarized.Aggregator.SUM, source = "sellingPrice", destination = "sellingPriceTotal")}, shuffle = @Key(
        group = {"categoryCode"}))) public class CategorySummary implements DataModel<CategorySummary>, Writable {
    private final StringOption categoryCode = new StringOption();
    private final LongOption amountTotal = new LongOption();
    private final LongOption sellingPriceTotal = new LongOption();
    @Override@SuppressWarnings("deprecation") public void reset() {
        this.categoryCode.setNull();
        this.amountTotal.setNull();
        this.sellingPriceTotal.setNull();
    }
    @Override@SuppressWarnings("deprecation") public void copyFrom(CategorySummary other) {
        this.categoryCode.copyFrom(other.categoryCode);
        this.amountTotal.copyFrom(other.amountTotal);
        this.sellingPriceTotal.copyFrom(other.sellingPriceTotal);
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
    /**
     * amount_totalを返す。
     * @return amount_total
     * @throws NullPointerException amount_totalの値が<code>null</code>である場合
     */
    public long getAmountTotal() {
        return this.amountTotal.get();
    }
    /**
     * amount_totalを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setAmountTotal(long value) {
        this.amountTotal.modify(value);
    }
    /**
     * <code>null</code>を許すamount_totalを返す。
     * @return amount_total
     */
    public LongOption getAmountTotalOption() {
        return this.amountTotal;
    }
    /**
     * amount_totalを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setAmountTotalOption(LongOption option) {
        this.amountTotal.copyFrom(option);
    }
    /**
     * selling_price_totalを返す。
     * @return selling_price_total
     * @throws NullPointerException selling_price_totalの値が<code>null</code>である場合
     */
    public long getSellingPriceTotal() {
        return this.sellingPriceTotal.get();
    }
    /**
     * selling_price_totalを設定する。
     * @param value 設定する値
     */
    @SuppressWarnings("deprecation") public void setSellingPriceTotal(long value) {
        this.sellingPriceTotal.modify(value);
    }
    /**
     * <code>null</code>を許すselling_price_totalを返す。
     * @return selling_price_total
     */
    public LongOption getSellingPriceTotalOption() {
        return this.sellingPriceTotal;
    }
    /**
     * selling_price_totalを設定する。
     * @param option 設定する値、<code>null</code>の場合にはこのプロパティが<code>null</code>を表すようになる
     */
    @SuppressWarnings("deprecation") public void setSellingPriceTotalOption(LongOption option) {
        this.sellingPriceTotal.copyFrom(option);
    }
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        result.append("class=category_summary");
        result.append(", categoryCode=");
        result.append(this.categoryCode);
        result.append(", amountTotal=");
        result.append(this.amountTotal);
        result.append(", sellingPriceTotal=");
        result.append(this.sellingPriceTotal);
        result.append("}");
        return result.toString();
    }
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + categoryCode.hashCode();
        result = prime * result + amountTotal.hashCode();
        result = prime * result + sellingPriceTotal.hashCode();
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
        CategorySummary other = (CategorySummary) obj;
        if(this.categoryCode.equals(other.categoryCode) == false) {
            return false;
        }
        if(this.amountTotal.equals(other.amountTotal) == false) {
            return false;
        }
        if(this.sellingPriceTotal.equals(other.sellingPriceTotal) == false) {
            return false;
        }
        return true;
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
        categoryCode.write(out);
        amountTotal.write(out);
        sellingPriceTotal.write(out);
    }
    @Override public void readFields(DataInput in) throws IOException {
        categoryCode.readFields(in);
        amountTotal.readFields(in);
        sellingPriceTotal.readFields(in);
    }
}