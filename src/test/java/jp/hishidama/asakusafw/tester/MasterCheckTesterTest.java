package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.MasterCheckTester.MasterCheckResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test3JoinOperator;

public class MasterCheckTesterTest {

	/**
	 * @see Test3JoinOperator#check(StoreInfo, SalesDetail)
	 */
	@Test
	public void check1() {
		MasterCheckTester tester = new MasterCheckTester(Test3JoinOperator.class, "check");

		List<StoreInfo> master = new ArrayList<>();
		master.add(newStoreInfo("s0", "zzz"));
		master.add(newStoreInfo("s1", "abc"));
		master.add(newStoreInfo("s2", "def"));
		master.add(newStoreInfo("s2", "ghi"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 7));
		tx.add(newSalesDetail("s3", "i32", 6));
		tx.add(newSalesDetail("s4", "i4", 5));

		MasterCheckResult<SalesDetail> result = tester.execute(master, tx);

		List<SalesDetail> found = result.found;
		assertThat(found.size(), is(2));
		assertThat(getSalesDetail(found, "s2", "i21"), is(not(nullValue())));
		assertThat(getSalesDetail(found, "s2", "i22"), is(not(nullValue())));

		List<SalesDetail> missed = result.missed;
		assertThat(missed.size(), is(3));
		assertThat(getSalesDetail(missed, "s3", "i31"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s3", "i32"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s4", "i4"), is(not(nullValue())));
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
