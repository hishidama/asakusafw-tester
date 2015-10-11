package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.Branch;

public class BranchTester extends OperatorTester {

	public BranchTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Branch.class);
	}

	public <T extends DataModel<T>, E extends Enum<E>> BranchResult<T, E> execute(List<T> list, Object... args) {
		checkOperatorMethodParameterCount(1 + args.length);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		Class<E> branchEnumClass = getBranchEnumClass();
		BranchResult<T, E> result = new BranchResult<>(branchEnumClass);
		for (T data : list) {
			argsArray[0] = data;
			try {
				@SuppressWarnings("unchecked")
				E r = (E) method.invoke(operator, argsArray);
				result.add(r, data);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}
}
