package edu.eci.arsw.synchdrive.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.arsw.synchdrive.connection.HttpConnectionService;
import edu.eci.arsw.synchdrive.model.App;
import edu.eci.arsw.synchdrive.model.Car;
import edu.eci.arsw.synchdrive.model.Driver;
import edu.eci.arsw.synchdrive.persistence.AppRepository;
import edu.eci.arsw.synchdrive.persistence.CarRepository;
import edu.eci.arsw.synchdrive.persistence.DriverRepository;
import edu.eci.arsw.synchdrive.persistence.SynchdrivePersistenceException;
import edu.eci.arsw.synchdrive.services.DriverServices;

@Service
public class DriverServicesImpl implements DriverServices {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Driver> getAllDrivers() throws SynchdrivePersistenceException {

        return driverRepository.findAll();

    }

    @Override
    public void saveDriver(Driver driver) throws SynchdrivePersistenceException {
        Optional<Driver> optionalDriver = driverRepository.findByEmail(driver.getEmail());
        if (optionalDriver.isPresent()) {
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_ALREDY_EXISTS);
        } else {
            String rawPassword = driver.getPassword();
            String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
            driver.setPassword(encodedPassword);
            driverRepository.save(driver);
        }

    }

    @Override
    public Driver findDriverByEmail(String driver) throws SynchdrivePersistenceException {
        Optional<Driver> optinalDriver = driverRepository.findByEmail(driver);
        if (!optinalDriver.isPresent())
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        return optinalDriver.get();
    }

    @Override
    public void updateApps(String driver, App app) throws SynchdrivePersistenceException {
        Optional<Driver> optinalDriver = driverRepository.findByEmail(driver);
        boolean present = optinalDriver.isPresent();
        if (!present){
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        }else{
            Driver dri = optinalDriver.get();
            app.setDriver(dri);
            appRepository.save(app);
            if (!dri.getApps().isEmpty()){
                List<App> apps = dri.getApps();
                apps.add(app);
                dri.setApps(apps);
            }else{
                List<App> newApp = new ArrayList<>();
                newApp.add(app);
                dri.setApps(newApp);
            }
            driverRepository.save(dri);

        }
        
    }

    @Override
    public List<App> findAppsByEmail(String user) throws SynchdrivePersistenceException {
        Optional<Driver> optinalUser = driverRepository.findByEmail(user);
        boolean present = optinalUser.isPresent();
        System.out.println(present);
        if (!present)
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        return optinalUser.get().getApps();
    }

    @Override
    public List<Car> findCarsByEmail(String user) throws SynchdrivePersistenceException {
        Optional<Driver> optinalUser = driverRepository.findByEmail(user);
        boolean present = optinalUser.isPresent();
        System.out.println(present);
        if (!present)
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        return optinalUser.get().getCars();
    }
    

    @Override
    public void updateDriver(String user, Driver driver) throws SynchdrivePersistenceException, IOException  {
        Optional<Driver> optinalDriver = driverRepository.findByEmail(user);
        boolean present = optinalDriver.isPresent();
        if (!present){
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        }else{
            Driver dri = optinalDriver.get();
            setApps(dri,driver.getApps());
            setCars(dri,driver.getCars());
            dri.setCellPhone(driver.getCellPhone());
            dri.setFirstName(driver.getFirstName());
            dri.setLastName(driver.getLastName());
            dri.setName(driver.getUserName());
            dri.setPassword(driver.getPassword());
            driverRepository.save(dri);
        }
        
    }
    private void setApps(Driver driver, List<App> apps) throws SynchdrivePersistenceException, IOException {
        
        List<App> newApps = new ArrayList<>();
        if (!apps.isEmpty()){

            List<App> currentApps = driver.getApps();
            for (App j: currentApps){
                appRepository.delete(j);
            }
            for (App i : apps) {
                Boolean flag = false;
                if (i.getName().equals("Uber")) {
                    String response = HttpConnectionService.getUberAppDriver(driver.getEmail());
                    System.out.println(response);
                    
                    if (!response.equals("202")) {
                        
                        //throw new SynchdrivePersistenceException(SynchdrivePersistenceException.APP_NOT_FOUND);
                    }
                    flag = true;
                }
                if (true) {
                    i.setDriver(driver);
                    appRepository.save(i);
                    newApps.add(i);
                }
            }
            driver.setApps(newApps);

        }

        

    }

    private void setCars(Driver driver, List<Car> cars) throws SynchdrivePersistenceException{
        Boolean flag = false;
        if (driver.getCars().isEmpty()){
            for (Car i: cars){
                i.setDriver(driver);
                carRepository.save(i);
            }
            driver.setCars(cars);
        }else{
            List<Car> currentCars = driver.getCars();
            for (Car i: cars){
                for (Car j: currentCars){
                    if (j.getPlate().equals(i.getPlate())){
                        flag = true;
                        throw new SynchdrivePersistenceException(SynchdrivePersistenceException.CAR_ALREDY_EXISTS);
                    }
                }
                if(!flag){
                    i.setDriver(driver);
                    carRepository.save(i);
                    currentCars.add(i);
                }
            }
            driver.setCars(currentCars);
        }

    }

    

    @Override
    public void updateCar(String user, Car car) throws SynchdrivePersistenceException {
        Optional<Driver> optinalDriver = driverRepository.findByEmail(user);
        boolean present = optinalDriver.isPresent();
        if (!present){
            throw new SynchdrivePersistenceException(SynchdrivePersistenceException.DRIVER_NOT_FOUND);
        }else{
            Driver dri = optinalDriver.get();
            car.setDriver(dri);
            carRepository.save(car);
            if (!dri.getCars().isEmpty()){
                List<Car> cars = dri.getCars();
                cars.add(car);
                dri.setCars(cars);
            }else{
                List<Car> newCar = new ArrayList<>();
                newCar.add(car);
                dri.setCars(newCar);
            }
            driverRepository.save(dri);

        }
    

    }
}
