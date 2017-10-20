package com.rci.omega2.ejb.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import com.rci.omega2.ejb.dto.simulation.InstalmentDTO;
import com.rci.omega2.entity.BankEntity;
import com.rci.omega2.entity.CountryEntity;
import com.rci.omega2.entity.CustomerReferenceEntity;
import com.rci.omega2.entity.GuarantorEntity;
import com.rci.omega2.entity.GuarantorReferenceEntity;
import com.rci.omega2.entity.InstalmentEntity;
import com.rci.omega2.entity.ProposalEntity;

public class AppOrderUtils {

    public static ArrayList<GuarantorEntity> ordinateGuarantorList(Set<GuarantorEntity> list) {
        ArrayList<GuarantorEntity> lista = new ArrayList<>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<GuarantorEntity>() {
            @Override
            public int compare(GuarantorEntity ob1, GuarantorEntity ob2) {
                return ob1.getId().compareTo(ob2.getId());
            }
        });
        
        return lista;
    }
    
    public static ArrayList<ProposalEntity> ordinateProposalList(Collection<ProposalEntity> list) {
        ArrayList<ProposalEntity> lista = new ArrayList<>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<ProposalEntity>() {
            @Override
            public int compare(ProposalEntity ob1, ProposalEntity ob2) {
                return ob1.getId().compareTo(ob2.getId());
            }
        });
    
        return lista;
    }
    
    public static ArrayList<InstalmentDTO> ordinateInstalmentDTOList(Collection<InstalmentDTO> list) {
        ArrayList<InstalmentDTO> lista = new ArrayList<>();
        lista.addAll(list);
     
        Collections.sort(lista, new Comparator<InstalmentDTO>() {
            @Override
            public int compare(InstalmentDTO ob1, InstalmentDTO ob2) {
                return ob1.getNumberInstallment().compareTo(ob2.getNumberInstallment());
            }
        });
        
        return lista;
    }    

    public static ArrayList<GuarantorReferenceEntity> ordinateGuarantorReferenceList(Collection<GuarantorReferenceEntity> list) {
        ArrayList<GuarantorReferenceEntity> lista = new ArrayList<>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<GuarantorReferenceEntity>() {
            @Override
            public int compare(GuarantorReferenceEntity ob1, GuarantorReferenceEntity ob2) {
                return ob2.getId().compareTo(ob1.getId());
            }
        });

        return lista;
    }
    
    public static ArrayList<CustomerReferenceEntity> ordinateCustomerReferenceList(Collection<CustomerReferenceEntity> list) {
        ArrayList<CustomerReferenceEntity> lista = new ArrayList<>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<CustomerReferenceEntity>() {
            @Override
            public int compare(CustomerReferenceEntity ob1, CustomerReferenceEntity ob2) {
                return ob2.getId().compareTo(ob1.getId());
            }
        });
        
        return lista;
    }
    
    public static ArrayList<InstalmentEntity> ordinateInstalmentEntityList(Collection<InstalmentEntity> list) {
        ArrayList<InstalmentEntity> lista = new ArrayList<>();
        lista.addAll(list);
     
        Collections.sort(lista, new Comparator<InstalmentEntity>() {
            @Override
            public int compare(InstalmentEntity ob1, InstalmentEntity ob2) {
                return ob1.getInstalment().compareTo(ob2.getInstalment());
            }
        });
        
        return lista;
    }
    
    public static ArrayList<BankEntity> ordinateBankEntity(Collection<BankEntity> list) {
        ArrayList<BankEntity> lista = new ArrayList<BankEntity>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<BankEntity>() {
            @Override
            public int compare(BankEntity ob1, BankEntity ob2) {
                return ob1.getDescription().toUpperCase().compareTo(ob2.getDescription().toUpperCase());
            }
        });
        
        return lista;
    }
    
    public static ArrayList<CountryEntity> ordinateCountryEntity(Collection<CountryEntity> list) {
        ArrayList<CountryEntity> lista = new ArrayList<CountryEntity>();
        lista.addAll(list);
        
        Collections.sort(lista, new Comparator<CountryEntity>() {
            @Override
            public int compare(CountryEntity ob1, CountryEntity ob2) {
                return ob1.getDescription().toUpperCase().compareTo(ob2.getDescription().toUpperCase());
            }
        });
        
        return lista;
    }  
    
}
