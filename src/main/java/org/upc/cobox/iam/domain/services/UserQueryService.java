package org.upc.cobox.iam.domain.services;

import org.upc.cobox.iam.domain.model.aggregates.User;
import org.upc.cobox.iam.domain.model.queries.GetAllUsersQuery;
import org.upc.cobox.iam.domain.model.queries.GetUserByEmailQuery;
import org.upc.cobox.iam.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}