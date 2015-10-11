package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.JoinedSalesInfo;
import com.example.operator.Test4SumOperator;

public class SummarizeTesterTest {

	/**
	 * @see Test4SumOperator#sum1(JoinedSalesInfo)
	 */
	@Test
	public void sum1() {
		SummarizeTester tester = new SummarizeTester(Test4SumOperator.class, "sum1");

		List<JoinedSalesInfo> list = new ArrayList<>();
		list.add(newJoinedSalesInfo("c1", 1, 100));
		list.add(newJoinedSalesInfo("c1", 2, 10));
		list.add(newJoinedSalesInfo("c1", 3, 1));
		list.add(newJoinedSalesInfo("c2", 4, 40));
		list.add(newJoinedSalesInfo("c2", 5, 50));

		List<CategorySummary> out = tester.execute(list);

		assertThat(out.size(), is(2));
		{
			CategorySummary r = getCategorySummary(out, "c1");
			assertThat(r.getAmountTotal(), is(6L));
			assertThat(r.getSellingPriceTotal(), is(111L));
		}
		{
			CategorySummary r = getCategorySummary(out, "c2");
			assertThat(r.getAmountTotal(), is(9L));
			assertThat(r.getSellingPriceTotal(), is(90L));
		}
	}

	private static JoinedSalesInfo newJoinedSalesInfo(String code, int amount, int price) {
		JoinedSalesInfo result = new JoinedSalesInfo();
		result.setCategoryCodeAsString(code);
		result.setAmount(amount);
		result.setSellingPrice(price);
		return result;
	}

	private static CategorySummary getCategorySummary(List<CategorySummary> list, String code) {
		for (CategorySummary in : list) {
			if (in.getCategoryCodeAsString().equals(code)) {
				return in;
			}
		}
		return null;
	}
}
