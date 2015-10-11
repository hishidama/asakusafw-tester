package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.OperatorTester.OperatorResults;

import org.junit.Test;

import com.asakusafw.runtime.core.Result;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test2DataOperator;

public class ExtractTesterTest {

	/**
	 * @see Test2DataOperator#extract1(StoreInfo, Result)
	 */
	@Test
	public void extract1() {
		ExtractTester tester = new ExtractTester(Test2DataOperator.class, "extract1");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("123", "abc"));
		list.add(newStoreInfo(null, "def"));
		list.add(newStoreInfo("789", "ghi"));

		OperatorResults result = tester.execute(list);

		List<StoreInfo> out = result.get(0);
		assertThat(out.size(), is(2));
		assertThat(out.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out.get(1).getStoreCodeAsString(), is("789"));
	}

	/**
	 * @see Test2DataOperator#extract2(StoreInfo, Result, Result)
	 */
	@Test
	public void extract2() {
		ExtractTester tester = new ExtractTester(Test2DataOperator.class, "extract2");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("123", "abc"));
		list.add(newStoreInfo(null, "def"));
		list.add(newStoreInfo("789", "ghi"));

		OperatorResults result = tester.execute(list);

		List<SalesDetail> out1 = result.get(0);
		assertThat(out1.size(), is(2));
		assertThat(out1.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out1.get(1).getStoreCodeAsString(), is("789"));

		List<StoreInfo> out2 = result.get(1);
		assertThat(out2.size(), is(2));
		assertThat(out2.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out2.get(1).getStoreCodeAsString(), is("789"));
	}

	/**
	 * @see Test2DataOperator#extract3(StoreInfo, Result, Result, String)
	 */
	@Test
	public void extract3() {
		ExtractTester tester = new ExtractTester(Test2DataOperator.class, "extract3");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("123", "abc"));
		list.add(newStoreInfo(null, "def"));
		list.add(newStoreInfo("789", "ghi"));

		OperatorResults result = tester.execute(list, "default");

		List<SalesDetail> out1 = result.get(0);
		assertThat(out1.size(), is(3));
		assertThat(out1.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out1.get(1).getStoreCodeAsString(), is("default"));
		assertThat(out1.get(2).getStoreCodeAsString(), is("789"));

		List<StoreInfo> out2 = result.get(1);
		assertThat(out2.size(), is(3));
		assertThat(out2.get(0).getStoreCodeAsString(), is("123"));
		assertThat(out2.get(1).getStoreCodeAsString(), is("default"));
		assertThat(out2.get(2).getStoreCodeAsString(), is("789"));
	}

	private static StoreInfo newStoreInfo(String code, String name) {
		StoreInfo result = new StoreInfo();
		result.setStoreCodeAsString(code);
		result.setStoreNameAsString(name);
		return result;
	}
}
