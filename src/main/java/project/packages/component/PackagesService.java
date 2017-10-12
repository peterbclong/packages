package project.packages.component;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Provides packages services.
 */
@Service
public class PackagesService {
  private PackagePriceAccumulator accumulator;
  private PackageProductPopulator populator;
  private PackageDao dao;

  PackagesService(PackageDao dao, PackagePriceAccumulator accumulator, PackageProductPopulator populator) {
    this.accumulator = accumulator;
    this.populator = populator;
    this.dao = dao;
  }

  public long createPackage(Package toCreate) throws Exception {
    if (toCreate.products == null || toCreate.products.isEmpty()) {
      throw new IllegalArgumentException("Package products must be supplied");
    }
    return dao.create(populator.populate(toCreate));
  }

  public Optional<Package> getPackage(long id, String currency) throws Exception {
    Package maybePackage = dao.read(id);
    return maybePackage != null ? Optional.of(accumulator.accumulate(maybePackage, currency)) : Optional.empty();
  }

  public boolean updatePackage(long id, Package toUpdate) throws Exception {
    if (toUpdate.products == null || toUpdate.products.isEmpty()) {
      throw new IllegalArgumentException("Package products must be supplied");
    }
    toUpdate.id = id;
    return dao.update(populator.populate(toUpdate));
  }

  public Collection<Package> getAllPackages() throws Exception {
    return dao.readAll();
  }

  public boolean deletePackage(long id) {
    return dao.delete(id);
  }
}
