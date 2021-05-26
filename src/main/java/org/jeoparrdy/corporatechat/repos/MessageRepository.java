package org.jeoparrdy.corporatechat.repos;

import org.jeoparrdy.corporatechat.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
