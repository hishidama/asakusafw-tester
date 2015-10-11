package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.hishidama.asakusafw.tester.MasterJoinUpdateTester.MasterJoinUpdateResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test3JoinOperator;

public class MasterJoinUpdateTesterTest {

	/**
	 * @see Test3JoinOperator#update1(StoreInfo, SalesDetail)
	 */
	@Test
	public void update1() {
		MasterJoinUpdateTester tester = new MasterJoinUpdateTester(Test3JoinOperator.class, "update1");

		List<StoreInfo> master = new ArrayList<>();
		master.add(newStoreInfo("s0", "zzz"));
		master.add(newStoreInfo("s1", "abc"));
		master.add(newStoreInfo("s2", "def"));
		master.add(newStoreInfo("s2", "ghi"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s1", "i11", 9));
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 6));
		tx.add(newSalesDetail("s3", "i32", 7));
		tx.add(newSalesDetail("s4", "i4", 5));

		MasterJoinUpdateResult<SalesDetail> result = tester.execute(master, tx);

		List<SalesDetail> updated = result.updated;
		assertThat(updated.size(), is(3));
		assertThat(getSalesDetail(updated, "s1", "i11").getFileNameAsString(), is("abc"));
		for (String item : Arrays.asList("i21", "i22")) {
			String name = getSalesDetail(updated, "s2", item).getFileNameAsString();
			if (name.equals("def") || name.equals("ghi")) {
				// success
			} else {
				fail(name);
			}
		}

		List<SalesDetail> missed = result.missed;
		assertThat(missed.size(), is(3));
		assertThat(getSalesDetail(missed, "s3", "i31"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s3", "i32"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s4", "i4"), is(not(nullValue())));
	}

	/**
	 * @see Test3JoinOperator#update2(StoreInfo, SalesDetail, String)
	 */
	@Test
	public void update2() {
		MasterJoinUpdateTester tester = new MasterJoinUpdateTester(Test3JoinOperator.class, "update2");

		List<StoreInfo> master = new ArrayList<>();
		master.add(newStoreInfo("s0", "zzz"));
		master.add(newStoreInfo("s1", "abc"));
		master.add(newStoreInfo("s2", "def"));
		master.add(newStoreInfo("s2", "ghi"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s1", "i11", 9));
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 6));
		tx.add(newSalesDetail("s3", "i32", 7));
		tx.add(newSalesDetail("s4", "i4", 5));

		MasterJoinUpdateResult<SalesDetail> result = tester.execute(master, tx, "zzz");

		List<SalesDetail> updated = result.updated;
		assertThat(updated.size(), is(3));
		assertThat(getSalesDetail(updated, "s1", "i11").getFileNameAsString(), is("abczzz"));
		for (String item : Arrays.asList("i21", "i22")) {
			String name = getSalesDetail(updated, "s2", item).getFileNameAsString();
			if (name.equals("defzzz") || name.equals("ghizzz")) {
				// success
			} else {
				fail(name);
			}
		}

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
