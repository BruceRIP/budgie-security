/**
 * 
 */
package mx.budgie.billers.accounts.service;

import java.util.Set;

import mx.budgie.billers.accounts.vo.AccountRequest;
import mx.budgie.billers.accounts.vo.AccountVO;
import mx.budgie.billers.accounts.vo.PackageVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
public interface AccountService {

	public AccountVO createAccount(final AccountRequest account, final String clientAuthentication, PackageVO packageVO);
	
	public AccountVO findAccountByBillerID(final String billerID);
	
	public AccountVO findAccountByEmail(final String email);
	
	public AccountVO findAccountByNickname(final String nickname);

	public AccountVO updateAccount(final AccountVO account);
	
	public void deleteAccount(final String email);
	
	public AccountVO findAccountToActivate(final String accountReference);
	
	public AccountVO updateRoles(final String billerID, final Set<String> params, boolean deleted);
	
	public AccountVO resendActivationCode(final AccountVO account);
}
