package jp.hishidama.asakusafw.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.hishidama.asakusafw.tester.KeyWrapper.GroupValue;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.operator.MasterSelection;

public class MasterOpTester extends KeyOpTester {

	protected MasterOpTester(Class<?> operatorClass, String methodName,
			Class<? extends Annotation> targetAnnotationClass) {
		super(operatorClass, methodName, targetAnnotationClass);
	}

	protected final <M, T> void checkMasterTxModelType(List<M> master, List<T> tx) {
		Class<?>[] types = getOperatorMethod().getParameterTypes();
		Class<?> mclass = types[0];
		Class<?> tclass = types[1];
		checkMasterTxModelType(master, tx, mclass, tclass);
	}

	protected final <M, T> void checkMasterTxModelType(List<M> master, List<T> tx, Class<?> mclass, Class<?> tclass) {
		for (M in : master) {
			if (in.getClass() != mclass) {
				throw new IllegalArgumentException(MessageFormat.format("unmatch class. expected={0}, master={1}",
						mclass.getSimpleName(), in));
			}
		}

		for (T in : tx) {
			if (in.getClass() != tclass) {
				throw new IllegalArgumentException(MessageFormat.format("unmatch class. expected={0}, tx={1}",
						tclass.getSimpleName(), in));
			}
		}
	}

	public static class MasterOpInput<M, T> {
		public List<M> master;
		public List<T> tx;
	}

	public static class MasterTxPair<M, T> {
		public M master;
		public T tx;
	}

	public <M extends DataModel<?>, T extends DataModel<?>> List<MasterOpInput<M, T>> divideInput(List<M> master,
			List<T> tx) {
		KeyDataPair m = new KeyDataPair(findKey(0), master);
		KeyDataPair t = new KeyDataPair(findKey(1), tx);
		Map<GroupValue, DivideResult> map = super.divide(m, t);

		List<MasterOpInput<M, T>> resultList = new ArrayList<>();
		for (DivideResult dr : map.values()) {
			MasterOpInput<M, T> result = new MasterOpInput<>();
			result.master = dr.getDataList(0);
			result.tx = dr.getDataList(1);
			resultList.add(result);
		}
		return resultList;
	}

	public <M extends DataModel<?>, T extends DataModel<?>> List<MasterTxPair<M, T>> resolveInput(List<M> master,
			List<T> tx) {
		List<MasterOpInput<M, T>> sort = divideInput(master, tx);

		List<MasterTxPair<M, T>> resultList = new ArrayList<>();
		for (MasterOpInput<M, T> input : sort) {
			for (T tx0 : input.tx) {
				MasterTxPair<M, T> result = new MasterTxPair<>();

				if (input.master.isEmpty()) {
					result.master = null;
				} else {
					result.master = selectMaster(input.master, tx0);
				}

				result.tx = tx0;
				resultList.add(result);
			}
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	protected final <M extends DataModel<?>, T extends DataModel<?>> M selectMaster(List<M> master, T tx0) {
		assert !master.isEmpty();
		M master0 = master.get(0);

		Method method = getMasterSelectionMethod(master0.getClass(), tx0.getClass());
		if (method == null) {
			// マスター選択演算子が無い場合、masterのどれか1つを返す。
			// 複数のマスターレコードがあった場合、どれが返るかは未定義。
			return master0;
		}

		try {
			Object operator = getOperatorImpl();
			return (M) method.invoke(operator, master, tx0);
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("invoke MasterSelection-method error. method={0}", method),
					e);
		}
	}

	private boolean searchMasterSelectionMethod = false;

	private Method masterSelectionMethod;

	protected final Method getMasterSelectionMethod(Class<?> masterClass, Class<?> txClass) {
		if (searchMasterSelectionMethod) {
			return masterSelectionMethod;
		}
		searchMasterSelectionMethod = true;

		String methodName = getMasterSelectionMethodName();
		if (methodName == null) {
			masterSelectionMethod = null;
			return masterSelectionMethod;
		}

		Method method;
		try {
			method = getOperatorClass().getMethod(methodName, List.class, txClass);
		} catch (Exception e) {
			throw new IllegalStateException(MessageFormat.format("search MasterSelection error. selection=\"{0}\"",
					methodName), e);
		}

		MasterSelection a = method.getAnnotation(MasterSelection.class);
		if (a == null) {
			throw new IllegalStateException(MessageFormat.format("@MasterSelection not specified. method={0}", method));
		}

		masterSelectionMethod = method;
		return masterSelectionMethod;
	}

	protected final String getMasterSelectionMethodName() {
		for (Annotation a : getOperatorMethod().getAnnotations()) {
			if (getTargetAnnotationClass().isAssignableFrom(a.annotationType())) {
				try {
					Method selectionMethod = a.getClass().getMethod("selection");
					String name = (String) selectionMethod.invoke(a);
					if (MasterSelection.NO_SELECTION.equals(name)) {
						return null;
					}
					return name;
				} catch (Exception e) {
					throw new IllegalStateException("MasterOp \"selection\" error", e);
				}
			}
		}
		return null;
	}
}
