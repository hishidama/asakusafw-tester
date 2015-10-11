package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.OperatorTester.BranchResult;

import org.junit.Test;

import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test1FlowOperator;
import com.example.operator.Test1FlowOperator.BranchFilter;

public class BranchTesterTest {

	/**
	 * @see Test1FlowOperator#branch1(StoreInfo)
	 */
	@Test
	public void branch1() {
		BranchTester tester = new BranchTester(Test1FlowOperator.class, "branch1");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("", "zz"));
		list.add(newStoreInfo("a", "zz"));
		list.add(newStoreInfo("bb", "zz"));
		list.add(newStoreInfo("ccc", "zz"));
		list.add(newStoreInfo("dddd", "zz"));

		BranchResult<StoreInfo, Test1FlowOperator.BranchFilter> result = tester.execute(list);

		List<StoreInfo> out1 = result.get(BranchFilter.OUT1);
		assertThat(out1.size(), is(1));
		assertThat(out1.get(0).getStoreCodeAsString(), is("a"));

		List<StoreInfo> out2 = result.get(BranchFilter.OUT2);
		assertThat(out2.size(), is(1));
		assertThat(out2.get(0).getStoreCodeAsString(), is("bb"));

		List<StoreInfo> missed = result.get(BranchFilter.MISSED);
		assertThat(missed.size(), is(3));
		assertThat(missed.get(0).getStoreCodeAsString(), is(""));
		assertThat(missed.get(1).getStoreCodeAsString(), is("ccc"));
		assertThat(missed.get(2).getStoreCodeAsString(), is("dddd"));
	}

	/**
	 * @see Test1FlowOperator#branch2(StoreInfo, int)
	 */
	@Test
	public void branch2() {
		BranchTester tester = new BranchTester(Test1FlowOperator.class, "branch2");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("", "zz"));
		list.add(newStoreInfo("a", "zz"));
		list.add(newStoreInfo("bb", "zz"));
		list.add(newStoreInfo("ccc", "zz"));

		BranchResult<StoreInfo, Test1FlowOperator.BranchFilter> result = tester.execute(list, 1);

		List<StoreInfo> out1 = result.get(BranchFilter.OUT1);
		assertThat(out1.size(), is(1));
		assertThat(out1.get(0).getStoreCodeAsString(), is(""));

		List<StoreInfo> out2 = result.get(BranchFilter.OUT2);
		assertThat(out2.size(), is(1));
		assertThat(out2.get(0).getStoreCodeAsString(), is("a"));

		List<StoreInfo> missed = result.get(BranchFilter.MISSED);
		assertThat(missed.size(), is(2));
		assertThat(missed.get(0).getStoreCodeAsString(), is("bb"));
		assertThat(missed.get(1).getStoreCodeAsString(), is("ccc"));
	}

	private static StoreInfo newStoreInfo(String code, String name) {
		StoreInfo result = new StoreInfo();
		result.setStoreCodeAsString(code);
		result.setStoreNameAsString(name);
		return result;
	}
}
