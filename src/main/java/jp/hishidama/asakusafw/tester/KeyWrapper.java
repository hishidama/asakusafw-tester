package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.model.PropertyOrder;
import com.asakusafw.vocabulary.model.Key;

public class KeyWrapper {
	private final Key annotation;

	public KeyWrapper(Key annotation) {
		this.annotation = annotation;
	}

	public List<String> getGroupProperties() {
		return Arrays.asList(annotation.group());
	}

	public List<String> getOrderProperties() {
		return Arrays.asList(annotation.order());
	}

	public static class NameOrder {
		public String name;
		public boolean asc;
	}

	public List<NameOrder> getOrder() {
		List<String> propertyList = getOrderProperties();

		List<NameOrder> resultList = new ArrayList<>();
		for (final String property : propertyList) {
			String name = property;
			boolean asc = true;

			String prefix = null;
			if (property.startsWith("+")) {
				prefix = "+";
				name = property.substring(1);
				asc = true;
			} else if (property.startsWith("-")) {
				prefix = "-";
				name = property.substring(1);
				asc = false;
			}
			String suffix = null;
			int n = name.indexOf(' ');
			if (n >= 0) {
				suffix = name.substring(n + 1).trim();
				name = name.substring(0, n);
				if (suffix.equalsIgnoreCase("ASC")) {
					asc = true;
				} else if (suffix.equalsIgnoreCase("DESC")) {
					asc = false;
				} else {
					throw new UnsupportedOperationException(MessageFormat.format(
							"illegal ASC/DESC. order.name=\"{0}\"", property));
				}
			}
			if (prefix != null && suffix != null) {
				throw new UnsupportedOperationException(MessageFormat.format("duplicate {1},{2}. order.name=\"{0}\"",
						property, prefix, suffix));
			}

			NameOrder r = new NameOrder();
			r.name = name;
			r.asc = asc;
			resultList.add(r);
		}
		return resultList;
	}

	public static class GroupValue implements Comparable<GroupValue> {
		private List<Comparable<Object>> list;

		public GroupValue(List<Comparable<Object>> list) {
			this.list = list;
		}

		@Override
		public boolean equals(Object obj) {
			GroupValue that = (GroupValue) obj;
			return compareTo(that) == 0;
		}

		@Override
		public int hashCode() {
			int code = 0;
			for (Comparable<Object> value : list) {
				code ^= value.hashCode();
			}
			return code;
		}

		@Override
		public int compareTo(GroupValue that) {
			if (list.size() != that.list.size()) {
				throw new IllegalStateException(MessageFormat.format("this={0}, that={1}", this, that));
			}
			for (int i = 0; i < list.size(); i++) {
				Comparable<Object> v1 = this.list.get(i);
				Comparable<Object> v2 = that.list.get(i);
				int c = v1.compareTo(v2);
				if (c != 0) {
					return c;
				}
			}
			return 0;
		}

		@Override
		public String toString() {
			return list.toString();
		}
	}

	public GroupValue getGroupValue(DataModel<?> model) {
		List<String> propertyList = getGroupProperties();

		List<Comparable<Object>> result = new ArrayList<>(propertyList.size());
		for (String name : propertyList) {
			Comparable<Object> value = getValueOption(model, name);
			result.add(value);
		}
		return new GroupValue(result);
	}

	@SuppressWarnings("unchecked")
	public Comparable<Object> getValueOption(DataModel<?> model, String name) {
		Method method = getGetOptionMethod(model.getClass(), name);
		try {
			return (Comparable<Object>) method.invoke(model);
		} catch (Exception e) {
			throw new IllegalStateException(MessageFormat.format("invoke error. name=\"{0}\" in {1}", name, model), e);
		}
	}

	private Map<String, Method> getterMap = new HashMap<>();

	private Method getGetOptionMethod(Class<?> modelClass, String name) {
		Method method = getterMap.get(name);
		if (method == null) {
			try {
				String methodName = getGetOptionMethodName(modelClass, name);
				method = modelClass.getMethod(methodName);
			} catch (Exception e) {
				throw new IllegalStateException(MessageFormat.format("not found method. name=\"{0}\" in {1}", name,
						modelClass.getName()), e);
			}
			getterMap.put(name, method);
		}
		return method;
	}

	private static String getGetOptionMethodName(Class<?> modelClass, String name) {
		StringBuilder sb = new StringBuilder(name.length() + 16);
		sb.append("get");

		if (isSnakeCase(modelClass, name)) {
			String[] ss = name.trim().split("_");
			for (String s : ss) {
				if (s.length() >= 1) {
					sb.append(s.substring(0, 1).toUpperCase());
					sb.append(s.substring(1).toLowerCase());
				}
			}
		} else {
			sb.append(name.substring(0, 1).toUpperCase());
			sb.append(name.substring(1));
		}

		sb.append("Option");
		return sb.toString();
	}

	private static boolean isSnakeCase(Class<?> modelClass, String name) {
		PropertyOrder properties = modelClass.getAnnotation(PropertyOrder.class);
		for (String s : properties.value()) {
			if (s.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("@Key(group=");
		sb.append(Arrays.asList(annotation.group()));
		sb.append(", order=");
		sb.append(Arrays.asList(annotation.order()));
		sb.append(")");
		return sb.toString();
	}
}