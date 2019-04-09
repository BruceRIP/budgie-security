/**
 * 
 */
package mx.budgie.billers.packages.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mx.budgie.billers.packages.constant.PackagesConstants;
import mx.budgie.billers.packages.service.PackageService;
import mx.budgie.billers.packages.vo.PackageVO;
import mx.budgie.billers.packages.vo.UpdatePackageVO;

/**
 * @author brucewayne
 *
 */
@RestController
@RequestMapping(value = PackagesConstants.ROOT_PATH)
@Api(value = PackagesConstants.ROOT_PATH)
public class PackageController {
	
	@Autowired
	private PackageService service;

	@ApiOperation(value = "Create package for billers", notes = "It needs al packages fields")
	@PostMapping(value=PackagesConstants.PACKAGE_CREATE
			,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> createPackage(@RequestBody @Valid PackageVO packageVO){				
		boolean flag = service.createPackage(packageVO);
		JSONObject json = new JSONObject();
		try {
			if(flag){			
				json.put("code", 200);
				json.put("message", "Successful");
				return new ResponseEntity<Object>(json.toString(), HttpStatus.OK);
			}
			json.put("code", 200);
			json.put("message", "Successful");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(json.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Get all package of billers", notes = "Get all packages")
	@GetMapping(value=PackagesConstants.PACKAGE_RECOVER			
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> findAllPackages(){
		List<PackageVO> packagesList = service.findPackages();
		return new ResponseEntity<Object>(packagesList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a specific package of billers", notes = "Get package by package id")
	@GetMapping(value=PackagesConstants.PACKAGE_RECOVER_BY_ID
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> findPackage(final @PathVariable("packageID") Integer packageID){
		PackageVO packageVO = service.findPackageByID(packageID);
		return new ResponseEntity<Object>(packageVO, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Update a specific package of billers", notes = "It needs the package object ")	
	@PutMapping(value=PackagesConstants.PACKAGE_UPDATE
			,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE
			,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> updatePackage(@RequestBody @Valid UpdatePackageVO packageVO){				
		boolean flag = service.updatePackage(packageVO);
		JSONObject json = new JSONObject();
		try {
			if(flag){			
				json.put("code", 200);
				json.put("message", "Successful");
				return new ResponseEntity<Object>(json.toString(), HttpStatus.OK);
			}
			json.put("code", 200);
			json.put("message", "Successful");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(json.toString(), HttpStatus.BAD_REQUEST);
	}
}
