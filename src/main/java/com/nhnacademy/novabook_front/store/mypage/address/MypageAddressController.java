package com.nhnacademy.novabook_front.store.mypage.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage/addresses")
@Controller
public class MypageAddressController {

    @GetMapping
    public String getAddressForm() {
        return "/store/mypage/address/address_list";
    }

    @PostMapping("/address/{addressId}/update")
    public String updateAddress(@PathVariable Long addressId) {
        return "";
    }
}
