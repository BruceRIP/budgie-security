/**
 * 
 */
package mx.budgie.billers.packages.service;

import java.util.List;

import mx.budgie.billers.packages.vo.PackageVO;
import mx.budgie.billers.packages.vo.UpdatePackageVO;

/**
 * @author brucewayne
 *
 */
public interface PackageService {

	public boolean createPackage(PackageVO packageVO);
	
	public PackageVO findPackageByID(Integer id);
	
	public List<PackageVO> findPackages();
	
	public boolean updatePackage(UpdatePackageVO pkgVO);
}
