package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.Update;

public class UpdateTester extends OperatorTester {

	public UpdateTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Update.class);
	}

	public <T extends DataModel<T>> List<T> execute(List<T> list, Object... args) {
		checkOperatorMethodParameterCount(1 + args.length);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		List<T> result = new ArrayList<>();
		for (T data : list) {
			argsArray[0] = data;
			try {
				method.invoke(operator, argsArray);
				result.add(data);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}
}
