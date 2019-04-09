/**
 * 
 */
package mx.budgie.billers.accounts.builder;

import java.util.List;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 * @description Builder item for convert the objeto in other <Source,Target>
 */
public abstract class AbstractBuilder<S, T> implements Bul<S,T>{
	
	
	@Override
	public S createSource() {
		return null;
	}

	@Override
	public T createDocument() {
		return null;
	}

	@Override
	public T buildDocumentFromSource(S source) {
		return null;
	}

	@Override
	public S buildSourceFromDocument(T document) {
		return null;
	}

	@Override
	public List<T> buildListDocumentsFromList(List<S> listSource) {
		return null;
	}

	@Override
	public List<S> buildListFromListDocuments(List<T> listDocuments) {
		return null;
	}

	@Override
	public String buildJsonFromSource(S source) throws Exception {
		return null;
	}
	
}

interface Bul<S,T>{
	
	public abstract S createSource();
	public abstract T createDocument();
	public T buildDocumentFromSource(S source);
	public S buildSourceFromDocument(T document);
	public List<T> buildListDocumentsFromList(List<S> listSource);
	public List<S> buildListFromListDocuments(List<T> listDocuments);
	public String buildJsonFromSource(final S source) throws Exception;
	
}