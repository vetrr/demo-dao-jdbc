package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deletByID(Seller id) {

    }

    @Override
    public Seller findByID(Integer id) {
        PreparedStatement st =null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Department dep = iniciateDep(rs);

                Seller sler = iniciateSeller(rs, dep);

                return sler;

            } return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }
    private Department iniciateDep(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
    private Seller iniciateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sler = new Seller();
        sler.setId(rs.getInt("id"));
        sler.setName(rs.getString("name"));
        sler.setEmail(rs.getString("email"));
        sler.setBirthDate(rs.getDate("birthdate"));
        sler.setBaseSalary(rs.getDouble("basesalary"));
        sler.setDepartment(dep);
        return sler;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }


    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement st =null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "+
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY seller.name;");
            st.setInt(1, dep.getId());
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();

            while (rs.next()){
                Department department = iniciateDep(rs);

                Seller sler = iniciateSeller(rs, department);
                sellerList.add(sler);

            } return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
