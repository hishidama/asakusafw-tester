package com.example.operator;

import com.asakusafw.vocabulary.operator.Branch;
import com.example.modelgen.dmdl.model.StoreInfo;

/**
 * フロー制御系演算子
 */
public abstract class Test1FlowOperator {

	public enum BranchFilter {
		OUT1, OUT2, MISSED
	}

	@Branch
	public BranchFilter branch1(StoreInfo store) {
		switch (store.getStoreCodeAsString().length()) {
		case 1:
			return BranchFilter.OUT1;
		case 2:
			return BranchFilter.OUT2;
		default:
			return BranchFilter.MISSED;
		}
	}

	@Branch
	public BranchFilter branch2(StoreInfo store, int arg) {
		switch (store.getStoreCodeAsString().length() + arg) {
		case 1:
			return BranchFilter.OUT1;
		case 2:
			return BranchFilter.OUT2;
		default:
			return BranchFilter.MISSED;
		}
	}
}
