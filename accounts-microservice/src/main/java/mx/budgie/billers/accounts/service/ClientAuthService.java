/**
 * 
 */
package mx.budgie.billers.accounts.service;

import mx.budgie.billers.accounts.vo.TokensResponse;
import mx.budgie.billers.accounts.vo.ClientAuthenticationVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
public interface ClientAuthService {

	public TokensResponse saveClient(final String clientName);
	
	public ClientAuthenticationVO findClientByClientId(final String name);
	
	public boolean deleteClientByName(final String name);
	
	public ClientAuthenticationVO updateClient(final ClientAuthenticationVO clientVO, final boolean deleted);
	
	public boolean validateAuthenticationClient(final String username, final String password);
	
}
