/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package QLSP;

import dao.HoaDAO;
import dao.LoaiDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Hoa;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class QLSPNewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        response.setContentType("text/html;charset=UTF-8");
        HoaDAO hoaDao=new HoaDAO();
        LoaiDAO loai=new LoaiDAO();
        String action="list";
        if(request.getParameter("action")!=null)
        {
            action=request.getParameter("action");
        }
        switch (action) {
            case "list":
                ArrayList<Hoa> dsHoa=hoaDao.getAll();
                request.setAttribute("dsHoa", dsHoa);
                request.getRequestDispatcher("admin/list_product.jsp").forward(request, response);
                break;
            case "add":
                    if(request.getMethod().equalsIgnoreCase("get"))
                    {
                        request.setAttribute("dsloai", loai.getAll());
                        request.getRequestDispatcher("admin/list_product.jsp").forward(request, response);
                    } else if(request.getMethod().equalsIgnoreCase("post"))
                    {
                        String tenhoa=request.getParameter("tenhoa");
                        double gia=Double.parseDouble("gia");
                        Part part=request.getPart("hinh");
                        int maloai=Integer.parseInt(request.getParameter("maloai"));
                        
                        String realPart=request.getServletContext().getRealPath("assets/images/products");
                        String filename =Paths.get(part.getSubmittedFileName()).getFileName().toString();
                        part.write(realPart+"/"+filename);
                        
                        Hoa objInsert=new Hoa(0, tenhoa, gia, filename, maloai, new Date(new java.util.Date().getTime()));
                        if(hoaDao.Insert(objInsert))
                        {
                            request.setAttribute("Success","Thêm sảm phẩm thành công");
                        }else
                        {
                            request.setAttribute("Success","Thêm sảm phẩm Thất bại ");
                        }
                        request.getRequestDispatcher("QLSPNewServlet?action=list").forward(request, response);
                    }
                    
                break;
                
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
