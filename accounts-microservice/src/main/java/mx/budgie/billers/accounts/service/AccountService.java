/**
 * 
 */
package mx.budgie.billers.accounts.service;

import mx.budgie.billers.accounts.vo.AccountRequestVO;
import mx.budgie.billers.accounts.vo.AccountVO;
import mx.budgie.billers.accounts.vo.PackageVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
public interface AccountService {

	public AccountVO createAccount(final AccountRequestVO account, final String clientAuthentication, PackageVO packageVO);
	
	public AccountVO findAccountByBillerID(final String billerID);
	
	public AccountVO findAccountByEmail(final String email);
	
	public AccountVO findAccountByNickname(final String nickname);

	public AccountVO updateAccount(final AccountVO account);
	
	public void deleteAccount(final String email);
}
