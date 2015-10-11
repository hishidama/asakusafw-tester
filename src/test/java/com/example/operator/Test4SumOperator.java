package com.example.operator;

import java.util.List;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Fold;
import com.asakusafw.vocabulary.operator.GroupSort;
import com.asakusafw.vocabulary.operator.Summarize;
import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.JoinedSalesInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;

/**
 * 集計系演算子
 */
public abstract class Test4SumOperator {

	@Summarize
	public abstract CategorySummary sum1(JoinedSalesInfo in);

	@Fold
	public void fold1(@Key(group = { "store_code" }) SalesDetail left, SalesDetail right) {
		if (left.getSellingPriceOption().isNull()) {
			left.setSellingPriceOption(right.getSellingPriceOption());
		} else {
			left.getSellingPriceOption().add(right.getSellingPriceOption());
		}
	}

	@Fold
	public void fold2(@Key(group = { "store_code" }) SalesDetail left, SalesDetail right, int defaultValue) {
		if (left.getSellingPriceOption().isNull()) {
			left.setSellingPrice(defaultValue);
		}
		left.getSellingPriceOption().add(right.getSellingPriceOption().or(defaultValue));
	}

	@GroupSort
	public void sort1(@Key(group = { "store_code" }, order = { "store_name DESC" }) List<StoreInfo> list,
			Result<StoreInfo> out1) {
		out1.add(list.get(0));
	}

	@GroupSort
	public void sort2(@Key(group = { "store_code" }, order = { "store_name DESC" }) List<StoreInfo> list,
			Result<StoreInfo> out1, Result<StoreInfo> out2) {
		boolean first = true;
		for (StoreInfo in : list) {
			if (first) {
				out1.add(in);
				first = false;
			} else {
				out2.add(in);
			}
		}
	}
}
