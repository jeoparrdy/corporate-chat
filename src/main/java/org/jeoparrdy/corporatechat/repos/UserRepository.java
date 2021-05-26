package org.jeoparrdy.corporatechat.repos;

import org.jeoparrdy.corporatechat.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getBySessionId(String sessionId);

}
