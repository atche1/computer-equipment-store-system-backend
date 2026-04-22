package com.school.ppmg.computer_equipment_store_system_api.services;

import com.school.ppmg.computer_equipment_store_system_api.dtos.user.ChangePasswordRequest;
import com.school.ppmg.computer_equipment_store_system_api.dtos.user.MyAccountResponse;
import com.school.ppmg.computer_equipment_store_system_api.dtos.user.UpdateMyAccountRequest;

public interface UserService {

    MyAccountResponse getMyAccount();

    MyAccountResponse updateMyAccount(UpdateMyAccountRequest request);
    void changePassword(ChangePasswordRequest request);
}