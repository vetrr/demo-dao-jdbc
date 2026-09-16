package model;

import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deletByID(Seller id);
    Seller findByID(Integer id);
    List<Seller> findAll();
}
