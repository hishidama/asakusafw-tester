package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.Extract;

public class ExtractTester extends OperatorTester {

	public ExtractTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Extract.class);
	}

	public <T extends DataModel<T>> OperatorResults execute(List<T> list, Object... args) {
		OperatorResults result = createOperatorResults();
		checkOperatorMethodParameterCount(1 + result.size() + args.length);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.addAll(result.getResults());
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		for (T data : list) {
			argsArray[0] = data;
			try {
				method.invoke(operator, argsArray);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}
}
