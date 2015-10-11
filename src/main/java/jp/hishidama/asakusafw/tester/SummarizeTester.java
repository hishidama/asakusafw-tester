package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.runtime.value.ByteOption;
import com.asakusafw.runtime.value.DecimalOption;
import com.asakusafw.runtime.value.DoubleOption;
import com.asakusafw.runtime.value.FloatOption;
import com.asakusafw.runtime.value.IntOption;
import com.asakusafw.runtime.value.LongOption;
import com.asakusafw.runtime.value.ShortOption;
import com.asakusafw.runtime.value.ValueOption;
import com.asakusafw.vocabulary.model.Summarized;
import com.asakusafw.vocabulary.model.Summarized.Folding;
import com.asakusafw.vocabulary.model.Summarized.Term;
import com.asakusafw.vocabulary.operator.Summarize;

public class SummarizeTester extends GroupOpTester {

	public SummarizeTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, Summarize.class);
	}

	public <T extends DataModel<T>, S extends DataModel<S>> List<S> execute(List<T> list) {
		checkSummarizeModelType(list);

		List<List<? extends DataModel<?>>> l = new ArrayList<>();
		l.add(list);
		List<GroupOpInput> inputList = divideInput(l);

		Class<S> stype = getSummarizedType();

		List<S> resultList = new ArrayList<>();
		for (GroupOpInput input : inputList) {
			S result;
			try {
				result = (S) stype.newInstance();
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			List<T> ilist = input.get(0);
			boolean first = true;
			for (T in : ilist) {
				summarize(result, in, first);
				first = false;
			}

			resultList.add(result);
		}

		return resultList;
	}

	protected <T> void checkSummarizeModelType(List<T> list) {
		Class<?> clazz = getSummarizedTerm().source();

		for (T in : list) {
			if (in.getClass() != clazz) {
				throw new IllegalArgumentException(MessageFormat.format("unmatch class. expected={0}, data={1}",
						clazz.getSimpleName(), in));
			}
		}
	}

	@Override
	protected KeyWrapper findKey(int parameterIndex) {
		Term term = getSummarizedTerm();
		return new KeyWrapper(term.shuffle());
	}

	protected <T, S> void summarize(S result, T in, boolean first) {
		Class<?> dtype = getSummarizedType();
		Term term = getSummarizedTerm();
		Class<?> stype = term.source();

		for (Folding folding : term.foldings()) {
			String sname = getMethodName(folding.source());
			String dname = getMethodName(folding.destination());
			Object s, d;
			try {
				Method smethod = stype.getMethod(sname);
				s = smethod.invoke(in);
				Method dmethod = dtype.getMethod(dname);
				d = dmethod.invoke(result);
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			try {

				switch (folding.aggregator()) {
				case ANY:
					executeAny(s, d, first);
					break;
				case COUNT:
					executeCount(s, d, first);
					break;
				case MAX:
					executeMax(s, d, first);
					break;
				case MIN:
					executeMin(s, d, first);
					break;
				case SUM:
					executeSum(s, d, first);
					break;
				default:
					throw new UnsupportedOperationException(MessageFormat.format("folding.aggregator={0}",
							folding.aggregator()));
				}
			} catch (Exception e) {
				throw new IllegalStateException(MessageFormat.format("error. {0} {1}.{2} -> {3}.{4}",
						folding.aggregator(), stype.getSimpleName(), sname, dtype.getSimpleName(), dname));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	protected <V extends ValueOption<V>> void executeAny(Object s, Object d, boolean first) {
		checkTypeMismatch(s, d);

		V soption = (V) s;
		V doption = (V) d;
		doption.copyFrom(soption);
	}

	@SuppressWarnings("deprecation")
	protected void executeCount(Object s, Object d, boolean first) {
		if (d instanceof LongOption) {
			LongOption doption = (LongOption) d;
			if (first) {
				doption.modify(1);
			} else {
				doption.add(1);
			}
		} else {
			throw new UnsupportedOperationException(MessageFormat.format("type={0}", d.getClass().getSimpleName()));
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	protected <V extends ValueOption<V>> void executeMax(Object s, Object d, boolean first) {
		checkTypeMismatch(s, d);

		V soption = (V) s;
		V doption = (V) d;
		if (first || soption.compareTo(doption) > 0) {
			doption.copyFrom(soption);
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	protected <V extends ValueOption<V>> void executeMin(Object s, Object d, boolean first) {
		checkTypeMismatch(s, d);

		V soption = (V) s;
		V doption = (V) d;
		if (first || soption.compareTo(doption) < 0) {
			doption.copyFrom(soption);
		}
	}

	protected void executeSum(Object s, Object d, boolean first) {
		if (d instanceof LongOption) {
			executeSum(s, (LongOption) d, first);
		} else if (d instanceof DoubleOption) {
			executeSum(s, (DoubleOption) d, first);
		} else if (d instanceof DecimalOption) {
			executeSum(s, (DecimalOption) d, first);
		} else {
			throw new UnsupportedOperationException(MessageFormat.format("type={0}", d.getClass().getSimpleName()));
		}
	}

	@SuppressWarnings("deprecation")
	protected void executeSum(Object s, LongOption doption, boolean first) {
		long delta;
		if (s instanceof ByteOption) {
			delta = ((ByteOption) s).get();
		} else if (s instanceof ShortOption) {
			delta = ((ShortOption) s).get();
		} else if (s instanceof IntOption) {
			delta = ((IntOption) s).get();
		} else if (s instanceof LongOption) {
			delta = ((LongOption) s).get();
		} else {
			throw new UnsupportedOperationException(MessageFormat.format("type={0}", s.getClass().getSimpleName()));
		}
		if (first) {
			doption.modify(delta);
		} else {
			doption.add(delta);
		}
	}

	@SuppressWarnings("deprecation")
	protected void executeSum(Object s, DoubleOption doption, boolean first) {
		double delta;
		if (s instanceof FloatOption) {
			delta = ((FloatOption) s).get();
		} else if (s instanceof DoubleOption) {
			delta = ((DoubleOption) s).get();
		} else {
			throw new UnsupportedOperationException(MessageFormat.format("type={0}", s.getClass().getSimpleName()));
		}
		if (first) {
			doption.modify(delta);
		} else {
			doption.add(delta);
		}
	}

	@SuppressWarnings("deprecation")
	protected void executeSum(Object s, DecimalOption doption, boolean first) {
		BigDecimal delta;
		if (s instanceof DecimalOption) {
			delta = ((DecimalOption) s).get();
		} else {
			throw new UnsupportedOperationException(MessageFormat.format("type={0}", s.getClass().getSimpleName()));
		}
		if (first) {
			doption.modify(delta);
		} else {
			doption.add(delta);
		}
	}

	private static void checkTypeMismatch(Object s, Object d) {
		if (s.getClass() != d.getClass()) {
			throw new IllegalStateException(MessageFormat.format("type mismatch. src={0}, dst={1}", s.getClass()
					.getSimpleName(), d.getClass().getSimpleName()));
		}
	}

	private static String getMethodName(String name) {
		StringBuilder sb = new StringBuilder(name.length() + 16);
		sb.append("get");
		sb.append(name.substring(0, 1).toUpperCase());
		sb.append(name.substring(1));
		sb.append("Option");
		return sb.toString();
	}

	protected final Term getSummarizedTerm() {
		Class<?> rtype = getSummarizedType();
		Summarized sum = rtype.getAnnotation(Summarized.class);
		if (sum == null) {
			throw new IllegalStateException();
		}
		return sum.term();
	}

	@SuppressWarnings("unchecked")
	protected final <S> Class<S> getSummarizedType() {
		Method method = getOperatorMethod();
		return (Class<S>) method.getReturnType();
	}
}
