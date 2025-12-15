package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Entity.Address;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AddressRequestDTO {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String  postalCode;


    public void validateAddressRequest(AddressRequestDTO request)throws Exception{

        if(Helper.isNull(request.getHouseNumber())){
            throw new Exception(Constans.BAD_REQUEST);

        }
        if(Helper.isNull(request.getStreet()) || Helper.isBlank(request.getStreet())){
            throw new Exception(Constans.BAD_REQUEST);
        }
        if(Helper.isNull(request.getCity()) || Helper.isBlank(request.getCity())){
            throw new Exception(Constans.BAD_REQUEST);
        }
        if(Helper.isNull(request.getCountry()) || Helper.isBlank(request.getCountry())){
            throw new Exception(Constans.BAD_REQUEST);
        }
        if(Helper.isNull(request.getPostalCode()) || Helper.isBlank(request.getPostalCode())){
            throw new Exception(Constans.BAD_REQUEST);
        }
    }
    public Address toAddressEntity(AddressRequestDTO addressRequestDTO) {
        if (Helper.isNull(addressRequestDTO)) return null;

        Address address = new Address();
        address.setCity(addressRequestDTO.getCity());
        address.setCountry(addressRequestDTO.getCountry());
        address.setPostalCode(addressRequestDTO.getPostalCode());
        return address;
    }

}
