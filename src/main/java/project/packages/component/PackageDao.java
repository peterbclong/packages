package project.packages.component;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Provides access to a repository for packages.
 */
@Service
class PackageDao {
  private Map<Long, Package> repository = Collections.synchronizedMap(new HashMap<>());
  private AtomicLong idGenerator = new AtomicLong();

  long create(Package toCreate) {
    long id = idGenerator.getAndIncrement();
    toCreate.id = id;
    repository.put(id, toCreate);
    return id;
  }

  Package read(long id) throws Exception {
    return rehydrate(repository.get(id));
  }

  Collection<Package> readAll() throws Exception {
    Collection<Package> result = new ArrayList<>();
    for (Package toClone : repository.values()) {
      result.add(rehydrate(toClone));
    }
    return result;
  }

  boolean update(Package toUpdate) {
    return repository.replace(toUpdate.id, toUpdate) != null;
  }

  boolean delete(long id) {
    return repository.remove(id) != null;
  }

  private Package rehydrate(Package toClone) throws Exception {
    if (toClone != null) {
      ByteArrayOutputStream bytesOutStream = new ByteArrayOutputStream();
      ObjectOutputStream objectOutStream = new ObjectOutputStream(bytesOutStream);
      objectOutStream.writeObject(toClone);
      ObjectInputStream objectInStream = new ObjectInputStream(new ByteArrayInputStream(bytesOutStream.toByteArray()));
      return (Package) objectInStream.readObject();
    } else {
      return null;
    }
  }
}

