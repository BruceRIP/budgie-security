/**
 * 
 */
package mx.budgie.security.sso.builder;

import java.util.List;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 * @description Builder item for convert the object in other <Source,Target>
 */
public abstract class AbstractBuilder<S, T> implements Builder<S,T>{
	
	public abstract T createObject();

	@Override
	public T buildDocumentFromSource(S source) {
		return null;
	}

	@Override
	public S buildSourceFromDocument(T document) {
		return null;
	}

	@Override
	public List<T> buildListDocumentFromList(List<S> listSource) {
		return null;
	}

	@Override
	public String buildJsonFromSource(S source) throws Exception {
		return null;
	}			
	
}

interface Builder<S,T>{
	
	public T buildDocumentFromSource(S source);
	public S buildSourceFromDocument(T document);
	public List<T> buildListDocumentFromList(List<S> listSource);
	public String buildJsonFromSource(final S source) throws Exception;
	
}