package com.putusaputra.bazzar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.putusaputra.bazzar.model.PackageRequirement;

@Repository
public interface PackageRequirementRepository extends CrudRepository<PackageRequirement, String> {

}
