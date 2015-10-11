package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.testing.MockResult;
import com.asakusafw.runtime.value.ByteOption;
import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateOption;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateTimeOption;
import com.asakusafw.runtime.value.DoubleOption;
import com.asakusafw.runtime.value.FloatOption;
import com.asakusafw.runtime.value.IntOption;
import com.asakusafw.runtime.value.LongOption;
import com.asakusafw.runtime.value.ShortOption;
import com.asakusafw.runtime.value.StringOption;
import com.asakusafw.runtime.value.ValueOption;

public class TesterMockResult<T extends DataModel<T>> extends MockResult<T> {

	@Override
	public void add(T result) {
		super.add(result);

		// Resultにaddした後のオブジェクトは破壊されることがある、のをエミュレート
		destroy(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T bless(T result) {
		try {
			T copy = (T) result.getClass().newInstance();
			copy.copyFrom(result);
			return copy;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("deprecation")
	protected void destroy(T result) {
		for (Method method : result.getClass().getMethods()) {
			String name = method.getName();
			if (name.startsWith("get") && name.endsWith("Option")) {
				try {
					Object object = method.invoke(result);
					if (object instanceof StringOption) {
						((StringOption) object).modify("destroy by Result#add()");
					} else if (object instanceof ByteOption) {
						((ByteOption) object).modify(Byte.MAX_VALUE);
					} else if (object instanceof ShortOption) {
						((ShortOption) object).modify(Short.MAX_VALUE);
					} else if (object instanceof IntOption) {
						((IntOption) object).modify(Integer.MAX_VALUE);
					} else if (object instanceof LongOption) {
						((LongOption) object).modify(Long.MAX_VALUE);
					} else if (object instanceof FloatOption) {
						((FloatOption) object).modify(Float.MAX_VALUE);
					} else if (object instanceof DoubleOption) {
						((DoubleOption) object).modify(Double.MAX_VALUE);
					} else if (object instanceof DateOption) {
						((DateOption) object).modify(new Date(1234, 12, 31));
					} else if (object instanceof DateTimeOption) {
						((DateTimeOption) object).modify(new DateTime(1111));
					} else if (object instanceof ValueOption) {
						((ValueOption<?>) object).setNull();
					}
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public String toString() {
		return getResults().toString();
	}
}
