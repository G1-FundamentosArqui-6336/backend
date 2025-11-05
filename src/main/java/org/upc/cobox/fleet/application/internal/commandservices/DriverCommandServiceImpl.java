package org.upc.cobox.fleet.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.upc.cobox.fleet.domain.exceptions.DriverNotFoundException;
import org.upc.cobox.fleet.domain.model.aggregates.Driver;
import org.upc.cobox.fleet.domain.model.commands.CreateDriverCommand;
import org.upc.cobox.fleet.domain.services.DriverCommandService;
import org.upc.cobox.fleet.infrastructure.persistence.jpa.repositories.DriverRepository;
import org.upc.cobox.fleet.domain.model.commands.UpdateDriverStatusOnCompletedRouteCommand;

@Service
public class DriverCommandServiceImpl implements DriverCommandService {

    private final DriverRepository driverRepository;
    public DriverCommandServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Long handle(CreateDriverCommand command) {
        var driver = new Driver(command);
        driverRepository.save(driver);
        return driver.getId();
    }

    @Override
    public void handle(UpdateDriverStatusOnCompletedRouteCommand command) {
        driverRepository.findById(command.driverId()).map(driver -> {
            driver.returnFromRoute();
            driverRepository.save(driver);
            return driver.getId();
        }).orElseThrow(() -> new DriverNotFoundException(command.driverId()));
    }
}
