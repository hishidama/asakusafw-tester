package jp.hishidama.asakusafw.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.runtime.model.DataModel;

public class OperatorTester {

	private final Class<?> operatorClass;
	private final Method operatorMethod;
	private final Object operatorImpl;
	private final Class<? extends Annotation> targetAnnotationClass;

	protected OperatorTester(Class<?> operatorClass, String operatorMethodName,
			Class<? extends Annotation> targetAnnotationClass) {
		if (operatorClass == null) {
			throw new IllegalArgumentException(MessageFormat.format("illegal operatorClass={0}", operatorClass));
		}
		if (operatorMethodName == null) {
			throw new IllegalArgumentException(MessageFormat.format("illegal operatorMethodName={0}",
					operatorMethodName));
		}
		assert targetAnnotationClass != null;

		this.targetAnnotationClass = targetAnnotationClass;

		String name = operatorClass.getName();
		if (name.endsWith("Impl")) {
			name = name.substring(0, name.length() - 4);
			try {
				operatorClass = Class.forName(name);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(e);
			}
		}
		this.operatorClass = operatorClass;

		String implName = name + "Impl";
		try {
			Class<?> implClass = Class.forName(implName);
			this.operatorImpl = implClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		Method method;
		{
			List<Method> list = new ArrayList<>();
			for (Method m : operatorClass.getMethods()) {
				if (m.getName().equals(operatorMethodName)) {
					list.add(m);
				}
			}
			if (list.isEmpty()) {
				throw new IllegalArgumentException(MessageFormat.format("no such method. method={0}#{1}",
						operatorClass.getName(), operatorMethodName));
			} else if (list.size() != 1) {
				throw new IllegalArgumentException(MessageFormat.format("duplicate method. method={0}#{1}",
						operatorClass.getName(), operatorMethodName));
			}
			method = list.get(0);
		}
		this.operatorMethod = method;

		List<Class<? extends Annotation>> list = new ArrayList<>();
		for (Annotation a : method.getAnnotations()) {
			Class<? extends Annotation> c = a.annotationType();
			if (targetAnnotationClass.isAssignableFrom(c)) {
				list.add(c);
				break;
			}
		}
		if (list.size() != 1) {
			throw new IllegalArgumentException(MessageFormat.format(
					"illegal method annotation. {2} is expected @{3}. method={0}#{1}", operatorClass.getName(),
					operatorMethodName, this.getClass().getSimpleName(), targetAnnotationClass.getSimpleName()));
		}
	}

	public final Class<?> getOperatorClass() {
		return operatorClass;
	}

	@SuppressWarnings("unchecked")
	public final <O> O getOperatorImpl() {
		return (O) operatorImpl;
	}

	public final Class<? extends Annotation> getTargetAnnotationClass() {
		return targetAnnotationClass;
	}

	public final Method getOperatorMethod() {
		return operatorMethod;
	}

	protected final void checkOperatorMethodParameterCount(int argsCount) {
		if (getOperatorMethod().getParameterTypes().length != argsCount) {
			throw new IllegalArgumentException(MessageFormat.format(
					"illegal arguments. method={0}, your args count={1}", operatorMethod, argsCount));
		}
	}

	public static class OperatorResults {
		private final List<TesterMockResult<?>> map;

		public OperatorResults(int size) {
			map = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				TesterMockResult<?> result = new TesterMockResult<>();
				map.add(result);
			}
		}

		public int size() {
			return map.size();
		}

		public List<TesterMockResult<?>> getResults() {
			return map;
		}

		@SuppressWarnings("unchecked")
		public <M extends DataModel<?>> List<M> get(int index) {
			TesterMockResult<?> result = map.get(index);
			return (List<M>) result.getResults();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(1024);
			int i = 0;
			for (TesterMockResult<?> result : map) {
				sb.append(i++);
				sb.append("=");
				sb.append(result);
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	protected final OperatorResults createOperatorResults() {
		Method method = getOperatorMethod();
		int size = 0;
		for (Class<?> ptype : method.getParameterTypes()) {
			if (Result.class.isAssignableFrom(ptype)) {
				size++;
			}
		}
		return new OperatorResults(size);
	}

	@SuppressWarnings("unchecked")
	protected final <E extends Enum<E>> Class<E> getBranchEnumClass() {
		Method method = getOperatorMethod();
		return (Class<E>) method.getReturnType();
	}

	public static class BranchResult<T, E extends Enum<E>> {
		private final Map<E, List<T>> map;

		public BranchResult(Class<E> keyType) {
			map = new EnumMap<>(keyType);
		}

		public void add(E key, T data) {
			List<T> list = map.get(key);
			if (list == null) {
				list = new ArrayList<>();
				map.put(key, list);
			}
			list.add(data);
		}

		public List<T> get(E key) {
			return map.get(key);
		}
	}
}
