package jp.hishidama.asakusafw.tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.GroupSort;

public class GroupSortTester extends GroupOpTester {

	public GroupSortTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, GroupSort.class);
	}

	public OperatorResults execute(List<? extends DataModel<?>> list, Object... args) {
		List<List<? extends DataModel<?>>> temp = new ArrayList<>(1);
		temp.add(list);
		return executeGroup(temp, Arrays.asList(args));
	}
}
