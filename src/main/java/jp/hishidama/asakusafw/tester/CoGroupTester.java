package jp.hishidama.asakusafw.tester;

import java.util.ArrayList;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.CoGroup;

public class CoGroupTester extends GroupOpTester {

	public CoGroupTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, CoGroup.class);
	}

	@SuppressWarnings("unchecked")
	public OperatorResults execute(Object... listsOrArgs) {
		List<List<? extends DataModel<?>>> lists = new ArrayList<>();
		List<Object> args = new ArrayList<>();

		Class<?>[] types = getOperatorMethod().getParameterTypes();
		int size = Math.min(listsOrArgs.length, types.length);
		boolean isList = true;
		for (int i = 0; i < size; i++) {
			Object obj = listsOrArgs[i];
			if (isList) {
				if (obj instanceof List && types[i].isInstance(obj)) {
					lists.add((List<? extends DataModel<?>>) obj);
					continue;
				}
				isList = false;
			}
			args.add(obj);
		}

		return executeExplicit(lists, args);
	}

	public OperatorResults executeExplicit(List<List<? extends DataModel<?>>> lists, List<Object> args) {
		return executeGroup(lists, args);
	}
}
