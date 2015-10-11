package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.MasterBranch;

public class MasterBranchTester extends MasterOpTester {

	public MasterBranchTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, MasterBranch.class);
	}

	public <M extends DataModel<M>, T extends DataModel<T>, E extends Enum<E>> BranchResult<T, E> execute(
			List<M> master, List<T> tx, Object... args) {
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

		Class<E> branchEnumClass = getBranchEnumClass();
		BranchResult<T, E> result = new BranchResult<>(branchEnumClass);
		for (MasterTxPair<M, T> input : inputList) {
			argsArray[0] = input.master;
			argsArray[1] = input.tx;
			try {
				@SuppressWarnings("unchecked")
				E r = (E) method.invoke(operator, argsArray);
				result.add(r, input.tx);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}
}
