package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.MasterJoinTester.MasterJoinResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.JoinedSalesInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test3JoinOperator;

public class MasterJoinTesterTest {

	/**
	 * @see Test3JoinOperator#check(StoreInfo, SalesDetail)
	 */
	@Test
	public void join1() {
		MasterJoinTester tester = new MasterJoinTester(Test3JoinOperator.class, "join");

		List<ItemInfo> master = new ArrayList<>();
		master.add(newItemInfo("i01", "zzz"));
		master.add(newItemInfo("i11", "abc"));
		master.add(newItemInfo("i21", "def"));
		master.add(newItemInfo("s21", "ghi"));
		master.add(newItemInfo("i22", "def"));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(newSalesDetail("s2", "i21", 9));
		tx.add(newSalesDetail("s2", "i22", 8));
		tx.add(newSalesDetail("s3", "i31", 6));
		tx.add(newSalesDetail("s3", "i32", 7));
		tx.add(newSalesDetail("s4", "i4", 5));

		MasterJoinResult<JoinedSalesInfo, SalesDetail> result = tester.execute(master, tx);

		List<JoinedSalesInfo> joined = result.joined;
		assertThat(joined.size(), is(2));
		assertThat(getJoinedSalesInfo(joined, "i21", 9), is(not(nullValue())));
		assertThat(getJoinedSalesInfo(joined, "i22", 8), is(not(nullValue())));

		List<SalesDetail> missed = result.missed;
		assertThat(missed.size(), is(3));
		assertThat(getSalesDetail(missed, "s3", "i31"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s3", "i32"), is(not(nullValue())));
		assertThat(getSalesDetail(missed, "s4", "i4"), is(not(nullValue())));
	}

	/**
	 * @see Test3JoinOperator#check(StoreInfo, SalesDetail)
	 */
	@Test
	public void join2() {
		MasterJoinTester tester = new MasterJoinTester(Test3JoinOperator.class, "join");

		List<ItemInfo> master = new ArrayList<>();
		{
			ItemInfo item = new ItemInfo();
			item.setItemCodeAsString("i1");
			item.setItemNameAsString("item1");
			item.setCategoryCodeAsString("c1");
			item.setCategoryNameAsString("category1");
			master.add(item);
		}

		List<SalesDetail> tx = new ArrayList<>();
		{
			SalesDetail detail = new SalesDetail();
			detail.setStoreCodeAsString("s1");
			detail.setItemCodeAsString("i1");
			detail.setUnitSellingPrice(123);
			detail.setAmount(4);
			detail.setSellingPrice(123 * 4);
			tx.add(detail);
		}

		MasterJoinResult<JoinedSalesInfo, SalesDetail> result = tester.execute(master, tx);

		List<JoinedSalesInfo> joined = result.joined;
		assertThat(joined.size(), is(1));
		{
			JoinedSalesInfo j = joined.get(0);
			assertThat(j.getItemCodeAsString(), is("i1"));
			assertThat(j.getAmount(), is(4));
			assertThat(j.getSellingPrice(), is(123 * 4));
			assertThat(j.getCategoryCodeAsString(), is("c1"));
		}

		List<SalesDetail> missed = result.missed;
		assertThat(missed.size(), is(0));
	}

	private static ItemInfo newItemInfo(String code, String name) {
		ItemInfo result = new ItemInfo();
		result.setItemCodeAsString(code);
		result.setItemNameAsString(name);
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

	private static JoinedSalesInfo getJoinedSalesInfo(List<JoinedSalesInfo> list, String item, int amount) {
		for (JoinedSalesInfo in : list) {
			if (in.getItemCodeAsString().equals(item) && in.getAmount() == amount) {
				return in;
			}
		}
		return null;
	}
}
