package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.OperatorTester.BranchResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test1FlowOperator.BranchFilter;
import com.example.operator.Test3JoinOperator;

public class MasterBranchTesterTest {

	/**
	 * @see Test3JoinOperator#branch1(StoreInfo, SalesDetail)
	 */
	@Test
	public void branch1() {
		MasterBranchTester tester = new MasterBranchTester(Test3JoinOperator.class, "branch1");

		List<StoreInfo> master = new ArrayList<>();
		master.add(newStoreInfo("s0", "zzz"));
		master.add(newStoreInfo("s1", "ab"));
		master.add(newStoreInfo("s2", "def"));
		master.add(newStoreInfo("s2", "ghi"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s1", "i11", 9));
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 7));
		tx.add(newSalesDetail("s3", "i32", 6));

		BranchResult<SalesDetail, BranchFilter> result = tester.execute(master, tx);

		List<SalesDetail> out1 = result.get(BranchFilter.OUT1);
		assertThat(out1.size(), is(1));
		assertThat(getSalesDetail(out1, "s1", "i11"), is(not(nullValue())));

		List<SalesDetail> out2 = result.get(BranchFilter.OUT2);
		assertThat(out2.size(), is(2));
		assertThat(getSalesDetail(out2, "s2", "i21"), is(not(nullValue())));
		assertThat(getSalesDetail(out2, "s2", "i22"), is(not(nullValue())));

		List<SalesDetail> missed = result.get(BranchFilter.MISSED);
		assertThat(missed.size(), is(2));
		assertThat(getSalesDetail(missed, "s3", "i31"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s3", "i32"), is(not(nullValue())));
	}

	/**
	 * @see Test3JoinOperator#branch2(StoreInfo, SalesDetail, int)
	 */
	@Test
	public void branch2() {
		MasterBranchTester tester = new MasterBranchTester(Test3JoinOperator.class, "branch2");

		List<StoreInfo> master = new ArrayList<>();
		master.add(newStoreInfo("s0", "zzz"));
		master.add(newStoreInfo("s1", "ab"));
		master.add(newStoreInfo("s2", "def"));
		master.add(newStoreInfo("s2", "ghi"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s1", "i11", 9));
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 7));
		tx.add(newSalesDetail("s3", "i32", 6));

		BranchResult<SalesDetail, BranchFilter> result = tester.execute(master, tx, 3);

		List<SalesDetail> out1 = result.get(BranchFilter.OUT1);
		assertThat(out1.size(), is(1));
		assertThat(getSalesDetail(out1, "s1", "i11"), is(not(nullValue())));

		List<SalesDetail> out2 = result.get(BranchFilter.OUT2);
		assertThat(out2.size(), is(2));
		assertThat(getSalesDetail(out2, "s2", "i21"), is(not(nullValue())));
		assertThat(getSalesDetail(out2, "s2", "i22"), is(not(nullValue())));

		List<SalesDetail> missed = result.get(BranchFilter.MISSED);
		assertThat(missed.size(), is(2));
		assertThat(getSalesDetail(missed, "s3", "i31"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s3", "i32"), is(not(nullValue())));
	}

	private static StoreInfo newStoreInfo(String code, String name) {
		StoreInfo result = new StoreInfo();
		result.setStoreCodeAsString(code);
		result.setStoreNameAsString(name);
		return result;
	}

	private static SalesDetail newSalesDetail(String code, String item, int amount) {
		SalesDetail result = new SalesDetail();
		result.setStoreCodeAsString(code);
		result.setItemCodeAsString(item);
		result.setAmount(amount);
		return result;
	}

	private static SalesDetail getSalesDetail(List<SalesDetail> list, String code, String item) {
		for (SalesDetail in : list) {
			if (in.getStoreCodeAsString().equals(code) && in.getItemCodeAsString().equals(item)) {
				return in;
			}
		}
		return null;
	}
}
