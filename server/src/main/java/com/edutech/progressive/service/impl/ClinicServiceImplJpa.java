package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;

public class ClinicServiceImplJpa  implements ClinicService
{

    @Override
    public List<Clinic> getAllClinics() {
        // TODO Auto-generated method stub
       List<Clinic> clinics=new ArrayList<>();
       return clinics;
       // throw new UnsupportedOperationException("Unimplemented method 'getAllClinics'");
    }

    @Override
    public Clinic getClinicById(int clinicId) {
        // TODO Auto-generated method stub
       return null;

       // throw new UnsupportedOperationException("Unimplemented method 'getClinicById'");
    }

    @Override
    public Integer addClinic(Clinic clinic) {
        // TODO Auto-generated method stub
       return -1;
       // throw new UnsupportedOperationException("Unimplemented method 'addClinic'");
    }

    @Override
    public void updateClinic(Clinic clinic) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'updateClinic'");
    }

    @Override
    public void deleteClinic(int clinicId) {
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'deleteClinic'");
    }

}