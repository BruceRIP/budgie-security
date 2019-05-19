/**
 * 
 */
package mx.budgie.billers.accounts.service;

import java.util.List;

import mx.budgie.billers.accounts.vo.ClientAuthenticationVO;
import mx.budgie.billers.accounts.vo.TokensResponse;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
public interface ClientAuthService {

	public TokensResponse saveClient(final String billerID, final String applicationName, final String tokenType);
	
	public ClientAuthenticationVO findClientByClientId(final String name);
	
	public List<ClientAuthenticationVO> findClientByBillerID(final String billerID);
	
	public boolean deleteClientByName(final String billerID, final String name);
	
	public ClientAuthenticationVO updateClient(final String billerID, final ClientAuthenticationVO clientVO, final boolean deleted);
	
	public boolean validateAuthenticationClient(final String username, final String password);
	
	public String findClientIdByToken(final String token);
	
}
