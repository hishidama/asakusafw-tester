/**
 * Copyright 2015 Hishidama
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.operator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.asakusafw.tester.MasterJoinTester;
import jp.hishidama.asakusafw.tester.MasterJoinTester.MasterJoinResult;

import org.junit.Test;

import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateTime;
import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.JoinedSalesInfo;
import com.example.modelgen.dmdl.model.SalesDetail;

/**
 * {@link CategorySummaryOperator}のテスト。
 */
public class CategorySummaryOperatorTest {

	/**
	 * {@link CategorySummaryOperator#joinItemInfo(ItemInfo, SalesDetail)}
	 */
	@Test
	public void testerTest() {
		// Tester生成
		MasterJoinTester tester = new MasterJoinTester(CategorySummaryOperator.class, "joinItemInfo");

		// 入力データ作成
		List<ItemInfo> master = new ArrayList<>();
		master.add(item("i1", "A", 1, 10));
		master.add(item("i1", "C", 21, 30));

		List<SalesDetail> tx = new ArrayList<>();
		tx.add(sales(123, "i1", 5));
		tx.add(sales(456, "i1", 15));
		tx.add(sales(789, "i1", 25));

		// テストの実行
		MasterJoinResult<JoinedSalesInfo, SalesDetail> result = tester.execute(master, tx);

		// 実行結果の検査
		List<JoinedSalesInfo> joined = result.joined;
		assertThat(joined.size(), is(2));
		assertThat(search(joined, 123).getCategoryCodeAsString(), is("A"));
		assertThat(search(joined, 789).getCategoryCodeAsString(), is("C"));

		List<SalesDetail> missed = result.missed;
		assertThat(missed.size(), is(1));
		assertThat(missed.get(0).getAmount(), is(456));
	}

	private ItemInfo item(String item, String categoryCode, int begin, int end) {
		ItemInfo object = new ItemInfo();
		object.setItemCodeAsString(item);
		object.setCategoryCodeAsString(categoryCode);
		object.setBeginDate(new Date(2011, 1, begin));
		object.setEndDate(new Date(2011, 1, end));
		return object;
	}

	private SalesDetail sales(int amount, String item, int day) {
		SalesDetail object = new SalesDetail();
		object.setSalesDateTime(new DateTime(2011, 1, day, 0, 0, 0));
		object.setItemCodeAsString(item);
		object.setAmount(amount);
		return object;
	}

	private JoinedSalesInfo search(List<JoinedSalesInfo> list, int amount) {
		for (JoinedSalesInfo object : list) {
			if (object.getAmount() == amount) {
				return object;
			}
		}
		fail();
		return null;
	}
}
