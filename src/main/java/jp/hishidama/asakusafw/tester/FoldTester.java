package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.Fold;

public class FoldTester extends GroupOpTester {

	public FoldTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Fold.class);
	}

	public <T extends DataModel<?>> List<T> execute(List<T> list, Object... args) {
		checkOperatorMethodParameterCount(2 + args.length);

		List<List<? extends DataModel<?>>> l = new ArrayList<>();
		l.add(list);
		List<GroupOpInput> inputList = divideInput(l);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.add(null);
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		List<T> result = new ArrayList<>();
		for (GroupOpInput input : inputList) {
			List<T> ilist = input.get(0);
			T first = null;
			for (T data : ilist) {
				if (first == null) {
					first = data;
				} else {
					argsArray[0] = first;
					argsArray[1] = data;
					try {
						method.invoke(operator, argsArray);
					} catch (RuntimeException e) {
						throw e;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}

			if (first != null) {
				result.add(first);
			}
		}

		return result;
	}
}
