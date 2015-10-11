package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.Convert;

public class ConvertTester extends OperatorTester {

	public ConvertTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Convert.class);
	}

	public static class ConvertResult<T, C> {
		public final List<C> out = new ArrayList<>();
		public final List<T> original = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public <T extends DataModel<T>, C extends DataModel<C>> ConvertResult<T, C> execute(List<T> list, Object... args) {
		checkOperatorMethodParameterCount(1 + args.length);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		ConvertResult<T, C> result = new ConvertResult<>();
		for (T data : list) {
			argsArray[0] = data;
			try {
				C r = (C) method.invoke(operator, argsArray);
				C copy = (C) r.getClass().newInstance();
				copy.copyFrom(r);
				result.out.add(copy);
				result.original.add(data);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}
}
