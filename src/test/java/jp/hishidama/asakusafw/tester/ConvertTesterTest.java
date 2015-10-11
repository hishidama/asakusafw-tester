package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.ConvertTester.ConvertResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test2DataOperator;

public class ConvertTesterTest {

	/**
	 * @see Test2DataOperator#convert1(SalesDetail)
	 */
	@Test
	public void convert1() {
		ConvertTester tester = new ConvertTester(Test2DataOperator.class, "convert1");

		List<SalesDetail> list = new ArrayList<>();
		list.add(newSalesDetail("123"));
		list.add(newSalesDetail("456"));

		ConvertResult<SalesDetail, StoreInfo> result = tester.execute(list);

		List<StoreInfo> out = result.out;
		assertThat(out.size(), is(2));
		assertThat(out.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out.get(1).getStoreCodeAsString(), is("456"));
	}

	/**
	 * @see Test2DataOperator#convert2(SalesDetail, String)
	 */
	@Test
	public void convert2() {
		ConvertTester tester = new ConvertTester(Test2DataOperator.class, "convert2");

		List<SalesDetail> list = new ArrayList<>();
		list.add(newSalesDetail("123"));
		list.add(newSalesDetail("456"));

		ConvertResult<SalesDetail, StoreInfo> result = tester.execute(list, "zzz");

		List<StoreInfo> out = result.out;
		assertThat(out.size(), is(2));
		assertThat(out.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out.get(0).getStoreNameAsString(), is("zzz"));
		assertThat(out.get(1).getStoreCodeAsString(), is("456"));
		assertThat(out.get(1).getStoreNameAsString(), is("zzz"));
	}

	private static SalesDetail newSalesDetail(String code) {
		SalesDetail result = new SalesDetail();
		result.setStoreCodeAsString(code);
		return result;
	}
}
