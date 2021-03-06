package goveed20.LiteraryAssociationApplication.controllers;

import goveed20.LiteraryAssociationApplication.dtos.RetailerData;
import goveed20.LiteraryAssociationApplication.exceptions.BadRequestException;
import goveed20.LiteraryAssociationApplication.services.RetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retailer")
@CrossOrigin
public class RetailerController {

    @Autowired
    private RetailerService retailerService;

    @PreAuthorize("hasAuthority('READER') or hasAuthority('WRITER')")
    @GetMapping("/{name}/payment-services")
    public ResponseEntity<?> getPaymentServicesForRetailer(@PathVariable("name") String retailer) {
        try {
            return new ResponseEntity<>(retailerService.getPaymentServicesForRetailer(retailer), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
    @GetMapping("/payment-services")
    public ResponseEntity<?> getAvailableServices() {
        try {
            return new ResponseEntity<>(retailerService.getAvailableServices(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
    @GetMapping("/{serviceName}/registration-fields")
    public ResponseEntity<?> getServiceRegistrationFields(@PathVariable String serviceName) {
        try {
            return new ResponseEntity<>(retailerService.getServiceRegistrationFields(serviceName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
    @PostMapping
    public ResponseEntity<?> registerRetailer(@RequestBody RetailerData retailerData) {
        try {
            return new ResponseEntity<>(retailerService.registerRetailer(retailerData), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
