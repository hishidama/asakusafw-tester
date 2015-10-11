package jp.hishidama.asakusafw.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.hishidama.asakusafw.tester.KeyWrapper.GroupValue;

import com.asakusafw.runtime.model.DataModel;

public class GroupOpTester extends KeyOpTester {

	public GroupOpTester(Class<?> operatorClass, String methodName, Class<? extends Annotation> targetAnnotationClass) {
		super(operatorClass, methodName, targetAnnotationClass);
	}

	protected final OperatorResults executeGroup(List<List<? extends DataModel<?>>> lists, List<Object> args) {
		OperatorResults result = createOperatorResults();
		checkOperatorMethodParameterCount(lists.size() + result.size() + args.size());

		List<GroupOpInput> inputList = divideInput(lists);

		Object operator = getOperatorImpl();
		Method method = getOperatorMethod();
		List<Object> argsList = new ArrayList<>();
		for (int i = 0; i < lists.size(); i++) {
			argsList.add(null);
		}
		argsList.addAll(result.getResults());
		argsList.addAll(args);
		Object[] argsArray = argsList.toArray();

		for (GroupOpInput input : inputList) {
			for (int i = 0; i < input.size(); i++) {
				KeyWrapper key = input.getKey(i);
				List<? extends DataModel<?>> list = input.get(i);
				sort(list, key);

				argsArray[i] = list;
			}
			try {
				method.invoke(operator, argsArray);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return result;
	}

	public static class GroupOpInput {
		private final List<KeyWrapper> keyList;
		private final List<List<DataModel<?>>> map;

		public GroupOpInput(List<KeyDataPair> keyDataList) {
			int size = keyDataList.size();
			this.keyList = new ArrayList<>(size);
			this.map = new ArrayList<>(size);
			for (KeyDataPair keyData : keyDataList) {
				keyList.add(keyData.key);
				List<DataModel<?>> list = new ArrayList<>();
				map.add(list);
			}
		}

		public int size() {
			return keyList.size();
		}

		public KeyWrapper getKey(int index) {
			return keyList.get(index);
		}

		public void add(int index, DataModel<?> data) {
			List<DataModel<?>> list = map.get(index);
			list.add(data);
		}

		@SuppressWarnings("unchecked")
		public <M> List<M> get(int index) {
			return (List<M>) map.get(index);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(1024);
			int i = 0;
			for (KeyWrapper key : keyList) {
				sb.append(key);
				sb.append("-");
				sb.append(map.get(i++));
				sb.append("\n");
			}
			return sb.toString();
		}
	}

	public List<GroupOpInput> divideInput(List<List<? extends DataModel<?>>> listList) {
		List<KeyDataPair> keyDataList = new ArrayList<>(listList.size());
		{
			int i = 0;
			for (List<? extends DataModel<?>> list : listList) {
				KeyDataPair key = new KeyDataPair(findKey(i++), list);
				keyDataList.add(key);
			}
		}
		Map<GroupValue, DivideResult> map = super.divide(keyDataList.toArray(new KeyDataPair[keyDataList.size()]));

		List<GroupOpInput> resultList = new ArrayList<>();
		for (DivideResult dr : map.values()) {
			GroupOpInput result = new GroupOpInput(keyDataList);
			for (int i = 0; i < keyDataList.size(); i++) {
				List<DataModel<?>> list = dr.getDataList(i);
				for (DataModel<?> data : list) {
					result.add(i, data);
				}
			}
			resultList.add(result);
		}
		return resultList;
	}
}
