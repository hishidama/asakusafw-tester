package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.OperatorTester.OperatorResults;

import org.junit.Test;

import com.asakusafw.runtime.core.Result;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test4SumOperator;

public class GroupSortTesterTest {

	/**
	 * @see Test4SumOperator#sort1(List, Result)
	 */
	@Test
	public void sort1() {
		GroupSortTester tester = new GroupSortTester(Test4SumOperator.class, "sort1");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("s1", "abc"));
		list.add(newStoreInfo("s2", "def"));
		list.add(newStoreInfo("s2", "ghi"));
		list.add(newStoreInfo("s3", "jkl"));

		OperatorResults result = tester.execute(list);

		List<StoreInfo> out1 = result.get(0);
		assertThat(out1.size(), is(3));
		assertThat(getStoreInfo(out1, "s1").getStoreNameAsString(), is("abc"));
		assertThat(getStoreInfo(out1, "s2").getStoreNameAsString(), is("ghi"));
		assertThat(getStoreInfo(out1, "s3").getStoreNameAsString(), is("jkl"));
	}

	/**
	 * @see Test4SumOperator#sort2(List, Result, Result)
	 */
	@Test
	public void sort2() {
		GroupSortTester tester = new GroupSortTester(Test4SumOperator.class, "sort2");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("s1", "abc"));
		list.add(newStoreInfo("s2", "def"));
		list.add(newStoreInfo("s2", "ghi"));
		list.add(newStoreInfo("s3", "jkl"));

		OperatorResults result = tester.execute(list);

		List<StoreInfo> out1 = result.get(0);
		assertThat(out1.size(), is(3));
		assertThat(getStoreInfo(out1, "s1").getStoreNameAsString(), is("abc"));
		assertThat(getStoreInfo(out1, "s2").getStoreNameAsString(), is("ghi"));
		assertThat(getStoreInfo(out1, "s3").getStoreNameAsString(), is("jkl"));

		List<StoreInfo> out2 = result.get(1);
		assertThat(out2.size(), is(1));
		assertThat(getStoreInfo(out2, "s2").getStoreNameAsString(), is("def"));
	}

	private static StoreInfo newStoreInfo(String code, String name) {
		StoreInfo result = new StoreInfo();
		result.setStoreCodeAsString(code);
		result.setStoreNameAsString(name);
		return result;
	}

	private static StoreInfo getStoreInfo(List<StoreInfo> list, String code) {
		for (StoreInfo in : list) {
			if (in.getStoreCodeAsString().equals(code)) {
				return in;
			}
		}
		return null;
	}
}
