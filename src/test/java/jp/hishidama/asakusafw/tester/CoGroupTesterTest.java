package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.OperatorTester.OperatorResults;

import org.junit.Test;

import com.asakusafw.runtime.core.Result;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test3JoinOperator;

public class CoGroupTesterTest {

	/**
	 * @see Test3JoinOperator#group11(List, Result)
	 */
	@Test
	public void group11() {
		CoGroupTester tester = new CoGroupTester(Test3JoinOperator.class, "group11");

		List<StoreInfo> list1 = new ArrayList<>();
		list1.add(newStoreInfo("s1", "abc"));
		list1.add(newStoreInfo("s2", "def"));
		list1.add(newStoreInfo("s2", "ghi"));
		list1.add(newStoreInfo("s3", "jkl"));

		OperatorResults result = tester.execute(list1);

		List<StoreInfo> out1 = result.get(0);
		assertThat(out1.size(), is(3));
		assertThat(getStoreInfo(out1, "s1").getStoreNameAsString(), is("abc"));
		assertThat(getStoreInfo(out1, "s2").getStoreNameAsString(), is("ghi"));
		assertThat(getStoreInfo(out1, "s3").getStoreNameAsString(), is("jkl"));
	}

	/**
	 * @see Test3JoinOperator#group21(List, List, Result)
	 */
	@Test
	public void group21() {
		CoGroupTester tester = new CoGroupTester(Test3JoinOperator.class, "group21");

		List<StoreInfo> list1 = new ArrayList<>();
		list1.add(newStoreInfo("s1", "abc"));
		list1.add(newStoreInfo("s2", "def"));
		list1.add(newStoreInfo("s3", "ghi"));
		list1.add(newStoreInfo("s3", "jkl"));

		List<StoreInfo> list2 = new ArrayList<>();
		list2.add(newStoreInfo("s2", "123"));
		list2.add(newStoreInfo("s3", "301"));
		list2.add(newStoreInfo("s3", "302"));
		list2.add(newStoreInfo("s3", "303"));
		list2.add(newStoreInfo("s4", "789"));

		OperatorResults result = tester.execute(list1, list2);

		List<StoreInfo> out1 = result.get(0);
		assertThat(out1.size(), is(1 + 1 * 1 + 2 * 3 + 1));
		assertThat(getStoreInfo(out1, "s1", "abc"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s2", "def123"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "ghi301"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "ghi302"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "ghi303"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "jkl301"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "jkl302"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s3", "jkl303"), is(not(nullValue())));
		assertThat(getStoreInfo(out1, "s4", "789"), is(not(nullValue())));
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

	private static StoreInfo getStoreInfo(List<StoreInfo> list, String code, String name) {
		for (StoreInfo in : list) {
			if (in.getStoreCodeAsString().equals(code) && in.getStoreNameAsString().equals(name)) {
				return in;
			}
		}
		return null;
	}
}
