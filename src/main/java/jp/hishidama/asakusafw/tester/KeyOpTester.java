package jp.hishidama.asakusafw.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.hishidama.asakusafw.tester.KeyWrapper.GroupValue;
import jp.hishidama.asakusafw.tester.KeyWrapper.NameOrder;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.model.Key;

public class KeyOpTester extends OperatorTester {

	protected KeyOpTester(Class<?> operatorClass, String operatorMethodName,
			Class<? extends Annotation> targetAnnotationClass) {
		super(operatorClass, operatorMethodName, targetAnnotationClass);
	}

	protected KeyWrapper findKey(int parameterIndex) {
		Annotation[] annotations = getParameterAnnotation(parameterIndex);
		for (Annotation a : annotations) {
			Class<? extends Annotation> atype = a.annotationType();
			if (Key.class.isAssignableFrom(atype)) {
				return new KeyWrapper((Key) a);
			}
		}
		throw new IllegalStateException(MessageFormat.format("not found @Key. method={0}, parameterIndex={1}",
				getOperatorMethod(), parameterIndex));
	}

	protected final Class<?> getParameterType(int parameterIndex) {
		Method method = getOperatorMethod();
		Class<?>[] types = method.getParameterTypes();
		if (parameterIndex >= types.length) {
			throw new IllegalStateException(MessageFormat.format("not found parameter. method={0}, parameterIndex={1}",
					method, parameterIndex));
		}
		return types[parameterIndex];
	}

	protected final Annotation[] getParameterAnnotation(int parameterIndex) {
		Method method = getOperatorMethod();
		Annotation[][] as = method.getParameterAnnotations();
		if (parameterIndex >= as.length) {
			throw new IllegalStateException(MessageFormat.format("not found parameter. method={0}, parameterIndex={1}",
					method, parameterIndex));
		}
		return as[parameterIndex];
	}

	protected static class KeyDataPair {
		public KeyWrapper key;
		public List<? extends DataModel<?>> dataList;

		public KeyDataPair(KeyWrapper key, List<? extends DataModel<?>> list) {
			this.key = key;
			this.dataList = list;
		}

		@Override
		public String toString() {
			return key.toString() + dataList.toString();
		}
	}

	protected static class DivideResult {
		private final List<List<DataModel<?>>> map;

		public DivideResult(int size) {
			map = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				List<DataModel<?>> list = new ArrayList<>();
				map.add(list);
			}
		}

		public void addData(int index, DataModel<?> data) {
			List<DataModel<?>> list = getDataList(index);
			list.add(data);
		}

		@SuppressWarnings("unchecked")
		public <T extends DataModel<?>> List<T> getDataList(int index) {
			return (List<T>) map.get(index);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(1024);
			for (List<DataModel<?>> list : map) {
				sb.append(list);
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	protected final Map<GroupValue, DivideResult> divide(KeyDataPair... keyData) {
		Map<GroupValue, DivideResult> map = new TreeMap<>();
		int index = 0;
		for (KeyDataPair pair : keyData) {
			KeyWrapper key = pair.key;
			for (DataModel<?> data : pair.dataList) {
				GroupValue value = key.getGroupValue(data);
				DivideResult result = map.get(value);
				if (result == null) {
					result = new DivideResult(keyData.length);
					map.put(value, result);
				}
				result.addData(index, data);
			}
			index++;
		}
		return map;
	}

	protected final <T extends DataModel<?>> void sort(List<T> list, final KeyWrapper key) {
		if (list.isEmpty()) {
			return;
		}

		final List<NameOrder> nameList = key.getOrder();
		Comparator<T> comparator = new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				for (NameOrder order : nameList) {
					Comparable<Object> v1 = key.getValueOption(o1, order.name);
					Comparable<Object> v2 = key.getValueOption(o2, order.name);
					int c;
					if (order.asc) {
						c = v1.compareTo(v2);
					} else {
						c = v2.compareTo(v1);
					}
					if (c != 0) {
						return c;
					}
				}
				return 0;
			}
		};
		Collections.sort(list, comparator);
	}
}
