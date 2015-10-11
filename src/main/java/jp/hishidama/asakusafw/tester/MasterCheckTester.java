package jp.hishidama.asakusafw.tester;

import java.util.ArrayList;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.MasterCheck;

public class MasterCheckTester extends MasterOpTester {

	public MasterCheckTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, MasterCheck.class);
	}

	public static class MasterCheckResult<T> {
		public final List<T> found = new ArrayList<>();
		public final List<T> missed = new ArrayList<>();
	}

	public <M extends DataModel<M>, T extends DataModel<T>> MasterCheckResult<T> execute(List<M> master, List<T> tx) {
		checkMasterTxModelType(master, tx);

		List<MasterTxPair<M, T>> inputList = resolveInput(master, tx);

		MasterCheckResult<T> result = new MasterCheckResult<>();
		for (MasterTxPair<M, T> input : inputList) {
			if (input.master != null) {
				result.found.add(input.tx);
			} else {
				result.missed.add(input.tx);
			}
		}

		return result;
	}
}
