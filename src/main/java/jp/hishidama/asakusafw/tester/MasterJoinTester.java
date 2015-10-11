package jp.hishidama.asakusafw.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.asakusafw.runtime.model.DataModel;
import com.asakusafw.vocabulary.model.Joined;
import com.asakusafw.vocabulary.model.Joined.Mapping;
import com.asakusafw.vocabulary.model.Joined.Term;
import com.asakusafw.vocabulary.operator.MasterJoin;

public class MasterJoinTester extends MasterOpTester {

	public MasterJoinTester(Class<?> operatorClass, String methodName) {
		super(operatorClass, methodName, MasterJoin.class);
	}

	public static class MasterJoinResult<J, T> {
		public final List<J> joined = new ArrayList<>();
		public final List<T> missed = new ArrayList<>();
	}

	public <M extends DataModel<M>, T extends DataModel<T>, J extends DataModel<J>> MasterJoinResult<J, T> execute(
			List<M> master, List<T> tx) {
		checkJoinModelType(master, tx);

		List<MasterTxPair<M, T>> inputList = resolveInput(master, tx);

		MasterJoinResult<J, T> result = new MasterJoinResult<>();
		for (MasterTxPair<M, T> input : inputList) {
			if (input.master != null) {
				J joined = createJoinedModel(input.master, input.tx);
				result.joined.add(joined);
			} else {
				result.missed.add(input.tx);
			}
		}

		return result;
	}

	protected <M, T> void checkJoinModelType(List<M> master, List<T> tx) {
		Class<?> mclass = getJoinedTerm(0).source();
		Class<?> tclass = getJoinedTerm(1).source();
		checkMasterTxModelType(master, tx, mclass, tclass);
	}

	@Override
	protected KeyWrapper findKey(int parameterIndex) {
		Term term = getJoinedTerm(parameterIndex);
		return new KeyWrapper(term.shuffle());
	}

	@SuppressWarnings("unchecked")
	protected final <M extends DataModel<?>, T extends DataModel<?>, J extends DataModel<?>> J createJoinedModel(
			M master, T tx) {
		J result;
		try {
			result = (J) getJoinedType().newInstance();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for (int i = 0; i < 2; i++) {
			Object input = (i == 0) ? master : tx;
			Term term = getJoinedTerm(i);
			for (Mapping mapping : term.mappings()) {
				String sname = getMethodName("get", mapping.source());
				String dname = getMethodName("set", mapping.destination());
				try {
					Method smethod = term.source().getMethod(sname);
					Object r = smethod.invoke(input);
					Method dmethod = result.getClass().getMethod(dname, r.getClass());
					dmethod.invoke(result, r);
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		return result;
	}

	private static String getMethodName(String prefix, String name) {
		StringBuilder sb = new StringBuilder(name.length() + 16);
		sb.append(prefix);
		sb.append(name.substring(0, 1).toUpperCase());
		sb.append(name.substring(1));
		sb.append("Option");
		return sb.toString();
	}

	protected final Term getJoinedTerm(int parameterIndex) {
		Class<?> rtype = getJoinedType();
		Joined joined = rtype.getAnnotation(Joined.class);
		if (joined == null) {
			throw new IllegalStateException();
		}

		Class<?> ptype = getParameterType(parameterIndex);

		Term[] terms = joined.terms();
		Class<?> type0 = terms[0].source();
		Class<?> type1 = terms[1].source();
		if (type0 == type1) {
			return terms[parameterIndex];
		}
		for (Term term : joined.terms()) {
			if (term.source() == ptype) {
				return term;
			}
		}
		throw new IllegalStateException();
	}

	@SuppressWarnings("unchecked")
	protected final <J> Class<J> getJoinedType() {
		Method method = getOperatorMethod();
		return (Class<J>) method.getReturnType();
	}
}
