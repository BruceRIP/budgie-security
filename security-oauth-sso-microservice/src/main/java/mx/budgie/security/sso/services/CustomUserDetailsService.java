/**
 * 
 */
package mx.budgie.security.sso.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.AccountAuthorizationDocument;
import mx.budgie.billers.accounts.mongo.documents.AccountStatus;
import mx.budgie.billers.accounts.mongo.repositories.AccountsRepository;
import mx.budgie.security.sso.builder.AccountBuilder;
import mx.budgie.security.sso.constants.SecurityConstants;
import mx.budgie.security.sso.vo.AccountVO;
import mx.budgie.security.sso.vo.UserVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Service(SecurityConstants.SERVICE_CUSTOM_USER_DETAIL)
public class CustomUserDetailsService implements UserDetailsService{

	private final Logger LOGGER = LogManager.getLogger(getClass());	
	@Autowired
	@Qualifier(RepositoryConstants.MONGO_BILLER_ACCOUNT_REPOSITORY)
	private AccountsRepository accountRepository;
	
	@Autowired
	private AccountBuilder accountBuilder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("--- Looking for user from User by username {} ---", username);
		Assert.notNull(accountRepository, "Account Repository can't null");
		AccountAuthorizationDocument document = accountRepository.findByNickname(username);
		if(document != null){
			AccountVO account = accountBuilder.buildSourceFromDocument(document);
			if(null != account){
				LOGGER.info("--- User is in the box {} ---", account.getNickname());
				UserVO user = new UserVO();				
				user.setEnabled(AccountStatus.validateStatus(account.getAccountStatus().toString()));
				user.setNickname(account.getNickname());
				user.setPassword(account.getPassword());
				user.setAuthorities(account.getRoles());
				return user; 
			}
		}
		LOGGER.error("User not found");
		throw new UsernameNotFoundException("User not found, check the correct name in the db");		
	}

}
