package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.MasterJoinUpdate;

public class MasterJoinUpdateTester extends MasterOpTester {

	public MasterJoinUpdateTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, MasterJoinUpdate.class);
	}

	public static class MasterJoinUpdateResult<T> {
		public final List<T> updated = new ArrayList<>();
		public final List<T> missed = new ArrayList<>();
	}

	public <M extends DataModel<M>, T extends DataModel<T>> MasterJoinUpdateResult<T> execute(List<M> master,
			List<T> tx, Object... args) {
		checkOperatorMethodParameterCount(2 + args.length);
		checkMasterTxModelType(master, tx);

		List<MasterTxPair<M, T>> inputList = resolveInput(master, tx);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		argsList.add(null);
		argsList.add(null);
		argsList.addAll(Arrays.asList(args));
		Object[] argsArray = argsList.toArray();

		MasterJoinUpdateResult<T> result = new MasterJoinUpdateResult<>();
		for (MasterTxPair<M, T> input : inputList) {
			if (input.master != null) {
				argsArray[0] = input.master;
				argsArray[1] = input.tx;
				try {
					method.invoke(operator, argsArray);
					result.updated.add(input.tx);
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				result.missed.add(input.tx);
			}
		}

		return result;
	}
}
