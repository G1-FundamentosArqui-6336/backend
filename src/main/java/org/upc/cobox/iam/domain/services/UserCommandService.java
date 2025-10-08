package org.upc.cobox.iam.domain.services;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.upc.cobox.iam.domain.model.aggregates.User;
import org.upc.cobox.iam.domain.model.commands.SignInCommand;
import org.upc.cobox.iam.domain.model.commands.SignUpCommand;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
}
