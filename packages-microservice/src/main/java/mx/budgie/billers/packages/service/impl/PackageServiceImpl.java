/**
 * 
 */
package mx.budgie.billers.packages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.budgie.billers.packages.mongo.documents.PackageDocument;
import mx.budgie.billers.packages.mongo.repository.PackageRepository;
import mx.budgie.billers.packages.service.PackageService;
import mx.budgie.billers.packages.vo.PackageVO;
import mx.budgie.billers.packages.vo.UpdatePackageVO;

/**
 * @author brucewayne
 *
 */
@Service
public class PackageServiceImpl implements PackageService{

	@Autowired
	private PackageRepository repository;
	
	@Override
	public boolean createPackage(PackageVO packageVO) {
		PackageDocument document = new PackageDocument();
		document.setActive(packageVO.isActive());
		document.setTotalRegisteredCustomer(packageVO.getTotalRegisteredCustomer());
		document.setIdPackage(packageVO.getIdPackage());
		document.setOfferDescription(packageVO.getOfferDescription());
		document.setNamePackage(packageVO.getNamePackage());
		document.setOnSale(packageVO.isOnSale());
		document.setTotalActiveDays(packageVO.getTotalActiveDays());
		document.setTotalActiveSessions(packageVO.getTotalActiveSessions());
		document.setTotalBills(packageVO.getTotalBills());
		document.setTotalFreeBills(packageVO.getTotalFreeBills());
		document.setPrice(packageVO.getPrice());
		document.setTemplateBill(packageVO.getTemplateBill());
		document.setClientManagement(packageVO.getClientManagement());
		document.setPersonalSupport(packageVO.getPersonalSupport());
		document.setServices(packageVO.getServices());
		document.setTimeStorage(packageVO.getTimeStorage());
		document.setAddendas(packageVO.getAddendas());
		document.setTutorials(packageVO.getTutorials());
		document.setScheduledDelivery(packageVO.getScheduledDelivery());
		document.setStatisticsReports(packageVO.getStatisticsReports());
		repository.save(document);
		return Boolean.TRUE;
	}

	@Override
	public PackageVO findPackageByID(Integer id) {
		PackageDocument document = repository.findByIdPackage(id);
		if(null !=  document) {			
			return createPackageVO(document);
		}
		return null;
	}

	@Override
	public List<PackageVO> findPackages() {
		List<PackageDocument> documentList = repository.findAll();
		if(null != documentList && !documentList.isEmpty()) {
			List<PackageVO> packageList = new ArrayList<>();
			for(PackageDocument document : documentList) {				
				packageList.add(createPackageVO(document));
			}
			return packageList;
		}
		return null;
	}

	@Override
	public boolean updatePackage(UpdatePackageVO pkgVO) {
		PackageDocument document = repository.findByIdPackage(pkgVO.getIdPackage());
		if(pkgVO.getNamePackage() != null) {
			document.setNamePackage(pkgVO.getNamePackage());
		}if(pkgVO.getAddendas() != null) {
			document.setAddendas(pkgVO.getAddendas());
		}if(pkgVO.getClientManagement() != null) {
			document.setClientManagement(pkgVO.getClientManagement());
		}if(pkgVO.getOfferDescription() != null) {
			document.setOfferDescription(pkgVO.getOfferDescription());
		}if(pkgVO.getPersonalSupport() != null) {
			document.setPersonalSupport(pkgVO.getPersonalSupport());
		}if(pkgVO.getPrice() != null) {
			document.setPrice(pkgVO.getPrice());
		}if(pkgVO.getScheduledDelivery() != null) {
			document.setScheduledDelivery(pkgVO.getScheduledDelivery());
		}if(pkgVO.getServices() != null) {
			document.setServices(pkgVO.getServices());
		}if(pkgVO.getStatisticsReports() != null) {
			document.setStatisticsReports(pkgVO.getStatisticsReports());
		}if(pkgVO.getTemplateBill() != null) {
			document.setTemplateBill(pkgVO.getTemplateBill());
		}if(pkgVO.getTimeStorage() != null) {
			document.setTimeStorage(pkgVO.getTimeStorage());
		}if(pkgVO.getTotalActiveDays() != null) {
			document.setTotalActiveDays(pkgVO.getTotalActiveDays());
		}if(pkgVO.getTotalActiveSessions() != null) {
			document.setTotalActiveSessions(pkgVO.getTotalActiveSessions());
		}if(pkgVO.getTotalBills() != null) {
			document.setTotalBills(pkgVO.getTotalBills());
		}if(pkgVO.getTotalFreeBills() != null) {
			document.setTotalFreeBills(pkgVO.getTotalFreeBills());
		}if(pkgVO.getTutorials() != null) {
			document.setTutorials(pkgVO.getTutorials());
		}if(pkgVO.isOnSale() != null) {
			document.setOnSale(pkgVO.isOnSale());
		}if(pkgVO.isActive() != null) {
			document.setActive(pkgVO.isActive());
		}if(pkgVO.getTotalRegisteredCustomer() != null) {
			document.setTotalRegisteredCustomer(pkgVO.getTotalRegisteredCustomer());
		}		
		repository.save(document);
		return true;
	}
	
	private PackageVO createPackageVO(PackageDocument document) {
		PackageVO vo = new PackageVO();
		vo.setActive(document.isActive());
		vo.setTotalRegisteredCustomer(document.getTotalRegisteredCustomer());
		vo.setIdPackage(document.getIdPackage());
		vo.setOfferDescription(document.getOfferDescription());
		vo.setNamePackage(document.getNamePackage());
		vo.setOnSale(document.isOnSale());
		vo.setTotalActiveDays(document.getTotalActiveDays());
		vo.setTotalActiveSessions(document.getTotalActiveSessions());
		vo.setTotalBills(document.getTotalBills());
		vo.setTotalFreeBills(document.getTotalFreeBills());
		vo.setPrice(document.getPrice());
		vo.setTemplateBill(document.getTemplateBill());
		vo.setClientManagement(document.getClientManagement().replaceAll("<>", String.valueOf(document.getTotalRegisteredCustomer())));
		vo.setPersonalSupport(document.getPersonalSupport());
		vo.setServices(document.getServices());
		vo.setTimeStorage(document.getTimeStorage());
		vo.setAddendas(document.getAddendas());
		vo.setTutorials(document.getTutorials());
		vo.setScheduledDelivery(document.getScheduledDelivery());
		vo.setStatisticsReports(document.getStatisticsReports());
		return vo;
	}

}
