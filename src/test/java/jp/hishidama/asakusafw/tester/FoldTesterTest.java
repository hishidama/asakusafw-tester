package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.operator.Test4SumOperator;

public class FoldTesterTest {

	/**
	 * @see Test4SumOperator#fold1(SalesDetail, SalesDetail)
	 */
	@Test
	public void fold1() {
		FoldTester tester = new FoldTester(Test4SumOperator.class, "fold1");

		List<SalesDetail> list = new ArrayList<>();
		list.add(newSalesDetail("s1", 1));
		list.add(newSalesDetail("s1", 10));
		list.add(newSalesDetail("s1", 100));
		list.add(newSalesDetail("s2", null));
		list.add(newSalesDetail("s2", 2));
		list.add(newSalesDetail("s3", 3));
		list.add(newSalesDetail("s3", null));
		list.add(newSalesDetail("s4", 4));
		list.add(newSalesDetail("s5", null));

		List<SalesDetail> result = tester.execute(list);

		assertThat(result.size(), is(5));
		assertThat(getSalesDetail(result, "s1").getSellingPrice(), is(111));
		assertThat(getSalesDetail(result, "s2").getSellingPrice(), is(2));
		assertThat(getSalesDetail(result, "s3").getSellingPrice(), is(3));
		assertThat(getSalesDetail(result, "s4").getSellingPrice(), is(4));
		assertThat(getSalesDetail(result, "s5").getSellingPriceOption().isNull(), is(true));
	}

	/**
	 * @see Test4SumOperator#fold2(SalesDetail, SalesDetail, int)
	 */
	@Test
	public void fold2() {
		FoldTester tester = new FoldTester(Test4SumOperator.class, "fold2");

		List<SalesDetail> list = new ArrayList<>();
		list.add(newSalesDetail("s1", 1));
		list.add(newSalesDetail("s1", 10));
		list.add(newSalesDetail("s1", 100));
		list.add(newSalesDetail("s2", null));
		list.add(newSalesDetail("s2", 2));
		list.add(newSalesDetail("s3", 3));
		list.add(newSalesDetail("s3", null));
		list.add(newSalesDetail("s4", 4));
		list.add(newSalesDetail("s5", null));

		List<SalesDetail> result = tester.execute(list, 9000);

		assertThat(result.size(), is(5));
		assertThat(getSalesDetail(result, "s1").getSellingPrice(), is(111));
		assertThat(getSalesDetail(result, "s2").getSellingPrice(), is(9002));
		assertThat(getSalesDetail(result, "s3").getSellingPrice(), is(9003));
		assertThat(getSalesDetail(result, "s4").getSellingPrice(), is(4));
		assertThat(getSalesDetail(result, "s5").getSellingPriceOption().isNull(), is(true));
	}

	private static SalesDetail newSalesDetail(String code, Integer price) {
		SalesDetail result = new SalesDetail();
		result.setStoreCodeAsString(code);
		if (price != null) {
			result.setSellingPrice(price);
		}
		return result;
	}

	private static SalesDetail getSalesDetail(List<SalesDetail> list, String code) {
		for (SalesDetail in : list) {
			if (in.getStoreCodeAsString().equals(code)) {
				return in;
			}
		}
		return null;
	}
}
