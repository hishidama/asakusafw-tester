package com.example.operator;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.operator.Convert;
import com.asakusafw.vocabulary.operator.Extract;
import com.asakusafw.vocabulary.operator.Update;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;

/**
 * データ操作系演算子
 */
public abstract class Test2DataOperator {

	private final StoreInfo storeInfo = new StoreInfo();

	private final SalesDetail salesDetail = new SalesDetail();

	@Update
	public void update1(StoreInfo store) {
		store.setStoreNameAsString(store.getStoreCodeAsString() + "-name");
	}

	@Update
	public void update2(StoreInfo store, String arg) {
		store.setStoreNameAsString(store.getStoreCodeAsString() + arg);
	}

	@Convert
	public StoreInfo convert1(SalesDetail detail) {
		StoreInfo result = this.storeInfo;
		result.setStoreCodeOption(detail.getStoreCodeOption());
		return result;
	}

	@Convert
	public StoreInfo convert2(SalesDetail detail, String name) {
		StoreInfo result = this.storeInfo;
		result.setStoreCodeOption(detail.getStoreCodeOption());
		result.setStoreNameAsString(name);
		return result;
	}

	@Extract
	public void extract1(StoreInfo store, Result<StoreInfo> out) {
		if (!store.getStoreCodeOption().isNull()) {
			out.add(store);
		}
	}

	@Extract
	public void extract2(StoreInfo store, Result<SalesDetail> out1, Result<StoreInfo> out2) {
		if (!store.getStoreCodeOption().isNull()) {
			SalesDetail result = this.salesDetail;
			result.reset();
			result.setStoreCodeOption(store.getStoreCodeOption());
			out1.add(result);

			out2.add(store);
		}
	}

	@Extract
	public void extract3(StoreInfo store, Result<SalesDetail> out1, Result<StoreInfo> out2, String code) {
		if (store.getStoreCodeOption().isNull()) {
			store.setStoreCodeAsString(code);
		}

		SalesDetail result = this.salesDetail;
		result.reset();
		result.setStoreCodeOption(store.getStoreCodeOption());
		out1.add(result);

		out2.add(store);
	}
}
