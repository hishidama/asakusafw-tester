package com.example.operator;

import java.util.List;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.CoGroup;
import com.asakusafw.vocabulary.operator.MasterBranch;
import com.asakusafw.vocabulary.operator.MasterCheck;
import com.asakusafw.vocabulary.operator.MasterJoin;
import com.asakusafw.vocabulary.operator.MasterJoinUpdate;
import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.JoinedSalesInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test1FlowOperator.BranchFilter;

/**
 * 結合系演算子
 */
public abstract class Test3JoinOperator {

	private final StoreInfo storeInfo = new StoreInfo();

	@MasterCheck
	public abstract boolean check(@Key(group = { "store_code" }) StoreInfo master,
			@Key(group = { "store_code" }) SalesDetail tx);

	@MasterJoin
	public abstract JoinedSalesInfo join(ItemInfo master, SalesDetail tx);

	@MasterBranch
	public BranchFilter branch1(@Key(group = { "store_code" }) StoreInfo master,
			@Key(group = { "store_code" }) SalesDetail tx) {
		if (master == null) {
			return BranchFilter.MISSED;
		}

		if (master.getStoreNameAsString().length() < 3) {
			return BranchFilter.OUT1;
		} else {
			return BranchFilter.OUT2;
		}
	}

	@MasterBranch
	public BranchFilter branch2(@Key(group = { "store_code" }) StoreInfo master,
			@Key(group = { "store_code" }) SalesDetail tx, int n) {
		if (master == null) {
			return BranchFilter.MISSED;
		}

		if (master.getStoreNameAsString().length() < n) {
			return BranchFilter.OUT1;
		} else {
			return BranchFilter.OUT2;
		}
	}

	@MasterJoinUpdate
	public void update1(@Key(group = { "store_code" }) StoreInfo master, @Key(group = { "store_code" }) SalesDetail tx) {
		tx.setFileNameOption(master.getStoreNameOption());
	}

	@MasterJoinUpdate
	public void update2(@Key(group = { "store_code" }) StoreInfo master, @Key(group = { "store_code" }) SalesDetail tx,
			String arg) {
		tx.setFileNameAsString(master.getStoreNameAsString() + arg);
	}

	@CoGroup
	public void group11(@Key(group = { "store_code" }, order = { "store_name DESC" }) List<StoreInfo> list1,
			Result<StoreInfo> out1) {
		out1.add(list1.get(0));
	}

	@CoGroup
	public void group21(@Key(group = { "store_code" }, order = { "store_name ASC" }) List<StoreInfo> list1,
			@Key(group = { "store_code" }, order = { "store_name" }) List<StoreInfo> list2, Result<StoreInfo> out1) {
		if (list1.isEmpty() || list2.isEmpty()) {
			for (StoreInfo in : list1) {
				out1.add(in);
			}
			for (StoreInfo in : list2) {
				out1.add(in);
			}
			return;
		}

		for (StoreInfo in1 : list1) {
			for (StoreInfo in2 : list2) {
				StoreInfo result = this.storeInfo;
				result.copyFrom(in1);
				result.setStoreNameAsString(in1.getStoreNameAsString() + in2.getStoreNameAsString());
				out1.add(result);
			}
		}
	}
}
