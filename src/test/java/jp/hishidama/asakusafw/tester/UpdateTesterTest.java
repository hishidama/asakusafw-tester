package jp.hishidama.asakusafw.tester;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.Test2DataOperator;

public class UpdateTesterTest {

	/**
	 * @see Test2DataOperator#update1(StoreInfo)
	 */
	@Test
	public void update1() {
		UpdateTester tester = new UpdateTester(Test2DataOperator.class, "update1");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("123", "zz"));
		list.add(newStoreInfo("456", "zz"));

		List<StoreInfo> result = tester.execute(list);

		assertThat(result.size(), is(2));
		assertThat(result.get(0).getStoreNameAsString(), is("123-name"));
		assertThat(result.get(1).getStoreNameAsString(), is("456-name"));
	}

	/**
	 * @see Test2DataOperator#update2(StoreInfo, String)
	 */
	@Test
	public void update2() {
		UpdateTester tester = new UpdateTester(Test2DataOperator.class, "update2");

		List<StoreInfo> list = new ArrayList<>();
		list.add(newStoreInfo("123", "zz"));
		list.add(newStoreInfo("456", "zz"));

		List<StoreInfo> result = tester.execute(list, "aaa");

		assertThat(result.size(), is(2));
		assertThat(result.get(0).getStoreNameAsString(), is("123aaa"));
		assertThat(result.get(1).getStoreNameAsString(), is("456aaa"));
	}

	private static StoreInfo newStoreInfo(String code, String name) {
		StoreInfo result = new StoreInfo();
		result.setStoreCodeAsString(code);
		result.setStoreNameAsString(name);
		return result;
	}
}
