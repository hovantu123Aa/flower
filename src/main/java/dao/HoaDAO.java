/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Hoa;


/**
 *
 * @author Administrator
 */
public class HoaDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public ArrayList<Hoa> getTop10() throws SQLException {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select top 10 * from Hoa order by gia desc";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //Phương thức đọc hoa theo thể loại
    public ArrayList<Hoa> getByCategoryId(int maloai) throws SQLException {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select * from Hoa where maloai=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maloai);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //phuong thuc doc tat ca san pham (Hoa) từ CSDL
    public ArrayList<Hoa> getAll() throws SQLException {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select * from Hoa";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //phuong thuc them mới sản phẩm (Hoa)
    public boolean Insert(Hoa hoa) throws SQLException {
        String sql = "insert into hoa (tenhoa,gia,hinh,maloai,ngaycapnhat) values (?,?,?,?,?)";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, hoa.getTenhoa());
            ps.setDouble(2, hoa.getGia());
            ps.setString(3, hoa.getHinh());
            ps.setInt(4, hoa.getMaloai());
            ps.setDate(5, hoa.getNgaycapnhat());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return false;
    }

    //phuong thuc cập nhật sản phẩm (Hoa)
    public boolean Update(Hoa hoa) throws SQLException {
        String sql = "update hoa set tenhoa=?,gia=?,hinh=?,maloai=?,ngaycapnhat=? where mahoa=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, hoa.getTenhoa());
            ps.setDouble(2, hoa.getGia());
            ps.setString(3, hoa.getHinh());
            ps.setInt(4, hoa.getMaloai());
            ps.setDate(5, hoa.getNgaycapnhat());
            ps.setInt(6, hoa.getMahoa());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return false;
    }

    //phuong thuc xoá sản phẩm (Hoa)
    public boolean Delete(int mahoa) throws SQLException {
        String sql = "delete from hoa where mahoa=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mahoa);
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return false;
    }

    //phuong thuc lấy thông tin sản phẩm (Hoa) theo mã hoa 
    public Hoa getById(int mahoa) throws SQLException {
        Hoa kq = null;
        String sql = "select * from Hoa where mahoa=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mahoa);
            rs = ps.executeQuery();
            if (rs.next()) {
                kq = new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return kq;
    }

    public ArrayList<Hoa> getByPage(int pageIndex, int pageSize) {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select * from Hoa order by mahoa OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi" + ex.toString());
        }
        return ds;
    }

    public static void main(String[] args) throws SQLException {
        HoaDAO hoaDao = new HoaDAO();
        System.out.println("Lay tat ca hoa");
        ArrayList<Hoa> dsHoa = hoaDao.getByPage(2, 5);
        for (Hoa hoa : dsHoa) {
            System.out.println(hoa);
        }

        //tìm hoa theo mahoa=1
        System.out.println("Tim hoa co mahoa=1");
        Hoa kq = hoaDao.getById(1);
        if (kq != null) {
            System.out.println(kq);
        }
    }
}
        