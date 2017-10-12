package project.packages.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import project.packages.component.Package;
import project.packages.component.PackagesService;

/**
 * Provides package manipulation end-points.
 */
@RestController
@RequestMapping("/packages")
@Api("Store and Retrieve Packages (Lists of Products)")
public class PackagesController {
  private PackagesService service;

  @Autowired
  PackagesController(PackagesService service) {
    this.service = service;
  }

  @RequestMapping(consumes = "application/json", method = RequestMethod.POST)
  @ApiOperation(value = "Create a Package",
			notes = "The package id and price are not required. " +
			        "The products field is required and must contain at least one product. " +
							"For each product, the name and usdPrice fields are not required. " +
							"The identifier of the newly created package is contained in the location response header.")
  public ResponseEntity<?> createPackage(@RequestBody Package toCreate) {
    if (toCreate == null) {
      return ResponseEntity.badRequest().build();
    }
    try {
      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(service.createPackage(toCreate)).toUri()).build();
    } catch (IllegalArgumentException x) {
      return ResponseEntity.badRequest().build();
    } catch (Exception x) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{id}")
  @ApiOperation(value = "Retrieve a Package",
		 	notes = "A currency code may be supplied. " +
			        "In the returned package, the price field will contain the sum of the products' prices, converted " +
							"from USD if requested.")
  public ResponseEntity<Package> getPackage(@PathVariable(name = "id") long id,
                                            @RequestParam(defaultValue = "USD", name = "currency", required = false)
                                                String currency) {
    try {
      Optional<Package> maybePackage = service.getPackage(id, currency);
      return maybePackage.isPresent() ? ResponseEntity.ok(maybePackage.get()) : ResponseEntity.notFound().build();
    } catch (Exception x) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @RequestMapping(consumes = "application/json", method = RequestMethod.PUT, value = "/{id}")
  @ApiOperation(value = "Update a Package",
			notes = "The package price is not required. " +
			        "The products field is required and must contain at least one product. " +
							"For each product, the name and usdPrice fields are not required.")
  public ResponseEntity<Package> updatePackage(@PathVariable(name = "id") long id, @RequestBody Package toUpdate) {
    if (toUpdate == null) {
      return ResponseEntity.badRequest().build();
    }
    try {
      return service.updatePackage(id, toUpdate) ? ResponseEntity.noContent().build() :
          ResponseEntity.notFound().build();
    } catch (IllegalArgumentException x) {
      return ResponseEntity.badRequest().build();
    } catch (Exception x) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @ApiOperation("Delete a Package")
  public ResponseEntity<Package> deletePackage(@PathVariable(name = "id") long id) {
    return service.deletePackage(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation("Retrieve All Packages")
  public ResponseEntity<Collection<Package>> getAllPackages() {
    try {
      return ResponseEntity.ok(service.getAllPackages());
    } catch (Exception x) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}

