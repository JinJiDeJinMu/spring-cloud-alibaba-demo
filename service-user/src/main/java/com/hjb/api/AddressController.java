package com.hjb.api;

import com.hjb.domain.ReceiveAddress;
import com.hjb.service.ReceiveAddressService;
import com.hjb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/address")
public class AddressController {

    @Autowired
    public ReceiveAddressService receiveAddressService;

    @RequestMapping(value = "/listByUserId", method = RequestMethod.GET)
    List<ReceiveAddress> getReceiveAddressByUserId(@RequestParam("userId") Long userId){
        return receiveAddressService.getUserAddress(userId);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    ReceiveAddress getReceiveAddressById(@RequestParam("id") Long id){
        return receiveAddressService.getById(id);
    }
}
